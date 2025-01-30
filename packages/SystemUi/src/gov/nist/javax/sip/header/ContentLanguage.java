package gov.nist.javax.sip.header;

import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ContentLanguage extends SIPHeader {
    private static final long serialVersionUID = -5195728427134181070L;
    protected Locale locale;

    public ContentLanguage() {
        super("Content-Language");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        ContentLanguage contentLanguage = (ContentLanguage) super.clone();
        Locale locale = this.locale;
        if (locale != null) {
            contentLanguage.locale = (Locale) locale.clone();
        }
        return contentLanguage;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        if ("".equals(this.locale.getCountry())) {
            return this.locale.getLanguage();
        }
        return this.locale.getLanguage() + '-' + this.locale.getCountry();
    }

    public ContentLanguage(String str) {
        super("Content-Language");
        int indexOf = str.indexOf(45);
        if (indexOf >= 0) {
            this.locale = new Locale(str.substring(0, indexOf), str.substring(indexOf + 1));
        } else {
            this.locale = new Locale(str);
        }
    }
}
