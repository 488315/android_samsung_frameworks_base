package android.app.usage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.CrateInfo;
import android.os.storage.StorageManager;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes.dex */
public class StorageStatsManager {
    private final Context mContext;
    private final IStorageStatsManager mService;

    public StorageStatsManager(Context context, IStorageStatsManager iStorageStatsManager) {
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mService = (IStorageStatsManager) Objects.requireNonNull(iStorageStatsManager);
    }

    public boolean isQuotaSupported(UUID uuid) {
        try {
            return this.mService.isQuotaSupported(StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isQuotaSupported(String str) {
        return isQuotaSupported(StorageManager.convert(str));
    }

    public boolean isReservedSupported(UUID uuid) {
        try {
            return this.mService.isReservedSupported(StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getTotalBytes(UUID uuid) throws IOException {
        try {
            return this.mService.getTotalBytes(StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public long getTotalBytes(String str) throws IOException {
        return getTotalBytes(StorageManager.convert(str));
    }

    public long getFreeBytes(UUID uuid) throws IOException {
        try {
            return this.mService.getFreeBytes(StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public long getFreeBytes(String str) throws IOException {
        return getFreeBytes(StorageManager.convert(str));
    }

    public long getCacheBytes(UUID uuid) throws IOException {
        try {
            return this.mService.getCacheBytes(StorageManager.convert(uuid), this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public long getCacheBytes(String str) throws IOException {
        return getCacheBytes(StorageManager.convert(str));
    }

    public StorageStats queryStatsForPackage(UUID uuid, String str, UserHandle userHandle) throws PackageManager.NameNotFoundException, IOException {
        try {
            return this.mService.queryStatsForPackage(StorageManager.convert(uuid), str, userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public StorageStats queryStatsForPackage(String str, String str2, UserHandle userHandle) throws PackageManager.NameNotFoundException, IOException {
        return queryStatsForPackage(StorageManager.convert(str), str2, userHandle);
    }

    public StorageStats queryStatsForUid(UUID uuid, int i) throws IOException {
        try {
            return this.mService.queryStatsForUid(StorageManager.convert(uuid), i, this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public StorageStats queryStatsForUid(String str, int i) throws IOException {
        return queryStatsForUid(StorageManager.convert(str), i);
    }

    public StorageStats queryStatsForUser(UUID uuid, UserHandle userHandle) throws IOException {
        try {
            return this.mService.queryStatsForUser(StorageManager.convert(uuid), userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public StorageStats queryStatsForUser(String str, UserHandle userHandle) throws IOException {
        return queryStatsForUser(StorageManager.convert(str), userHandle);
    }

    public ExternalStorageStats queryExternalStatsForUser(UUID uuid, UserHandle userHandle) throws IOException {
        try {
            return this.mService.queryExternalStatsForUser(StorageManager.convert(uuid), userHandle.getIdentifier(), this.mContext.getOpPackageName());
        } catch (ParcelableException e) {
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public ExternalStorageStats queryExternalStatsForUser(String str, UserHandle userHandle) throws IOException {
        return queryExternalStatsForUser(StorageManager.convert(str), userHandle);
    }

    public long getCacheQuotaBytes(String str, int i) {
        try {
            return this.mService.getCacheQuotaBytes(str, i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Collection<CrateInfo> queryCratesForUid(UUID uuid, int i) throws IOException, PackageManager.NameNotFoundException {
        try {
            return ((ParceledListSlice) Objects.requireNonNull(this.mService.queryCratesForUid(StorageManager.convert(uuid), i, this.mContext.getOpPackageName()))).getList();
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public Collection<CrateInfo> queryCratesForPackage(UUID uuid, String str, UserHandle userHandle) throws PackageManager.NameNotFoundException, IOException {
        try {
            return ((ParceledListSlice) Objects.requireNonNull(this.mService.queryCratesForPackage(StorageManager.convert(uuid), str, userHandle.getIdentifier(), this.mContext.getOpPackageName()))).getList();
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public Collection<CrateInfo> queryCratesForUser(UUID uuid, UserHandle userHandle) throws PackageManager.NameNotFoundException, IOException {
        try {
            return ((ParceledListSlice) Objects.requireNonNull(this.mService.queryCratesForUser(StorageManager.convert(uuid), userHandle.getIdentifier(), this.mContext.getOpPackageName()))).getList();
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            e.maybeRethrow(IOException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }
}
