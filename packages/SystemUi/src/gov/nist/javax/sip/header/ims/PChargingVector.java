package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PChargingVector extends ParametersHeader {
    public PChargingVector() {
        super("P-Charging-Vector");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        getNameValue().encode(stringBuffer);
        if (this.parameters.containsKey("icid-generated-at")) {
            stringBuffer.append(";");
            stringBuffer.append("icid-generated-at");
            stringBuffer.append("=");
            stringBuffer.append(getParameter("icid-generated-at"));
        }
        if (this.parameters.containsKey("term-ioi")) {
            stringBuffer.append(";");
            stringBuffer.append("term-ioi");
            stringBuffer.append("=");
            stringBuffer.append(getParameter("term-ioi"));
        }
        if (this.parameters.containsKey("orig-ioi")) {
            stringBuffer.append(";");
            stringBuffer.append("orig-ioi");
            stringBuffer.append("=");
            stringBuffer.append(getParameter("orig-ioi"));
        }
        return stringBuffer.toString();
    }
}
