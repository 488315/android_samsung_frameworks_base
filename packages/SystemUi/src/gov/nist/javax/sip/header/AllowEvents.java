package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AllowEvents extends SIPHeader {
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
