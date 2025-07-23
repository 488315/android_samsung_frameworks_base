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
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes5.dex */
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
    private static ConcurrentHashMap<Integer, ArrayList<VpnConfig>> mConfigByUserMap = new ConcurrentHashMap<>();
    public static final Parcelable.Creator<VpnConfig> CREATOR = new Parcelable.Creator<VpnConfig>() { // from class: com.android.internal.net.VpnConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnConfig createFromParcel(Parcel parcel) {
            VpnConfig vpnConfig = new VpnConfig();
            vpnConfig.user = parcel.readString();
            vpnConfig.interfaze = parcel.readString();
            vpnConfig.session = parcel.readString();
            vpnConfig.mtu = parcel.readInt();
            parcel.readTypedList(vpnConfig.addresses, LinkAddress.CREATOR);
            parcel.readTypedList(vpnConfig.routes, RouteInfo.CREATOR);
            vpnConfig.dnsServers = parcel.createStringArrayList();
            vpnConfig.searchDomains = parcel.createStringArrayList();
            vpnConfig.allowedApplications = parcel.createStringArrayList();
            vpnConfig.disallowedApplications = parcel.createStringArrayList();
            vpnConfig.allowPortBypass = parcel.readInt() != 0;
            vpnConfig.dport = parcel.readInt();
            vpnConfig.fwmark = parcel.readInt();
            vpnConfig.priority = parcel.readInt();
            vpnConfig.netTableId = parcel.readInt();
            vpnConfig.netIfaceName = parcel.readString();
            vpnConfig.netIfaceAddress = parcel.readString();
            vpnConfig.configureIntent = (PendingIntent) parcel.readParcelable(null, PendingIntent.class);
            vpnConfig.startTime = parcel.readLong();
            vpnConfig.legacy = parcel.readInt() != 0;
            vpnConfig.blocking = parcel.readInt() != 0;
            vpnConfig.allowBypass = parcel.readInt() != 0;
            vpnConfig.allowIPv4 = parcel.readInt() != 0;
            vpnConfig.allowIPv6 = parcel.readInt() != 0;
            vpnConfig.isMetered = parcel.readInt() != 0;
            vpnConfig.requiresInternetValidation = parcel.readInt() != 0;
            vpnConfig.excludeLocalRoutes = parcel.readInt() != 0;
            vpnConfig.underlyingNetworks = (Network[]) parcel.createTypedArray(Network.CREATOR);
            vpnConfig.proxyInfo = (ProxyInfo) parcel.readParcelable(null, ProxyInfo.class);
            return vpnConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnConfig[] newArray(int i) {
            return new VpnConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static Intent getIntentForConfirmation() {
        Intent intent = new Intent();
        ComponentName unflattenFromString = ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_customVpnConfirmDialogComponent));
        intent.setClassName(unflattenFromString.getPackageName(), unflattenFromString.getClassName());
        return intent;
    }

    public static PendingIntent getIntentForStatusPanel(Context context) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.ManageDialog");
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, UserHandle.CURRENT);
    }

    public static CharSequence getVpnLabel(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.net.VpnService");
        intent.setPackage(str);
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (queryIntentServices != null && queryIntentServices.size() == 1) {
            return queryIntentServices.get(0).loadLabel(packageManager);
        }
        return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager);
    }

    public static PendingIntent getIntentForStatusPanelEnterpriseVpn(Context context, VpnConfig vpnConfig, boolean z) {
        Intent intent = new Intent();
        if (!z) {
            String str = vpnConfig.session;
            Iterator<VpnConfig> it = mConfigsReceived.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next().session.equals(str)) {
                    Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Removing iterator for profile : " + str);
                    it.remove();
                    break;
                }
            }
            Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : config size =  " + mConfigsReceived.size());
            if (mConfigsReceived.size() == 0) {
                Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Returning null");
                return null;
            }
        } else {
            if (vpnConfig != null) {
                Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Adding iterator for profile : " + vpnConfig.session);
            }
            mConfigsReceived.add(vpnConfig);
        }
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", mConfigsReceived);
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, vpnConfig == null ? 536870912 : 301989888, null, UserHandle.CURRENT);
    }

    public static PendingIntent getIntentForStatusPanelRefresh(Context context) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", mConfigsReceived);
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, 301989888, null, UserHandle.CURRENT);
    }

    public static PendingIntent getIntentForStatusPanelEnterpriseVpnAsUser(Context context, VpnConfig vpnConfig, boolean z, int i) {
        Intent intent = new Intent();
        ArrayList<VpnConfig> arrayList = mConfigByUserMap.get(Integer.valueOf(i));
        if (vpnConfig == null) {
            return null;
        }
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        if (z) {
            if (vpnConfig != null) {
                Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Adding iterator for profile : " + vpnConfig.session);
            }
            int i2 = 0;
            while (true) {
                if (i2 < arrayList.size()) {
                    if (arrayList.get(i2).session.equals(vpnConfig.session)) {
                        break;
                    }
                    i2++;
                } else {
                    arrayList.add(vpnConfig);
                    mConfigByUserMap.put(Integer.valueOf(i), arrayList);
                    break;
                }
            }
        } else {
            String str = vpnConfig.session;
            Iterator<VpnConfig> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next().session.equals(str)) {
                    Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Removing iterator for profile : " + str);
                    it.remove();
                    break;
                }
            }
            Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : config size =  " + mConfigsReceived.size());
            if (arrayList.size() == 0) {
                Log.d(TAG, "getIntentForStatusPanelEnterpriseVpn : Returning null");
                return null;
            }
        }
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", arrayList);
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, 301989888, null, new UserHandle(i));
    }

    public static PendingIntent getIntentForStatusPanelRefreshAsUser(Context context, int i) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.EnterpriseVpnDialog");
        intent.putParcelableArrayListExtra("config", mConfigByUserMap.get(Integer.valueOf(i)));
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, 301989888, null, new UserHandle(i));
    }

    public static PendingIntent getIntentForStatusPanelAsUser(Context context, int i) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.ManageDialog");
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, 33554432, null, new UserHandle(i));
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

    public VpnConfig(VpnConfig vpnConfig) {
        this.mtu = -1;
        this.addresses = new ArrayList();
        this.routes = new ArrayList();
        this.startTime = -1L;
        this.isMetered = true;
        this.requiresInternetValidation = false;
        this.excludeLocalRoutes = false;
        this.user = vpnConfig.user;
        this.interfaze = vpnConfig.interfaze;
        this.session = vpnConfig.session;
        this.mtu = vpnConfig.mtu;
        this.addresses = copyOf(vpnConfig.addresses);
        this.routes = copyOf(vpnConfig.routes);
        this.dnsServers = copyOf(vpnConfig.dnsServers);
        this.searchDomains = copyOf(vpnConfig.searchDomains);
        this.allowedApplications = copyOf(vpnConfig.allowedApplications);
        this.disallowedApplications = copyOf(vpnConfig.disallowedApplications);
        this.configureIntent = vpnConfig.configureIntent;
        this.startTime = vpnConfig.startTime;
        this.legacy = vpnConfig.legacy;
        this.blocking = vpnConfig.blocking;
        this.allowBypass = vpnConfig.allowBypass;
        this.allowIPv4 = vpnConfig.allowIPv4;
        this.allowIPv6 = vpnConfig.allowIPv6;
        this.isMetered = vpnConfig.isMetered;
        this.requiresInternetValidation = vpnConfig.requiresInternetValidation;
        this.excludeLocalRoutes = vpnConfig.excludeLocalRoutes;
        Network[] networkArr = vpnConfig.underlyingNetworks;
        this.underlyingNetworks = networkArr != null ? (Network[]) Arrays.copyOf(networkArr, networkArr.length) : null;
        this.proxyInfo = vpnConfig.proxyInfo;
    }

    private static <T> List<T> copyOf(List<T> list) {
        if (list != null) {
            return new ArrayList(list);
        }
        return null;
    }

    public void addLegacyRoutes(String str) {
        if (str.trim().equals("")) {
            return;
        }
        for (String str2 : str.trim().split(" ")) {
            this.routes.add(new RouteInfo(new IpPrefix(str2), null, null, 1));
        }
    }

    public void addLegacyAddresses(String str) {
        if (str.trim().equals("")) {
            return;
        }
        for (String str2 : str.trim().split(" ")) {
            this.addresses.add(new LinkAddress(str2));
        }
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
        return "VpnConfig{ user=" + this.user + ", interface=" + this.interfaze + ", session=" + this.session + ", mtu=" + this.mtu + ", addresses=" + toString(this.addresses) + ", routes=" + toString(this.routes) + ", dns=" + toString(this.dnsServers) + ", searchDomains=" + toString(this.searchDomains) + ", allowedApps=" + toString(this.allowedApplications) + ", disallowedApps=" + toString(this.disallowedApplications) + ", configureIntent=" + this.configureIntent + ", startTime=" + this.startTime + ", legacy=" + this.legacy + ", blocking=" + this.blocking + ", allowBypass=" + this.allowBypass + ", allowIPv4=" + this.allowIPv4 + ", allowIPv6=" + this.allowIPv6 + ", isMetered=" + this.isMetered + ", requiresInternetValidation=" + this.requiresInternetValidation + ", excludeLocalRoutes=" + this.excludeLocalRoutes + ", underlyingNetworks=" + Arrays.toString(this.underlyingNetworks) + ", proxyInfo=" + this.proxyInfo + "}";
    }

    static <T> String toString(List<T> list) {
        if (list == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        return Arrays.toString(list.toArray());
    }
}
