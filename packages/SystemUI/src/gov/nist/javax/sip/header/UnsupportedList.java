package gov.nist.javax.sip.header;

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
