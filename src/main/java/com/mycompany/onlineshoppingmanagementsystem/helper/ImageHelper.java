/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.Part;

/**
 *
 * @author Misbahul Haque
 */
public class ImageHelper {

    public ImageHelper() {
    }

    public boolean deleteImage(Path imgPath) {

        try {
            Files.delete(imgPath);
            System.out.println("deleted");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean addImage(Part part, String path) throws IOException {

        FileOutputStream fos = null;

        try {
            
            fos = new FileOutputStream(path);
            InputStream is = part.getInputStream();

            byte data[] = new byte[is.available()];
            is.read(data);
            fos.write(data);
            fos.close();

            return true;
        } catch (Exception e) {
            if (fos != null) {
                fos.close();
            }
            e.printStackTrace();

            return false;
        }

    }
}