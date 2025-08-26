package gov.nist.javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class TimeStamp extends SIPHeader implements Header {
    private static final long serialVersionUID = -3711322366481232720L;
    protected int delay;
    protected float delayFloat;
    protected long timeStamp;
    private float timeStampFloat;

    public TimeStamp() {
        super("Timestamp");
        this.timeStamp = -1L;
        this.delayFloat = -1.0f;
        this.timeStampFloat = -1.0f;
        this.delay = -1;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        long j = this.timeStamp;
        String string = (j == -1 && this.timeStampFloat == -1.0f) ? "" : j != -1 ? Long.toString(j) : Float.toString(this.timeStampFloat);
        int i = this.delay;
        String string2 = (i == -1 && this.delayFloat == -1.0f) ? "" : i != -1 ? Integer.toString(i) : Float.toString(this.delayFloat);
        if (string.equals("") && string2.equals("")) {
            return "";
        }
        if (!string.equals("")) {
            stringBuffer.append(string);
        }
        if (!string2.equals("")) {
            stringBuffer.append(" ");
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    public final void setDelay(float f) throws InvalidArgumentException {
        if (f < 0.0f && f != -1.0f) {
            throw new InvalidArgumentException("JAIN-SIP Exception, TimeStamp, setDelay(), the delay parameter is <0");
        }
        this.delayFloat = f;
        this.delay = -1;
    }

    public final void setTime(long j) throws InvalidArgumentException {
        if (j < -1) {
            throw new InvalidArgumentException("Illegal timestamp");
        }
        this.timeStamp = j;
        this.timeStampFloat = -1.0f;
    }

    public final void setTimeStamp(float f) throws InvalidArgumentException {
        if (f < 0.0f) {
            throw new InvalidArgumentException("JAIN-SIP Exception, TimeStamp, setTimeStamp(), the timeStamp parameter is <0");
        }
        this.timeStamp = -1L;
        this.timeStampFloat = f;
    }
}
