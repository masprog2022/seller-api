package com.masprogtech.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "C:/uploads/";

    public String saveImage(MultipartFile file) {
        try {
            // Gera um nome único para a imagem
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);

            // Cria o diretório caso não exista
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Salva a imagem na pasta local
            file.transferTo(path.toFile());

            // Retorna a URL da imagem
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
    }
}
