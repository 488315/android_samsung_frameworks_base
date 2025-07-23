package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
