package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
