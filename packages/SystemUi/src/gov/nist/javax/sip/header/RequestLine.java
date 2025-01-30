package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.GenericURI;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RequestLine extends SIPObject {
    private static final long serialVersionUID = -3286426172326043129L;
    protected String method;
    protected String sipVersion = "SIP/2.0";
    protected GenericURI uri;

    public RequestLine() {
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        RequestLine requestLine = (RequestLine) super.clone();
        GenericURI genericURI = this.uri;
        if (genericURI != null) {
            requestLine.uri = (GenericURI) genericURI.clone();
        }
        return requestLine;
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        RequestLine requestLine = (RequestLine) obj;
        try {
            if (this.method.equals(requestLine.method) && this.uri.equals(requestLine.uri)) {
                return this.sipVersion.equals(requestLine.sipVersion);
            }
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    public final String getMethod() {
        return this.method;
    }

    public final void setMethod(String str) {
        this.method = str;
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        String str = this.method;
        if (str != null) {
            stringBuffer.append(str);
            stringBuffer.append(" ");
        }
        GenericURI genericURI = this.uri;
        if (genericURI != null) {
            genericURI.encode(stringBuffer);
            stringBuffer.append(" ");
        }
        stringBuffer.append(this.sipVersion);
        stringBuffer.append("\r\n");
        return stringBuffer;
    }

    public RequestLine(GenericURI genericURI, String str) {
        this.uri = genericURI;
        this.method = str;
    }
}
