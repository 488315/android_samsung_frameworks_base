package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
