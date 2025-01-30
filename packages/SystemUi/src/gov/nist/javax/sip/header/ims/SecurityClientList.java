package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SecurityClientList extends SIPHeaderList<SecurityClient> {
    private static final long serialVersionUID = 3094231003329176217L;

    public SecurityClientList() {
        super(SecurityClient.class, "Security-Client");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        SecurityClientList securityClientList = new SecurityClientList();
        securityClientList.clonehlist(this.hlist);
        return securityClientList;
    }
}
