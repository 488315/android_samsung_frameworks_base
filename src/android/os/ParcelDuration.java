package android.os;

import android.os.Parcelable;
import java.time.Duration;

/* loaded from: classes3.dex */
public final class ParcelDuration implements Parcelable {
    public static final Parcelable.Creator<ParcelDuration> CREATOR = new Parcelable.Creator<ParcelDuration>() { // from class: android.os.ParcelDuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelDuration createFromParcel(Parcel parcel) {
            return new ParcelDuration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelDuration[] newArray(int i) {
            return new ParcelDuration[i];
        }
    };
    private final int mNanos;
    private final long mSeconds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelDuration(long j) {
        this(Duration.ofMillis(j));
    }

    public ParcelDuration(Duration duration) {
        this.mSeconds = duration.getSeconds();
        this.mNanos = duration.getNano();
    }

    private ParcelDuration(Parcel parcel) {
        this.mSeconds = parcel.readLong();
        this.mNanos = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mSeconds);
        parcel.writeInt(this.mNanos);
    }

    public Duration getDuration() {
        return Duration.ofSeconds(this.mSeconds, this.mNanos);
    }

    public String toString() {
        return getDuration().toString();
    }
}
