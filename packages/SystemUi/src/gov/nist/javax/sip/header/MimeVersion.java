package gov.nist.javax.sip.header;

import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MimeVersion extends SIPHeader {
    private static final long serialVersionUID = -7951589626435082068L;
    protected int majorVersion;
    protected int minorVersion;

    public MimeVersion() {
        super("MIME-Version");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return Integer.toString(this.majorVersion) + "." + Integer.toString(this.minorVersion);
    }

    public final void setMajorVersion(int i) {
        if (i < 0) {
            throw new InvalidArgumentException("JAIN-SIP Exception, MimeVersion, setMajorVersion(), the majorVersion parameter is null");
        }
        this.majorVersion = i;
    }

    public final void setMinorVersion(int i) {
        if (i < 0) {
            throw new InvalidArgumentException("JAIN-SIP Exception, MimeVersion, setMinorVersion(), the minorVersion parameter is null");
        }
        this.minorVersion = i;
    }
}
