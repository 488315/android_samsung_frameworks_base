package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
