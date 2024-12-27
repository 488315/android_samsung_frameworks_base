package com.android.internal.net;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.Network;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class VpnConfig implements Parcelable {
    public static final String DIALOGS_PACKAGE = "com.android.vpndialogs";
    public static final String LEGACY_VPN = "[Legacy VPN]";
    private static final boolean REMOVE = false;
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public static final String TAG = "VpnConfig";
    public List<LinkAddress> addresses;
    public boolean allowBypass;
    public boolean allowIPv4;
    public boolean allowIPv6;
    public boolean allowPortBypass;
    public List<String> allowedApplications;
    public boolean blocking;
    public PendingIntent configureIntent;
    public List<String> disallowedApplications;
    public List<String> dnsServers;
    public int dport;
    public boolean excludeLocalRoutes;
    public int fwmark;
    public String interfaze;
    public boolean isMetered;
    public boolean legacy;
    public int mtu;
    public String netIfaceAddress;
    public String netIfaceName;
    public int netTableId;
    public int priority;
    public ProxyInfo proxyInfo;
    public boolean requiresInternetValidation;
    public List<RouteInfo> routes;
    public List<String> searchDomains;
    public String session;
    public long startTime;
    public Network[] underlyingNetworks;
    public String user;
    private static ArrayList<VpnConfig> mConfigsReceived = new ArrayList<>();
    private static ConcurrentHashMap<Integer, ArrayList<VpnConfig>> mConfigByUserMap =
            new ConcurrentHashMap<>();
    public static final Parcelable.Creator<VpnConfig> CREATOR =
            new Parcelable.Creator<
                    VpnConfig>() { // from class: com.android.internal.net.VpnConfig.1
                @Override // android.os.Parcelable.Creator
                public VpnConfig createFromParcel(Parcel in) {
                    VpnConfig config = new VpnConfig();
                    config.user = in.readString();
                    config.interfaze = in.readString();
                    config.session = in.readString();
                    config.mtu = in.readInt();
                    in.readTypedList(config.addresses, LinkAddress.CREATOR);
                    in.readTypedList(config.routes, RouteInfo.CREATOR);
                    config.dnsServers = in.createStringArrayList();
                    config.searchDomains = in.createStringArrayList();
                    config.allowedApplications = in.createStringArrayList();
                    config.disallowedApplications = in.createStringArrayList();
                    config.allowPortBypass = in.readInt() != 0;
                    config.dport = in.readInt();
                    config.fwmark = in.readInt();
                    config.priority = in.readInt();
                    config.netTableId = in.readInt();
                    config.netIfaceName = in.readString();
                    config.netIfaceAddress = in.readString();
                    config.configureIntent =
                            (PendingIntent) in.readParcelable(null, PendingIntent.class);
                    config.startTime = in.readLong();
                    config.legacy = in.readInt() != 0;
                    config.blocking = in.readInt() != 0;
                    config.allowBypass = in.readInt() != 0;
                    config.allowIPv4 = in.readInt() != 0;
                    config.allowIPv6 = in.readInt() != 0;
                    config.isMetered = in.readInt() != 0;
                    config.requiresInternetValidation = in.readInt() != 0;
                    config.excludeLocalRoutes = in.readInt() != 0;
                    config.underlyingNetworks = (Network[]) in.createTypedArray(Network.CREATOR);
                    config.proxyInfo = (ProxyInfo) in.readParcelable(null, ProxyInfo.class);
                    return config;
                }

                @Override // android.os.Parcelable.Creator
                public VpnConfig[] newArray(int size) {
                    return new VpnConfig[size];
                }
            };

    public static Intent getIntentForConfirmation() {
        Intent intent = new Intent();
        ComponentName componentName =
                ComponentName.unflattenFromString(
                        Resources.getSystem()
                                .getString(R.string.config_customVpnConfirmDialogComponent));
        intent.setClassName(componentName.getPackageName(), componentName.getClassName());
        return intent;
    }

    public static PendingIntent getIntentForStatusPanel(Context context) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.ManageDialog");
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(
                context, 0, intent, 67108864, null, UserHandle.CURRENT);
    }

    public static CharSequence getVpnLabel(Context context, String packageName)
            throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent("android.net.VpnService");
        intent.setPackage(packageName);
        List<ResolveInfo> services = pm.queryIntentServices(intent, 0);
        if (services != null && services.size() == 1) {
            return services.get(0).loadLabel(pm);
        }
        return pm.getApplicationInfo(packageName, 0).loadLabel(pm);
    }

    public static PendingIntent getIntentForStatusPanelEnterpriseVpn(
            Context context, VpnConfig config, boolean configOption) {
        Intent intent = new Intent();
        if (!configOption) {
            String configSession = config.session;
            Iterator iterator = mConfigsReceived.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    break;
                }
                VpnConfig configLocal = iterator.next();
                if (configLocal.session.equals(configSession)) {
                    Log.d(
                            TAG,
                            "getIntentForStatusPanelEnterpriseVpn : Removing iterator for profile :"
                                + " "
                                    + configSession);
                    iterator.remove();
                    break;
                }
            }
            Log.d(
                    TAG,
                    "getIntentForStatusPanelEnterpriseVpn : config size =  "
                            + mConfigsReceived.size());
            if (mConfigsReceived.size() == 0) {
                Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Returning null");
                return null;
            }
        } else {
            if (config != null) {
                Log.d(
                        TAG,
                        "getIntentForStatusPanelEnterpriseVpn : Adding iterator for profile : "
                                + config.session);
            }
            mConfigsReceived.add(config);
        }
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", mConfigsReceived);
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(
                context,
                0,
                intent,
                config == null ? 536870912 : 301989888,
                null,
                UserHandle.CURRENT);
    }

    public static PendingIntent getIntentForStatusPanelRefresh(Context context) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", mConfigsReceived);
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(
                context, 0, intent, 301989888, null, UserHandle.CURRENT);
    }

    public static PendingIntent getIntentForStatusPanelEnterpriseVpnAsUser(
            Context context, VpnConfig config, boolean configOption, int domain) {
        ArrayList<VpnConfig> receivedConfig;
        Intent intent = new Intent();
        ArrayList<VpnConfig> receivedConfig2 = mConfigByUserMap.get(Integer.valueOf(domain));
        if (config == null) {
            return null;
        }
        if (receivedConfig2 != null) {
            receivedConfig = receivedConfig2;
        } else {
            receivedConfig = new ArrayList<>();
        }
        if (!configOption) {
            String configSession = config.session;
            Iterator iterator = receivedConfig.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    break;
                }
                VpnConfig configLocal = iterator.next();
                if (configLocal.session.equals(configSession)) {
                    Log.d(
                            TAG,
                            "getIntentForStatusPanelEnterpriseVpn : Removing iterator for profile :"
                                + " "
                                    + configSession);
                    iterator.remove();
                    break;
                }
            }
            Log.d(
                    TAG,
                    "getIntentForStatusPanelEnterpriseVpn : config size =  "
                            + mConfigsReceived.size());
            if (receivedConfig.size() == 0) {
                Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Returning null");
                return null;
            }
        } else {
            if (config != null) {
                Log.d(
                        TAG,
                        "getIntentForStatusPanelEnterpriseVpn : Adding iterator for profile : "
                                + config.session);
            }
            boolean isFoundProfile = false;
            int i = 0;
            while (true) {
                if (i >= receivedConfig.size()) {
                    break;
                }
                VpnConfig configLocal2 = receivedConfig.get(i);
                if (!configLocal2.session.equals(config.session)) {
                    i++;
                } else {
                    isFoundProfile = true;
                    break;
                }
            }
            if (!isFoundProfile) {
                receivedConfig.add(config);
                mConfigByUserMap.put(Integer.valueOf(domain), receivedConfig);
            }
        }
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", receivedConfig);
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(
                context, 0, intent, 301989888, null, new UserHandle(domain));
    }

    public static PendingIntent getIntentForStatusPanelRefreshAsUser(Context context, int domain) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", mConfigByUserMap.get(Integer.valueOf(domain)));
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(
                context, 0, intent, 301989888, null, new UserHandle(domain));
    }

    public static PendingIntent getIntentForStatusPanelAsUser(Context context, int user) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.ManageDialog");
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(
                context, 0, intent, 33554432, null, new UserHandle(user));
    }

    public VpnConfig() {
        this.mtu = -1;
        this.addresses = new ArrayList();
        this.routes = new ArrayList();
        this.startTime = -1L;
        this.isMetered = true;
        this.requiresInternetValidation = false;
        this.excludeLocalRoutes = false;
    }

    public VpnConfig(VpnConfig other) {
        this.mtu = -1;
        this.addresses = new ArrayList();
        this.routes = new ArrayList();
        this.startTime = -1L;
        this.isMetered = true;
        this.requiresInternetValidation = false;
        this.excludeLocalRoutes = false;
        this.user = other.user;
        this.interfaze = other.interfaze;
        this.session = other.session;
        this.mtu = other.mtu;
        this.addresses = copyOf(other.addresses);
        this.routes = copyOf(other.routes);
        this.dnsServers = copyOf(other.dnsServers);
        this.searchDomains = copyOf(other.searchDomains);
        this.allowedApplications = copyOf(other.allowedApplications);
        this.disallowedApplications = copyOf(other.disallowedApplications);
        this.configureIntent = other.configureIntent;
        this.startTime = other.startTime;
        this.legacy = other.legacy;
        this.blocking = other.blocking;
        this.allowBypass = other.allowBypass;
        this.allowIPv4 = other.allowIPv4;
        this.allowIPv6 = other.allowIPv6;
        this.isMetered = other.isMetered;
        this.requiresInternetValidation = other.requiresInternetValidation;
        this.excludeLocalRoutes = other.excludeLocalRoutes;
        this.underlyingNetworks =
                other.underlyingNetworks != null
                        ? (Network[])
                                Arrays.copyOf(
                                        other.underlyingNetworks, other.underlyingNetworks.length)
                        : null;
        this.proxyInfo = other.proxyInfo;
    }

    private static <T> List<T> copyOf(List<T> list) {
        if (list != null) {
            return new ArrayList(list);
        }
        return null;
    }

    public void addLegacyRoutes(String routesStr) {
        if (routesStr.trim().equals("")) {
            return;
        }
        String[] routes = routesStr.trim().split(" ");
        for (String route : routes) {
            RouteInfo info = new RouteInfo(new IpPrefix(route), null, null, 1);
            this.routes.add(info);
        }
    }

    public void addLegacyAddresses(String addressesStr) {
        if (addressesStr.trim().equals("")) {
            return;
        }
        String[] addresses = addressesStr.trim().split(" ");
        for (String address : addresses) {
            LinkAddress addr = new LinkAddress(address);
            this.addresses.add(addr);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.user);
        parcel.writeString(this.interfaze);
        parcel.writeString(this.session);
        parcel.writeInt(this.mtu);
        parcel.writeTypedList(this.addresses);
        parcel.writeTypedList(this.routes);
        parcel.writeStringList(this.dnsServers);
        parcel.writeStringList(this.searchDomains);
        parcel.writeStringList(this.allowedApplications);
        parcel.writeStringList(this.disallowedApplications);
        parcel.writeInt(this.allowPortBypass ? 1 : 0);
        parcel.writeInt(this.dport);
        parcel.writeInt(this.fwmark);
        parcel.writeInt(this.priority);
        parcel.writeInt(this.netTableId);
        parcel.writeString(this.netIfaceName);
        parcel.writeString(this.netIfaceAddress);
        parcel.writeParcelable(this.configureIntent, i);
        parcel.writeLong(this.startTime);
        parcel.writeInt(this.legacy ? 1 : 0);
        parcel.writeInt(this.blocking ? 1 : 0);
        parcel.writeInt(this.allowBypass ? 1 : 0);
        parcel.writeInt(this.allowIPv4 ? 1 : 0);
        parcel.writeInt(this.allowIPv6 ? 1 : 0);
        parcel.writeInt(this.isMetered ? 1 : 0);
        parcel.writeInt(this.requiresInternetValidation ? 1 : 0);
        parcel.writeInt(this.excludeLocalRoutes ? 1 : 0);
        parcel.writeTypedArray(this.underlyingNetworks, i);
        parcel.writeParcelable(this.proxyInfo, i);
    }

    public String toString() {
        return TAG
                + "{ user="
                + this.user
                + ", interface="
                + this.interfaze
                + ", session="
                + this.session
                + ", mtu="
                + this.mtu
                + ", addresses="
                + toString(this.addresses)
                + ", routes="
                + toString(this.routes)
                + ", dns="
                + toString(this.dnsServers)
                + ", searchDomains="
                + toString(this.searchDomains)
                + ", allowedApps="
                + toString(this.allowedApplications)
                + ", disallowedApps="
                + toString(this.disallowedApplications)
                + ", configureIntent="
                + this.configureIntent
                + ", startTime="
                + this.startTime
                + ", legacy="
                + this.legacy
                + ", blocking="
                + this.blocking
                + ", allowBypass="
                + this.allowBypass
                + ", allowIPv4="
                + this.allowIPv4
                + ", allowIPv6="
                + this.allowIPv6
                + ", isMetered="
                + this.isMetered
                + ", requiresInternetValidation="
                + this.requiresInternetValidation
                + ", excludeLocalRoutes="
                + this.excludeLocalRoutes
                + ", underlyingNetworks="
                + Arrays.toString(this.underlyingNetworks)
                + ", proxyInfo="
                + this.proxyInfo
                + "}";
    }

    static <T> String toString(List<T> ls) {
        if (ls == null) {
            return "null";
        }
        return Arrays.toString(ls.toArray());
    }
}
