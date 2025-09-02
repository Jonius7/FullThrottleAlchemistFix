package jonius7.ftafix.core;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JarPatcher {

    private static final Logger LOGGER = LogManager.getLogger("FtaFix");

    public static void stripForestry(File inputJar, File outputJar) throws IOException {
        try (ZipFile zip = new ZipFile(inputJar);
             ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(outputJar.toPath()))) {

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                // Skip forestry folder
                if (entry.getName().startsWith("forestry/")) {
                    LOGGER.info("Skipping {}", entry.getName());
                    continue;
                }

                out.putNextEntry(new ZipEntry(entry.getName()));
                try (InputStream in = zip.getInputStream(entry)) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                }
                out.closeEntry();
            }
            LOGGER.info("Finished patching FullThrottle Alchemist jar.");
        }
    }
}
