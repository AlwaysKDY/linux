package com.example.web_backend.Controller;

import lombok.Data;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Base64;

@Data
public class ImageObjectService {
    private byte[] imageResource;
    private String imageType;
    public ImageObjectService(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        byte[] imageData = Files.readAllBytes(imageFile.toPath());
        this.setImageResource(imageData);
        this.setImageType(CheckImageType(imageFile));
    }
    public ImageObjectService(File imageFile) throws IOException {
        byte[] imageData = Files.readAllBytes(imageFile.toPath());
        this.setImageResource(imageData);
        this.setImageType(CheckImageType(imageFile));
    }
    public ImageObjectService() {
    }
    public String CheckImageType(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        URLConnection connection = imageFile.toURL().openConnection();
        return connection.getContentType();
    }
    public String CheckImageType(File imageFile) throws IOException {
        URLConnection connection = imageFile.toURL().openConnection();
       return connection.getContentType();
    }
    public void copyImage(String originPath,String targetPath) throws IOException {
        File originFile = new File(originPath);
        File targetFile = new File(targetPath);
        FileInputStream fis = new FileInputStream(originFile);
        byte[] imageBytes = IOUtils.toByteArray(fis);
        fis.close();
        Files.write(targetFile.toPath(),imageBytes);
    }
    public ImageObjectService(String imagePath, String fuction2) throws IOException {//备用
        File imageFile = new File(imagePath);
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] imageBytes = IOUtils.toByteArray(fis);
        fis.close();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        this.setImageResource(base64Image.getBytes());
        this.setImageType(CheckImageType(imageFile));
    }
}