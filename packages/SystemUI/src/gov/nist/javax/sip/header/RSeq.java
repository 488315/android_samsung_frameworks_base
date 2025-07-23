package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RSeq extends SIPHeader implements Header {
    private static final long serialVersionUID = 8765762413224043394L;
    protected long sequenceNumber;

    public RSeq() {
        super("RSeq");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return Long.toString(this.sequenceNumber);
    }

    public final void setSeqNumber(long j) {
        if (j <= 0 || j > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("Bad seq number ", j));
        }
        this.sequenceNumber = j;
    }
}
