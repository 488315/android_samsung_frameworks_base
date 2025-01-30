package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class WarningList extends SIPHeaderList<Warning> {
    private static final long serialVersionUID = -1423278728898430175L;

    public WarningList() {
        super(Warning.class, "Warning");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        WarningList warningList = new WarningList();
        warningList.clonehlist(this.hlist);
        return warningList;
    }
}
