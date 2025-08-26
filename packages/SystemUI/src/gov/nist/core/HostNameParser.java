package gov.nist.core;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class HostNameParser extends ParserCore {
    public static final char[] VALID_DOMAIN_LABEL_CHAR = {65533, '-', '.'};
    public final boolean stripAddressScopeZones;

    public HostNameParser(String str) {
        this.stripAddressScopeZones = false;
        this.lexer = new LexerCore("charLexer", str);
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x01bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final HostPort hostPort(boolean z) throws ParseException {
        String strSubstring;
        char cLookAhead;
        int iIndexOf;
        boolean z2 = this.stripAddressScopeZones;
        if (this.lexer.lookAhead(0) == '[') {
            StringBuffer stringBuffer = new StringBuffer();
            if (!z2) {
                while (true) {
                    if (!this.lexer.hasMoreChars()) {
                        break;
                    }
                    char cLookAhead2 = this.lexer.lookAhead(0);
                    if (StringTokenizer.isHexDigit(cLookAhead2) || cLookAhead2 == '.' || cLookAhead2 == ':' || cLookAhead2 == '[') {
                        this.lexer.consume(1);
                        stringBuffer.append(cLookAhead2);
                    } else if (cLookAhead2 == ']') {
                        this.lexer.consume(1);
                        stringBuffer.append(cLookAhead2);
                        strSubstring = stringBuffer.toString();
                    }
                }
                throw new ParseException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.lexer.buffer, ": Illegal Host name "), this.lexer.ptr);
            }
            while (true) {
                if (!this.lexer.hasMoreChars()) {
                    break;
                }
                char cLookAhead3 = this.lexer.lookAhead(0);
                if (StringTokenizer.isHexDigit(cLookAhead3) || cLookAhead3 == '.' || cLookAhead3 == ':' || cLookAhead3 == '[') {
                    this.lexer.consume(1);
                    stringBuffer.append(cLookAhead3);
                } else if (cLookAhead3 == ']') {
                    this.lexer.consume(1);
                    stringBuffer.append(cLookAhead3);
                    strSubstring = stringBuffer.toString();
                } else if (cLookAhead3 == '%') {
                    this.lexer.consume(1);
                    String rest = this.lexer.getRest();
                    if (rest != null && rest.length() != 0 && (iIndexOf = rest.indexOf(93)) != -1) {
                        this.lexer.consume(iIndexOf + 1);
                        stringBuffer.append("]");
                        strSubstring = stringBuffer.toString();
                    }
                }
            }
            throw new ParseException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.lexer.buffer, ": Illegal Host name "), this.lexer.ptr);
        }
        String rest2 = this.lexer.getRest();
        int iIndexOf2 = rest2.indexOf(63);
        int iIndexOf3 = rest2.indexOf(59);
        if (iIndexOf2 == -1 || (iIndexOf3 != -1 && iIndexOf2 > iIndexOf3)) {
            iIndexOf2 = iIndexOf3;
        }
        if (iIndexOf2 == -1) {
            iIndexOf2 = rest2.length();
        }
        String strSubstring2 = rest2.substring(0, iIndexOf2);
        int iIndexOf4 = strSubstring2.indexOf(58);
        if (iIndexOf4 == -1 || strSubstring2.indexOf(58, iIndexOf4 + 1) == -1) {
            LexerCore lexerCore = this.lexer;
            int i = lexerCore.ptr;
            lexerCore.consumeValidChars(VALID_DOMAIN_LABEL_CHAR);
            LexerCore lexerCore2 = this.lexer;
            strSubstring = lexerCore2.buffer.substring(i, lexerCore2.ptr);
        } else {
            LexerCore lexerCore3 = this.lexer;
            int i2 = lexerCore3.ptr;
            lexerCore3.consumeValidChars(new char[]{65533, ':'});
            StringBuffer stringBuffer2 = new StringBuffer("[");
            LexerCore lexerCore4 = this.lexer;
            stringBuffer2.append(lexerCore4.buffer.substring(i2, lexerCore4.ptr));
            stringBuffer2.append("]");
            strSubstring = stringBuffer2.toString();
        }
        if (strSubstring.length() == 0) {
            throw new ParseException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.lexer.buffer, ": Missing host name"), this.lexer.ptr);
        }
        Host host = new Host(strSubstring);
        HostPort hostPort = new HostPort();
        hostPort.host = host;
        if (z) {
            this.lexer.SPorHT();
        }
        if (this.lexer.hasMoreChars() && (cLookAhead = this.lexer.lookAhead(0)) != '\t' && cLookAhead != '\n' && cLookAhead != '\r' && cLookAhead != ' ') {
            if (cLookAhead != '%') {
                if (cLookAhead != ',' && cLookAhead != '/') {
                    if (cLookAhead == ':') {
                        this.lexer.consume(1);
                        if (z) {
                            this.lexer.SPorHT();
                        }
                        try {
                            hostPort.port = Integer.parseInt(this.lexer.number());
                            return hostPort;
                        } catch (NumberFormatException unused) {
                            throw new ParseException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.lexer.buffer, " :Error parsing port "), this.lexer.ptr);
                        }
                    }
                    if (cLookAhead != ';' && cLookAhead != '>' && cLookAhead != '?') {
                        if (!z) {
                            throw new ParseException(this.lexer.buffer + " Illegal character in hostname:" + this.lexer.lookAhead(0), this.lexer.ptr);
                        }
                    }
                }
            } else if (!z2) {
            }
        }
        return hostPort;
    }

    public HostNameParser(LexerCore lexerCore) {
        this.stripAddressScopeZones = false;
        this.lexer = lexerCore;
        lexerCore.selectLexer("charLexer");
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }
}
