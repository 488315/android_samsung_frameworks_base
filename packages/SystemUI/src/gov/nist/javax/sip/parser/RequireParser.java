package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.RequireList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class RequireParser extends HeaderParser {
    public RequireParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
