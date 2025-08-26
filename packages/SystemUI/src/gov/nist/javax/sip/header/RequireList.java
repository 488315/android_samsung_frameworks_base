package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public final class RequireList extends SIPHeaderList<Require> {
    private static final long serialVersionUID = -1760629092046963213L;

    public RequireList() {
        super(Require.class, "Require");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        RequireList requireList = new RequireList();
        requireList.clonehlist(this.hlist);
        return requireList;
    }
}
