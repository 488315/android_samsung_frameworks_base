package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
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
    /* renamed from: getFirst */
    public final SIPHeader mo3438getFirst() {
        AcceptLanguage acceptLanguage = (AcceptLanguage) super.mo3438getFirst();
        return acceptLanguage != null ? acceptLanguage : new AcceptLanguage();
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList
    /* renamed from: getLast */
    public final SIPHeader mo3439getLast() {
        AcceptLanguage acceptLanguage = (AcceptLanguage) super.mo3439getLast();
        return acceptLanguage != null ? acceptLanguage : new AcceptLanguage();
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList
    /* renamed from: getFirst, reason: collision with other method in class */
    public final Object mo3438getFirst() {
        AcceptLanguage acceptLanguage = (AcceptLanguage) super.mo3438getFirst();
        return acceptLanguage != null ? acceptLanguage : new AcceptLanguage();
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList
    /* renamed from: getLast, reason: collision with other method in class */
    public final Object mo3439getLast() {
        AcceptLanguage acceptLanguage = (AcceptLanguage) super.mo3439getLast();
        return acceptLanguage != null ? acceptLanguage : new AcceptLanguage();
    }
}
