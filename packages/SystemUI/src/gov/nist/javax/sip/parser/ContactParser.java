package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ContactParser extends AddressParametersParser {
    public ContactParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        char cLookAhead;
        headerName(2087);
        ContactList contactList = new ContactList();
        while (true) {
            Contact contact = new Contact();
            if (this.lexer.lookAhead(0) == '*') {
                char cLookAhead2 = this.lexer.lookAhead(1);
                if (cLookAhead2 == ' ' || cLookAhead2 == '\t' || cLookAhead2 == '\r' || cLookAhead2 == '\n') {
                    this.lexer.match(42);
                    contact.setWildCardFlag$1();
                } else {
                    parse((AddressParametersHeader) contact);
                }
            } else {
                parse((AddressParametersHeader) contact);
            }
            contactList.add((SIPHeader) contact);
            this.lexer.SPorHT();
            cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (cLookAhead == '\n' || cLookAhead == 0) {
            return contactList;
        }
        throw createParseException("unexpected char");
    }

    public ContactParser(Lexer lexer) {
        super(lexer);
        this.lexer = lexer;
    }
}
