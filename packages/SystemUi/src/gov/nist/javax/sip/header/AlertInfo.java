package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.GenericURI;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AlertInfo extends ParametersHeader {
    private static final long serialVersionUID = 4159657362051508719L;
    protected String string;
    protected GenericURI uri;

    public AlertInfo() {
        super("Alert-Info");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        AlertInfo alertInfo = (AlertInfo) super.clone();
        GenericURI genericURI = this.uri;
        if (genericURI != null) {
            alertInfo.uri = (GenericURI) genericURI.clone();
        } else {
            String str = this.string;
            if (str != null) {
                alertInfo.string = str;
            }
        }
        return alertInfo;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.uri != null) {
            stringBuffer.append("<");
            stringBuffer.append(this.uri.encode());
            stringBuffer.append(">");
        } else {
            String str = this.string;
            if (str != null) {
                stringBuffer.append(str);
            }
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public final void setAlertInfo(GenericURI genericURI) {
        this.uri = genericURI;
    }

    public final void setAlertInfo(String str) {
        this.string = str;
    }
}
