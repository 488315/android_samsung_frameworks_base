package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RetryAfter extends ParametersHeader {
    private static final long serialVersionUID = -1029458515616146140L;
    protected String comment;
    protected Integer retryAfter;

    public RetryAfter() {
        super("Retry-After");
        this.retryAfter = new Integer(0);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        Integer num = this.retryAfter;
        if (num != null) {
            stringBuffer.append(num);
        }
        if (this.comment != null) {
            stringBuffer.append(" (" + this.comment + ")");
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";" + this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public final void setComment(String str) {
        if (str == null) {
            throw new NullPointerException("the comment parameter is null");
        }
        this.comment = str;
    }

    public final void setDuration(int i) {
        if (i < 0) {
            throw new InvalidArgumentException("the duration parameter is <0");
        }
        this.parameters.set(Integer.valueOf(i), "duration");
    }

    public final void setRetryAfter(int i) {
        if (i < 0) {
            throw new InvalidArgumentException(AbstractC0000x2c234b15.m0m("invalid parameter ", i));
        }
        this.retryAfter = Integer.valueOf(i);
    }
}
