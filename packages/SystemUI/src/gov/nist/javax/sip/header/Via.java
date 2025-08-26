package gov.nist.javax.sip.header;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import javax.sip.header.ViaHeader;

/* loaded from: classes4.dex */
public class Via extends ParametersHeader implements ViaHeader {
    private static final long serialVersionUID = 5281728373401351378L;
    protected String comment;
    private boolean rPortFlag;
    protected HostPort sentBy;
    protected Protocol sentProtocol;

    public Via() {
        super("Via");
        this.rPortFlag = false;
        this.sentProtocol = new Protocol();
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        Via via = (Via) super.clone();
        Protocol protocol = this.sentProtocol;
        if (protocol != null) {
            via.sentProtocol = (Protocol) protocol.clone();
        }
        HostPort hostPort = this.sentBy;
        if (hostPort != null) {
            via.sentBy = (HostPort) hostPort.clone();
        }
        String parameter = getParameter("rport");
        int iIntValue = -1;
        if (((parameter == null || parameter.equals("")) ? -1 : Integer.valueOf(parameter).intValue()) != -1) {
            String parameter2 = getParameter("rport");
            if (parameter2 != null && !parameter2.equals("")) {
                iIntValue = Integer.valueOf(parameter2).intValue();
            }
            via.parameters.set(Integer.valueOf(iIntValue), "rport");
        }
        return via;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        Host host;
        Host host2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViaHeader)) {
            return false;
        }
        Via via = (Via) ((ViaHeader) obj);
        if (!getProtocol().equalsIgnoreCase(via.getProtocol())) {
            return false;
        }
        Protocol protocol = this.sentProtocol;
        String hostname = null;
        String str = protocol == null ? null : protocol.transport;
        Protocol protocol2 = via.sentProtocol;
        if (!str.equalsIgnoreCase(protocol2 == null ? null : protocol2.transport)) {
            return false;
        }
        HostPort hostPort = this.sentBy;
        String hostname2 = (hostPort == null || (host2 = hostPort.getHost()) == null) ? null : host2.getHostname();
        HostPort hostPort2 = via.sentBy;
        if (hostPort2 != null && (host = hostPort2.getHost()) != null) {
            hostname = host.getHostname();
        }
        if (!hostname2.equalsIgnoreCase(hostname)) {
            return false;
        }
        HostPort hostPort3 = this.sentBy;
        int port = hostPort3 == null ? -1 : hostPort3.getPort();
        HostPort hostPort4 = via.sentBy;
        return port == (hostPort4 != null ? hostPort4.getPort() : -1) && equalParameters(via);
    }

    public final String getProtocol() {
        Protocol protocol = this.sentProtocol;
        if (protocol == null) {
            return null;
        }
        return protocol.protocolName + '/' + protocol.protocolVersion;
    }

    public final void setComment(String str) {
        this.comment = str;
    }

    public final void setSentBy(HostPort hostPort) {
        this.sentBy = hostPort;
    }

    public final void setSentProtocol(Protocol protocol) {
        this.sentProtocol = protocol;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        this.sentProtocol.encode(stringBuffer);
        stringBuffer.append(" ");
        this.sentBy.encode(stringBuffer);
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        if (this.comment != null) {
            stringBuffer.append(" ");
            stringBuffer.append("(");
            stringBuffer.append(this.comment);
            stringBuffer.append(")");
        }
        if (this.rPortFlag) {
            stringBuffer.append(";rport");
        }
    }
}
