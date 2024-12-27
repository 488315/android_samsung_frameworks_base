package android.os;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.SignedPackage;
import android.content.pm.SignedPackageParcel;
import android.util.ArraySet;
import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SystemApi
public class SystemConfigManager {
    private static final String TAG = SystemConfigManager.class.getSimpleName();
    private final ISystemConfig mInterface =
            ISystemConfig.Stub.asInterface(
                    ServiceManager.getService(Context.SYSTEM_CONFIG_SERVICE));

    public Set<String> getDisabledUntilUsedPreinstalledCarrierApps() {
        try {
            List<String> apps = this.mInterface.getDisabledUntilUsedPreinstalledCarrierApps();
            return new ArraySet(apps);
        } catch (RemoteException e) {
            Log.e(TAG, "Caught remote exception");
            return Collections.emptySet();
        }
    }

    public Map<String, List<String>> getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        try {
            return this.mInterface.getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
        } catch (RemoteException e) {
            Log.e(TAG, "Caught remote exception");
            return Collections.emptyMap();
        }
    }

    public Map<String, List<CarrierAssociatedAppEntry>>
            getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() {
        try {
            return this.mInterface.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
        } catch (RemoteException e) {
            Log.e(TAG, "Caught remote exception", e);
            return Collections.emptyMap();
        }
    }

    public int[] getSystemPermissionUids(String permissionName) {
        try {
            return this.mInterface.getSystemPermissionUids(permissionName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<ComponentName> getEnabledComponentOverrides(String packageName) {
        try {
            return this.mInterface.getEnabledComponentOverrides(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ComponentName> getDefaultVrComponents() {
        try {
            return this.mInterface.getDefaultVrComponents();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return Collections.emptyList();
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
            List<SignedPackageParcel> parcels =
                    this.mInterface.getEnhancedConfirmationTrustedPackages();
            return (Set)
                    parcels.stream()
                            .map(new SystemConfigManager$$ExternalSyntheticLambda0())
                            .collect(Collectors.toSet());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public Set<SignedPackage> getEnhancedConfirmationTrustedInstallers() {
        try {
            List<SignedPackageParcel> parcels =
                    this.mInterface.getEnhancedConfirmationTrustedInstallers();
            return (Set)
                    parcels.stream()
                            .map(new SystemConfigManager$$ExternalSyntheticLambda0())
                            .collect(Collectors.toSet());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
