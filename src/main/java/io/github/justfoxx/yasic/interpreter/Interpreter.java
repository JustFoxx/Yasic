package io.github.justfoxx.yasic.interpreter;

import io.github.justfoxx.yasic.Main;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {
    private File file;
    public Interpreter(File file) throws IOException {
        this.file = file;
        getFile();
    }

    private void scanFile(String chars) throws IOException {
        Scanner scanner = new Scanner(chars);
        List<Key> keys = scanner.scanKeys();
        for(Key key : keys) {
            Main.logger.info(key.toString());
        }
    }
    private void getFile() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        scanFile(new String(bytes, Charset.defaultCharset()));
    }
}
