package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ErrorInfoList extends SIPHeaderList<ErrorInfo> {
    private static final long serialVersionUID = 1;

    public ErrorInfoList() {
        super(ErrorInfo.class, "Error-Info");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ErrorInfoList errorInfoList = new ErrorInfoList();
        errorInfoList.clonehlist(this.hlist);
        return errorInfoList;
    }
}
