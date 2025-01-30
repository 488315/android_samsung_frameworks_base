package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.AbstractC0000x2c234b15;
import gov.nist.javax.sip.header.CallIdentifier;
import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Join extends ParametersHeader {
    private static final long serialVersionUID = -840116548918120056L;
    public String callId;
    public CallIdentifier callIdentifier;

    public Join() {
        super("Join");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String str = this.callId;
        if (str == null) {
            return null;
        }
        if (this.parameters.isEmpty()) {
            return str;
        }
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, ";");
        m2m.append(this.parameters.encode());
        return m2m.toString();
    }

    public Join(String str) {
        super("Join");
        this.callIdentifier = new CallIdentifier(str);
    }
}
