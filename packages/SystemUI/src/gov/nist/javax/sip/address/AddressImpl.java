package gov.nist.javax.sip.address;

import gov.nist.core.HostPort;
import javax.sip.address.Address;

/* loaded from: classes4.dex */
public final class AddressImpl extends NetObject implements Address {
    private static final long serialVersionUID = 429592779568617259L;
    protected GenericURI address;
    protected int addressType = 1;
    protected String displayName;

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        AddressImpl addressImpl = (AddressImpl) super.clone();
        GenericURI genericURI = this.address;
        if (genericURI != null) {
            addressImpl.address = (GenericURI) genericURI.clone();
        }
        return addressImpl;
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Address) {
            return this.address.equals(((AddressImpl) ((Address) obj)).address);
        }
        return false;
    }

    public final int getAddressType() {
        return this.addressType;
    }

    public final HostPort getHostPort() {
        GenericURI genericURI = this.address;
        if (!(genericURI instanceof SipUri)) {
            throw new RuntimeException("address is not a SipUri");
        }
        SipUri sipUri = (SipUri) genericURI;
        Authority authority = sipUri.authority;
        if (authority != null) {
            HostPort hostPort = authority.hostPort;
            if ((hostPort == null ? null : hostPort.getHost()) != null) {
                return sipUri.authority.hostPort;
            }
        }
        return null;
    }

    public final int hashCode() {
        return this.address.hashCode();
    }

    public final void setAddressType(int i) {
        this.addressType = i;
    }

    public final void setDisplayName(String str) {
        this.displayName = str;
        this.addressType = 1;
    }

    public final void setURI(GenericURI genericURI) {
        this.address = genericURI;
    }

    public final void setWildCardFlag() {
        this.addressType = 3;
        SipUri sipUri = new SipUri();
        this.address = sipUri;
        sipUri.setUser("*");
    }

    @Override // gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        if (this.addressType == 3) {
            stringBuffer.append('*');
            return stringBuffer;
        }
        if (this.displayName != null) {
            stringBuffer.append("\"");
            stringBuffer.append(this.displayName);
            stringBuffer.append("\"");
            stringBuffer.append(" ");
        }
        if (this.address != null) {
            if (this.addressType == 1 || this.displayName != null) {
                stringBuffer.append("<");
            }
            this.address.encode(stringBuffer);
            if (this.addressType == 1 || this.displayName != null) {
                stringBuffer.append(">");
            }
        }
        return stringBuffer;
    }
}
