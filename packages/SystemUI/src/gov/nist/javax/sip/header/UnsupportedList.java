package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UnsupportedList extends SIPHeaderList<Unsupported> {
    private static final long serialVersionUID = -4052610269407058661L;

    public UnsupportedList() {
        super(Unsupported.class, "Unsupported");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        UnsupportedList unsupportedList = new UnsupportedList();
        unsupportedList.clonehlist(this.hlist);
        return unsupportedList;
    }
}
