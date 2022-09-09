package io.github.justfoxx.yasic.interpreter;

public enum KeyType {
    //keywords
    PRINT, RETURN, NOT, IS,
    THEN, END, LET, FUNC,
    IF, ELSE, WHILE, FOR,
    BREAK, STRUCT, NOTHING,
    TRUE, FALSE, AND, OR,
    //one/two character
    PLUS, COLON, EQUAL, MINUS, COMMENT,
    DOT, SLASH, STAR, GREATER,
    GREATER_EQUAL, LESS, LESS_EQUAL,
    //literal
    STRING, NUMBER, OBJECT,

    EOS
}
