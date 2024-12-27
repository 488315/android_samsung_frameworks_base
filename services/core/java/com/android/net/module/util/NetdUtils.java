package com.android.net.module.util;

import android.net.INetd;
import android.net.InterfaceConfigurationParcel;
import android.net.IpPrefix;
import android.net.RouteInfo;
import android.net.RouteInfoParcel;
import android.net.TetherConfigParcel;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.system.OsConstants;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NetdUtils {
    public static final String TAG = "NetdUtils";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum ModifyOperation {
        ADD,
        REMOVE
    }

    public static void addRoutesToLocalNetwork(INetd iNetd, String str, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RouteInfo routeInfo = (RouteInfo) it.next();
            if (!routeInfo.isDefaultRoute()) {
                modifyRoute(iNetd, ModifyOperation.ADD, 99, routeInfo);
            }
        }
        modifyRoute(
                iNetd,
                ModifyOperation.ADD,
                99,
                new RouteInfo(new IpPrefix("fe80::/64"), null, str, 1));
    }

    public static String findNextHop(RouteInfo routeInfo) {
        int type = routeInfo.getType();
        return type != 1
                ? type != 7 ? type != 9 ? "" : INetd.NEXTHOP_THROW : INetd.NEXTHOP_UNREACHABLE
                : routeInfo.hasGateway() ? routeInfo.getGateway().getHostAddress() : "";
    }

    public static InterfaceConfigurationParcel getInterfaceConfigParcel(INetd iNetd, String str) {
        try {
            return iNetd.interfaceGetCfg(str);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean hasFlag(
            InterfaceConfigurationParcel interfaceConfigurationParcel, String str) {
        validateFlag(str);
        return new HashSet(Arrays.asList(interfaceConfigurationParcel.flags)).contains(str);
    }

    public static void modifyRoute(
            INetd iNetd, ModifyOperation modifyOperation, int i, RouteInfo routeInfo) {
        String str = routeInfo.getInterface();
        String ipPrefix = routeInfo.getDestination().toString();
        String findNextHop = findNextHop(routeInfo);
        try {
            int ordinal = modifyOperation.ordinal();
            if (ordinal == 0) {
                iNetd.networkAddRoute(i, str, ipPrefix, findNextHop);
            } else if (ordinal == 1) {
                iNetd.networkRemoveRoute(i, str, ipPrefix, findNextHop);
            } else {
                throw new IllegalStateException("Unsupported modify operation:" + modifyOperation);
            }
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void networkAddInterface(INetd iNetd, String str, int i, int i2)
            throws ServiceSpecificException, RemoteException {
        for (int i3 = 1; i3 <= i; i3++) {
            try {
                iNetd.networkAddInterface(99, str);
                return;
            } catch (ServiceSpecificException e) {
                if (e.errorCode != OsConstants.EBUSY || i3 >= i) {
                    Log.e(TAG, "Retry Netd#networkAddInterface failure: " + e);
                    throw e;
                }
                SystemClock.sleep(i2);
            }
        }
    }

    public static String[] removeAndAddFlags(String[] strArr, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        try {
            validateFlag(str2);
            for (String str3 : strArr) {
                if (!str.equals(str3) && !str2.equals(str3)) {
                    arrayList.add(str3);
                }
            }
            arrayList.add(str2);
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid InterfaceConfigurationParcel", e);
        }
    }

    public static int removeRoutesFromLocalNetwork(INetd iNetd, List list) {
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                modifyRoute(iNetd, ModifyOperation.REMOVE, 99, (RouteInfo) it.next());
            } catch (IllegalStateException unused) {
                i++;
            }
        }
        return i;
    }

    public static void setInterfaceConfig(
            INetd iNetd, InterfaceConfigurationParcel interfaceConfigurationParcel) {
        try {
            iNetd.interfaceSetCfg(interfaceConfigurationParcel);
        } catch (RemoteException | ServiceSpecificException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void setInterfaceDown(INetd iNetd, String str) {
        InterfaceConfigurationParcel interfaceConfigParcel = getInterfaceConfigParcel(iNetd, str);
        interfaceConfigParcel.flags =
                removeAndAddFlags(
                        interfaceConfigParcel.flags, INetd.IF_STATE_UP, INetd.IF_STATE_DOWN);
        setInterfaceConfig(iNetd, interfaceConfigParcel);
    }

    public static void setInterfaceUp(INetd iNetd, String str) {
        InterfaceConfigurationParcel interfaceConfigParcel = getInterfaceConfigParcel(iNetd, str);
        interfaceConfigParcel.flags =
                removeAndAddFlags(
                        interfaceConfigParcel.flags, INetd.IF_STATE_DOWN, INetd.IF_STATE_UP);
        setInterfaceConfig(iNetd, interfaceConfigParcel);
    }

    public static void tetherInterface(INetd iNetd, String str, IpPrefix ipPrefix)
            throws RemoteException, ServiceSpecificException {
        tetherInterface(iNetd, str, ipPrefix, 20, 50);
    }

    public static void tetherInterface(INetd iNetd, String str, IpPrefix ipPrefix, int i, int i2)
            throws RemoteException, ServiceSpecificException {
        iNetd.tetherInterfaceAdd(str);
        networkAddInterface(iNetd, str, i, i2);
        ModifyOperation modifyOperation = ModifyOperation.ADD;
        modifyRoute(iNetd, modifyOperation, 99, new RouteInfo(ipPrefix, null, str, 1));
        modifyRoute(
                iNetd, modifyOperation, 99, new RouteInfo(new IpPrefix("fe80::/64"), null, str, 1));
    }

    public static void tetherStart(INetd iNetd, boolean z, String[] strArr)
            throws RemoteException, ServiceSpecificException {
        TetherConfigParcel tetherConfigParcel = new TetherConfigParcel();
        tetherConfigParcel.usingLegacyDnsProxy = z;
        tetherConfigParcel.dhcpRanges = strArr;
        iNetd.tetherStartWithConfiguration(tetherConfigParcel);
    }

    public static RouteInfoParcel toRouteInfoParcel(RouteInfo routeInfo) {
        int type = routeInfo.getType();
        String str = "";
        if (type != 1) {
            if (type == 7) {
                str = INetd.NEXTHOP_UNREACHABLE;
            } else if (type == 9) {
                str = INetd.NEXTHOP_THROW;
            }
        } else if (routeInfo.hasGateway()) {
            str = routeInfo.getGateway().getHostAddress();
        }
        RouteInfoParcel routeInfoParcel = new RouteInfoParcel();
        routeInfoParcel.ifName = routeInfo.getInterface();
        routeInfoParcel.destination = routeInfo.getDestination().toString();
        routeInfoParcel.nextHop = str;
        routeInfoParcel.mtu = routeInfo.getMtu();
        return routeInfoParcel;
    }

    public static void untetherInterface(INetd iNetd, String str)
            throws RemoteException, ServiceSpecificException {
        try {
            iNetd.tetherInterfaceRemove(str);
        } finally {
            iNetd.networkRemoveInterface(99, str);
        }
    }

    public static void validateFlag(String str) {
        if (str.indexOf(32) >= 0) {
            throw new IllegalArgumentException("flag contains space: ".concat(str));
        }
    }
}
