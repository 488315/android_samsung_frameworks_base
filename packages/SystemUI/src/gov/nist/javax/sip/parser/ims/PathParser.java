package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.Path;
import gov.nist.javax.sip.header.ims.PathList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class PathParser extends AddressParametersParser {
    public PathParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        char cLookAhead;
        PathList pathList = new PathList();
        this.lexer.match(2119);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        while (true) {
            Path path = new Path();
            parse((AddressParametersHeader) path);
            pathList.add((SIPHeader) path);
            this.lexer.SPorHT();
            cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (cLookAhead == '\n') {
            return pathList;
        }
        throw createParseException("unexpected char");
    }

    public PathParser(Lexer lexer) {
        super(lexer);
    }
}
