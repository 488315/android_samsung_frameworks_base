package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Event extends ParametersHeader implements Header {
    private static final long serialVersionUID = -6458387810431874841L;
    protected String eventType;

    public Event() {
        super("Event");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    public final void setEventType(String str) {
        if (str == null) {
            throw new NullPointerException(" the eventType is null");
        }
        this.eventType = str;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        String str = this.eventType;
        if (str != null) {
            stringBuffer.append(str);
        }
        if (this.parameters.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        this.parameters.encode(stringBuffer);
    }
}
