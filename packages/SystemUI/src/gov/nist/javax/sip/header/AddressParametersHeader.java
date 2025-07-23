package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AddressParametersHeader extends ParametersHeader {
    protected AddressImpl address;

    public AddressParametersHeader(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public Object clone() {
        AddressParametersHeader addressParametersHeader = (AddressParametersHeader) super.clone();
        AddressImpl addressImpl = this.address;
        if (addressImpl != null) {
            addressParametersHeader.address = (AddressImpl) addressImpl.clone();
        }
        return addressParametersHeader;
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HeaderAddress) || !(obj instanceof Parameters)) {
            return false;
        }
        HeaderAddress headerAddress = (HeaderAddress) obj;
        return getAddress().equals(headerAddress.getAddress()) && equalParameters((Parameters) headerAddress);
    }

    public AddressImpl getAddress() {
        return this.address;
    }

    public void setAddress(AddressImpl addressImpl) {
        this.address = addressImpl;
    }

    public AddressParametersHeader(String str, boolean z) {
        super(str, z);
    }
}
