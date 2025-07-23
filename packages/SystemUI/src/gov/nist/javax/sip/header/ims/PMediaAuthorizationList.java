package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
