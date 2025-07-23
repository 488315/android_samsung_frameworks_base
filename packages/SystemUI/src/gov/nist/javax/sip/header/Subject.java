package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Subject extends SIPHeader implements Header {
    private static final long serialVersionUID = -6479220126758862528L;
    protected String subject;

    public Subject() {
        super("Subject");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String str = this.subject;
        return str != null ? str : "";
    }

    public final void setSubject(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,  Subject, setSubject(), the subject parameter is null");
        }
        this.subject = str;
    }
}
