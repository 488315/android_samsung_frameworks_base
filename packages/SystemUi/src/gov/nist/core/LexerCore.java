package gov.nist.core;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import gov.nist.javax.sip.Utils;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
            char lookAhead = lookAhead(0);
            while (true) {
                if (lookAhead != ' ' && lookAhead != '\t') {
                    return;
                }
                consume(1);
                lookAhead = lookAhead(0);
            }
        } catch (ParseException unused) {
        }
    }

    public final void addKeyword(int i, String str) {
        char[] cArr = Utils.toHex;
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        Integer valueOf = Integer.valueOf(i);
        this.currentLexer.put(upperCase, valueOf);
        Hashtable hashtable = globalSymbolTable;
        if (hashtable.containsKey(valueOf)) {
            return;
        }
        hashtable.put(valueOf, upperCase);
    }

    public final String byteStringNoSemicolon() {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            try {
                char lookAhead = lookAhead(0);
                if (lookAhead == 0 || lookAhead == '\n' || lookAhead == ';' || lookAhead == ',') {
                    break;
                }
                consume(1);
                stringBuffer.append(lookAhead);
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
                char lookAhead = lookAhead(0);
                if (lookAhead == 0 || lookAhead == '\n' || lookAhead == '/') {
                    break;
                }
                consume(1);
                stringBuffer.append(lookAhead);
            } catch (ParseException unused) {
                return stringBuffer.toString();
            }
        }
        return stringBuffer.toString();
    }

    public final String comment() {
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
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, " :unexpected EOL"), this.ptr);
            }
            if (nextChar == '\\') {
                stringBuffer.append(nextChar);
                char nextChar2 = getNextChar();
                if (nextChar2 == 0) {
                    throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, " : unexpected EOL"), this.ptr);
                }
                stringBuffer.append(nextChar2);
            } else {
                stringBuffer.append(nextChar);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0032 A[Catch: ParseException -> 0x0036, LOOP:0: B:2:0x0001->B:17:0x0032, LOOP_END, TRY_LEAVE, TryCatch #0 {ParseException -> 0x0036, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:9:0x0013, B:13:0x002d, B:22:0x001a, B:23:0x001f, B:24:0x0024, B:17:0x0032), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0036 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void consumeValidChars(char[] cArr) {
        while (hasMoreChars()) {
            try {
                char lookAhead = lookAhead(0);
                boolean z = false;
                for (char c : cArr) {
                    switch (c) {
                        case 65533:
                            z = StringTokenizer.isAlphaDigit(lookAhead);
                            break;
                        case 65534:
                            z = StringTokenizer.isDigit(lookAhead);
                            break;
                        case CustomDeviceManager.QUICK_PANEL_ALL /* 65535 */:
                            z = StringTokenizer.isAlpha(lookAhead);
                            break;
                        default:
                            if (lookAhead == c) {
                                z = true;
                                break;
                            } else {
                                z = false;
                                break;
                            }
                    }
                    if (z) {
                        if (z) {
                            return;
                        } else {
                            consume(1);
                        }
                    }
                }
                if (z) {
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

    public final String getString() {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            char lookAhead = lookAhead(0);
            String str = this.buffer;
            if (lookAhead == 0) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "unexpected EOL"), this.ptr);
            }
            if (lookAhead == '/') {
                consume(1);
                return stringBuffer.toString();
            }
            if (lookAhead == '\\') {
                consume(1);
                char lookAhead2 = lookAhead(0);
                if (lookAhead2 == 0) {
                    throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "unexpected EOL"), this.ptr);
                }
                consume(1);
                stringBuffer.append(lookAhead2);
            } else {
                consume(1);
                stringBuffer.append(lookAhead);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0079 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Token match(int i) {
        char lookAhead;
        boolean z = false;
        String str = this.buffer;
        if (i <= 2048 || i >= 4096) {
            if (i > 4096) {
                char lookAhead2 = lookAhead(0);
                if (i == 4098) {
                    if (!StringTokenizer.isDigit(lookAhead2)) {
                        throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "\nExpecting DIGIT"), this.ptr);
                    }
                    Token token = new Token();
                    this.currentMatch = token;
                    token.tokenValue = String.valueOf(lookAhead2);
                    this.currentMatch.tokenType = i;
                    consume(1);
                } else if (i == 4099) {
                    if (!StringTokenizer.isAlpha(lookAhead2)) {
                        throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "\nExpecting ALPHA"), this.ptr);
                    }
                    Token token2 = new Token();
                    this.currentMatch = token2;
                    token2.tokenValue = String.valueOf(lookAhead2);
                    this.currentMatch.tokenType = i;
                    consume(1);
                }
            } else {
                char c = (char) i;
                char lookAhead3 = lookAhead(0);
                if (lookAhead3 != c) {
                    throw new ParseException(str + "\nExpecting  >>>" + c + "<<< got >>>" + lookAhead3 + "<<<", this.ptr);
                }
                consume(1);
            }
        } else if (i == 4095) {
            try {
                z = isTokenChar(lookAhead(0));
            } catch (ParseException unused) {
            }
            if (!z) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "\nID expected"), this.ptr);
            }
            String ttoken = ttoken();
            Token token3 = new Token();
            this.currentMatch = token3;
            token3.tokenValue = ttoken;
            token3.tokenType = 4095;
        } else if (i == 4094) {
            try {
                lookAhead = lookAhead(0);
            } catch (ParseException unused2) {
            }
            if (!StringTokenizer.isAlphaDigit(lookAhead) && lookAhead != '\'' && lookAhead != '=' && lookAhead != '[' && lookAhead != '*' && lookAhead != '+' && lookAhead != ':' && lookAhead != ';' && lookAhead != '?' && lookAhead != '@') {
                switch (lookAhead) {
                    default:
                        switch (lookAhead) {
                            default:
                                switch (lookAhead) {
                                    default:
                                        switch (lookAhead) {
                                        }
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                        z = true;
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
                if (z) {
                    throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "\nID expected"), this.ptr);
                }
                String ttokenSafe = ttokenSafe();
                Token token4 = new Token();
                this.currentMatch = token4;
                token4.tokenValue = ttokenSafe;
                token4.tokenType = 4094;
            }
            z = true;
            if (z) {
            }
        } else {
            String ttoken2 = ttoken();
            Hashtable hashtable = this.currentLexer;
            char[] cArr = Utils.toHex;
            Integer num = (Integer) hashtable.get(ttoken2.toUpperCase(Locale.ENGLISH));
            if (num == null || num.intValue() != i) {
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, "\nUnexpected Token : ", ttoken2), this.ptr);
            }
            Token token5 = new Token();
            this.currentMatch = token5;
            token5.tokenValue = ttoken2;
            token5.tokenType = i;
        }
        return this.currentMatch;
    }

    public final String number() {
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

    public final Token[] peekNextToken(int i) {
        boolean z;
        int i2 = this.ptr;
        Token[] tokenArr = new Token[i];
        for (int i3 = 0; i3 < i; i3++) {
            Token token = new Token();
            try {
                z = isTokenChar(lookAhead(0));
            } catch (ParseException unused) {
                z = false;
            }
            if (z) {
                String ttoken = ttoken();
                token.tokenValue = ttoken;
                char[] cArr = Utils.toHex;
                String upperCase = ttoken.toUpperCase(Locale.ENGLISH);
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

    public final String quotedString() {
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
                throw new ParseException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, " :unexpected EOL"), this.ptr);
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

    /* JADX WARN: Removed duplicated region for block: B:30:0x0048 A[Catch: ParseException -> 0x0055, TryCatch #0 {ParseException -> 0x0055, blocks: (B:3:0x0002, B:5:0x0008, B:40:0x0014, B:24:0x0038, B:25:0x003b, B:26:0x003e, B:27:0x0041, B:30:0x0048, B:34:0x004c), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x004c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0045 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String ttokenSafe() {
        int i = this.ptr;
        while (hasMoreChars()) {
            try {
                boolean z = false;
                char lookAhead = lookAhead(0);
                if (StringTokenizer.isAlphaDigit(lookAhead)) {
                    consume(1);
                } else {
                    if (lookAhead != '\'' && lookAhead != '[' && lookAhead != '*' && lookAhead != '+' && lookAhead != ':' && lookAhead != ';' && lookAhead != '?' && lookAhead != '@') {
                        switch (lookAhead) {
                            default:
                                switch (lookAhead) {
                                    default:
                                        switch (lookAhead) {
                                            default:
                                                switch (lookAhead) {
                                                }
                                            case ']':
                                            case '^':
                                            case '_':
                                            case '`':
                                                z = true;
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
                        if (z) {
                            return this.buffer.substring(i, this.ptr);
                        }
                        consume(1);
                    }
                    z = true;
                    if (z) {
                    }
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
