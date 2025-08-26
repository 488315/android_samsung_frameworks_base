package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public class SupportedList extends SIPHeaderList<Supported> {
    private static final long serialVersionUID = -4539299544895602367L;

    public SupportedList() {
        super(Supported.class, "Supported");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        SupportedList supportedList = new SupportedList();
        supportedList.clonehlist(this.hlist);
        return supportedList;
    }
}
