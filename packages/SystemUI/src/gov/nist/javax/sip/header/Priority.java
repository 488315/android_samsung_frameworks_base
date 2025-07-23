package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Priority extends SIPHeader implements Header {
    private static final long serialVersionUID = 3837543366074322106L;
    protected String priority;

    public Priority() {
        super("Priority");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.priority;
    }

    public final void setPriority(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,Priority, setPriority(), the priority parameter is null");
        }
        this.priority = str;
    }
}
