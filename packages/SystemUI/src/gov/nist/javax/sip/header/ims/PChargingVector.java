package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PChargingVector extends ParametersHeader implements Header {
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
