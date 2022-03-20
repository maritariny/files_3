package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {

        // 1. Произвести распаковку архива в папке savegames
        File dirSavegames = new File("D://Games//savegames");
        openZip(dirSavegames.getPath() + "\\output.zip", dirSavegames.getPath());

        // 2. Произвести считывание и десериализацию одного из разархивированных файлов save.dat
        GameProgress gp = openProgress(dirSavegames.getPath() + "\\save1.dat");

        // 3. Вывести в консоль состояние сохранненой игры
        System.out.println(gp);
    }

    public static boolean openZip(String zipFileFullName, String path) {

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFileFullName))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = path + "\\" + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
                System.out.println("Распакован файл " + name);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static GameProgress openProgress(String fileFullName) {
        GameProgress gp = null;
        try (FileInputStream fis = new FileInputStream(fileFullName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gp = (GameProgress) ois.readObject();
            System.out.println("Десериализован файл " + fileFullName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return gp;
        }
        return gp;
    }
}
