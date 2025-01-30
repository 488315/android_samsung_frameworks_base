package gov.nist.javax.sip.header;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import javax.sip.header.ViaHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
        int i = -1;
        if (((parameter == null || parameter.equals("")) ? -1 : Integer.valueOf(parameter).intValue()) != -1) {
            String parameter2 = getParameter("rport");
            if (parameter2 != null && !parameter2.equals("")) {
                i = Integer.valueOf(parameter2).intValue();
            }
            via.parameters.set(Integer.valueOf(i), "rport");
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
        if (getProtocol().equalsIgnoreCase(via.getProtocol())) {
            Protocol protocol = this.sentProtocol;
            String str = null;
            String str2 = protocol == null ? null : protocol.transport;
            Protocol protocol2 = via.sentProtocol;
            if (str2.equalsIgnoreCase(protocol2 == null ? null : protocol2.transport)) {
                HostPort hostPort = this.sentBy;
                String hostname = (hostPort == null || (host = hostPort.getHost()) == null) ? null : host.getHostname();
                HostPort hostPort2 = via.sentBy;
                if (hostPort2 != null && (host2 = hostPort2.getHost()) != null) {
                    str = host2.getHostname();
                }
                if (hostname.equalsIgnoreCase(str)) {
                    HostPort hostPort3 = this.sentBy;
                    int port = hostPort3 == null ? -1 : hostPort3.getPort();
                    HostPort hostPort4 = via.sentBy;
                    if (port == (hostPort4 != null ? hostPort4.getPort() : -1) && equalParameters(via)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
