package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;
import javax.sip.header.MaxForwardsHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
            throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "bad max forwards value "));
        }
        this.maxForwards = i;
    }

    public MaxForwards(int i) throws InvalidArgumentException {
        super("Max-Forwards");
        setMaxForwards(i);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.maxForwards);
    }
}
