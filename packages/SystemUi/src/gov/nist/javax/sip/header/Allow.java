package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Allow extends SIPHeader {
    private static final long serialVersionUID = -3105079479020693930L;
    protected String method;

    public Allow() {
        super("Allow");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.method;
    }

    public final void setMethod(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, Allow, setMethod(), the method parameter is null.");
        }
        this.method = str;
    }

    public Allow(String str) {
        super("Allow");
        this.method = str;
    }
}
