package android.os;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.SignedPackage;
import android.os.ISystemConfig;
import android.util.ArraySet;
import android.util.Log;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SystemApi
/* loaded from: classes3.dex */
public class SystemConfigManager {
    private static final String TAG = "SystemConfigManager";
    private final ISystemConfig mInterface = ISystemConfig.Stub.asInterface(ServiceManager.getService(Context.SYSTEM_CONFIG_SERVICE));

    public Set<String> getDisabledUntilUsedPreinstalledCarrierApps() {
        try {
            return new ArraySet(this.mInterface.getDisabledUntilUsedPreinstalledCarrierApps());
        } catch (RemoteException unused) {
            Log.e(TAG, "Caught remote exception");
            return Collections.EMPTY_SET;
        }
    }

    public Map<String, List<String>> getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        try {
            return this.mInterface.getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
        } catch (RemoteException unused) {
            Log.e(TAG, "Caught remote exception");
            return Collections.EMPTY_MAP;
        }
    }

    public Map<String, List<CarrierAssociatedAppEntry>> getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() {
        try {
            return this.mInterface.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
        } catch (RemoteException e) {
            Log.e(TAG, "Caught remote exception", e);
            return Collections.EMPTY_MAP;
        }
    }

    public int[] getSystemPermissionUids(String str) {
        try {
            return this.mInterface.getSystemPermissionUids(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<ComponentName> getEnabledComponentOverrides(String str) {
        try {
            return this.mInterface.getEnabledComponentOverrides(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ComponentName> getDefaultVrComponents() {
        try {
            return this.mInterface.getDefaultVrComponents();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return Collections.EMPTY_LIST;
        }
    }

    public List<String> getPreventUserDisablePackages() {
        try {
            return this.mInterface.getPreventUserDisablePackages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Set<SignedPackage> getEnhancedConfirmationTrustedPackages() {
        try {
            return (Set) this.mInterface.getEnhancedConfirmationTrustedPackages().stream().map(new SystemConfigManager$$ExternalSyntheticLambda0()).collect(Collectors.toSet());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Set<SignedPackage> getEnhancedConfirmationTrustedInstallers() {
        try {
            return (Set) this.mInterface.getEnhancedConfirmationTrustedInstallers().stream().map(new SystemConfigManager$$ExternalSyntheticLambda0()).collect(Collectors.toSet());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
