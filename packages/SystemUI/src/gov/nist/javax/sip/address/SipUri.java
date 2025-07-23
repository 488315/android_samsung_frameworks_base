package gov.nist.javax.sip.address;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import gov.nist.core.GenericObject;
import gov.nist.core.HostPort;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import java.text.ParseException;
import java.util.Iterator;
import javax.sip.address.SipURI;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SipUri extends GenericURI implements SipURI {
    private static final long serialVersionUID = 7749781076218987044L;
    protected Authority authority;
    protected NameValueList qheaders;
    protected TelephoneNumber telephoneSubscriber;
    protected NameValueList uriParms;

    public SipUri() {
        this.scheme = "sip";
        this.uriParms = new NameValueList();
        NameValueList nameValueList = new NameValueList();
        this.qheaders = nameValueList;
        nameValueList.setSeparator("&");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        SipUri sipUri = (SipUri) super.clone();
        Authority authority = this.authority;
        if (authority != null) {
            sipUri.authority = (Authority) authority.clone();
        }
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            sipUri.uriParms = (NameValueList) nameValueList.clone();
        }
        NameValueList nameValueList2 = this.qheaders;
        if (nameValueList2 != null) {
            sipUri.qheaders = (NameValueList) nameValueList2.clone();
        }
        TelephoneNumber telephoneNumber = this.telephoneSubscriber;
        if (telephoneNumber != null) {
            sipUri.telephoneSubscriber = (TelephoneNumber) telephoneNumber.clone();
        }
        return sipUri;
    }

    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:203:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00fc  */
    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(java.lang.Object r8) {
        /*
            Method dump skipped, instructions count: 696
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gov.nist.javax.sip.address.SipUri.equals(java.lang.Object):boolean");
    }

    public final String getHost() {
        Authority authority = this.authority;
        if (authority != null) {
            HostPort hostPort = authority.hostPort;
            if ((hostPort == null ? null : hostPort.getHost()) != null) {
                HostPort hostPort2 = this.authority.hostPort;
                return (hostPort2 != null ? hostPort2.getHost() : null).encode();
            }
        }
        return null;
    }

    @Override // javax.sip.header.Parameters
    public final String getParameter(String str) {
        Object value = this.uriParms.getValue(str);
        if (value == null) {
            return null;
        }
        return value instanceof GenericObject ? ((GenericObject) value).encode() : value.toString();
    }

    @Override // javax.sip.header.Parameters
    public final Iterator getParameterNames() {
        return this.uriParms.getNames();
    }

    public final int getPort() {
        Authority authority = this.authority;
        HostPort hostPort = null;
        if (authority != null) {
            HostPort hostPort2 = authority.hostPort;
            if ((hostPort2 == null ? null : hostPort2.getHost()) != null) {
                hostPort = this.authority.hostPort;
            }
        }
        if (hostPort == null) {
            return -1;
        }
        return hostPort.getPort();
    }

    @Override // gov.nist.javax.sip.address.GenericURI
    public final String getScheme() {
        return this.scheme;
    }

    public final String getUser() {
        UserInfo userInfo = this.authority.userInfo;
        if (userInfo != null) {
            return userInfo.user;
        }
        return null;
    }

    public final String getUserPassword() {
        UserInfo userInfo;
        Authority authority = this.authority;
        if (authority == null || (userInfo = authority.userInfo) == null) {
            return null;
        }
        return userInfo.password;
    }

    public final void removeHeaders() {
        this.qheaders = new NameValueList();
    }

    public final void removeParameter(String str) {
        this.uriParms.delete(str);
    }

    public final void removeParameters() {
        this.uriParms = new NameValueList();
    }

    public final void setHostPort(HostPort hostPort) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        this.authority.hostPort = hostPort;
    }

    public final void setParameter(String str, String str2) {
        if (str.equalsIgnoreCase("ttl")) {
            try {
                Integer.parseInt(str2);
            } catch (NumberFormatException unused) {
                throw new ParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("bad parameter ", str2), 0);
            }
        }
        this.uriParms.set(str2, str);
    }

    public final void setQHeader(NameValue nameValue) {
        this.qheaders.set(nameValue);
    }

    public final void setScheme(String str) {
        if (str.compareToIgnoreCase("sip") != 0 && str.compareToIgnoreCase("sips") != 0) {
            throw new IllegalArgumentException("bad scheme ".concat(str));
        }
        this.scheme = str.toLowerCase();
    }

    public final void setUriParameter(NameValue nameValue) {
        this.uriParms.set(nameValue);
    }

    public final void setUser(String str) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        Authority authority = this.authority;
        if (authority.userInfo == null) {
            authority.userInfo = new UserInfo();
        }
        UserInfo userInfo = authority.userInfo;
        userInfo.user = str;
        if (str == null || (str.indexOf("#") < 0 && str.indexOf(";") < 0)) {
            userInfo.userType = 2;
        } else {
            userInfo.userType = 1;
        }
    }

    public final void setUserParam(String str) {
        this.uriParms.set(str, "user");
    }

    public final void setUserPassword(String str) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        Authority authority = this.authority;
        if (authority.userInfo == null) {
            authority.userInfo = new UserInfo();
        }
        authority.userInfo.password = str;
    }

    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.javax.sip.address.NetObject, javax.sip.address.URI
    public final String toString() {
        return encode();
    }

    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.scheme);
        stringBuffer.append(":");
        Authority authority = this.authority;
        if (authority != null) {
            authority.encode(stringBuffer);
        }
        if (!this.uriParms.isEmpty()) {
            stringBuffer.append(";");
            this.uriParms.encode(stringBuffer);
        }
        if (!this.qheaders.isEmpty()) {
            stringBuffer.append("?");
            this.qheaders.encode(stringBuffer);
        }
        return stringBuffer;
    }
}
