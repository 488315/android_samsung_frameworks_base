package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
