package gov.nist.javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SIPETag extends SIPHeader implements Header {
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

    public SIPETag(String str) throws ParseException {
        this();
        setETag(str);
    }
}
