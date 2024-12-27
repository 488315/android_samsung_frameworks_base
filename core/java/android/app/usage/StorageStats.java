package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;
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
        public StorageStats createFromParcel(Parcel in) {
            return new StorageStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageStats[] newArray(int size) {
            return new StorageStats[size];
        }
    };
    public long apkBytes;
    public long cacheBytes;
    public long codeBytes;
    public long curProfBytes;
    public long dataBytes;
    public long dexoptBytes;
    public long dmBytes;
    public long externalCacheBytes;
    public long libBytes;
    public long refProfBytes;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppDataType {
    }

    public long getAppBytes() {
        return this.codeBytes;
    }

    public long getAppBytesByDataType(int dataType) {
        switch (dataType) {
            case 0:
                return this.dexoptBytes;
            case 1:
                return this.refProfBytes;
            case 2:
                return this.curProfBytes;
            case 3:
                return this.apkBytes;
            case 4:
                return this.dmBytes;
            case 5:
                return this.libBytes;
            default:
                return 0L;
        }
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

    public StorageStats(Parcel in) {
        this.codeBytes = in.readLong();
        this.dataBytes = in.readLong();
        this.cacheBytes = in.readLong();
        this.dexoptBytes = in.readLong();
        this.refProfBytes = in.readLong();
        this.curProfBytes = in.readLong();
        this.apkBytes = in.readLong();
        this.libBytes = in.readLong();
        this.dmBytes = in.readLong();
        this.externalCacheBytes = in.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.codeBytes);
        dest.writeLong(this.dataBytes);
        dest.writeLong(this.cacheBytes);
        dest.writeLong(this.dexoptBytes);
        dest.writeLong(this.refProfBytes);
        dest.writeLong(this.curProfBytes);
        dest.writeLong(this.apkBytes);
        dest.writeLong(this.libBytes);
        dest.writeLong(this.dmBytes);
        dest.writeLong(this.externalCacheBytes);
    }
}
