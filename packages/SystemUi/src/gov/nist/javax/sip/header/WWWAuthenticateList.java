package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
