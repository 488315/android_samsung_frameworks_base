package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MinExpires extends SIPHeader {
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
            throw new InvalidArgumentException(AbstractC0000x2c234b15.m0m("bad argument ", i));
        }
        this.expires = i;
    }
}
