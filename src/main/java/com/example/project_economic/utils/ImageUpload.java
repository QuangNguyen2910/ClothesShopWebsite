package com.example.project_economic.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "D:\\java\\spring\\Project_Economic\\src\\main\\resources\\static\\images\\Image_product";

    public boolean isUploadImage(MultipartFile file) {
        boolean isUpload = false;
        try {
            Files.copy(
                    file.getInputStream()
                    , Paths.get(UPLOAD_FOLDER+ File.separator, file.getOriginalFilename())
                    , StandardCopyOption.REPLACE_EXISTING);
            isUpload=true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile fileImage){
        boolean isExisted=false;
        try{
            File file =new File(UPLOAD_FOLDER+"\\"+fileImage.getOriginalFilename());
            isExisted=file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExisted;
    }
}
