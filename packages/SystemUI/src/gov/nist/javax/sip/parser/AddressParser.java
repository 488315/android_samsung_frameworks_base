package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AddressParser extends Parser {
    public AddressParser(Lexer lexer) {
        this.lexer = lexer;
        lexer.selectLexer("charLexer");
    }

    public final AddressImpl address() {
        String nextToken;
        char lookAhead;
        int i = 0;
        while (this.lexer.hasMoreChars() && (lookAhead = this.lexer.lookAhead(i)) != '<' && lookAhead != '\"' && lookAhead != ':' && lookAhead != '/') {
            if (lookAhead == 0) {
                throw createParseException("unexpected EOL");
            }
            i++;
        }
        char lookAhead2 = this.lexer.lookAhead(i);
        if (lookAhead2 != '<' && lookAhead2 != '\"') {
            if (lookAhead2 != ':' && lookAhead2 != '/') {
                throw createParseException("Bad address spec");
            }
            AddressImpl addressImpl = new AddressImpl();
            GenericURI uriReference = new URLParser((Lexer) this.lexer).uriReference(false);
            addressImpl.setAddressType(2);
            addressImpl.setURI(uriReference);
            return addressImpl;
        }
        if (this.lexer.lookAhead(0) == '<') {
            this.lexer.consume(1);
            this.lexer.selectLexer("sip_urlLexer");
            this.lexer.SPorHT();
            GenericURI uriReference2 = new URLParser((Lexer) this.lexer).uriReference(true);
            AddressImpl addressImpl2 = new AddressImpl();
            addressImpl2.setAddressType(1);
            addressImpl2.setURI(uriReference2);
            this.lexer.SPorHT();
            this.lexer.match(62);
            return addressImpl2;
        }
        AddressImpl addressImpl3 = new AddressImpl();
        addressImpl3.setAddressType(1);
        if (this.lexer.lookAhead(0) == '\"') {
            nextToken = this.lexer.quotedString();
            this.lexer.SPorHT();
        } else {
            nextToken = this.lexer.getNextToken('<');
        }
        addressImpl3.setDisplayName(nextToken.trim());
        this.lexer.match(60);
        this.lexer.SPorHT();
        GenericURI uriReference3 = new URLParser((Lexer) this.lexer).uriReference(true);
        new AddressImpl();
        addressImpl3.setAddressType(1);
        addressImpl3.setURI(uriReference3);
        this.lexer.SPorHT();
        this.lexer.match(62);
        return addressImpl3;
    }

    public AddressParser(String str) {
        this.lexer = new Lexer("charLexer", str);
    }
}
