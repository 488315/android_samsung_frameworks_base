package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AcceptEncoding;
import gov.nist.javax.sip.header.AcceptEncodingList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class AcceptEncodingParser extends HeaderParser {
    public AcceptEncodingParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        AcceptEncodingList acceptEncodingList = new AcceptEncodingList();
        headerName(2067);
        if (this.lexer.lookAhead(0) == '\n') {
            acceptEncodingList.add((SIPHeader) new AcceptEncoding());
            return acceptEncodingList;
        }
        while (this.lexer.lookAhead(0) != '\n') {
            AcceptEncoding acceptEncoding = new AcceptEncoding();
            if (this.lexer.lookAhead(0) != ';') {
                this.lexer.match(4095);
                acceptEncoding.setEncoding(this.lexer.currentMatch.tokenValue);
            }
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.match(59);
                this.lexer.SPorHT();
                this.lexer.match(113);
                this.lexer.SPorHT();
                this.lexer.match(61);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                try {
                    acceptEncoding.setQValue(Float.parseFloat(this.lexer.currentMatch.tokenValue));
                    this.lexer.SPorHT();
                } catch (NumberFormatException e) {
                    throw createParseException(e.getMessage());
                } catch (InvalidArgumentException e2) {
                    throw createParseException(e2.getMessage());
                }
            }
            acceptEncodingList.add((SIPHeader) acceptEncoding);
            if (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
            }
        }
        return acceptEncodingList;
    }

    public AcceptEncodingParser(Lexer lexer) {
        super(lexer);
    }
}
