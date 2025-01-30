package android.net;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.IPackageManager;
import android.content.p002pm.PackageManager;
import android.net.IVpnManager;
import android.p009os.Binder;
import android.p009os.Debug;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.net.NetworkUtilsInternal;
import com.android.internal.net.VpnConfig;
import com.samsung.android.knox.net.vpn.serviceprovider.GenericVpnContext;
import com.samsung.android.knoxguard.KnoxGuardManager;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class VpnService extends Service {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String FAST_PACKAGE_NAME = "com.samsung.android.fast";
    private static final String KNOXGUARD_PACKAGE_NAME = "com.samsung.android.kgclient";
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public static final String SERVICE_META_DATA_SUPPORTS_ALWAYS_ON = "android.net.VpnService.SUPPORTS_ALWAYS_ON";
    private static final int SYSTEM_VPN = 0;
    private static final String TAG = "VpnService";

    private static boolean isSecureWifiPackage(Context context) {
        if (FAST_PACKAGE_NAME.equals(context.getPackageName())) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.checkSignatures("android", FAST_PACKAGE_NAME) == 0) {
                return true;
            }
            Log.m96e(TAG, "Secure Wi-Fi signature mismatched");
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IVpnManager getService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
    }

    private static Intent prepareAndAuthorizeVpnForFast() {
        try {
            IVpnManager vm = getService();
            int userId = UserHandle.myUserId();
            if (!vm.prepareVpn(FAST_PACKAGE_NAME, null, userId)) {
                vm.prepareVpn(null, FAST_PACKAGE_NAME, userId);
            }
            vm.setVpnPackageAuthorization(FAST_PACKAGE_NAME, userId, 1);
            return null;
        } catch (RemoteException e) {
            return VpnConfig.getIntentForConfirmation();
        }
    }

    public static Intent prepare(Context context) {
        if (context instanceof GenericVpnContext) {
            Log.m94d(TAG, "prepare function with generic vpn context is called for knox vpn profile");
            GenericVpnContext knoxVPNContext = (GenericVpnContext) context;
            String profile = knoxVPNContext.getVPNProfile();
            knoxVPNContext.getVPNState();
            knoxVPNContext.getApplicationContext().getPackageName();
            boolean isMetaEnabled = knoxVPNContext.isMetaEnabled();
            int profileType = 0;
            try {
                profileType = getService().knoxVpnProfileType(profile);
                if (getService().prepareEnterpriseVpnExt(profile, isMetaEnabled)) {
                    return null;
                }
            } catch (RemoteException e) {
                Log.m96e(TAG, "Exception occured while trying to prepare knox vpn profile");
            }
            if (profileType == 0) {
                return VpnConfig.getIntentForConfirmation();
            }
            return null;
        }
        if (isSecureWifiPackage(context)) {
            return prepareAndAuthorizeVpnForFast();
        }
        try {
            if (getService().prepareVpn(context.getPackageName(), null, context.getUserId())) {
                return null;
            }
        } catch (RemoteException e2) {
        }
        return VpnConfig.getIntentForConfirmation();
    }

    @SystemApi
    public static void prepareAndAuthorize(Context context) {
        IVpnManager vm = getService();
        String packageName = context.getPackageName();
        try {
            int userId = context.getUserId();
            if (!vm.prepareVpn(packageName, null, userId)) {
                vm.prepareVpn(null, packageName, userId);
            }
            vm.setVpnPackageAuthorization(packageName, userId, 1);
        } catch (RemoteException e) {
        }
    }

    public boolean protect(int socket) {
        try {
            if (getService() != null) {
                if (!getService().getChainingEnabledForProfile(Binder.getCallingUid())) {
                    return NetworkUtilsInternal.protectFromVpn(socket);
                }
                if (DBG) {
                    Log.m94d(TAG, "protect is not going to be called for " + Binder.getCallingUid());
                    return true;
                }
                return true;
            }
            return NetworkUtilsInternal.protectFromVpn(socket);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean protect(Socket socket) {
        return protect(socket.getFileDescriptor$().getInt$());
    }

    public boolean protect(DatagramSocket socket) {
        return protect(socket.getFileDescriptor$().getInt$());
    }

    public boolean addAddress(InetAddress address, int prefixLength) {
        check(address, prefixLength);
        try {
            return getService().addVpnAddress(address.getHostAddress(), prefixLength);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean removeAddress(InetAddress address, int prefixLength) {
        check(address, prefixLength);
        try {
            return getService().removeVpnAddress(address.getHostAddress(), prefixLength);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean setUnderlyingNetworks(Network[] networks) {
        try {
            return getService().setUnderlyingNetworksForVpn(networks);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public final boolean isAlwaysOn() {
        try {
            return getService().isCallerCurrentAlwaysOnVpnApp();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final boolean isLockdownEnabled() {
        try {
            return getService().isCallerCurrentAlwaysOnVpnLockdownApp();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent == null || !"android.net.VpnService".equals(intent.getAction())) {
            return null;
        }
        return new Callback();
    }

    public void onRevoke() {
        stopSelf();
    }

    private class Callback extends Binder {
        private Callback() {
        }

        @Override // android.p009os.Binder
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            if (code == 16777215) {
                VpnService.this.onRevoke();
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void check(InetAddress address, int prefixLength) {
        if (address.isLoopbackAddress()) {
            throw new IllegalArgumentException("Bad address");
        }
        if (address instanceof Inet4Address) {
            if (prefixLength < 0 || prefixLength > 32) {
                throw new IllegalArgumentException("Bad prefixLength");
            }
        } else {
            if (address instanceof Inet6Address) {
                if (prefixLength < 0 || prefixLength > 128) {
                    throw new IllegalArgumentException("Bad prefixLength");
                }
                return;
            }
            throw new IllegalArgumentException("Unsupported family");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkNonPrefixBytes(InetAddress address, int prefixLength) {
        IpPrefix prefix = new IpPrefix(address, prefixLength);
        if (!prefix.getAddress().equals(address)) {
            throw new IllegalArgumentException("Bad address");
        }
    }

    public class Builder {
        private final List<LinkAddress> mAddresses;
        private final VpnConfig mConfig;
        private final List<RouteInfo> mRoutes;

        public Builder() {
            VpnConfig vpnConfig = new VpnConfig();
            this.mConfig = vpnConfig;
            this.mAddresses = new ArrayList();
            this.mRoutes = new ArrayList();
            vpnConfig.user = VpnService.this.getClass().getName();
        }

        public Builder setSession(String session) {
            this.mConfig.session = session;
            return this;
        }

        public Builder setConfigureIntent(PendingIntent intent) {
            this.mConfig.configureIntent = intent;
            return this;
        }

        public Builder setMtu(int mtu) {
            if (mtu <= 0) {
                throw new IllegalArgumentException("Bad mtu");
            }
            this.mConfig.mtu = mtu;
            return this;
        }

        public Builder setHttpProxy(ProxyInfo proxyInfo) {
            this.mConfig.proxyInfo = proxyInfo;
            return this;
        }

        public Builder addAddress(InetAddress address, int prefixLength) {
            VpnService.check(address, prefixLength);
            if (address.isAnyLocalAddress()) {
                throw new IllegalArgumentException("Bad address");
            }
            this.mAddresses.add(new LinkAddress(address, prefixLength));
            return this;
        }

        public Builder addAddress(String address, int prefixLength) {
            return addAddress(InetAddress.parseNumericAddress(address), prefixLength);
        }

        private Builder addRoute(IpPrefix prefix, int type) {
            VpnService.check(prefix.getAddress(), prefix.getPrefixLength());
            RouteInfo newRoute = new RouteInfo(prefix, null, null, type);
            int index = findRouteIndexByDestination(newRoute);
            if (index == -1) {
                this.mRoutes.add(newRoute);
            } else {
                this.mRoutes.set(index, newRoute);
            }
            return this;
        }

        public Builder addRoute(InetAddress address, int prefixLength) {
            VpnService.checkNonPrefixBytes(address, prefixLength);
            return addRoute(new IpPrefix(address, prefixLength), 1);
        }

        public Builder addRoute(IpPrefix prefix) {
            return addRoute(prefix, 1);
        }

        public Builder addRoute(String address, int prefixLength) {
            return addRoute(InetAddress.parseNumericAddress(address), prefixLength);
        }

        public Builder excludeRoute(IpPrefix prefix) {
            return addRoute(prefix, 9);
        }

        public Builder addDnsServer(InetAddress address) {
            if (address.isLoopbackAddress() || address.isAnyLocalAddress()) {
                throw new IllegalArgumentException("Bad address");
            }
            if (this.mConfig.dnsServers == null) {
                this.mConfig.dnsServers = new ArrayList();
            }
            this.mConfig.dnsServers.add(address.getHostAddress());
            return this;
        }

        public Builder addDnsServer(String address) {
            return addDnsServer(InetAddress.parseNumericAddress(address));
        }

        public Builder addSearchDomain(String domain) {
            if (this.mConfig.searchDomains == null) {
                this.mConfig.searchDomains = new ArrayList();
            }
            this.mConfig.searchDomains.add(domain);
            return this;
        }

        public Builder allowFamily(int family) {
            if (family == OsConstants.AF_INET) {
                this.mConfig.allowIPv4 = true;
            } else if (family == OsConstants.AF_INET6) {
                this.mConfig.allowIPv6 = true;
            } else {
                throw new IllegalArgumentException(family + " is neither " + OsConstants.AF_INET + " nor " + OsConstants.AF_INET6);
            }
            return this;
        }

        private void verifyApp(String packageName) throws PackageManager.NameNotFoundException {
            IPackageManager pm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            try {
                pm.getApplicationInfo(packageName, 0L, UserHandle.getCallingUserId());
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }

        private boolean isSecureWifiUid(int callingUid) {
            IPackageManager pm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            try {
                String[] packages = pm.getPackagesForUid(callingUid);
                if (packages != null && packages.length > 0 && VpnService.FAST_PACKAGE_NAME.equals(packages[0])) {
                    if (pm.checkSignatures("android", VpnService.FAST_PACKAGE_NAME, UserHandle.getCallingUserId()) == 0) {
                        return true;
                    }
                    Log.m96e(VpnService.TAG, "Secure Wi-Fi signature mismatched");
                }
            } catch (Exception e) {
            }
            return false;
        }

        private void verifyAppAsUser(String packageName, int userId) throws PackageManager.NameNotFoundException {
            IPackageManager pm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            try {
                if (pm.getPackageUid(packageName, 0L, userId) < 0) {
                    throw new PackageManager.NameNotFoundException(packageName);
                }
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }

        public Builder addAllowedApplication(String packageName) throws PackageManager.NameNotFoundException {
            int callingUid = Binder.getCallingUid();
            if (isSecureWifiUid(callingUid)) {
                String[] packageUserId = packageName.split(":");
                if (packageUserId.length == 2) {
                    if (this.mConfig.disallowedSecureFolderApps != null) {
                        throw new UnsupportedOperationException("addDisallowedSecureFolderApps already called");
                    }
                    String packageName2 = packageUserId[0];
                    int userId = Integer.parseInt(packageUserId[1]);
                    verifyAppAsUser(packageName2, userId);
                    if (this.mConfig.allowedSecureFolderApps == null) {
                        this.mConfig.allowedSecureFolderApps = new ArrayList();
                    }
                    this.mConfig.allowedSecureFolderApps.add(packageName2);
                    return this;
                }
            }
            if (this.mConfig.disallowedApplications != null) {
                throw new UnsupportedOperationException("addDisallowedApplication already called");
            }
            verifyApp(packageName);
            if (packageName.equals(VpnService.KNOXGUARD_PACKAGE_NAME) && KnoxGuardManager.getInstance().isVpnExceptionRequired()) {
                return this;
            }
            if (this.mConfig.allowedApplications == null) {
                this.mConfig.allowedApplications = new ArrayList();
            }
            this.mConfig.allowedApplications.add(packageName);
            return this;
        }

        public Builder addDisallowedApplication(String packageName) throws PackageManager.NameNotFoundException {
            int callingUid = Binder.getCallingUid();
            if (isSecureWifiUid(callingUid)) {
                String[] packageUserId = packageName.split(":");
                if (packageUserId.length == 2) {
                    if (this.mConfig.allowedSecureFolderApps != null) {
                        throw new UnsupportedOperationException("addAllowedSecureFolderApps already called");
                    }
                    String packageName2 = packageUserId[0];
                    int userId = Integer.parseInt(packageUserId[1]);
                    verifyAppAsUser(packageName2, userId);
                    if (this.mConfig.disallowedSecureFolderApps == null) {
                        this.mConfig.disallowedSecureFolderApps = new ArrayList();
                    }
                    this.mConfig.disallowedSecureFolderApps.add(packageName2);
                    return this;
                }
            }
            if (this.mConfig.allowedApplications != null) {
                throw new UnsupportedOperationException("addAllowedApplication already called");
            }
            verifyApp(packageName);
            if (this.mConfig.disallowedApplications == null) {
                this.mConfig.disallowedApplications = new ArrayList();
            }
            this.mConfig.disallowedApplications.add(packageName);
            return this;
        }

        public Builder allowBypass() {
            this.mConfig.allowBypass = true;
            return this;
        }

        public Builder setBlocking(boolean blocking) {
            this.mConfig.blocking = blocking;
            return this;
        }

        public Builder setUnderlyingNetworks(Network[] networks) {
            this.mConfig.underlyingNetworks = networks != null ? (Network[]) networks.clone() : null;
            return this;
        }

        public Builder setMetered(boolean isMetered) {
            this.mConfig.isMetered = isMetered;
            return this;
        }

        public ParcelFileDescriptor establish() {
            if (this.mConfig.allowedApplications == null && KnoxGuardManager.getInstance().isVpnExceptionRequired()) {
                try {
                    addDisallowedApplication(VpnService.KNOXGUARD_PACKAGE_NAME);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.m96e(VpnService.TAG, "Exception occured while calling addDisallowedApplication");
                }
            }
            this.mConfig.addresses = this.mAddresses;
            this.mConfig.routes = this.mRoutes;
            try {
                return VpnService.getService().establishVpn(this.mConfig);
            } catch (RemoteException e2) {
                throw new IllegalStateException(e2);
            }
        }

        private int findRouteIndexByDestination(RouteInfo route) {
            for (int i = 0; i < this.mRoutes.size(); i++) {
                if (this.mRoutes.get(i).getDestination().equals(route.getDestination())) {
                    return i;
                }
            }
            return -1;
        }

        public List<RouteInfo> routes() {
            return Collections.unmodifiableList(this.mRoutes);
        }
    }
}
