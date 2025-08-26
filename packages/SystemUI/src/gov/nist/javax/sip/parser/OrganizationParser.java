package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Organization;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class OrganizationParser extends HeaderParser {
    public OrganizationParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
