package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RecordRouteList extends SIPHeaderList<RecordRoute> {
    private static final long serialVersionUID = 1724940469426766691L;

    public RecordRouteList() {
        super(RecordRoute.class, "Record-Route");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        RecordRouteList recordRouteList = new RecordRouteList();
        recordRouteList.clonehlist(this.hlist);
        return recordRouteList;
    }
}
