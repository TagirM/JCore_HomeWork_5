// 1. Написать функцию, создающую резервную копию всех файлов в директории (без поддиректорий) во вновь созданную папку ./backup
package ru.gb.jcore.HomeWork5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileWorker {
    public static void main(String[] args) throws IOException {
        direct();
    }

    public static void direct() throws IOException {
        File allFile = new File("/Users/Admin/IdeaProjects/JCore/src/ru/gb/jcore/HomeWork5/originalFolder/");
        for (File f : allFile.listFiles()) {
            if (f.isFile()){
                Files.copy(f.toPath(), new File("/Users/Admin/IdeaProjects/JCore/src/ru/gb/jcore/HomeWork5/backup/" + File.separator + f.getName()).toPath());
            }
        }
    }
}
