package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContentLanguageList extends SIPHeaderList<ContentLanguage> {
    private static final long serialVersionUID = -5302265987802886465L;

    public ContentLanguageList() {
        super(ContentLanguage.class, "Content-Language");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ContentLanguageList contentLanguageList = new ContentLanguageList();
        contentLanguageList.clonehlist(this.hlist);
        return contentLanguageList;
    }
}
