package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class AcceptLanguageList extends SIPHeaderList<AcceptLanguage> {
    private static final long serialVersionUID = -3289606805203488840L;

    public AcceptLanguageList() {
        super(AcceptLanguage.class, "Accept-Language");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        AcceptLanguageList acceptLanguageList = new AcceptLanguageList();
        acceptLanguageList.clonehlist(this.hlist);
        return acceptLanguageList;
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList
    public final Header getFirst() {
        AcceptLanguage acceptLanguage = (AcceptLanguage) super.getFirst();
        return acceptLanguage != null ? acceptLanguage : new AcceptLanguage();
    }
}
