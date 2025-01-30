package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AcceptEncoding extends ParametersHeader {
    private static final long serialVersionUID = -1476807565552873525L;
    protected String contentCoding;

    public AcceptEncoding() {
        super("Accept-Encoding");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    public final void setEncoding(String str) {
        if (str == null) {
            throw new NullPointerException(" encoding parameter is null");
        }
        this.contentCoding = str;
    }

    public final void setQValue(float f) {
        double d = f;
        if (d < 0.0d || d > 1.0d) {
            throw new InvalidArgumentException("qvalue out of range!");
        }
        Float valueOf = Float.valueOf(f);
        NameValue nameValue = this.parameters.getNameValue("q");
        if (nameValue != null) {
            nameValue.setValueAsObject(valueOf);
        } else {
            this.parameters.set(new NameValue("q", valueOf));
        }
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        String str = this.contentCoding;
        if (str != null) {
            stringBuffer.append(str);
        }
        NameValueList nameValueList = this.parameters;
        if (nameValueList == null || nameValueList.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        stringBuffer.append(this.parameters.encode());
    }
}
