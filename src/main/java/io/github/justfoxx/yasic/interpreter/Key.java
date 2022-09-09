package io.github.justfoxx.yasic.interpreter;

public class Key {
    final KeyType keyType;
    final String value;
    final int line;
    final Object literal;

    public Key(KeyType keyType, String value, Object literal, int line) {
        this.keyType = keyType;
        this.value = value;
        this.line = line;
        this.literal = literal;
    }

    @Override
    public String toString() {
        return keyType + " " + value + " " + literal;
    }
}
