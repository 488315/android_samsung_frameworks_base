package gov.nist.javax.sip.header;

import com.sec.ims.IMSParameter;
import gov.nist.core.NameValueList;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class Reason extends ParametersHeader implements Header {
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
