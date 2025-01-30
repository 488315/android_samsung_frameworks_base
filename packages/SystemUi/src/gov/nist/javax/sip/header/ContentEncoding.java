package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ContentEncoding extends SIPHeader {
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
