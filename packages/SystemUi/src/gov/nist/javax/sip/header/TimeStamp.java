package gov.nist.javax.sip.header;

import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TimeStamp extends SIPHeader {
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
        String l = (j == -1 && this.timeStampFloat == -1.0f) ? "" : j != -1 ? Long.toString(j) : Float.toString(this.timeStampFloat);
        int i = this.delay;
        String num = (i == -1 && this.delayFloat == -1.0f) ? "" : i != -1 ? Integer.toString(i) : Float.toString(this.delayFloat);
        if (l.equals("") && num.equals("")) {
            return "";
        }
        if (!l.equals("")) {
            stringBuffer.append(l);
        }
        if (!num.equals("")) {
            stringBuffer.append(" ");
            stringBuffer.append(num);
        }
        return stringBuffer.toString();
    }

    public final void setDelay(float f) {
        if (f < 0.0f && f != -1.0f) {
            throw new InvalidArgumentException("JAIN-SIP Exception, TimeStamp, setDelay(), the delay parameter is <0");
        }
        this.delayFloat = f;
        this.delay = -1;
    }

    public final void setTime(long j) {
        if (j < -1) {
            throw new InvalidArgumentException("Illegal timestamp");
        }
        this.timeStamp = j;
        this.timeStampFloat = -1.0f;
    }

    public final void setTimeStamp(float f) {
        if (f < 0.0f) {
            throw new InvalidArgumentException("JAIN-SIP Exception, TimeStamp, setTimeStamp(), the timeStamp parameter is <0");
        }
        this.timeStamp = -1L;
        this.timeStampFloat = f;
    }
}
