package io.github.justfoxx.yasic;

import io.github.justfoxx.yasic.interpreter.Interpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Main {
    public static Logger logger = LoggerFactory.getLogger("yasic");
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            logger.error("No file specified.");
            return;
        }
        File file = new File(args[0]);
        if(file.exists()) {
            new Interpreter(file);
        } else {
            logger.error("File does not exist.");
        }
    }
}
