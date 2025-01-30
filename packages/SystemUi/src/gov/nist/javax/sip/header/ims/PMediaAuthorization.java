package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PMediaAuthorization extends SIPHeader implements PMediaAuthorizationHeader {
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
