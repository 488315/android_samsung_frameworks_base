package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.FromHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class From extends AddressParametersHeader implements FromHeader {
    private static final long serialVersionUID = -6312727234330643892L;

    public From() {
        super("From");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return (obj instanceof FromHeader) && super.equals(obj);
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader
    public final void setAddress(AddressImpl addressImpl) {
        this.address = addressImpl;
    }

    public From(C4830To c4830To) {
        super("From");
        this.address = c4830To.address;
        this.parameters = c4830To.parameters;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        if (this.address.getAddressType() == 2) {
            stringBuffer.append("<");
        }
        this.address.encode(stringBuffer);
        if (this.address.getAddressType() == 2) {
            stringBuffer.append(">");
        }
        if (this.parameters.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        this.parameters.encode(stringBuffer);
    }
}
