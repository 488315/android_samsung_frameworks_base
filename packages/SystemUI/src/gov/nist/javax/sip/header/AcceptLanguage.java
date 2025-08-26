package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public final class AcceptLanguage extends ParametersHeader implements Header {
    private static final long serialVersionUID = -4473982069737324919L;
    protected String languageRange;

    public AcceptLanguage() {
        super("Accept-Language");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = this.languageRange;
        if (str != null) {
            stringBuffer.append(str);
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public final void setLanguageRange(String str) {
        this.languageRange = str.trim();
    }

    public final void setQValue(float f) throws InvalidArgumentException {
        double d = f;
        if (d < 0.0d || d > 1.0d) {
            throw new InvalidArgumentException("qvalue out of range!");
        }
        if (f == -1.0f) {
            this.parameters.delete("q");
        } else {
            this.parameters.set(new NameValue("q", Float.valueOf(f)));
        }
    }
}
