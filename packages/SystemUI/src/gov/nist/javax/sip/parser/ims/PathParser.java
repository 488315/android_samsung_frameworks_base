package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.Path;
import gov.nist.javax.sip.header.ims.PathList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PathParser extends AddressParametersParser {
    public PathParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        char lookAhead;
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
            lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (lookAhead == '\n') {
            return pathList;
        }
        throw createParseException("unexpected char");
    }

    public PathParser(Lexer lexer) {
        super(lexer);
    }
}
