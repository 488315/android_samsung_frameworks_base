package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RecordRoute extends AddressParametersHeader implements HeaderAddress {
    private static final long serialVersionUID = 2388023364181727205L;

    public RecordRoute(AddressImpl addressImpl) {
        super("Record-Route");
        this.address = addressImpl;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
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

    public RecordRoute() {
        super("Record-Route");
    }
}
