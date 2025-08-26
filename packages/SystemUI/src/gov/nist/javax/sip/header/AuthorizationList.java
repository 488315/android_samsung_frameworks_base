package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public class AuthorizationList extends SIPHeaderList<Authorization> {
    private static final long serialVersionUID = 1;

    public AuthorizationList() {
        super(Authorization.class, "Authorization");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        AuthorizationList authorizationList = new AuthorizationList();
        authorizationList.clonehlist(this.hlist);
        return authorizationList;
    }
}
