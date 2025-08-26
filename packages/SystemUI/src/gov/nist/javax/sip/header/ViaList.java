package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public final class ViaList extends SIPHeaderList<Via> {
    private static final long serialVersionUID = 3899679374556152313L;

    public ViaList() {
        super(Via.class, "Via");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ViaList viaList = new ViaList();
        viaList.clonehlist(this.hlist);
        return viaList;
    }
}
