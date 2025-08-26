package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class ProxyRequire extends SIPHeader implements Header {
    private static final long serialVersionUID = -3269274234851067893L;
    protected String optionTag;

    public ProxyRequire() {
        super("Proxy-Require");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.optionTag;
    }

    public final void setOptionTag(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, ProxyRequire, setOptionTag(), the optionTag parameter is null");
        }
        this.optionTag = str;
    }

    public ProxyRequire(String str) {
        super("Proxy-Require");
        this.optionTag = str;
    }
}
