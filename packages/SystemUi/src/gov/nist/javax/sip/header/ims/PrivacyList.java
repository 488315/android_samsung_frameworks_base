package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
