package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CallInfoList extends SIPHeaderList<CallInfo> {
    private static final long serialVersionUID = -4949850334388806423L;

    public CallInfoList() {
        super(CallInfo.class, "Call-Info");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        CallInfoList callInfoList = new CallInfoList();
        callInfoList.clonehlist(this.hlist);
        return callInfoList;
    }
}
