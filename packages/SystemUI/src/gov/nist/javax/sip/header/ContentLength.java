package gov.nist.javax.sip.header;

import javax.sip.InvalidArgumentException;
import javax.sip.header.ContentLengthHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContentLength extends SIPHeader implements ContentLengthHeader {
    private static final long serialVersionUID = 1187190542411037027L;
    protected Integer contentLength;

    public ContentLength() {
        super("Content-Length");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return (obj instanceof ContentLengthHeader) && this.contentLength.intValue() == ((ContentLength) ((ContentLengthHeader) obj)).contentLength.intValue();
    }

    public final int getContentLength() {
        return this.contentLength.intValue();
    }

    public final void setContentLength(int i) {
        if (i < 0) {
            throw new InvalidArgumentException("JAIN-SIP Exception, ContentLength, setContentLength(), the contentLength parameter is <0");
        }
        this.contentLength = Integer.valueOf(i);
    }

    public ContentLength(int i) {
        super("Content-Length");
        this.contentLength = Integer.valueOf(i);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        Integer num = this.contentLength;
        if (num == null) {
            stringBuffer.append("0");
        } else {
            stringBuffer.append(num.toString());
        }
    }
}
