package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class RetryAfter extends ParametersHeader implements Header {
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

    public final void setDuration(int i) throws InvalidArgumentException {
        if (i < 0) {
            throw new InvalidArgumentException("the duration parameter is <0");
        }
        this.parameters.set(Integer.valueOf(i), "duration");
    }

    public final void setRetryAfter(int i) throws InvalidArgumentException {
        if (i < 0) {
            throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "invalid parameter "));
        }
        this.retryAfter = Integer.valueOf(i);
    }
}
