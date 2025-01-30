package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Organization;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class OrganizationParser extends HeaderParser {
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
