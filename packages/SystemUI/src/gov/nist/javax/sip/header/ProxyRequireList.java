package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public class ProxyRequireList extends SIPHeaderList<ProxyRequire> {
    private static final long serialVersionUID = 5648630649476486042L;

    public ProxyRequireList() {
        super(ProxyRequire.class, "Proxy-Require");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ProxyRequireList proxyRequireList = new ProxyRequireList();
        proxyRequireList.clonehlist(this.hlist);
        return proxyRequireList;
    }
}
