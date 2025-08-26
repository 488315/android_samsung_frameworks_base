package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public class WWWAuthenticateList extends SIPHeaderList<WWWAuthenticate> {
    private static final long serialVersionUID = -6978902284285501346L;

    public WWWAuthenticateList() {
        super(WWWAuthenticate.class, "WWW-Authenticate");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        WWWAuthenticateList wWWAuthenticateList = new WWWAuthenticateList();
        wWWAuthenticateList.clonehlist(this.hlist);
        return wWWAuthenticateList;
    }
}
