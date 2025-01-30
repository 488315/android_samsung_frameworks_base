package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RAck extends SIPHeader {
    private static final long serialVersionUID = 743999286077404118L;
    protected long cSeqNumber;
    protected String method;
    protected long rSeqNumber;

    public RAck() {
        super("RAck");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.rSeqNumber);
        stringBuffer.append(" ");
        stringBuffer.append(this.cSeqNumber);
        stringBuffer.append(" ");
        stringBuffer.append(this.method);
        return stringBuffer.toString();
    }

    public final void setCSequenceNumber(long j) {
        if (j <= 0 || j > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("Bad CSeq # ", j));
        }
        this.cSeqNumber = j;
    }

    public final void setMethod(String str) {
        this.method = str;
    }

    public final void setRSequenceNumber(long j) {
        if (j <= 0 || this.cSeqNumber > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("Bad rSeq # ", j));
        }
        this.rSeqNumber = j;
    }
}
