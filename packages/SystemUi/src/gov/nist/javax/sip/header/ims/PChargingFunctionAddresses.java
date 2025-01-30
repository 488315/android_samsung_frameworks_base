package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PChargingFunctionAddresses extends ParametersHeader {
    public PChargingFunctionAddresses() {
        super("P-Charging-Function-Addresses");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.duplicates.isEmpty()) {
            stringBuffer.append(this.duplicates.encode());
        }
        return stringBuffer.toString();
    }
}
