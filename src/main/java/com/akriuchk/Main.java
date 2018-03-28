package com.akriuchk;

import com.akriuchk.configuration.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext javaConfigContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }
}
