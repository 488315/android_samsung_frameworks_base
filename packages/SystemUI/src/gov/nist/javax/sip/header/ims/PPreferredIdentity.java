package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;

/* loaded from: classes4.dex */
public class PPreferredIdentity extends AddressParametersHeader implements HeaderAddress, Header {
    public PPreferredIdentity(AddressImpl addressImpl) {
        super("P-Preferred-Identity");
        this.address = addressImpl;
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
        return stringBuffer.toString();
    }

    public PPreferredIdentity() {
        super("P-Preferred-Identity");
    }
}
