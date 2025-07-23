package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
