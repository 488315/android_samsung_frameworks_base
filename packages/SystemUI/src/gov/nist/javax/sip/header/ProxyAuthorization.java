package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class ProxyAuthorization extends AuthenticationHeader implements Header {
    private static final long serialVersionUID = -6374966905199799098L;

    public ProxyAuthorization() {
        super("Proxy-Authorization");
    }
}
