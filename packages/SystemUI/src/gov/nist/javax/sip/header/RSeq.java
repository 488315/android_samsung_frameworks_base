package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

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

    public final void setSeqNumber(long j) throws InvalidArgumentException {
        if (j <= 0 || j > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("Bad seq number ", j));
        }
        this.sequenceNumber = j;
    }
}
