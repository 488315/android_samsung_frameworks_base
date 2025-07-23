package gov.nist.javax.sip.address;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
        if (this.password == null) {
            stringBuffer.append(this.user);
            return stringBuffer;
        }
        stringBuffer.append(this.user);
        stringBuffer.append(":");
        stringBuffer.append(this.password);
        return stringBuffer;
    }
}
