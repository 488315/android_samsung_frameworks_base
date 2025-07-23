package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CallInfo extends ParametersHeader implements Header {
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
