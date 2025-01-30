package gov.nist.javax.sip.address;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import gov.nist.core.GenericObject;
import gov.nist.core.HostPort;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.HeaderFactoryImpl;
import java.text.ParseException;
import java.util.Iterator;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.SipURI;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:224:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00f3  */
    @Override // gov.nist.javax.sip.address.GenericURI, gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        HostPort hostPort;
        int port;
        Authority authority;
        HostPort hostPort2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SipURI)) {
            return false;
        }
        SipUri sipUri = (SipUri) ((SipURI) obj);
        if (this.scheme.equalsIgnoreCase("sips") ^ sipUri.scheme.equalsIgnoreCase("sips")) {
            return false;
        }
        if ((getUser() == null) ^ (sipUri.getUser() == null)) {
            return false;
        }
        if ((getUserPassword() == null) ^ (sipUri.getUserPassword() == null)) {
            return false;
        }
        if (getUser() != null && !RFC2396UrlDecoder.decode(getUser()).equals(RFC2396UrlDecoder.decode(sipUri.getUser()))) {
            return false;
        }
        if (getUserPassword() != null && !RFC2396UrlDecoder.decode(getUserPassword()).equals(RFC2396UrlDecoder.decode(sipUri.getUserPassword()))) {
            return false;
        }
        if ((getHost() == null) ^ (sipUri.getHost() == null)) {
            return false;
        }
        if (getHost() != null && !getHost().equalsIgnoreCase(sipUri.getHost())) {
            return false;
        }
        Authority authority2 = this.authority;
        if (authority2 != null) {
            HostPort hostPort3 = authority2.hostPort;
            if ((hostPort3 == null ? null : hostPort3.getHost()) != null) {
                hostPort = this.authority.hostPort;
                port = hostPort != null ? -1 : hostPort.getPort();
                authority = sipUri.authority;
                if (authority != null) {
                    HostPort hostPort4 = authority.hostPort;
                    if ((hostPort4 == null ? null : hostPort4.getHost()) != null) {
                        hostPort2 = sipUri.authority.hostPort;
                        if (port != (hostPort2 == null ? -1 : hostPort2.getPort())) {
                            return false;
                        }
                        Iterator parameterNames = getParameterNames();
                        while (parameterNames.hasNext()) {
                            String str = (String) parameterNames.next();
                            String parameter = getParameter(str);
                            String parameter2 = sipUri.getParameter(str);
                            if (parameter != null && parameter2 != null && !RFC2396UrlDecoder.decode(parameter).equalsIgnoreCase(RFC2396UrlDecoder.decode(parameter2))) {
                                return false;
                            }
                        }
                        NameValueList nameValueList = this.uriParms;
                        boolean z = (nameValueList != null ? (String) nameValueList.getValue("transport") : null) == null;
                        NameValueList nameValueList2 = sipUri.uriParms;
                        if (z ^ ((nameValueList2 != null ? (String) nameValueList2.getValue("transport") : null) == null)) {
                            return false;
                        }
                        if ((sipUri.getParameter("user") == null) ^ (getParameter("user") == null)) {
                            return false;
                        }
                        Integer num = (Integer) this.uriParms.getValue("ttl");
                        boolean z2 = (num != null ? num.intValue() : -1) == -1;
                        Integer num2 = (Integer) sipUri.uriParms.getValue("ttl");
                        if (z2 ^ ((num2 != null ? num2.intValue() : -1) == -1)) {
                            return false;
                        }
                        if ((sipUri.getParameter("method") == null) ^ (getParameter("method") == null)) {
                            return false;
                        }
                        NameValue nameValue = this.uriParms.getNameValue("maddr");
                        boolean z3 = (nameValue == null ? null : (String) nameValue.getValueAsObject()) == null;
                        NameValue nameValue2 = sipUri.uriParms.getNameValue("maddr");
                        if (z3 ^ ((nameValue2 == null ? null : (String) nameValue2.getValueAsObject()) == null)) {
                            return false;
                        }
                        if (getHeaderNames().hasNext() && !sipUri.getHeaderNames().hasNext()) {
                            return false;
                        }
                        if (!getHeaderNames().hasNext() && sipUri.getHeaderNames().hasNext()) {
                            return false;
                        }
                        if (getHeaderNames().hasNext() && sipUri.getHeaderNames().hasNext()) {
                            try {
                                SipFactory.getInstance().getClass();
                                try {
                                    new HeaderFactoryImpl();
                                    Iterator headerNames = getHeaderNames();
                                    while (headerNames.hasNext()) {
                                        String str2 = (String) headerNames.next();
                                        String obj2 = this.qheaders.getValue(str2) != null ? this.qheaders.getValue(str2).toString() : null;
                                        String obj3 = sipUri.qheaders.getValue(str2) != null ? sipUri.qheaders.getValue(str2).toString() : null;
                                        if (obj2 == null && obj3 != null) {
                                            return false;
                                        }
                                        if (obj3 == null && obj2 != null) {
                                            return false;
                                        }
                                        if (obj2 != null || obj3 != null) {
                                            try {
                                                if (!HeaderFactoryImpl.createHeader(str2, RFC2396UrlDecoder.decode(obj2)).equals(HeaderFactoryImpl.createHeader(str2, RFC2396UrlDecoder.decode(obj3)))) {
                                                    return false;
                                                }
                                            } catch (ParseException unused) {
                                                toString();
                                                sipUri.toString();
                                                return false;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    if (e instanceof PeerUnavailableException) {
                                        throw ((PeerUnavailableException) e);
                                    }
                                    throw new PeerUnavailableException("Failed to create HeaderFactory", e);
                                }
                            } catch (PeerUnavailableException unused2) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                hostPort2 = null;
                if (port != (hostPort2 == null ? -1 : hostPort2.getPort())) {
                }
            }
        }
        hostPort = null;
        if (hostPort != null) {
        }
        authority = sipUri.authority;
        if (authority != null) {
        }
        hostPort2 = null;
        if (port != (hostPort2 == null ? -1 : hostPort2.getPort())) {
        }
    }

    public final Iterator getHeaderNames() {
        return this.qheaders.getNames();
    }

    public final String getHost() {
        Authority authority = this.authority;
        if (authority == null) {
            return null;
        }
        HostPort hostPort = authority.hostPort;
        if ((hostPort == null ? null : hostPort.getHost()) == null) {
            return null;
        }
        HostPort hostPort2 = this.authority.hostPort;
        return (hostPort2 != null ? hostPort2.getHost() : null).encode();
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
                throw new ParseException(KeyAttributes$$ExternalSyntheticOutline0.m21m("bad parameter ", str2), 0);
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
