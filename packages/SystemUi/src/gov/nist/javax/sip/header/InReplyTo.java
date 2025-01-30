package gov.nist.javax.sip.header;

import java.text.ParseException;
import javax.sip.header.CallIdHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class InReplyTo extends SIPHeader implements CallIdHeader {
    private static final long serialVersionUID = 1682602905733508890L;
    protected CallIdentifier callId;

    public InReplyTo() {
        super("In-Reply-To");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        InReplyTo inReplyTo = (InReplyTo) super.clone();
        CallIdentifier callIdentifier = this.callId;
        if (callIdentifier != null) {
            inReplyTo.callId = (CallIdentifier) callIdentifier.clone();
        }
        return inReplyTo;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.callId.encode();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, javax.sip.header.CallIdHeader
    public final String getCallId() {
        CallIdentifier callIdentifier = this.callId;
        if (callIdentifier == null) {
            return null;
        }
        return callIdentifier.encode();
    }

    public final void setCallId(String str) {
        try {
            this.callId = new CallIdentifier(str);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }

    public InReplyTo(CallIdentifier callIdentifier) {
        super("In-Reply-To");
        this.callId = callIdentifier;
    }
}
