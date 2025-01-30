package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
