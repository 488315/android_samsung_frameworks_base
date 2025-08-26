package android.net;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.IVpnManager;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
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

/* loaded from: classes3.dex */
public class VpnService extends Service {
    private static final boolean DBG = Debug.semIsProductDev();
    private static final String FAST_PACKAGE_NAME = "com.samsung.android.fast";
    private static final String KNOXGUARD_PACKAGE_NAME = "com.samsung.android.kgclient";
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public static final String SERVICE_META_DATA_SUPPORTS_ALWAYS_ON = "android.net.VpnService.SUPPORTS_ALWAYS_ON";
    private static final int SYSTEM_VPN = 0;
    private static final String TAG = "VpnService";

    private static boolean isSecureWifiPackage(Context context) {
        if (!FAST_PACKAGE_NAME.equals(context.getPackageName())) {
            return false;
        }
        if (context.getPackageManager().checkSignatures("android", FAST_PACKAGE_NAME) == 0) {
            return true;
        }
        Log.e(TAG, "Secure Wi-Fi signature mismatched");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IVpnManager getService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService(Context.VPN_MANAGEMENT_SERVICE));
    }

    private static Intent prepareAndAuthorizeVpnForFast() {
        try {
            IVpnManager service = getService();
            int iMyUserId = UserHandle.myUserId();
            if (!service.prepareVpn(FAST_PACKAGE_NAME, null, iMyUserId)) {
                service.prepareVpn(null, FAST_PACKAGE_NAME, iMyUserId);
            }
            service.setVpnPackageAuthorization(FAST_PACKAGE_NAME, iMyUserId, 1);
            return null;
        } catch (RemoteException unused) {
            return VpnConfig.getIntentForConfirmation();
        }
    }

    public static Intent prepare(Context context) {
        if (context instanceof GenericVpnContext) {
            Log.d(TAG, "prepare function with generic vpn context is called for knox vpn profile");
            GenericVpnContext genericVpnContext = (GenericVpnContext) context;
            String vPNProfile = genericVpnContext.getVPNProfile();
            genericVpnContext.getVPNState();
            genericVpnContext.getApplicationContext().getPackageName();
            boolean zIsMetaEnabled = genericVpnContext.isMetaEnabled();
            int iKnoxVpnProfileType = 0;
            try {
                iKnoxVpnProfileType = getService().knoxVpnProfileType(vPNProfile);
                if (getService().prepareEnterpriseVpnExt(vPNProfile, zIsMetaEnabled)) {
                    return null;
                }
            } catch (RemoteException unused) {
                Log.e(TAG, "Exception occured while trying to prepare knox vpn profile");
            }
            if (iKnoxVpnProfileType == 0) {
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
        } catch (RemoteException unused2) {
        }
        return VpnConfig.getIntentForConfirmation();
    }

    @SystemApi
    public static void prepareAndAuthorize(Context context) {
        IVpnManager service = getService();
        String packageName = context.getPackageName();
        try {
            int userId = context.getUserId();
            if (!service.prepareVpn(packageName, null, userId)) {
                service.prepareVpn(null, packageName, userId);
            }
            service.setVpnPackageAuthorization(packageName, userId, 1);
        } catch (RemoteException unused) {
        }
    }

    public boolean protect(int i) {
        try {
            if (getService() != null) {
                if (!getService().getChainingEnabledForProfile(Binder.getCallingUid())) {
                    return NetworkUtilsInternal.protectFromVpn(i);
                }
                if (!DBG) {
                    return true;
                }
                Log.d(TAG, "protect is not going to be called for " + Binder.getCallingUid());
                return true;
            }
            return NetworkUtilsInternal.protectFromVpn(i);
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean protect(Socket socket) {
        return protect(socket.getFileDescriptor$().getInt$());
    }

    public boolean protect(DatagramSocket datagramSocket) {
        return protect(datagramSocket.getFileDescriptor$().getInt$());
    }

    public boolean addAddress(InetAddress inetAddress, int i) {
        check(inetAddress, i);
        try {
            return getService().addVpnAddress(inetAddress.getHostAddress(), i);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean removeAddress(InetAddress inetAddress, int i) {
        check(inetAddress, i);
        try {
            return getService().removeVpnAddress(inetAddress.getHostAddress(), i);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean setUnderlyingNetworks(Network[] networkArr) {
        try {
            return getService().setUnderlyingNetworksForVpn(networkArr);
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

        @Override // android.os.Binder
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 16777215) {
                return false;
            }
            VpnService.this.onRevoke();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void check(InetAddress inetAddress, int i) {
        if (inetAddress.isLoopbackAddress()) {
            throw new IllegalArgumentException("Bad address");
        }
        if (inetAddress instanceof Inet4Address) {
            if (i < 0 || i > 32) {
                throw new IllegalArgumentException("Bad prefixLength");
            }
        } else {
            if (!(inetAddress instanceof Inet6Address)) {
                throw new IllegalArgumentException("Unsupported family");
            }
            if (i < 0 || i > 128) {
                throw new IllegalArgumentException("Bad prefixLength");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkNonPrefixBytes(InetAddress inetAddress, int i) {
        if (!new IpPrefix(inetAddress, i).getAddress().equals(inetAddress)) {
            throw new IllegalArgumentException("Bad address");
        }
    }

    public class Builder {
        private final List<LinkAddress> mAddresses;
        private final VpnConfig mConfig;
        private final List<RouteInfo> mRoutes;

        public Builder(VpnService vpnService) {
            VpnConfig vpnConfig = new VpnConfig();
            this.mConfig = vpnConfig;
            this.mAddresses = new ArrayList();
            this.mRoutes = new ArrayList();
            vpnConfig.user = vpnService.getClass().getName();
        }

        public Builder setSession(String str) {
            this.mConfig.session = str;
            return this;
        }

        public Builder setConfigureIntent(PendingIntent pendingIntent) {
            this.mConfig.configureIntent = pendingIntent;
            return this;
        }

        public Builder setMtu(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("Bad mtu");
            }
            this.mConfig.mtu = i;
            return this;
        }

        public Builder setHttpProxy(ProxyInfo proxyInfo) {
            this.mConfig.proxyInfo = proxyInfo;
            return this;
        }

        public Builder addAddress(InetAddress inetAddress, int i) {
            VpnService.check(inetAddress, i);
            if (inetAddress.isAnyLocalAddress()) {
                throw new IllegalArgumentException("Bad address");
            }
            this.mAddresses.add(new LinkAddress(inetAddress, i));
            return this;
        }

        public Builder addAddress(String str, int i) {
            return addAddress(InetAddress.parseNumericAddress(str), i);
        }

        private Builder addRoute(IpPrefix ipPrefix, int i) {
            VpnService.check(ipPrefix.getAddress(), ipPrefix.getPrefixLength());
            RouteInfo routeInfo = new RouteInfo(ipPrefix, null, null, i);
            int iFindRouteIndexByDestination = findRouteIndexByDestination(routeInfo);
            if (iFindRouteIndexByDestination == -1) {
                this.mRoutes.add(routeInfo);
                return this;
            }
            this.mRoutes.set(iFindRouteIndexByDestination, routeInfo);
            return this;
        }

        public Builder addRoute(InetAddress inetAddress, int i) {
            VpnService.checkNonPrefixBytes(inetAddress, i);
            return addRoute(new IpPrefix(inetAddress, i), 1);
        }

        public Builder addRoute(IpPrefix ipPrefix) {
            return addRoute(ipPrefix, 1);
        }

        public Builder addRoute(String str, int i) {
            return addRoute(InetAddress.parseNumericAddress(str), i);
        }

        public Builder excludeRoute(IpPrefix ipPrefix) {
            return addRoute(ipPrefix, 9);
        }

        public Builder addDnsServer(InetAddress inetAddress) {
            if (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) {
                throw new IllegalArgumentException("Bad address");
            }
            if (this.mConfig.dnsServers == null) {
                this.mConfig.dnsServers = new ArrayList();
            }
            this.mConfig.dnsServers.add(inetAddress.getHostAddress());
            return this;
        }

        public Builder addDnsServer(String str) {
            return addDnsServer(InetAddress.parseNumericAddress(str));
        }

        public Builder addSearchDomain(String str) {
            if (this.mConfig.searchDomains == null) {
                this.mConfig.searchDomains = new ArrayList();
            }
            this.mConfig.searchDomains.add(str);
            return this;
        }

        public Builder allowFamily(int i) {
            if (i == OsConstants.AF_INET) {
                this.mConfig.allowIPv4 = true;
                return this;
            }
            if (i == OsConstants.AF_INET6) {
                this.mConfig.allowIPv6 = true;
                return this;
            }
            throw new IllegalArgumentException(i + " is neither " + OsConstants.AF_INET + " nor " + OsConstants.AF_INET6);
        }

        private void verifyApp(String str) throws PackageManager.NameNotFoundException {
            try {
                IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getApplicationInfo(str, 0L, UserHandle.getCallingUserId());
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }

        public Builder addAllowedApplication(String str) throws PackageManager.NameNotFoundException {
            if (this.mConfig.disallowedApplications != null) {
                throw new UnsupportedOperationException("addDisallowedApplication already called");
            }
            verifyApp(str);
            if (str.equals(VpnService.KNOXGUARD_PACKAGE_NAME) && KnoxGuardManager.getInstance().isVpnExceptionRequired()) {
                return this;
            }
            if (this.mConfig.allowedApplications == null) {
                this.mConfig.allowedApplications = new ArrayList();
            }
            this.mConfig.allowedApplications.add(str);
            return this;
        }

        public Builder addDisallowedApplication(String str) throws PackageManager.NameNotFoundException {
            if (this.mConfig.allowedApplications != null) {
                throw new UnsupportedOperationException("addAllowedApplication already called");
            }
            verifyApp(str);
            if (this.mConfig.disallowedApplications == null) {
                this.mConfig.disallowedApplications = new ArrayList();
            }
            this.mConfig.disallowedApplications.add(str);
            return this;
        }

        public Builder allowBypass() {
            this.mConfig.allowBypass = true;
            return this;
        }

        public Builder setBlocking(boolean z) {
            this.mConfig.blocking = z;
            return this;
        }

        public Builder setUnderlyingNetworks(Network[] networkArr) {
            this.mConfig.underlyingNetworks = networkArr != null ? (Network[]) networkArr.clone() : null;
            return this;
        }

        public Builder setMetered(boolean z) {
            this.mConfig.isMetered = z;
            return this;
        }

        public ParcelFileDescriptor establish() {
            if (this.mConfig.allowedApplications == null && KnoxGuardManager.getInstance().isVpnExceptionRequired()) {
                try {
                    addDisallowedApplication(VpnService.KNOXGUARD_PACKAGE_NAME);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e(VpnService.TAG, "Exception occured while calling addDisallowedApplication");
                }
            }
            this.mConfig.addresses = this.mAddresses;
            this.mConfig.routes = this.mRoutes;
            try {
                return VpnService.getService().establishVpn(this.mConfig);
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }

        private int findRouteIndexByDestination(RouteInfo routeInfo) {
            for (int i = 0; i < this.mRoutes.size(); i++) {
                if (this.mRoutes.get(i).getDestination().equals(routeInfo.getDestination())) {
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
