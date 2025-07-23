package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AllowList extends SIPHeaderList<Allow> {
    private static final long serialVersionUID = -4699795429662562358L;

    public AllowList() {
        super(Allow.class, "Allow");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        AllowList allowList = new AllowList();
        allowList.clonehlist(this.hlist);
        return allowList;
    }
}
