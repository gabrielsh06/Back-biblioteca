package com.app.backbiblioteca;

import com.app.backbiblioteca.service636.s1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BackBibliotecaApplication {

    static void main() {
        SpringApplication.run(BackBibliotecaApplication.class);
    }

}
