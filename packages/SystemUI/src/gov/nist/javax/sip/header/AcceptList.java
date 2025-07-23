package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AcceptList extends SIPHeaderList<Accept> {
    private static final long serialVersionUID = -1800813338560484831L;

    public AcceptList() {
        super(Accept.class, "Accept");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        AcceptList acceptList = new AcceptList();
        acceptList.clonehlist(this.hlist);
        return acceptList;
    }
}
