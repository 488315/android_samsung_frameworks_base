package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MinExpires extends SIPHeader implements Header {
    private static final long serialVersionUID = 7001828209606095801L;
    protected int expires;

    public MinExpires() {
        super("Min-Expires");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return Integer.toString(this.expires);
    }

    public final void setExpires(int i) {
        if (i < 0) {
            throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "bad argument "));
        }
        this.expires = i;
    }
}
