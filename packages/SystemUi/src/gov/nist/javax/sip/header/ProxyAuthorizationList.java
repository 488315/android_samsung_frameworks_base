package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
