package jonius7.ftafix.core;

import java.io.*;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.zip.*;

public class JarPatcher {

    public static void stripForestry(File inputJar, File outputJar) throws IOException {
        try (ZipFile zip = new ZipFile(inputJar);
             ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(outputJar.toPath()))) {

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry e = entries.nextElement();

                // Skip forestry/ folder
                if (e.getName().startsWith("forestry/")) {
                    System.out.println("[FTA Fix] Skipping " + e.getName());
                    continue;
                }

                out.putNextEntry(new ZipEntry(e.getName()));
                try (InputStream in = zip.getInputStream(e)) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                }
                out.closeEntry();
            }
        }
    }
}