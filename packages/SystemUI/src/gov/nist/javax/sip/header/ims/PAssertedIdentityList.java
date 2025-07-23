package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PAssertedIdentityList extends SIPHeaderList<PAssertedIdentity> {
    private static final long serialVersionUID = -6465152445570308974L;

    public PAssertedIdentityList() {
        super(PAssertedIdentity.class, "P-Asserted-Identity");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        PAssertedIdentityList pAssertedIdentityList = new PAssertedIdentityList();
        pAssertedIdentityList.clonehlist(this.hlist);
        return pAssertedIdentityList;
    }
}
