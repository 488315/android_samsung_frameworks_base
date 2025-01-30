package gov.nist.core;

import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HostNameParser extends ParserCore {
    public static final char[] VALID_DOMAIN_LABEL_CHAR = {65533, '-', '.'};
    public final boolean stripAddressScopeZones;

    public HostNameParser(String str) {
        this.stripAddressScopeZones = false;
        this.lexer = new LexerCore("charLexer", str);
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    public final Host host() {
        String substring;
        boolean z = false;
        if (this.lexer.lookAhead(0) == '[') {
            substring = ipv6Reference();
        } else {
            String rest = this.lexer.getRest();
            int indexOf = rest.indexOf(63);
            int indexOf2 = rest.indexOf(59);
            if (indexOf == -1 || (indexOf2 != -1 && indexOf > indexOf2)) {
                indexOf = indexOf2;
            }
            if (indexOf == -1) {
                indexOf = rest.length();
            }
            String substring2 = rest.substring(0, indexOf);
            int indexOf3 = substring2.indexOf(58);
            if (indexOf3 != -1 && substring2.indexOf(58, indexOf3 + 1) != -1) {
                z = true;
            }
            if (z) {
                LexerCore lexerCore = this.lexer;
                int i = lexerCore.ptr;
                lexerCore.consumeValidChars(new char[]{65533, ':'});
                StringBuffer stringBuffer = new StringBuffer("[");
                LexerCore lexerCore2 = this.lexer;
                stringBuffer.append(lexerCore2.buffer.substring(i, lexerCore2.ptr));
                stringBuffer.append("]");
                substring = stringBuffer.toString();
            } else {
                LexerCore lexerCore3 = this.lexer;
                int i2 = lexerCore3.ptr;
                lexerCore3.consumeValidChars(VALID_DOMAIN_LABEL_CHAR);
                LexerCore lexerCore4 = this.lexer;
                substring = lexerCore4.buffer.substring(i2, lexerCore4.ptr);
            }
        }
        if (substring.length() != 0) {
            return new Host(substring);
        }
        throw new ParseException(this.lexer.buffer + ": Missing host name", this.lexer.ptr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x004b, code lost:
    
        if (r0 != '?') goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008e, code lost:
    
        if (r5 == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b8, code lost:
    
        throw new java.text.ParseException(r4.lexer.buffer + " Illegal character in hostname:" + r4.lexer.lookAhead(0), r4.lexer.ptr);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008b, code lost:
    
        if (r4.stripAddressScopeZones != false) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final HostPort hostPort(boolean z) {
        char lookAhead;
        Host host = host();
        HostPort hostPort = new HostPort();
        hostPort.host = host;
        if (z) {
            this.lexer.SPorHT();
        }
        if (this.lexer.hasMoreChars() && (lookAhead = this.lexer.lookAhead(0)) != '\t' && lookAhead != '\n' && lookAhead != '\r' && lookAhead != ' ') {
            if (lookAhead != '%') {
                if (lookAhead != ',' && lookAhead != '/') {
                    if (lookAhead == ':') {
                        this.lexer.consume(1);
                        if (z) {
                            this.lexer.SPorHT();
                        }
                        try {
                            hostPort.port = Integer.parseInt(this.lexer.number());
                        } catch (NumberFormatException unused) {
                            throw new ParseException(this.lexer.buffer + " :Error parsing port ", this.lexer.ptr);
                        }
                    } else if (lookAhead != ';') {
                        if (lookAhead != '>') {
                        }
                    }
                }
            }
        }
        return hostPort;
    }

    public final String ipv6Reference() {
        int indexOf;
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.stripAddressScopeZones) {
            while (true) {
                if (!this.lexer.hasMoreChars()) {
                    break;
                }
                char lookAhead = this.lexer.lookAhead(0);
                if (StringTokenizer.isHexDigit(lookAhead) || lookAhead == '.' || lookAhead == ':' || lookAhead == '[') {
                    this.lexer.consume(1);
                    stringBuffer.append(lookAhead);
                } else if (lookAhead == ']') {
                    this.lexer.consume(1);
                    stringBuffer.append(lookAhead);
                    return stringBuffer.toString();
                }
            }
        } else {
            while (true) {
                if (!this.lexer.hasMoreChars()) {
                    break;
                }
                char lookAhead2 = this.lexer.lookAhead(0);
                if (StringTokenizer.isHexDigit(lookAhead2) || lookAhead2 == '.' || lookAhead2 == ':' || lookAhead2 == '[') {
                    this.lexer.consume(1);
                    stringBuffer.append(lookAhead2);
                } else {
                    if (lookAhead2 == ']') {
                        this.lexer.consume(1);
                        stringBuffer.append(lookAhead2);
                        return stringBuffer.toString();
                    }
                    if (lookAhead2 == '%') {
                        this.lexer.consume(1);
                        String rest = this.lexer.getRest();
                        if (rest != null && rest.length() != 0 && (indexOf = rest.indexOf(93)) != -1) {
                            this.lexer.consume(indexOf + 1);
                            stringBuffer.append("]");
                            return stringBuffer.toString();
                        }
                    }
                }
            }
        }
        throw new ParseException(this.lexer.buffer + ": Illegal Host name ", this.lexer.ptr);
    }

    public HostNameParser(LexerCore lexerCore) {
        this.stripAddressScopeZones = false;
        this.lexer = lexerCore;
        lexerCore.selectLexer("charLexer");
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }
}
