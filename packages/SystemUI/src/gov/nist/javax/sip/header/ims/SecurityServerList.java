package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SecurityServerList extends SIPHeaderList<SecurityServer> {
    private static final long serialVersionUID = -1392066520803180238L;

    public SecurityServerList() {
        super(SecurityServer.class, "Security-Server");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        SecurityServerList securityServerList = new SecurityServerList();
        securityServerList.clonehlist(this.hlist);
        return securityServerList;
    }
}
