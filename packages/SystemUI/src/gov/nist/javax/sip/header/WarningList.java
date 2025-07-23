package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
