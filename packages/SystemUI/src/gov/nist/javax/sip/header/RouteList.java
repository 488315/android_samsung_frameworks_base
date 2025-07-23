package gov.nist.javax.sip.header;

import java.util.ListIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
        if (this.hlist.size() != routeList.hlist.size()) {
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
