package com.app.backbiblioteca.book.service;

import com.app.backbiblioteca.book.dto.response.BookLookupResponse;
import tools.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExternalBookService {

    private final RestClient restClient;
    private static final Pattern YEAR_PATTERN = Pattern.compile("\\b(\\d{4})\\b");

    public ExternalBookService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://openlibrary.org")
                .build();
    }

    public BookLookupResponse lookupByIsbn(String rawIsbn) {
        if (rawIsbn == null || rawIsbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }

        String cleanedIsbn = rawIsbn.replaceAll("[^0-9X]", "");
        String bibKey = "ISBN:" + cleanedIsbn;

        JsonNode root = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/books")
                        .queryParam("bibkeys", bibKey)
                        .queryParam("format", "json")
                        .queryParam("jscmd", "data")
                        .build())
                .retrieve()
                .body(JsonNode.class);

        if (root == null || !root.has(bibKey)) {
            throw new EntityNotFoundException("No book found in external repository for ISBN: " + cleanedIsbn);
        }

        JsonNode data = root.get(bibKey);

        String title = data.hasNonNull("title") ? data.get("title").asText() : null;

        String author = null;
        if (data.has("authors") && data.get("authors").isArray() && !data.get("authors").isEmpty()) {
            author = data.get("authors").get(0).path("name").asText(null);
        }

        String coverUrl = null;
        if (data.has("cover")) {
            JsonNode cover = data.get("cover");
            if (cover.hasNonNull("large")) {
                coverUrl = cover.get("large").asText();
            } else if (cover.hasNonNull("medium")) {
                coverUrl = cover.get("medium").asText();
            } else if (cover.hasNonNull("small")) {
                coverUrl = cover.get("small").asText();
            }
        }

        String publisher = null;
        if (data.has("publishers") && data.get("publishers").isArray() && !data.get("publishers").isEmpty()) {
            publisher = data.get("publishers").get(0).path("name").asText(null);
            if (publisher != null) {
                publisher = publisher.trim();
            }
        }

        Integer pages = data.hasNonNull("number_of_pages") ? data.get("number_of_pages").asInt() : null;

        Date publishedDate = null;
        if (data.hasNonNull("publish_date")) {
            publishedDate = parsePublishedDate(data.get("publish_date").asText());
        }

        return new BookLookupResponse(
                title,
                author,
                coverUrl,
                cleanedIsbn,
                publisher,
                publishedDate,
                pages
        );
    }

    private Date parsePublishedDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }

        String[] formats = {"MMMM d, yyyy", "MMMM yyyy", "yyyy-MM-dd", "yyyy"};
        for (String format : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                sdf.setLenient(false);
                return new Date(sdf.parse(dateStr.trim()).getTime());
            } catch (ParseException ignored) {
            }
        }

        Matcher matcher = YEAR_PATTERN.matcher(dateStr);
        if (matcher.find()) {
            try {
                int year = Integer.parseInt(matcher.group(1));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                return new Date(sdf.parse(String.valueOf(year)).getTime());
            } catch (Exception ignored) {
            }
        }

        return null;
    }
}
