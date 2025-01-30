package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PCalledPartyID extends AddressParametersHeader implements HeaderAddress {
    public PCalledPartyID(AddressImpl addressImpl) {
        super("P-Called-Party-ID");
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
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";" + this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public PCalledPartyID() {
        super("P-Called-Party-ID");
    }
}
