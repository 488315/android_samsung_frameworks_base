package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* loaded from: classes4.dex */
public class PVisitedNetworkIDList extends SIPHeaderList<PVisitedNetworkID> {
    private static final long serialVersionUID = -4346667490341752478L;

    public PVisitedNetworkIDList() {
        super(PVisitedNetworkID.class, "P-Visited-Network-ID");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        PVisitedNetworkIDList pVisitedNetworkIDList = new PVisitedNetworkIDList();
        pVisitedNetworkIDList.clonehlist(this.hlist);
        return pVisitedNetworkIDList;
    }
}
