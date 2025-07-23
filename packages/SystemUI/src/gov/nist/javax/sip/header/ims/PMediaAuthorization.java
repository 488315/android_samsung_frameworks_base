package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PMediaAuthorization extends SIPHeader implements PMediaAuthorizationHeader, Header {
    private static final long serialVersionUID = -6463630258703731133L;
    private String token;

    public PMediaAuthorization() {
        super("P-Media-Authorization");
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        PMediaAuthorization pMediaAuthorization = (PMediaAuthorization) super.clone();
        String str = this.token;
        if (str != null) {
            pMediaAuthorization.token = str;
        }
        return pMediaAuthorization;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.token;
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj instanceof PMediaAuthorizationHeader) {
            return this.token.equals(((PMediaAuthorization) ((PMediaAuthorizationHeader) obj)).token);
        }
        return false;
    }

    public final void setMediaAuthorizationToken(String str) {
        if (str == null || str.length() == 0) {
            throw new InvalidArgumentException(" the Media-Authorization-Token parameter is null or empty");
        }
        this.token = str;
    }
}
