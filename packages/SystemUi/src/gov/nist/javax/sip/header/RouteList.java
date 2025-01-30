package gov.nist.javax.sip.header;

import java.util.ListIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RouteList extends SIPHeaderList<Route> {
    private static final long serialVersionUID = 3407603519354809748L;

    public RouteList() {
        super(Route.class, "Route");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        RouteList routeList = new RouteList();
        routeList.clonehlist(this.hlist);
        return routeList;
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        return this.hlist.isEmpty() ? "" : super.encode();
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (!(obj instanceof RouteList)) {
            return false;
        }
        RouteList routeList = (RouteList) obj;
        if (size() != routeList.size()) {
            return false;
        }
        ListIterator listIterator = listIterator();
        ListIterator listIterator2 = routeList.listIterator();
        while (listIterator.hasNext()) {
            if (!((Route) listIterator.next()).equals((Route) listIterator2.next())) {
                return false;
            }
        }
        return true;
    }
}
