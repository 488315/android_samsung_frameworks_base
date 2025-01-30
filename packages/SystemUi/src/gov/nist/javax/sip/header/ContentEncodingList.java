package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContentEncodingList extends SIPHeaderList<ContentEncoding> {
    private static final long serialVersionUID = 7365216146576273970L;

    public ContentEncodingList() {
        super(ContentEncoding.class, "Content-Encoding");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ContentEncodingList contentEncodingList = new ContentEncodingList();
        contentEncodingList.clonehlist(this.hlist);
        return contentEncodingList;
    }
}
