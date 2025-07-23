package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
