package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PAccessNetworkInfo extends ParametersHeader implements PAccessNetworkInfoHeader, Header {
    private String accessType;
    private Object extendAccessInfo;

    public PAccessNetworkInfo() {
        super("P-Access-Network-Info");
        this.parameters.setSeparator(";");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        return (PAccessNetworkInfo) super.clone();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = this.accessType;
        if (str != null) {
            stringBuffer.append(str);
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append("; " + this.parameters.encode());
        }
        if (this.extendAccessInfo != null) {
            stringBuffer.append("; " + this.extendAccessInfo.toString());
        }
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return (obj instanceof PAccessNetworkInfoHeader) && super.equals(obj);
    }

    public final void setAccessType(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, P-Access-Network-Info, setAccessType(), the accessType parameter is null.");
        }
        this.accessType = str;
    }

    public PAccessNetworkInfo(String str) {
        this();
        setAccessType(str);
    }
}
