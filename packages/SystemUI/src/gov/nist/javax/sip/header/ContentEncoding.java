package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContentEncoding extends SIPHeader implements Header {
    private static final long serialVersionUID = 2034230276579558857L;
    protected String contentEncoding;

    public ContentEncoding() {
        super("Content-Encoding");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.contentEncoding;
    }

    public final void setEncoding(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,  encoding is null");
        }
        this.contentEncoding = str;
    }

    public ContentEncoding(String str) {
        super("Content-Encoding");
        this.contentEncoding = str;
    }
}
