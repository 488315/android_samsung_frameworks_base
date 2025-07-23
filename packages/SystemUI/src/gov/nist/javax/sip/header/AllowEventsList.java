package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
