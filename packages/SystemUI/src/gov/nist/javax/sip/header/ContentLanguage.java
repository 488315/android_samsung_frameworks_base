package gov.nist.javax.sip.header;

import java.util.Locale;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class ContentLanguage extends SIPHeader implements Header {
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
        int iIndexOf = str.indexOf(45);
        if (iIndexOf >= 0) {
            this.locale = new Locale(str.substring(0, iIndexOf), str.substring(iIndexOf + 1));
        } else {
            this.locale = new Locale(str);
        }
    }
}
