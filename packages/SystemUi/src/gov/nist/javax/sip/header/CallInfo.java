package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CallInfo extends ParametersHeader {
    private static final long serialVersionUID = -8179246487696752928L;
    protected GenericURI info;

    public CallInfo() {
        super("Call-Info");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        CallInfo callInfo = (CallInfo) super.clone();
        GenericURI genericURI = this.info;
        if (genericURI != null) {
            callInfo.info = (GenericURI) genericURI.clone();
        }
        return callInfo;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    public final void setInfo(GenericURI genericURI) {
        this.info = genericURI;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append("<");
        this.info.encode(stringBuffer);
        stringBuffer.append(">");
        NameValueList nameValueList = this.parameters;
        if (nameValueList == null || nameValueList.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        this.parameters.encode(stringBuffer);
    }
}
