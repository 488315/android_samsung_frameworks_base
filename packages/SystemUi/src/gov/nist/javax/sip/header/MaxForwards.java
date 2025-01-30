package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import javax.sip.InvalidArgumentException;
import javax.sip.header.MaxForwardsHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MaxForwards extends SIPHeader implements MaxForwardsHeader {
    private static final long serialVersionUID = -3096874323347175943L;
    protected int maxForwards;

    public MaxForwards() {
        super("Max-Forwards");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.maxForwards);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MaxForwardsHeader) && this.maxForwards == ((MaxForwards) ((MaxForwardsHeader) obj)).maxForwards;
    }

    public final void setMaxForwards(int i) {
        if (i < 0 || i > 255) {
            throw new InvalidArgumentException(AbstractC0000x2c234b15.m0m("bad max forwards value ", i));
        }
        this.maxForwards = i;
    }

    public MaxForwards(int i) {
        super("Max-Forwards");
        setMaxForwards(i);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.maxForwards);
    }
}
