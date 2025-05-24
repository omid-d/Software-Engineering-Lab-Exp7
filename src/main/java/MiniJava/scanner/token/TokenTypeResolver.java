package MiniJava.scanner.token;

import MiniJava.scanner.type.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenTypeResolver {

    public static Type resolve(String s) {
        for (Type t : Type.values()) {
            if (t.toString().equals(s)) return t;
        }

        for (Type t : Type.values()) {
            Pattern pattern = Pattern.compile(t.pattern);
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) return t;
        }

        throw new IllegalArgumentException("Unrecognized token: " + s);
    }
}
