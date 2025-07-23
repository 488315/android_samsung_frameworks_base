package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PVisitedNetworkID;
import gov.nist.javax.sip.header.ims.PVisitedNetworkIDList;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PVisitedNetworkIDParser extends ParametersParser {
    public PVisitedNetworkIDParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        PVisitedNetworkIDList pVisitedNetworkIDList = new PVisitedNetworkIDList();
        this.lexer.match(2123);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        while (true) {
            PVisitedNetworkID pVisitedNetworkID = new PVisitedNetworkID();
            if (this.lexer.lookAhead(0) == '\"') {
                StringBuffer stringBuffer = new StringBuffer();
                if (this.lexer.lookAhead(0) != '\"') {
                    throw createParseException("unexpected char");
                }
                this.lexer.consume(1);
                while (true) {
                    char nextChar = this.lexer.getNextChar();
                    if (nextChar == '\"') {
                        pVisitedNetworkID.setVisitedNetworkID(stringBuffer.toString());
                        parse(pVisitedNetworkID);
                        break;
                    }
                    if (nextChar == 0) {
                        throw new ParseException("unexpected EOL", 1);
                    }
                    if (nextChar == '\\') {
                        stringBuffer.append(nextChar);
                        stringBuffer.append(this.lexer.getNextChar());
                    } else {
                        stringBuffer.append(nextChar);
                    }
                }
            } else {
                this.lexer.match(4095);
                pVisitedNetworkID.setVisitedNetworkID(this.lexer.currentMatch);
                parse(pVisitedNetworkID);
            }
            pVisitedNetworkIDList.add((SIPHeader) pVisitedNetworkID);
            this.lexer.SPorHT();
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != ',') {
                if (lookAhead == '\n') {
                    return pVisitedNetworkIDList;
                }
                throw createParseException("unexpected char = " + lookAhead);
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
    }

    public PVisitedNetworkIDParser(Lexer lexer) {
        super(lexer);
    }
}
