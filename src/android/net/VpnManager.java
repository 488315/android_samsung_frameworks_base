package android.net;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.RemoteException;
import com.android.internal.R;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.GeneralSecurityException;
import java.util.List;

/* loaded from: classes3.dex */
public class VpnManager {
    public static final String ACTION_VPN_MANAGER_EVENT = "android.net.action.VPN_MANAGER_EVENT";
    public static final String CATEGORY_EVENT_ALWAYS_ON_STATE_CHANGED = "android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED";
    public static final String CATEGORY_EVENT_DEACTIVATED_BY_USER = "android.net.category.EVENT_DEACTIVATED_BY_USER";
    public static final String CATEGORY_EVENT_IKE_ERROR = "android.net.category.EVENT_IKE_ERROR";
    public static final String CATEGORY_EVENT_NETWORK_ERROR = "android.net.category.EVENT_NETWORK_ERROR";
    public static final int ERROR_CLASS_NOT_RECOVERABLE = 1;
    public static final int ERROR_CLASS_RECOVERABLE = 2;
    public static final int ERROR_CODE_NETWORK_IO = 3;
    public static final int ERROR_CODE_NETWORK_LOST = 2;
    public static final int ERROR_CODE_NETWORK_PROTOCOL_TIMEOUT = 1;
    public static final int ERROR_CODE_NETWORK_UNKNOWN_HOST = 0;
    public static final String EXTRA_ERROR_CLASS = "android.net.extra.ERROR_CLASS";
    public static final String EXTRA_ERROR_CODE = "android.net.extra.ERROR_CODE";
    public static final String EXTRA_SESSION_KEY = "android.net.extra.SESSION_KEY";
    public static final String EXTRA_TIMESTAMP_MILLIS = "android.net.extra.TIMESTAMP_MILLIS";
    public static final String EXTRA_UNDERLYING_LINK_PROPERTIES = "android.net.extra.UNDERLYING_LINK_PROPERTIES";
    public static final String EXTRA_UNDERLYING_NETWORK = "android.net.extra.UNDERLYING_NETWORK";
    public static final String EXTRA_UNDERLYING_NETWORK_CAPABILITIES = "android.net.extra.UNDERLYING_NETWORK_CAPABILITIES";
    public static final String EXTRA_VPN_PROFILE_STATE = "android.net.extra.VPN_PROFILE_STATE";
    public static final String NOTIFICATION_CHANNEL_VPN = "VPN";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_LEGACY = 3;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_NONE = -1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_OEM = 4;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_OEM_LEGACY = 6;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_OEM_SERVICE = 5;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_PLATFORM = 2;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_SERVICE = 1;
    private final Context mContext;
    private final IVpnManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VpnType {
    }

    private static Intent getIntentForConfirmation() {
        Intent intent = new Intent();
        intent.setComponent(ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_platformVpnConfirmDialogComponent)));
        return intent;
    }

    public VpnManager(Context context, IVpnManager iVpnManager) {
        this.mContext = (Context) Preconditions.checkNotNull(context, "missing Context");
        this.mService = (IVpnManager) Preconditions.checkNotNull(iVpnManager, "missing IVpnManager");
    }

    public Intent provisionVpnProfile(PlatformVpnProfile platformVpnProfile) {
        try {
            try {
                if (this.mService.provisionVpnProfile(platformVpnProfile.toVpnProfile(), this.mContext.getOpPackageName())) {
                    return null;
                }
                return getIntentForConfirmation();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (IOException | GeneralSecurityException e2) {
            throw new IllegalArgumentException("Failed to serialize PlatformVpnProfile", e2);
        }
    }

    public void deleteProvisionedVpnProfile() {
        try {
            this.mService.deleteVpnProfile(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String startProvisionedVpnProfileSession() {
        try {
            return this.mService.startVpnProfile(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void startProvisionedVpnProfile() {
        startProvisionedVpnProfileSession();
    }

    public void stopProvisionedVpnProfile() {
        try {
            this.mService.stopVpnProfile(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public VpnConfig getVpnConfig(int i) {
        try {
            return this.mService.getVpnConfig(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public VpnProfileState getProvisionedVpnProfileState() {
        try {
            return this.mService.getProvisionedVpnProfileState(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean prepareVpn(String str, String str2, int i) {
        try {
            return this.mService.prepareVpn(str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVpnPackageAuthorization(String str, int i, int i2) {
        try {
            this.mService.setVpnPackageAuthorization(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAlwaysOnVpnPackageSupportedForUser(int i, String str) {
        try {
            return this.mService.isAlwaysOnVpnPackageSupported(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAlwaysOnVpnPackageForUser(int i, String str, boolean z, List<String> list) {
        try {
            return this.mService.setAlwaysOnVpnPackage(i, str, z, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAlwaysOnVpnPackageForUser(int i) {
        try {
            return this.mService.getAlwaysOnVpnPackage(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVpnLockdownEnabled(int i) {
        try {
            return this.mService.isVpnLockdownEnabled(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAppExclusionList(int i, String str, List<String> list) {
        try {
            return this.mService.setAppExclusionList(i, str, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getAppExclusionList(int i, String str) {
        try {
            return this.mService.getAppExclusionList(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getVpnLockdownAllowlist(int i) {
        try {
            return this.mService.getVpnLockdownAllowlist(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LegacyVpnInfo getLegacyVpnInfo(int i) {
        try {
            return this.mService.getLegacyVpnInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startLegacyVpn(VpnProfile vpnProfile) {
        try {
            this.mService.startLegacyVpn(vpnProfile);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateLockdownVpn() {
        try {
            return this.mService.updateLockdownVpn();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public byte[] getFromVpnProfileStore(String str) {
        try {
            return this.mService.getFromVpnProfileStore(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean putIntoVpnProfileStore(String str, byte[] bArr) {
        try {
            return this.mService.putIntoVpnProfileStore(str, bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeFromVpnProfileStore(String str) {
        try {
            return this.mService.removeFromVpnProfileStore(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String[] listFromVpnProfileStore(String str) {
        try {
            return this.mService.listFromVpnProfileStore(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
