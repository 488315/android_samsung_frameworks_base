package android.os.storage;

import android.content.pm.UserInfo;
import android.os.IInstalld;
import android.os.IVold;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class StorageManagerInternal {

    public interface CloudProviderChangeListener {
        void onCloudProviderChanged(int i, String str);
    }

    public interface ResetListener {
        void onReset(IVold iVold);
    }

    public abstract void addResetListener(ResetListener resetListener);

    public abstract IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor parcelFileDescriptor, int i) throws IOException;

    public abstract int enableFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws IOException;

    public abstract void freeCache(String str, long j);

    public abstract int getExternalStorageMountMode(int i, String str);

    public abstract List<String> getPrimaryVolumeIds();

    public abstract boolean hasExternalStorageAccess(int i, String str);

    public abstract boolean hasLegacyExternalStorage(int i);

    public abstract boolean isCeStoragePrepared(int i);

    public abstract boolean isExternalStorageService(int i);

    public abstract boolean isFuseMounted(int i);

    public abstract void markCeStoragePrepared(int i);

    public abstract void onAppOpsChanged(int i, int i2, String str, int i3, int i4);

    public abstract void prepareAppDataAfterInstall(String str, int i);

    public abstract boolean prepareStorageDirs(int i, Set<String> set, String str);

    public abstract void prepareUserStorageForMove(String str, String str2, List<UserInfo> list);

    public abstract void registerCloudProviderChangeListener(CloudProviderChangeListener cloudProviderChangeListener);

    public abstract void registerStorageLockEventListener(ICeStorageLockEventListener iCeStorageLockEventListener);

    public abstract void resetUser(int i);

    public abstract void unregisterStorageLockEventListener(ICeStorageLockEventListener iCeStorageLockEventListener);
}
