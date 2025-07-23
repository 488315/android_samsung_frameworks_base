package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
