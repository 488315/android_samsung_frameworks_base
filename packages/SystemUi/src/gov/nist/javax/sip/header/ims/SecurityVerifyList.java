package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
