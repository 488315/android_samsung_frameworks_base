package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class AddressParser extends Parser {
    public AddressParser(Lexer lexer) {
        this.lexer = lexer;
        lexer.selectLexer("charLexer");
    }

    public final AddressImpl address() throws ParseException {
        String nextToken;
        char cLookAhead;
        int i = 0;
        while (this.lexer.hasMoreChars() && (cLookAhead = this.lexer.lookAhead(i)) != '<' && cLookAhead != '\"' && cLookAhead != ':' && cLookAhead != '/') {
            if (cLookAhead == 0) {
                throw createParseException("unexpected EOL");
            }
            i++;
        }
        char cLookAhead2 = this.lexer.lookAhead(i);
        if (cLookAhead2 != '<' && cLookAhead2 != '\"') {
            if (cLookAhead2 != ':' && cLookAhead2 != '/') {
                throw createParseException("Bad address spec");
            }
            AddressImpl addressImpl = new AddressImpl();
            GenericURI genericURIUriReference = new URLParser((Lexer) this.lexer).uriReference(false);
            addressImpl.setAddressType(2);
            addressImpl.setURI(genericURIUriReference);
            return addressImpl;
        }
        if (this.lexer.lookAhead(0) == '<') {
            this.lexer.consume(1);
            this.lexer.selectLexer("sip_urlLexer");
            this.lexer.SPorHT();
            GenericURI genericURIUriReference2 = new URLParser((Lexer) this.lexer).uriReference(true);
            AddressImpl addressImpl2 = new AddressImpl();
            addressImpl2.setAddressType(1);
            addressImpl2.setURI(genericURIUriReference2);
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
        GenericURI genericURIUriReference3 = new URLParser((Lexer) this.lexer).uriReference(true);
        new AddressImpl();
        addressImpl3.setAddressType(1);
        addressImpl3.setURI(genericURIUriReference3);
        this.lexer.SPorHT();
        this.lexer.match(62);
        return addressImpl3;
    }

    public AddressParser(String str) {
        this.lexer = new Lexer("charLexer", str);
    }
}
