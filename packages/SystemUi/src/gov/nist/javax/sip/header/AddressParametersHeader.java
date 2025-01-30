package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
