package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MinSE extends ParametersHeader implements Header {
    private static final long serialVersionUID = 3134344915465784267L;
    public int expires;

    public MinSE() {
        super("Min-SE");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String num = Integer.toString(this.expires);
        if (this.parameters.isEmpty()) {
            return num;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(num, ";");
        m.append(this.parameters.encode());
        return m.toString();
    }

    public final void setExpires(int i) {
        if (i < 0) {
            throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "bad argument "));
        }
        this.expires = i;
    }
}
