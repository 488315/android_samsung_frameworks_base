package gov.nist.javax.sip.header;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import gov.nist.core.NameValue;
import java.text.ParseException;

/* loaded from: classes4.dex */
public abstract class AuthenticationHeader extends ParametersHeader {
    protected String scheme;

    public AuthenticationHeader(String str) {
        super(str);
        this.parameters.setSeparator(",");
        this.scheme = "Digest";
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        this.parameters.setSeparator(",");
        return this.scheme + " " + this.parameters.encode();
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader
    public final void setParameter(String str, String str2) throws ParseException {
        NameValue nameValue = this.parameters.getNameValue(str.toLowerCase());
        if (nameValue != null) {
            nameValue.setValueAsObject(str2);
            return;
        }
        NameValue nameValue2 = new NameValue(str, str2);
        if (str.equalsIgnoreCase("qop") || str.equalsIgnoreCase("realm") || str.equalsIgnoreCase("cnonce") || str.equalsIgnoreCase("nonce") || str.equalsIgnoreCase(EnterpriseContainerConstants.EMAIL_USERNAME) || str.equalsIgnoreCase("domain") || str.equalsIgnoreCase("opaque") || str.equalsIgnoreCase("nextnonce") || str.equalsIgnoreCase("uri") || str.equalsIgnoreCase("response") || str.equalsIgnoreCase("ik") || str.equalsIgnoreCase("ck") || str.equalsIgnoreCase("integrity-protected")) {
            if ((!(this instanceof Authorization) && !(this instanceof ProxyAuthorization)) || !str.equalsIgnoreCase("qop")) {
                nameValue2.setQuotedValue();
            }
            if (str2 == null) {
                throw new NullPointerException("null value");
            }
            if (str2.startsWith("\"")) {
                throw new ParseException(str2.concat(" : Unexpected DOUBLE_QUOTE"), 0);
            }
        }
        this.parameters.set(nameValue2);
    }

    public final void setScheme(String str) {
        this.scheme = str;
    }

    public AuthenticationHeader() {
        this.parameters.setSeparator(",");
    }
}
