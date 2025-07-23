package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
