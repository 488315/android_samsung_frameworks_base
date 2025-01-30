package gov.nist.javax.sip.header.extensions;

import gov.nist.javax.sip.header.ParametersHeader;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class References extends ParametersHeader {
    private static final long serialVersionUID = 8536961681006637622L;
    private String callId;

    public References() {
        super("References");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        if (this.parameters.isEmpty()) {
            return this.callId;
        }
        return this.callId + ";" + this.parameters.encode();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String getName() {
        return "References";
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, javax.sip.header.Parameters
    public final String getParameter(String str) {
        return super.getParameter(str);
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, javax.sip.header.Parameters
    public final Iterator getParameterNames() {
        return super.getParameterNames();
    }

    public final void setCallId(String str) {
        this.callId = str;
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader
    public final void setParameter(String str, String str2) {
        super.setParameter(str, str2);
    }
}
