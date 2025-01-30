package gov.nist.javax.sip.address;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UserInfo extends NetObject {
    private static final long serialVersionUID = 7268593273924256144L;
    protected String password;
    protected String user;
    protected int userType;

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (UserInfo.class != obj.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) obj;
        if (this.userType != userInfo.userType || !this.user.equalsIgnoreCase(userInfo.user)) {
            return false;
        }
        String str = this.password;
        if (str != null && userInfo.password == null) {
            return false;
        }
        String str2 = userInfo.password;
        if (str2 != null && str == null) {
            return false;
        }
        if (str == str2) {
            return true;
        }
        return str.equals(str2);
    }

    @Override // gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        if (this.password != null) {
            stringBuffer.append(this.user);
            stringBuffer.append(":");
            stringBuffer.append(this.password);
        } else {
            stringBuffer.append(this.user);
        }
        return stringBuffer;
    }
}
