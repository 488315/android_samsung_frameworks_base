package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public class ProxyAuthorizationList extends SIPHeaderList<ProxyAuthorization> {
    private static final long serialVersionUID = -1;

    public ProxyAuthorizationList() {
        super(ProxyAuthorization.class, "Proxy-Authorization");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ProxyAuthorizationList proxyAuthorizationList = new ProxyAuthorizationList();
        proxyAuthorizationList.clonehlist(this.hlist);
        return proxyAuthorizationList;
    }
}
