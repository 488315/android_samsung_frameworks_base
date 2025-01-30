package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReasonList extends SIPHeaderList<Reason> {
    private static final long serialVersionUID = 7459989997463160670L;

    public ReasonList() {
        super(Reason.class, "Reason");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ReasonList reasonList = new ReasonList();
        reasonList.clonehlist(this.hlist);
        return reasonList;
    }
}
