package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public final class InReplyToList extends SIPHeaderList<InReplyTo> {
    private static final long serialVersionUID = -7993498496830999237L;

    public InReplyToList() {
        super(InReplyTo.class, "In-Reply-To");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        InReplyToList inReplyToList = new InReplyToList();
        inReplyToList.clonehlist(this.hlist);
        return inReplyToList;
    }
}
