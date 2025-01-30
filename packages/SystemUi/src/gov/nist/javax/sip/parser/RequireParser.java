package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.RequireList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RequireParser extends HeaderParser {
    public RequireParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        RequireList requireList = new RequireList();
        headerName(2089);
        while (this.lexer.lookAhead(0) != '\n') {
            Require require = new Require();
            require.setHeaderName("Require");
            this.lexer.match(4095);
            require.setOptionTag(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            requireList.add((SIPHeader) require);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                Require require2 = new Require();
                this.lexer.match(4095);
                require2.setOptionTag(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                requireList.add((SIPHeader) require2);
            }
        }
        return requireList;
    }

    public RequireParser(Lexer lexer) {
        super(lexer);
    }
}
