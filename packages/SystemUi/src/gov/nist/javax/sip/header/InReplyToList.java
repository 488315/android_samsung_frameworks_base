package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
