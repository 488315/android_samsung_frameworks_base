package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
