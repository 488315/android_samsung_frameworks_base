package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.AbstractC0000x2c234b15;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MinSE extends ParametersHeader {
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
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(num, ";");
        m2m.append(this.parameters.encode());
        return m2m.toString();
    }

    public final void setExpires(int i) {
        if (i < 0) {
            throw new InvalidArgumentException(AbstractC0000x2c234b15.m0m("bad argument ", i));
        }
        this.expires = i;
    }
}
