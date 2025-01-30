package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ProxyRequire extends SIPHeader {
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
