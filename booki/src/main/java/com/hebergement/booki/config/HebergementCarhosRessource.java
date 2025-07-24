package com.hebergement.booki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //  Indique que cette classe contient la configuration Spring
public class HebergementCarhosRessource implements WebMvcConfigurer {

    //  Injection de la valeur  de l'url définie dans application.properties :
    // Exemple : upload.path=T:/IUS/projet-de-soutenace-bts/images_upload
    @Value("${upload.path}")
    private String uploadPath;

    //  Cette méthode permet à Spring Boot de savoir comment accéder à des fichiers physiques (ex: images)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //L'URL /images_upload/** (ex: /images_upload/nom_photo.jpg) sera reliée
        //     au dossier situé physiquement dans uploadPath (ex: T:/IUS/projet.../images_upload/)
        registry.addResourceHandler("/images_upload/**") // chemin virtuel dans l'URL
                .addResourceLocations("file:///" + uploadPath + "/"); // chemin réel sur ton disque

        // Très important : le chemin doit finir par "/" pour que Spring sache naviguer dans le dossier
        //    "file:///" est nécessaire pour que le système comprenne que c’est un chemin local (disque)
    }
}

