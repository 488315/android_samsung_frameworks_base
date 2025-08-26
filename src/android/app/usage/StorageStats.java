package android.app.usage;

import android.app.usage.IStorageStatsManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class StorageStats implements Parcelable {
    public static final int APP_DATA_TYPE_FILE_TYPE_APK = 3;
    public static final int APP_DATA_TYPE_FILE_TYPE_CURRENT_PROFILE = 2;
    public static final int APP_DATA_TYPE_FILE_TYPE_DEXOPT_ARTIFACT = 0;
    public static final int APP_DATA_TYPE_FILE_TYPE_DM = 4;
    public static final int APP_DATA_TYPE_FILE_TYPE_REFERENCE_PROFILE = 1;
    public static final int APP_DATA_TYPE_LIB = 5;
    public static final Parcelable.Creator<StorageStats> CREATOR = new Parcelable.Creator<StorageStats>() { // from class: android.app.usage.StorageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageStats createFromParcel(Parcel parcel) {
            return new StorageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageStats[] newArray(int i) {
            return new StorageStats[i];
        }
    };
    private static final String TAG = "StorageStats";
    public long apkBytes;
    private boolean artStatsFetched;
    public long cacheBytes;
    public long codeBytes;
    public long curProfBytes;
    public long dataBytes;
    public long dexoptBytes;
    public long dmBytes;
    public long externalCacheBytes;
    public long libBytes;
    public String packageName;
    public long refProfBytes;
    public int uid;
    public int userHandle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppDataType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getAppBytes() {
        return this.codeBytes;
    }

    public long getAppBytesByDataType(int i) {
        if (i == 0) {
            return getDexoptBytes();
        }
        if (i == 1) {
            return getRefProfBytes();
        }
        if (i == 2) {
            return getCurProfBytes();
        }
        if (i == 3) {
            return this.apkBytes;
        }
        if (i == 4) {
            return this.dmBytes;
        }
        if (i != 5) {
            return 0L;
        }
        return this.libBytes;
    }

    public long getDataBytes() {
        return this.dataBytes;
    }

    public long getCacheBytes() {
        return this.cacheBytes;
    }

    public long getExternalCacheBytes() {
        return this.externalCacheBytes;
    }

    public StorageStats() {
    }

    public StorageStats(Parcel parcel) {
        this.packageName = parcel.readString8();
        this.userHandle = parcel.readInt();
        this.uid = parcel.readInt();
        this.codeBytes = parcel.readLong();
        this.dataBytes = parcel.readLong();
        this.cacheBytes = parcel.readLong();
        this.dexoptBytes = parcel.readLong();
        this.refProfBytes = parcel.readLong();
        this.curProfBytes = parcel.readLong();
        this.apkBytes = parcel.readLong();
        this.libBytes = parcel.readLong();
        this.dmBytes = parcel.readLong();
        this.externalCacheBytes = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.packageName);
        parcel.writeInt(this.userHandle);
        parcel.writeInt(this.uid);
        parcel.writeLong(this.codeBytes);
        parcel.writeLong(this.dataBytes);
        parcel.writeLong(this.cacheBytes);
        parcel.writeLong(this.dexoptBytes);
        parcel.writeLong(this.refProfBytes);
        parcel.writeLong(this.curProfBytes);
        parcel.writeLong(this.apkBytes);
        parcel.writeLong(this.libBytes);
        parcel.writeLong(this.dmBytes);
        parcel.writeLong(this.externalCacheBytes);
    }

    private void getArtManagedStats() {
        try {
            if (!Flags.getAppArtManagedBytes() || this.artStatsFetched) {
                return;
            }
            StorageStats storageStatsQueryArtManagedStats = IStorageStatsManager.Stub.asInterface(ServiceManager.getService(Context.STORAGE_STATS_SERVICE)).queryArtManagedStats(this.packageName, this.userHandle, this.uid);
            this.dexoptBytes = storageStatsQueryArtManagedStats.dexoptBytes;
            this.curProfBytes = storageStatsQueryArtManagedStats.curProfBytes;
            this.refProfBytes = storageStatsQueryArtManagedStats.refProfBytes;
            this.artStatsFetched = true;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get art stats", e);
            e.rethrowFromSystemServer();
        }
    }

    private long getDexoptBytes() {
        getArtManagedStats();
        return this.dexoptBytes;
    }

    private long getCurProfBytes() {
        getArtManagedStats();
        return this.curProfBytes;
    }

    private long getRefProfBytes() {
        getArtManagedStats();
        return this.refProfBytes;
    }
}
