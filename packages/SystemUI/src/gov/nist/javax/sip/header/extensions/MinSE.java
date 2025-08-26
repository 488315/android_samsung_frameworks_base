package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class MinSE extends ParametersHeader implements Header {
    private static final long serialVersionUID = 3134344915465784267L;
    public int expires;

    public MinSE() {
        super("Min-SE");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String string = Integer.toString(this.expires);
        if (this.parameters.isEmpty()) {
            return string;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ";");
        sbM.append(this.parameters.encode());
        return sbM.toString();
    }

    public final void setExpires(int i) throws InvalidArgumentException {
        if (i < 0) {
            throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "bad argument "));
        }
        this.expires = i;
    }
}
