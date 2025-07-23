package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.header.CallIdentifier;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Replaces extends ParametersHeader implements Header {
    private static final long serialVersionUID = 8765762413224043300L;
    public String callId;
    public CallIdentifier callIdentifier;

    public Replaces() {
        super("Replaces");
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
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ";");
        m.append(this.parameters.encode());
        return m.toString();
    }

    public Replaces(String str) throws IllegalArgumentException {
        super("Replaces");
        this.callIdentifier = new CallIdentifier(str);
    }
}
