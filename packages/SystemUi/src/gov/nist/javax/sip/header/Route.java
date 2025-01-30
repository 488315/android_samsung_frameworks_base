package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.RouteHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Route extends AddressParametersHeader implements RouteHeader {
    private static final long serialVersionUID = 5683577362998368846L;

    public Route() {
        super("Route");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return (obj instanceof RouteHeader) && super.equals(obj);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final int hashCode() {
        return this.address.getHostPort().encode().toLowerCase().hashCode();
    }

    public Route(AddressImpl addressImpl) {
        super("Route");
        this.address = addressImpl;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        if (this.address.getAddressType() == 1) {
            this.address.encode(stringBuffer);
        } else {
            stringBuffer.append('<');
            this.address.encode(stringBuffer);
            stringBuffer.append('>');
        }
        if (this.parameters.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        this.parameters.encode(stringBuffer);
    }
}
