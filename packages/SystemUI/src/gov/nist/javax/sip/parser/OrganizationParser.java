package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Organization;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class OrganizationParser extends HeaderParser {
    public OrganizationParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        Organization organization = new Organization();
        headerName(2093);
        organization.setHeaderName("Organization");
        this.lexer.SPorHT();
        organization.setOrganization(this.lexer.getRest().trim());
        return organization;
    }

    public OrganizationParser(Lexer lexer) {
        super(lexer);
    }
}
