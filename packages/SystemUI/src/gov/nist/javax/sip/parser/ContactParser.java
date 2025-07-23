package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContactParser extends AddressParametersParser {
    public ContactParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        char lookAhead;
        headerName(2087);
        ContactList contactList = new ContactList();
        while (true) {
            Contact contact = new Contact();
            if (this.lexer.lookAhead(0) == '*') {
                char lookAhead2 = this.lexer.lookAhead(1);
                if (lookAhead2 == ' ' || lookAhead2 == '\t' || lookAhead2 == '\r' || lookAhead2 == '\n') {
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
            lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (lookAhead == '\n' || lookAhead == 0) {
            return contactList;
        }
        throw createParseException("unexpected char");
    }

    public ContactParser(Lexer lexer) {
        super(lexer);
        this.lexer = lexer;
    }
}
