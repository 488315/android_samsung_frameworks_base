package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ServiceRouteList extends SIPHeaderList<ServiceRoute> {
    private static final long serialVersionUID = -4264811439080938519L;

    public ServiceRouteList() {
        super(ServiceRoute.class, "Service-Route");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ServiceRouteList serviceRouteList = new ServiceRouteList();
        serviceRouteList.clonehlist(this.hlist);
        return serviceRouteList;
    }
}
