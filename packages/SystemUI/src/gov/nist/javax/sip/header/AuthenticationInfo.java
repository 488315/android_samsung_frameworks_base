package gov.nist.javax.sip.header;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import gov.nist.core.NameValue;
import java.text.ParseException;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public final class AuthenticationInfo extends ParametersHeader implements Header {
    private static final long serialVersionUID = -4371927900917127057L;

    public AuthenticationInfo() {
        super("Authentication-Info");
        this.parameters.setSeparator(",");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.parameters.encode();
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader
    public final void setParameter(String str, String str2) throws ParseException {
        if (str == null) {
            throw new NullPointerException("null name");
        }
        NameValue nameValue = this.parameters.getNameValue(str.toLowerCase());
        if (nameValue != null) {
            nameValue.setValueAsObject(str2);
            return;
        }
        NameValue nameValue2 = new NameValue(str, str2);
        if (str.equalsIgnoreCase("qop") || str.equalsIgnoreCase("nextnonce") || str.equalsIgnoreCase("realm") || str.equalsIgnoreCase("cnonce") || str.equalsIgnoreCase("nonce") || str.equalsIgnoreCase("opaque") || str.equalsIgnoreCase(EnterpriseContainerConstants.EMAIL_USERNAME) || str.equalsIgnoreCase("domain") || str.equalsIgnoreCase("nextnonce") || str.equalsIgnoreCase("rspauth")) {
            if (str2 == null) {
                throw new NullPointerException("null value");
            }
            if (str2.startsWith("\"")) {
                throw new ParseException(str2.concat(" : Unexpected DOUBLE_QUOTE"), 0);
            }
            nameValue2.setQuotedValue();
        }
        this.parameters.set(nameValue2);
    }
}
