package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PAssertedIdentity extends AddressParametersHeader implements HeaderAddress, Header {
    public PAssertedIdentity(AddressImpl addressImpl) {
        super("P-Asserted-Identity");
        this.address = addressImpl;
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        return (PAssertedIdentity) super.clone();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.address.getAddressType() == 2) {
            stringBuffer.append("<");
        }
        stringBuffer.append(this.address.encode());
        if (this.address.getAddressType() == 2) {
            stringBuffer.append(">");
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append("," + this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public PAssertedIdentity() {
        super("P-Asserted-Identity");
    }
}
