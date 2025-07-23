package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.RouteHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
