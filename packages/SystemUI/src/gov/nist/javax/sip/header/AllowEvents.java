package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AllowEvents extends SIPHeader implements Header {
    private static final long serialVersionUID = 617962431813193114L;
    protected String eventType;

    public AllowEvents() {
        super("Allow-Events");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.eventType;
    }

    public final void setEventType(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,AllowEvents, setEventType(), the eventType parameter is null");
        }
        this.eventType = str;
    }

    public AllowEvents(String str) {
        super("Allow-Events");
        this.eventType = str;
    }
}
