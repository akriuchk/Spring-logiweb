package com.akriuchk;

import com.akriuchk.configuration.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

    public static void main(String[] args) {
//resourceHandlerMapping
        AnnotationConfigApplicationContext javaConfigContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        //        javaConfigContext.close();
    }
}
