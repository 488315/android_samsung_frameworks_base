package com.android.server.pm.permission;

/* loaded from: classes3.dex */
public interface LegacyPermissionManagerInternal {

    public interface PackagesProvider {
        String[] getPackages(int i);
    }

    public interface SyncAdapterPackagesProvider {
        String[] getPackages(String str, int i);
    }

    int checkSoundTriggerRecordAudioPermissionForDataDelivery(int i, String str, String str2, String str3);

    void grantDefaultPermissions(int i);

    void grantDefaultPermissionsToDefaultSimCallManager(String str, int i);

    void grantDefaultPermissionsToDefaultUseOpenWifiApp(String str, int i);

    void resetRuntimePermissions();

    void scheduleReadDefaultPermissionExceptions();

    void setDialerAppPackagesProvider(PackagesProvider packagesProvider);

    void setLocationExtraPackagesProvider(PackagesProvider packagesProvider);

    void setLocationPackagesProvider(PackagesProvider packagesProvider);

    void setSimCallManagerPackagesProvider(PackagesProvider packagesProvider);

    void setSmsAppPackagesProvider(PackagesProvider packagesProvider);

    void setSyncAdapterPackagesProvider(SyncAdapterPackagesProvider syncAdapterPackagesProvider);

    void setUseOpenWifiAppPackagesProvider(PackagesProvider packagesProvider);

    void setVoiceInteractionPackagesProvider(PackagesProvider packagesProvider);
}
