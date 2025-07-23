package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SIPHeader extends SIPObject implements SIPHeaderNames, Header {
    protected String headerName;

    public SIPHeader(String str) {
        this.headerName = str;
    }

    @Override // gov.nist.core.GenericObject
    public String encode() {
        return encode(new StringBuffer()).toString();
    }

    public abstract String encodeBody();

    public void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(encodeBody());
    }

    public String getCallId() {
        return encodeBody();
    }

    public String getName() {
        return this.headerName;
    }

    public int hashCode() {
        return this.headerName.hashCode();
    }

    public final void setHeaderName(String str) {
        this.headerName = str;
    }

    @Override // gov.nist.javax.sip.header.SIPObject
    public final String toString() {
        return encode();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.headerName);
        stringBuffer.append(":");
        stringBuffer.append(" ");
        encodeBody(stringBuffer);
        stringBuffer.append("\r\n");
        return stringBuffer;
    }

    public SIPHeader() {
    }
}
