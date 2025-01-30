package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Organization extends SIPHeader {
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
