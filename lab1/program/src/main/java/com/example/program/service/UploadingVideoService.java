package com.example.program.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadingVideoService {

    @Value("${upload.path}")
    private String uploadPath;

    public void upload(MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                throw new IOException("Failed to store empty file.");
            }
            Path destinationFile = Paths.get(uploadPath).resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(Paths.get(uploadPath).toAbsolutePath())) {
                // This is a security check
                throw new IOException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new IOException("Failed to store file.", e);
        }
    }
}
