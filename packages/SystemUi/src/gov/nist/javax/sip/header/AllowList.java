package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
