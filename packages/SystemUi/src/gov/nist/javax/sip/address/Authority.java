package gov.nist.javax.sip.address;

import gov.nist.core.HostPort;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Authority extends NetObject {
    private static final long serialVersionUID = -3570349777347017894L;
    protected HostPort hostPort;
    protected UserInfo userInfo;

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        Authority authority = (Authority) super.clone();
        HostPort hostPort = this.hostPort;
        if (hostPort != null) {
            authority.hostPort = (HostPort) hostPort.clone();
        }
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            authority.userInfo = (UserInfo) userInfo.clone();
        }
        return authority;
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        UserInfo userInfo;
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Authority authority = (Authority) obj;
        if (!this.hostPort.equals(authority.hostPort)) {
            return false;
        }
        UserInfo userInfo2 = this.userInfo;
        return userInfo2 == null || (userInfo = authority.userInfo) == null || userInfo2.equals(userInfo);
    }

    public final int hashCode() {
        HostPort hostPort = this.hostPort;
        if (hostPort != null) {
            return hostPort.encode().hashCode();
        }
        throw new UnsupportedOperationException("Null hostPort cannot compute hashcode");
    }

    @Override // gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            userInfo.encode(stringBuffer);
            stringBuffer.append("@");
            this.hostPort.encode(stringBuffer);
        } else {
            this.hostPort.encode(stringBuffer);
        }
        return stringBuffer;
    }
}
