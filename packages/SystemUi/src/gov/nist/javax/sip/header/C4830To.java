package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.ToHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: gov.nist.javax.sip.header.To */
/* loaded from: classes3.dex */
public final class C4830To extends AddressParametersHeader implements ToHeader {
    private static final long serialVersionUID = -4057413800584586316L;

    public C4830To() {
        super("To", true);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        return this.headerName + ": " + encodeBody() + "\r\n";
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return (obj instanceof ToHeader) && super.equals(obj);
    }

    public C4830To(From from) {
        super("To");
        this.address = from.address;
        this.parameters = from.parameters;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        AddressImpl addressImpl = this.address;
        if (addressImpl != null) {
            if (addressImpl.getAddressType() == 2) {
                stringBuffer.append("<");
            }
            this.address.encode(stringBuffer);
            if (this.address.getAddressType() == 2) {
                stringBuffer.append(">");
            }
            if (this.parameters.isEmpty()) {
                return;
            }
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
    }
}
