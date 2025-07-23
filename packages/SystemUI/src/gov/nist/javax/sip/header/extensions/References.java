package gov.nist.javax.sip.header.extensions;

import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class References extends ParametersHeader implements Header {
    private static final long serialVersionUID = 8536961681006637622L;
    private String callId;

    public References() {
        super("References");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        if (this.parameters.isEmpty()) {
            return this.callId;
        }
        return this.callId + ";" + this.parameters.encode();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String getName() {
        return "References";
    }

    public final void setCallId(String str) {
        this.callId = str;
    }
}
