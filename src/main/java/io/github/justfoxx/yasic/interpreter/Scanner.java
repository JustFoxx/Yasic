package io.github.justfoxx.yasic.interpreter;

import io.github.justfoxx.yasic.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.justfoxx.yasic.interpreter.KeyType.STRING;

class Scanner {
    private final String context;
    private final List<Key> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String context) {
        this.context = context;
    }

    List<Key> scanKeys() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Key(KeyType.EOS, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= context.length();
    }

    private void scanToken() {
        char c = nextChar();
        switch (c) {
            case '+' -> addKey(KeyType.PLUS);
            case '-' -> addKey(KeyType.MINUS);
            case '*' -> addKey(KeyType.STAR);
            case '/' -> addKey(KeyType.SLASH);
            case '.' -> addKey(KeyType.DOT);
            case '=' -> addKey(KeyType.EQUAL);
            case '>' -> addKey(matchNextChar('=')? KeyType.GREATER_EQUAL : KeyType.GREATER);
            case '<' -> addKey(matchNextChar('=')? KeyType.LESS_EQUAL : KeyType.LESS);
            case '#' -> addKey(KeyType.COMMENT);
            case ' ', '\r', '\t' -> {}
            case ':' -> line++;
            case '"' -> string();
            default -> {
                if(isDigit(c)) {
                    number();
                } else if(isAlpha(c)) {
                    identifier();
                } else {
                    ErrorHandler.error(line, "Unexpected character.");
                }
                ErrorHandler.error(line, "Unexpected character.");
            }
        }
    }

    private void number() {
        while (isDigit(peekChar())) nextChar();

        if (peekChar() == '.' && isDigit(peekNextChar())) {
            nextChar();

            while (isDigit(peekChar())) nextChar();
        }

        addKey(KeyType.NUMBER, Double.parseDouble(context.substring(start, current)));
    }

    private void string() {
        while (peekChar() != '"' && !isAtEnd()) {
            if (peekChar() == '\n') line++;
            nextChar();
        }
        if (isAtEnd()) {
            ErrorHandler.error(line, "Unterminated string.");
            return;
        }
        nextChar();
        String value = context.substring(start + 1, current - 1);
        addKey(STRING, value);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void identifier() {
        while (isAlphaNumeric(peekChar())) nextChar();
        String text = context.substring(start, current);
        KeyType type = keywords.get(text);
        if (type == null) type = KeyType.OBJECT;
        addKey(type);
    }

    private boolean matchNextChar(char expected) {
        if (isAtEnd()) return false;
        if (context.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private static final Map<String, KeyType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("AND",    KeyType.AND);
        keywords.put("STRUCT",  KeyType.STRUCT);
        keywords.put("ELSE",   KeyType.ELSE);
        keywords.put("False",  KeyType.FALSE);
        keywords.put("FOR",    KeyType.FOR);
        keywords.put("FUNC",    KeyType.FUNC);
        keywords.put("IF",     KeyType.IF);
        keywords.put("Nothing",    KeyType.NOTHING);
        keywords.put("OR",     KeyType.OR);
        keywords.put("PRINT",  KeyType.PRINT);
        keywords.put("RETURN", KeyType.RETURN);
        keywords.put("True",   KeyType.TRUE);
        keywords.put("LET",    KeyType.LET);
        keywords.put("WHILE",  KeyType.WHILE);
        keywords.put("NOT",    KeyType.NOT);
        keywords.put("IS",     KeyType.IS);
        keywords.put("THEN",   KeyType.THEN);
        keywords.put("END",    KeyType.END);
        keywords.put("BREAK",  KeyType.BREAK);

    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private char nextChar() {
        return context.charAt(current++);
    }

    private char peekChar() {
        if (isAtEnd()) return '\0';
        return context.charAt(current);
    }

    private char peekNextChar() {
        if (current + 1 >= context.length()) return '\0';
        return context.charAt(current + 1);
    }

    private void addKey(KeyType keyType) {
        addKey(keyType, null);
    }

    private void addKey(KeyType keyType, Object literal) {
        String string = context.substring(start, current);
        tokens.add(new Key(keyType, string, literal, line));
    }
}
