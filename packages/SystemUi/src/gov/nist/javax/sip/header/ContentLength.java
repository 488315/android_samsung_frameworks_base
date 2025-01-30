package gov.nist.javax.sip.header;

import com.sec.ims.configuration.DATA;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ContentLengthHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
        return (obj instanceof ContentLengthHeader) && getContentLength() == ((ContentLength) ((ContentLengthHeader) obj)).getContentLength();
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
            stringBuffer.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        } else {
            stringBuffer.append(num.toString());
        }
    }
}
