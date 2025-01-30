package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ProxyAuthenticateList extends SIPHeaderList<ProxyAuthenticate> {
    private static final long serialVersionUID = 1;

    public ProxyAuthenticateList() {
        super(ProxyAuthenticate.class, "Proxy-Authenticate");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ProxyAuthenticateList proxyAuthenticateList = new ProxyAuthenticateList();
        proxyAuthenticateList.clonehlist(this.hlist);
        return proxyAuthenticateList;
    }
}
