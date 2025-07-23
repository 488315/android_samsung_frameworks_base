package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PrivacyList extends SIPHeaderList<Privacy> {
    private static final long serialVersionUID = 1798720509806307461L;

    public PrivacyList() {
        super(Privacy.class, "Privacy");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        PrivacyList privacyList = new PrivacyList();
        privacyList.clonehlist(this.hlist);
        return privacyList;
    }
}
