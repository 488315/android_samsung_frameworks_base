package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Privacy extends SIPHeader implements PrivacyHeader, Header {
    private String privacy;

    public Privacy() {
        super("Privacy");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        Privacy privacy = (Privacy) super.clone();
        String str = this.privacy;
        if (str != null) {
            privacy.privacy = str;
        }
        return privacy;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.privacy;
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj instanceof PrivacyHeader) {
            return this.privacy.equals(((Privacy) ((PrivacyHeader) obj)).privacy);
        }
        return false;
    }

    public final void setPrivacy(String str) {
        if (str == null || str == "") {
            throw new NullPointerException("JAIN-SIP Exception,  Privacy, setPrivacy(), privacy value is null or empty");
        }
        this.privacy = str;
    }

    public Privacy(String str) {
        this();
        this.privacy = str;
    }
}
