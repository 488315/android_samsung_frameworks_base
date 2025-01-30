package gov.nist.javax.sip.address;

import gov.nist.core.GenericObject;
import gov.nist.core.NameValue;
import java.util.Iterator;
import javax.sip.header.Parameters;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TelURLImpl extends GenericURI implements Parameters {
    private static final long serialVersionUID = 5873527320305915954L;
    protected TelephoneNumber telephoneNumber;

    public TelURLImpl() {
        this.scheme = "tel";
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        TelURLImpl telURLImpl = (TelURLImpl) super.clone();
        TelephoneNumber telephoneNumber = this.telephoneNumber;
        if (telephoneNumber != null) {
            telURLImpl.telephoneNumber = (TelephoneNumber) telephoneNumber.clone();
        }
        return telURLImpl;
    }

    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // javax.sip.header.Parameters
    public final String getParameter(String str) {
        Object value = this.telephoneNumber.parameters.getValue(str);
        if (value == null) {
            return null;
        }
        return value instanceof GenericObject ? ((GenericObject) value).encode() : value.toString();
    }

    @Override // javax.sip.header.Parameters
    public final Iterator getParameterNames() {
        return this.telephoneNumber.parameters.getNames();
    }

    public final String getPhoneNumber() {
        return this.telephoneNumber.phoneNumber;
    }

    @Override // gov.nist.javax.sip.address.GenericURI
    public final String getScheme() {
        return this.scheme;
    }

    public final boolean isGlobal() {
        return this.telephoneNumber.isglobal;
    }

    public final void removeParameter(String str) {
        this.telephoneNumber.parameters.delete(str);
    }

    public final void setParameter(String str, String str2) {
        TelephoneNumber telephoneNumber = this.telephoneNumber;
        telephoneNumber.getClass();
        telephoneNumber.parameters.set(new NameValue(str, str2));
    }

    public final void setTelephoneNumber(TelephoneNumber telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.javax.sip.address.NetObject, javax.sip.address.URI
    public final String toString() {
        return this.scheme + ":" + this.telephoneNumber.encode();
    }

    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.scheme);
        stringBuffer.append(':');
        this.telephoneNumber.encode(stringBuffer);
        return stringBuffer;
    }
}
