package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Expires extends SIPHeader {
    private static final long serialVersionUID = 3134344915465784267L;
    protected int expires;

    public Expires() {
        super("Expires");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.expires);
        return stringBuffer.toString();
    }

    public final void setExpires(int i) {
        if (i < 0) {
            throw new InvalidArgumentException(AbstractC0000x2c234b15.m0m("bad argument ", i));
        }
        this.expires = i;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.expires);
    }
}
