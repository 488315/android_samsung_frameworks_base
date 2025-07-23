package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
