package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PAccessNetworkInfo extends ParametersHeader implements PAccessNetworkInfoHeader {
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
