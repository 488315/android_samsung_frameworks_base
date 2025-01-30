package gov.nist.javax.sip.header;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import gov.nist.core.NameValue;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AuthenticationInfo extends ParametersHeader {
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
    public final void setParameter(String str, String str2) {
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
