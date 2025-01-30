package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SIPETag extends SIPHeader {
    private static final long serialVersionUID = 3837543366074322107L;
    protected String entityTag;

    public SIPETag() {
        super("SIP-ETag");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.entityTag;
    }

    public final void setETag(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,SIP-ETag, setETag(), the etag parameter is null");
        }
        this.entityTag = str;
    }

    public SIPETag(String str) {
        this();
        setETag(str);
    }
}
