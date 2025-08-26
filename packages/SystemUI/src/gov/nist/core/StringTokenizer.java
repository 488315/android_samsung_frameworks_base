package gov.nist.core;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class StringTokenizer {
    public final String buffer;
    public final int bufferLen;
    public int ptr;
    public int savedPtr;

    public StringTokenizer() {
    }

    public static boolean isAlpha(char c) {
        return c <= 127 ? (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') : Character.isLowerCase(c) || Character.isUpperCase(c);
    }

    public static boolean isAlphaDigit(char c) {
        return c <= 127 ? (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c <= '9' && c >= '0') : Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c);
    }

    public static boolean isDigit(char c) {
        return c <= 127 ? c <= '9' && c >= '0' : Character.isDigit(c);
    }

    public static boolean isHexDigit(char c) {
        if (c < 'A' || c > 'F') {
            return (c >= 'a' && c <= 'f') || isDigit(c);
        }
        return true;
    }

    public final void consume(int i) {
        this.ptr += i;
    }

    public final char getNextChar() throws ParseException {
        int i = this.ptr;
        int i2 = this.bufferLen;
        String str = this.buffer;
        if (i >= i2) {
            throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " getNextChar: End of buffer"), this.ptr);
        }
        this.ptr = i + 1;
        return str.charAt(i);
    }

    public final String getNextToken(char c) throws ParseException {
        int i = this.ptr;
        while (true) {
            char cLookAhead = lookAhead(0);
            if (cLookAhead == c) {
                return this.buffer.substring(i, this.ptr);
            }
            if (cLookAhead == 0) {
                throw new ParseException("EOL reached", 0);
            }
            consume(1);
        }
    }

    public final boolean hasMoreChars() {
        return this.ptr < this.bufferLen;
    }

    public final char lookAhead(int i) {
        try {
            return this.buffer.charAt(this.ptr + i);
        } catch (IndexOutOfBoundsException unused) {
            return (char) 0;
        }
    }

    public StringTokenizer(String str) {
        this.buffer = str;
        this.bufferLen = str.length();
        this.ptr = 0;
    }
}
