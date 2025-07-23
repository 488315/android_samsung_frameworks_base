package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Allow extends SIPHeader implements Header {
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
