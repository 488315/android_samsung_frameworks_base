package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RSeq extends SIPHeader {
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
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("Bad seq number ", j));
        }
        this.sequenceNumber = j;
    }
}
