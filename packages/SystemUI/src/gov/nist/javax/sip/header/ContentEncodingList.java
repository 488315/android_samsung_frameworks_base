package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
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
