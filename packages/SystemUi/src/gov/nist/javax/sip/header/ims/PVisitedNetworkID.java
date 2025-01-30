package gov.nist.javax.sip.header.ims;

import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PVisitedNetworkID extends ParametersHeader implements PVisitedNetworkIDHeader {
    private boolean isQuoted;
    private String networkID;

    public PVisitedNetworkID() {
        super("P-Visited-Network-ID");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        PVisitedNetworkID pVisitedNetworkID = (PVisitedNetworkID) super.clone();
        String str = this.networkID;
        if (str != null) {
            pVisitedNetworkID.networkID = str;
        }
        pVisitedNetworkID.isQuoted = this.isQuoted;
        return pVisitedNetworkID;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = this.networkID;
        if (str != null) {
            if (this.isQuoted) {
                stringBuffer.append("\"" + this.networkID + "\"");
            } else {
                stringBuffer.append(str);
            }
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";" + this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (!(obj instanceof PVisitedNetworkIDHeader)) {
            return false;
        }
        PVisitedNetworkIDHeader pVisitedNetworkIDHeader = (PVisitedNetworkIDHeader) obj;
        return this.networkID.equals(((PVisitedNetworkID) pVisitedNetworkIDHeader).networkID) && equalParameters(pVisitedNetworkIDHeader);
    }

    public final void setVisitedNetworkID(Token token) {
        if (token == null) {
            throw new NullPointerException(" the networkID parameter is null");
        }
        this.networkID = token.tokenValue;
        this.isQuoted = false;
    }

    public PVisitedNetworkID(String str) {
        super("P-Visited-Network-ID");
        setVisitedNetworkID(str);
    }

    public PVisitedNetworkID(Token token) {
        super("P-Visited-Network-ID");
        setVisitedNetworkID(token.tokenValue);
    }

    public final void setVisitedNetworkID(String str) {
        if (str != null) {
            this.networkID = str;
            this.isQuoted = true;
            return;
        }
        throw new NullPointerException(" the networkID parameter is null");
    }
}
