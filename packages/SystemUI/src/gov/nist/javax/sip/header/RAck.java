package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class RAck extends SIPHeader implements Header {
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

    public final void setCSequenceNumber(long j) throws InvalidArgumentException {
        if (j <= 0 || j > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("Bad CSeq # ", j));
        }
        this.cSeqNumber = j;
    }

    public final void setMethod(String str) {
        this.method = str;
    }

    public final void setRSequenceNumber(long j) throws InvalidArgumentException {
        if (j <= 0 || this.cSeqNumber > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("Bad rSeq # ", j));
        }
        this.rSeqNumber = j;
    }
}
