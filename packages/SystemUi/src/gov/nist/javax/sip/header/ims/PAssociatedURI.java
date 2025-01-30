package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PAssociatedURI extends AddressParametersHeader implements HeaderAddress {
    public PAssociatedURI() {
        super("P-Associated-URI");
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        PAssociatedURI pAssociatedURI = (PAssociatedURI) super.clone();
        AddressImpl addressImpl = this.address;
        if (addressImpl != null) {
            pAssociatedURI.address = (AddressImpl) addressImpl.clone();
        }
        return pAssociatedURI;
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

    public PAssociatedURI(AddressImpl addressImpl) {
        super("P-Associated-URI");
        this.address = addressImpl;
    }

    public PAssociatedURI(GenericURI genericURI) {
        super("P-Associated-URI");
        AddressImpl addressImpl = new AddressImpl();
        this.address = addressImpl;
        addressImpl.setURI(genericURI);
    }
}
