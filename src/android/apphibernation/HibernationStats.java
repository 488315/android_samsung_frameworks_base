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
        public HibernationStats createFromParcel(Parcel parcel) {
            return new HibernationStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HibernationStats[] newArray(int i) {
            return new HibernationStats[i];
        }
    };
    private final long mDiskBytesSaved;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HibernationStats(long j) {
        this.mDiskBytesSaved = j;
    }

    private HibernationStats(Parcel parcel) {
        this.mDiskBytesSaved = parcel.readLong();
    }

    public long getDiskBytesSaved() {
        return this.mDiskBytesSaved;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mDiskBytesSaved);
    }
}
