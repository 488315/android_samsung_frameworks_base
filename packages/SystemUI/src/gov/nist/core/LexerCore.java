package gov.nist.core;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import gov.nist.javax.sip.Utils;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Locale;

/* loaded from: classes4.dex */
public class LexerCore extends StringTokenizer {
    public static final Hashtable globalSymbolTable = new Hashtable();
    public static final Hashtable lexerTables = new Hashtable();
    public Hashtable currentLexer;
    public Token currentMatch;

    public LexerCore() {
        this.currentLexer = new Hashtable();
    }

    public static final boolean isTokenChar(char c) {
        return StringTokenizer.isAlphaDigit(c) || c == '!' || c == '%' || c == '\'' || c == '~' || c == '*' || c == '+' || c == '-' || c == '.' || c == '_' || c == '`';
    }

    public final void SPorHT() {
        try {
            char cLookAhead = lookAhead(0);
            while (true) {
                if (cLookAhead != ' ' && cLookAhead != '\t') {
                    return;
                }
                consume(1);
                cLookAhead = lookAhead(0);
            }
        } catch (ParseException unused) {
        }
    }

    public final void addKeyword(int i, String str) {
        char[] cArr = Utils.toHex;
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        Integer numValueOf = Integer.valueOf(i);
        this.currentLexer.put(upperCase, numValueOf);
        Hashtable hashtable = globalSymbolTable;
        if (hashtable.containsKey(numValueOf)) {
            return;
        }
        hashtable.put(numValueOf, upperCase);
    }

    public final String byteStringNoSemicolon() {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            try {
                char cLookAhead = lookAhead(0);
                if (cLookAhead == 0 || cLookAhead == '\n' || cLookAhead == ';' || cLookAhead == ',') {
                    break;
                }
                consume(1);
                stringBuffer.append(cLookAhead);
            } catch (ParseException unused) {
                return stringBuffer.toString();
            }
        }
        return stringBuffer.toString();
    }

    public final String byteStringNoSlash() {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            try {
                char cLookAhead = lookAhead(0);
                if (cLookAhead == 0 || cLookAhead == '\n' || cLookAhead == '/') {
                    break;
                }
                consume(1);
                stringBuffer.append(cLookAhead);
            } catch (ParseException unused) {
                return stringBuffer.toString();
            }
        }
        return stringBuffer.toString();
    }

    public final String comment() throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        if (lookAhead(0) != '(') {
            return null;
        }
        consume(1);
        while (true) {
            char nextChar = getNextChar();
            if (nextChar == ')') {
                return stringBuffer.toString();
            }
            String str = this.buffer;
            if (nextChar == 0) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " :unexpected EOL"), this.ptr);
            }
            if (nextChar == '\\') {
                stringBuffer.append(nextChar);
                char nextChar2 = getNextChar();
                if (nextChar2 == 0) {
                    throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " : unexpected EOL"), this.ptr);
                }
                stringBuffer.append(nextChar2);
            } else {
                stringBuffer.append(nextChar);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0032 A[Catch: ParseException -> 0x0036, LOOP:0: B:24:0x0001->B:20:0x0032, LOOP_END, TRY_LEAVE, TryCatch #0 {ParseException -> 0x0036, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:9:0x0013, B:18:0x002d, B:13:0x001c, B:14:0x0021, B:15:0x0026, B:20:0x0032), top: B:24:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0036 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void consumeValidChars(char[] cArr) {
        while (hasMoreChars()) {
            try {
                char cLookAhead = lookAhead(0);
                boolean zIsAlphaDigit = false;
                for (char c : cArr) {
                    switch (c) {
                        case 65533:
                            zIsAlphaDigit = StringTokenizer.isAlphaDigit(cLookAhead);
                            break;
                        case 65534:
                            zIsAlphaDigit = StringTokenizer.isDigit(cLookAhead);
                            break;
                        case CustomDeviceManager.QUICK_PANEL_ALL /* 65535 */:
                            zIsAlphaDigit = StringTokenizer.isAlpha(cLookAhead);
                            break;
                        default:
                            if (cLookAhead == c) {
                                zIsAlphaDigit = true;
                                break;
                            } else {
                                zIsAlphaDigit = false;
                                break;
                            }
                    }
                    if (zIsAlphaDigit) {
                        if (zIsAlphaDigit) {
                            return;
                        } else {
                            consume(1);
                        }
                    }
                }
                if (zIsAlphaDigit) {
                }
            } catch (ParseException unused) {
                return;
            }
        }
    }

    public final String getRest() {
        int i = this.ptr;
        String str = this.buffer;
        if (i >= str.length()) {
            return null;
        }
        return str.substring(this.ptr);
    }

    public final String getString() throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            char cLookAhead = lookAhead(0);
            String str = this.buffer;
            if (cLookAhead == 0) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "unexpected EOL"), this.ptr);
            }
            if (cLookAhead == '/') {
                consume(1);
                return stringBuffer.toString();
            }
            if (cLookAhead == '\\') {
                consume(1);
                char cLookAhead2 = lookAhead(0);
                if (cLookAhead2 == 0) {
                    throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "unexpected EOL"), this.ptr);
                }
                consume(1);
                stringBuffer.append(cLookAhead2);
            } else {
                consume(1);
                stringBuffer.append(cLookAhead);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0078 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Token match(int i) throws ParseException {
        char cLookAhead;
        String str = this.buffer;
        boolean zIsTokenChar = false;
        if (i <= 2048 || i >= 4096) {
            if (i > 4096) {
                char cLookAhead2 = lookAhead(0);
                if (i == 4098) {
                    if (!StringTokenizer.isDigit(cLookAhead2)) {
                        throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\nExpecting DIGIT"), this.ptr);
                    }
                    Token token = new Token();
                    this.currentMatch = token;
                    token.tokenValue = String.valueOf(cLookAhead2);
                    this.currentMatch.tokenType = i;
                    consume(1);
                } else if (i == 4099) {
                    if (!StringTokenizer.isAlpha(cLookAhead2)) {
                        throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\nExpecting ALPHA"), this.ptr);
                    }
                    Token token2 = new Token();
                    this.currentMatch = token2;
                    token2.tokenValue = String.valueOf(cLookAhead2);
                    this.currentMatch.tokenType = i;
                    consume(1);
                }
            } else {
                char c = (char) i;
                char cLookAhead3 = lookAhead(0);
                if (cLookAhead3 != c) {
                    throw new ParseException(str + "\nExpecting  >>>" + c + "<<< got >>>" + cLookAhead3 + "<<<", this.ptr);
                }
                consume(1);
            }
        } else if (i == 4095) {
            try {
                zIsTokenChar = isTokenChar(lookAhead(0));
            } catch (ParseException unused) {
            }
            if (!zIsTokenChar) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\nID expected"), this.ptr);
            }
            String strTtoken = ttoken();
            Token token3 = new Token();
            this.currentMatch = token3;
            token3.tokenValue = strTtoken;
            token3.tokenType = 4095;
        } else if (i == 4094) {
            try {
                cLookAhead = lookAhead(0);
            } catch (ParseException unused2) {
            }
            if (!StringTokenizer.isAlphaDigit(cLookAhead) && cLookAhead != '\'' && cLookAhead != '=' && cLookAhead != '[' && cLookAhead != '*' && cLookAhead != '+' && cLookAhead != ':' && cLookAhead != ';' && cLookAhead != '?' && cLookAhead != '@') {
                switch (cLookAhead) {
                    default:
                        switch (cLookAhead) {
                            default:
                                switch (cLookAhead) {
                                    default:
                                        switch (cLookAhead) {
                                            case '{':
                                            case '|':
                                            case '}':
                                            case '~':
                                                break;
                                            default:
                                                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\nID expected"), this.ptr);
                                        }
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                        String strTtokenSafe = ttokenSafe();
                                        Token token4 = new Token();
                                        this.currentMatch = token4;
                                        token4.tokenValue = strTtokenSafe;
                                        token4.tokenType = 4094;
                                        break;
                                }
                            case '-':
                            case '.':
                            case '/':
                                break;
                        }
                    case '!':
                    case '\"':
                    case '#':
                    case '$':
                    case '%':
                        break;
                }
            }
            String strTtokenSafe2 = ttokenSafe();
            Token token42 = new Token();
            this.currentMatch = token42;
            token42.tokenValue = strTtokenSafe2;
            token42.tokenType = 4094;
        } else {
            String strTtoken2 = ttoken();
            Hashtable hashtable = this.currentLexer;
            char[] cArr = Utils.toHex;
            Integer num = (Integer) hashtable.get(strTtoken2.toUpperCase(Locale.ENGLISH));
            if (num == null || num.intValue() != i) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\nUnexpected Token : ", strTtoken2), this.ptr);
            }
            Token token5 = new Token();
            this.currentMatch = token5;
            token5.tokenValue = strTtoken2;
            token5.tokenType = i;
        }
        return this.currentMatch;
    }

    public final String number() throws ParseException {
        String str = this.buffer;
        int i = this.ptr;
        try {
            if (StringTokenizer.isDigit(lookAhead(0))) {
                consume(1);
                while (StringTokenizer.isDigit(lookAhead(0))) {
                    consume(1);
                }
                return str.substring(i, this.ptr);
            }
            throw new ParseException(str + ": Unexpected token at " + lookAhead(0), this.ptr);
        } catch (ParseException unused) {
            return str.substring(i, this.ptr);
        }
    }

    public final Token[] peekNextToken(int i) throws ParseException {
        boolean zIsTokenChar;
        int i2 = this.ptr;
        Token[] tokenArr = new Token[i];
        for (int i3 = 0; i3 < i; i3++) {
            Token token = new Token();
            try {
                zIsTokenChar = isTokenChar(lookAhead(0));
            } catch (ParseException unused) {
                zIsTokenChar = false;
            }
            if (zIsTokenChar) {
                String strTtoken = ttoken();
                token.tokenValue = strTtoken;
                char[] cArr = Utils.toHex;
                String upperCase = strTtoken.toUpperCase(Locale.ENGLISH);
                if (this.currentLexer.containsKey(upperCase)) {
                    token.tokenType = ((Integer) this.currentLexer.get(upperCase)).intValue();
                } else {
                    token.tokenType = 4095;
                }
            } else {
                char nextChar = getNextChar();
                token.tokenValue = String.valueOf(nextChar);
                if (StringTokenizer.isAlpha(nextChar)) {
                    token.tokenType = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_PERMISSION_NOT_ALLOWED;
                } else if (StringTokenizer.isDigit(nextChar)) {
                    token.tokenType = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL;
                } else {
                    token.tokenType = nextChar;
                }
            }
            tokenArr[i3] = token;
        }
        this.savedPtr = this.ptr;
        this.ptr = i2;
        return tokenArr;
    }

    public final String quotedString() throws ParseException {
        int i = this.ptr + 1;
        if (lookAhead(0) != '\"') {
            return null;
        }
        consume(1);
        while (true) {
            char nextChar = getNextChar();
            String str = this.buffer;
            if (nextChar == '\"') {
                return str.substring(i, this.ptr - 1);
            }
            if (nextChar == 0) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " :unexpected EOL"), this.ptr);
            }
            if (nextChar == '\\') {
                consume(1);
            }
        }
    }

    public final String ttoken() {
        int i = this.ptr;
        while (hasMoreChars() && isTokenChar(lookAhead(0))) {
            try {
                consume(1);
            } catch (ParseException unused) {
                return null;
            }
        }
        return this.buffer.substring(i, this.ptr);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0045 A[Catch: ParseException -> 0x0052, FALL_THROUGH, TryCatch #0 {ParseException -> 0x0052, blocks: (B:3:0x0002, B:5:0x0008, B:7:0x0014, B:24:0x0038, B:25:0x003b, B:26:0x003e, B:27:0x0041, B:29:0x0045, B:30:0x0049), top: B:34:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String ttokenSafe() {
        int i = this.ptr;
        while (hasMoreChars()) {
            try {
                char cLookAhead = lookAhead(0);
                if (StringTokenizer.isAlphaDigit(cLookAhead)) {
                    consume(1);
                } else {
                    if (cLookAhead != '\'' && cLookAhead != '[' && cLookAhead != '*' && cLookAhead != '+' && cLookAhead != ':' && cLookAhead != ';' && cLookAhead != '?' && cLookAhead != '@') {
                        switch (cLookAhead) {
                            default:
                                switch (cLookAhead) {
                                    default:
                                        switch (cLookAhead) {
                                            default:
                                                switch (cLookAhead) {
                                                    case '{':
                                                    case '|':
                                                    case '}':
                                                    case '~':
                                                        break;
                                                    default:
                                                        return this.buffer.substring(i, this.ptr);
                                                }
                                            case ']':
                                            case '^':
                                            case '_':
                                            case '`':
                                                consume(1);
                                                break;
                                        }
                                    case '-':
                                    case '.':
                                    case '/':
                                        break;
                                }
                            case '!':
                            case '\"':
                            case '#':
                            case '$':
                            case '%':
                                break;
                        }
                    }
                    consume(1);
                }
            } catch (ParseException unused) {
                return null;
            }
        }
        return this.buffer.substring(i, this.ptr);
    }

    public LexerCore(String str, String str2) {
        super(str2);
    }

    public void selectLexer(String str) {
    }
}
