package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.message.SIPRequest;
import javax.sip.InvalidArgumentException;
import javax.sip.header.CSeqHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CSeq extends SIPHeader implements CSeqHeader {
    private static final long serialVersionUID = -5405798080040422910L;
    protected String method;
    protected Long seqno;

    public CSeq() {
        super("CSeq");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        return this.headerName + ": " + encodeBody() + "\r\n";
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj instanceof CSeqHeader) {
            CSeq cSeq = (CSeq) ((CSeqHeader) obj);
            if (this.seqno.longValue() == cSeq.seqno.longValue() && this.method.equals(cSeq.method)) {
                return true;
            }
        }
        return false;
    }

    public final String getMethod() {
        return this.method;
    }

    public final void setMethod(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, CSeq, setMethod(), the meth parameter is null");
        }
        this.method = SIPRequest.getCannonicalName(str);
    }

    public final void setSeqNumber(long j) {
        if (j < 0) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("JAIN-SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is < 0 : ", j));
        }
        if (j > 2147483648L) {
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("JAIN-SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is too large : ", j));
        }
        this.seqno = Long.valueOf(j);
    }

    public CSeq(long j, String str) {
        this();
        this.seqno = Long.valueOf(j);
        this.method = SIPRequest.getCannonicalName(str);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.seqno);
        stringBuffer.append(" ");
        stringBuffer.append(this.method.toUpperCase());
    }
}
