package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class AllowEventsList extends SIPHeaderList<AllowEvents> {
    private static final long serialVersionUID = -684763195336212992L;

    public AllowEventsList() {
        super(AllowEvents.class, "Allow-Events");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        AllowEventsList allowEventsList = new AllowEventsList();
        allowEventsList.clonehlist(this.hlist);
        return allowEventsList;
    }
}
