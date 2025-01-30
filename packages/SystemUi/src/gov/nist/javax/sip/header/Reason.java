package gov.nist.javax.sip.header;

import com.sec.ims.IMSParameter;
import gov.nist.core.NameValueList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Reason extends ParametersHeader {
    private static final long serialVersionUID = -8903376965568297388L;
    public final String CAUSE;
    public final String TEXT;
    protected String protocol;

    public Reason() {
        super("Reason");
        this.TEXT = "text";
        this.CAUSE = IMSParameter.CALL.CAUSE;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.protocol);
        NameValueList nameValueList = this.parameters;
        if (nameValueList != null && !nameValueList.isEmpty()) {
            stringBuffer.append(";");
            stringBuffer.append(this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String getName() {
        return "Reason";
    }

    public final void setProtocol(String str) {
        this.protocol = str;
    }
}
