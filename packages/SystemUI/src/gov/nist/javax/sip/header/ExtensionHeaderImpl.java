package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ExtensionHeaderImpl extends SIPHeader implements Header {
    private static final long serialVersionUID = -8693922839612081849L;
    protected String value;

    public ExtensionHeaderImpl() {
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer(this.headerName);
        stringBuffer.append(": ");
        stringBuffer.append(this.value);
        stringBuffer.append("\r\n");
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String str = this.value;
        if (str != null) {
            return str;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer(encode());
            while (stringBuffer.length() > 0 && stringBuffer.charAt(0) != ':') {
                stringBuffer.deleteCharAt(0);
            }
            stringBuffer.deleteCharAt(0);
            String trim = stringBuffer.toString().trim();
            this.value = trim;
            return trim;
        } catch (Exception unused) {
            return null;
        }
    }

    public final void setValue(String str) {
        this.value = str;
    }

    public ExtensionHeaderImpl(String str) {
        super(str);
    }
}
