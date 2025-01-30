package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PAssociatedURIList extends SIPHeaderList<PAssociatedURI> {
    private static final long serialVersionUID = 4454306052557362851L;

    public PAssociatedURIList() {
        super(PAssociatedURI.class, "P-Associated-URI");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        PAssociatedURIList pAssociatedURIList = new PAssociatedURIList();
        pAssociatedURIList.clonehlist(this.hlist);
        return pAssociatedURIList;
    }
}
