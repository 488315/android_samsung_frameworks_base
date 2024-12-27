package android.apphibernation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class HibernationStats implements Parcelable {
    public static final Parcelable.Creator<HibernationStats> CREATOR = new Parcelable.Creator<HibernationStats>() { // from class: android.apphibernation.HibernationStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HibernationStats createFromParcel(Parcel in) {
            return new HibernationStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HibernationStats[] newArray(int size) {
            return new HibernationStats[size];
        }
    };
    private final long mDiskBytesSaved;

    public HibernationStats(long diskBytesSaved) {
        this.mDiskBytesSaved = diskBytesSaved;
    }

    private HibernationStats(Parcel in) {
        this.mDiskBytesSaved = in.readLong();
    }

    public long getDiskBytesSaved() {
        return this.mDiskBytesSaved;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mDiskBytesSaved);
    }
}
