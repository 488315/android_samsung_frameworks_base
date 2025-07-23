package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
