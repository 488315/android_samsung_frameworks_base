package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
