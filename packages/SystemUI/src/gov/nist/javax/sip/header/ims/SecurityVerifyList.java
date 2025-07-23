package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SecurityVerifyList extends SIPHeaderList<SecurityVerify> {
    private static final long serialVersionUID = 563201040577795125L;

    public SecurityVerifyList() {
        super(SecurityVerify.class, "Security-Verify");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        SecurityVerifyList securityVerifyList = new SecurityVerifyList();
        securityVerifyList.clonehlist(this.hlist);
        return securityVerifyList;
    }
}
