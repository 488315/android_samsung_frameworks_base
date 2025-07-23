package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
