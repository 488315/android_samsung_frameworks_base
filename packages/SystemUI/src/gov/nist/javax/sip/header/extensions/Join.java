package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.header.CallIdentifier;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class Join extends ParametersHeader implements Header {
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ";");
        sbM.append(this.parameters.encode());
        return sbM.toString();
    }

    public Join(String str) throws IllegalArgumentException {
        super("Join");
        this.callIdentifier = new CallIdentifier(str);
    }
}
