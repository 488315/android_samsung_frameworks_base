package gov.nist.javax.sip.header;

import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SubscriptionState extends ParametersHeader {
    private static final long serialVersionUID = -6673833053927258745L;
    protected int expires;
    protected String reasonCode;
    protected int retryAfter;
    protected String state;

    public SubscriptionState() {
        super("Subscription-State");
        this.expires = -1;
        this.retryAfter = -1;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    public final void setExpires(int i) {
        if (i < 0) {
            throw new InvalidArgumentException("JAIN-SIP Exception, SubscriptionState, setExpires(), the expires parameter is  < 0");
        }
        this.expires = i;
    }

    public final void setReasonCode(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, SubscriptionState, setReasonCode(), the reasonCode parameter is null");
        }
        this.reasonCode = str;
    }

    public final void setRetryAfter(int i) {
        if (i <= 0) {
            throw new InvalidArgumentException("JAIN-SIP Exception, SubscriptionState, setRetryAfter(), the retryAfter parameter is <=0");
        }
        this.retryAfter = i;
    }

    public final void setState(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, SubscriptionState, setState(), the state parameter is null");
        }
        this.state = str;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        String str = this.state;
        if (str != null) {
            stringBuffer.append(str);
        }
        if (this.reasonCode != null) {
            stringBuffer.append(";reason=");
            stringBuffer.append(this.reasonCode);
        }
        if (this.expires != -1) {
            stringBuffer.append(";expires=");
            stringBuffer.append(this.expires);
        }
        if (this.retryAfter != -1) {
            stringBuffer.append(";retry-after=");
            stringBuffer.append(this.retryAfter);
        }
        if (this.parameters.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        this.parameters.encode(stringBuffer);
    }
}
