package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
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
