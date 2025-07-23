package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
