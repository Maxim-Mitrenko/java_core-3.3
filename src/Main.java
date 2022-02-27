import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        File zip = new File("src\\savegames\\saves.zip");
        File dir = new File("src\\savegames");
        for (File file : openZip(zip, dir)) {
            System.out.println(openProgress(file));
        }
    }

    public static List<File> openZip(File zip, File dir) {
        List<File> files = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis.getNextEntry()){
                File file = new File(dir, entry.getName());
                FileOutputStream fos = new FileOutputStream(file);
                files.add(file);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                fos.close();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return files;
    }

    public static GameProgress openProgress(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }
}