package gov.nist.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class HostNameParser extends ParserCore {
    public static final char[] VALID_DOMAIN_LABEL_CHAR = {65533, '-', '.'};
    public final boolean stripAddressScopeZones;

    public HostNameParser(String str) {
        this.stripAddressScopeZones = false;
        this.lexer = new LexerCore("charLexer", str);
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0183, code lost:
    
        if (r9 != '?') goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01bd, code lost:
    
        if (r17 == false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01e7, code lost:
    
        throw new java.text.ParseException(r16.lexer.buffer + " Illegal character in hostname:" + r16.lexer.lookAhead(0), r16.lexer.ptr);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01ba, code lost:
    
        if (r1 != false) goto L93;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final gov.nist.core.HostPort hostPort(boolean r17) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gov.nist.core.HostNameParser.hostPort(boolean):gov.nist.core.HostPort");
    }

    public HostNameParser(LexerCore lexerCore) {
        this.stripAddressScopeZones = false;
        this.lexer = lexerCore;
        lexerCore.selectLexer("charLexer");
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }
}
