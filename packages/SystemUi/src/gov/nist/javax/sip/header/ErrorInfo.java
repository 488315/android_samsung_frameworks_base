package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.GenericURI;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ErrorInfo extends ParametersHeader {
    private static final long serialVersionUID = -6347702901964436362L;
    protected GenericURI errorInfo;

    public ErrorInfo() {
        super("Error-Info");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        ErrorInfo errorInfo = (ErrorInfo) super.clone();
        GenericURI genericURI = this.errorInfo;
        if (genericURI != null) {
            errorInfo.errorInfo = (GenericURI) genericURI.clone();
        }
        return errorInfo;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer("<");
        stringBuffer.append(this.errorInfo.toString());
        stringBuffer.append(">");
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public final void setErrorInfo(GenericURI genericURI) {
        this.errorInfo = genericURI;
    }

    public ErrorInfo(GenericURI genericURI) {
        this();
        this.errorInfo = genericURI;
    }
}
