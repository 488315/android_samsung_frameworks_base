package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PMediaAuthorizationList extends SIPHeaderList<PMediaAuthorization> {
    private static final long serialVersionUID = -8226328073989632317L;

    public PMediaAuthorizationList() {
        super(PMediaAuthorization.class, "P-Media-Authorization");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        PMediaAuthorizationList pMediaAuthorizationList = new PMediaAuthorizationList();
        pMediaAuthorizationList.clonehlist(this.hlist);
        return pMediaAuthorizationList;
    }
}
