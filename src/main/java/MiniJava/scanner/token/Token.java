package MiniJava.scanner.token;

import MiniJava.scanner.type.Type;

public class Token {
    public Type type;
    public String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", type.name(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Token) {
            Token temp = (Token) o;
            if (temp.type == this.type) {
                return this.type != Type.KEYWORDS || this.value.equals(temp.value);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = type.hashCode();
        if (type == Type.KEYWORDS) result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    // Delegating to facade
    public static Type getTyepFormString(String s) {
        return TokenTypeResolver.resolve(s);
    }
}
