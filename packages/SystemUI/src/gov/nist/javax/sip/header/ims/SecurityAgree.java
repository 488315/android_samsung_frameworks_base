package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValue;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.header.Parameters;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SecurityAgree extends ParametersHeader {
    private String secMechanism;

    public SecurityAgree(String str) {
        super(str);
        this.parameters.setSeparator(";");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        SecurityAgree securityAgree = (SecurityAgree) super.clone();
        String str = this.secMechanism;
        if (str != null) {
            securityAgree.secMechanism = str;
        }
        return securityAgree;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.secMechanism + "; " + this.parameters.encode();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj instanceof SecurityAgreeHeader) {
            Parameters parameters = (SecurityAgreeHeader) obj;
            if (this.secMechanism.equals(((SecurityAgree) parameters).secMechanism) && equalParameters(parameters)) {
                return true;
            }
        }
        return false;
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader
    public final void setParameter(String str, String str2) {
        if (str2 == null) {
            throw new NullPointerException("null value");
        }
        NameValue nameValue = this.parameters.getNameValue(str.toLowerCase());
        if (nameValue != null) {
            nameValue.setValueAsObject(str2);
            return;
        }
        NameValue nameValue2 = new NameValue(str, str2);
        if (str.equalsIgnoreCase("d-ver")) {
            nameValue2.setQuotedValue();
            if (str2.startsWith("\"")) {
                throw new ParseException(str2.concat(" : Unexpected DOUBLE_QUOTE"), 0);
            }
        }
        setParameter(nameValue2);
    }

    public final void setSecurityMechanism(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, SecurityAgree, setSecurityMechanism(), the sec-mechanism parameter is null");
        }
        this.secMechanism = str;
    }

    public SecurityAgree() {
        this.parameters.setSeparator(";");
    }
}
