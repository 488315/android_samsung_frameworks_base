package gov.nist.javax.sip.header;

import java.text.ParseException;
import javax.sip.header.CallIdHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CallID extends SIPHeader implements CallIdHeader {
    private static final long serialVersionUID = -6463630258703731156L;
    protected CallIdentifier callIdentifier;

    public CallID() {
        super("Call-ID");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        CallID callID = (CallID) super.clone();
        CallIdentifier callIdentifier = this.callIdentifier;
        if (callIdentifier != null) {
            callID.callIdentifier = (CallIdentifier) callIdentifier.clone();
        }
        return callID;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CallIdHeader) {
            return encodeBody().equalsIgnoreCase(((CallIdHeader) obj).getCallId());
        }
        return false;
    }

    public final void setCallId(String str) {
        try {
            this.callIdentifier = new CallIdentifier(str);
        } catch (IllegalArgumentException unused) {
            throw new ParseException(str, 0);
        }
    }

    public CallID(String str) {
        super("Call-ID");
        this.callIdentifier = new CallIdentifier(str);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        CallIdentifier callIdentifier = this.callIdentifier;
        if (callIdentifier != null) {
            callIdentifier.encode(stringBuffer);
        }
    }
}
