package gov.nist.core;

import java.net.InetAddress;

/* loaded from: classes4.dex */
public class Host extends GenericObject {
    private static final long serialVersionUID = -7233564517978323344L;
    protected int addressType;
    protected String hostname;
    private InetAddress inetAddress;
    private boolean stripAddressScopeZones;

    public Host() {
        this.stripAddressScopeZones = false;
        this.addressType = 1;
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj != null && getClass().equals(obj.getClass())) {
            return ((Host) obj).hostname.equals(this.hostname);
        }
        return false;
    }

    public final String getHostname() {
        return this.hostname;
    }

    public final int hashCode() {
        return this.hostname.hashCode();
    }

    public final void setHost(int i, String str) {
        int iIndexOf;
        this.inetAddress = null;
        if (str == null || str.indexOf(58) == -1) {
            this.addressType = i;
        } else {
            this.addressType = 3;
        }
        if (str != null) {
            String strTrim = str.trim();
            this.hostname = strTrim;
            if (this.addressType == 1) {
                this.hostname = strTrim.toLowerCase();
            }
            if (this.addressType == 3 && this.stripAddressScopeZones && (iIndexOf = this.hostname.indexOf(37)) != -1) {
                this.hostname = this.hostname.substring(0, iIndexOf);
            }
        }
    }

    @Override // gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        if (this.addressType == 3) {
            String str = this.hostname;
            if (str.charAt(0) != '[' || str.charAt(str.length() - 1) != ']') {
                stringBuffer.append('[');
                stringBuffer.append(this.hostname);
                stringBuffer.append(']');
                return stringBuffer;
            }
        }
        stringBuffer.append(this.hostname);
        return stringBuffer;
    }

    public Host(String str) throws IllegalArgumentException {
        this.stripAddressScopeZones = false;
        if (str != null) {
            this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
            setHost(2, str);
            return;
        }
        throw new IllegalArgumentException("null host name");
    }

    public Host(String str, int i) {
        this.stripAddressScopeZones = false;
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
        setHost(i, str);
    }
}
