package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class Organization extends SIPHeader implements Header {
    private static final long serialVersionUID = -2775003113740192712L;
    protected String organization;

    public Organization() {
        super("Organization");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.organization;
    }

    public final void setOrganization(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, Organization, setOrganization(), the organization parameter is null");
        }
        this.organization = str;
    }
}
