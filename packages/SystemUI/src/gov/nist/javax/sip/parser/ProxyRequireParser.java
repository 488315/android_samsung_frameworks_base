package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.ProxyRequireList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ProxyRequireParser extends HeaderParser {
    public ProxyRequireParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ProxyRequireList proxyRequireList = new ProxyRequireList();
        headerName(2074);
        while (this.lexer.lookAhead(0) != '\n') {
            ProxyRequire proxyRequire = new ProxyRequire();
            proxyRequire.setHeaderName("Proxy-Require");
            this.lexer.match(4095);
            proxyRequire.setOptionTag(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            proxyRequireList.add((SIPHeader) proxyRequire);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                ProxyRequire proxyRequire2 = new ProxyRequire();
                this.lexer.match(4095);
                proxyRequire2.setOptionTag(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                proxyRequireList.add((SIPHeader) proxyRequire2);
            }
        }
        return proxyRequireList;
    }

    public ProxyRequireParser(Lexer lexer) {
        super(lexer);
    }
}
