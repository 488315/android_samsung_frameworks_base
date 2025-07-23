package android.location;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.time.Duration;
import java.time.Instant;

/* loaded from: classes2.dex */
public final class LocationTime implements Parcelable {
    public static final Parcelable.Creator<LocationTime> CREATOR = new Parcelable.Creator<LocationTime>() { // from class: android.location.LocationTime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocationTime createFromParcel(Parcel parcel) {
            return new LocationTime(parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocationTime[] newArray(int i) {
            return new LocationTime[i];
        }
    };
    private final long mElapsedRealtimeNanos;
    private final long mUnixEpochTimeMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocationTime(long j, long j2) {
        this.mUnixEpochTimeMillis = j;
        this.mElapsedRealtimeNanos = j2;
    }

    public long getUnixEpochTimeMillis() {
        return this.mUnixEpochTimeMillis;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mUnixEpochTimeMillis);
        parcel.writeLong(this.mElapsedRealtimeNanos);
    }

    public String toString() {
        return "LocationTime{mUnixEpochTimeMillis=" + Instant.ofEpochMilli(this.mUnixEpochTimeMillis) + NavigationBarInflaterView.KEY_CODE_START + this.mUnixEpochTimeMillis + "), mElapsedRealtimeNanos=" + Duration.ofNanos(this.mElapsedRealtimeNanos) + NavigationBarInflaterView.KEY_CODE_START + this.mElapsedRealtimeNanos + ")}";
    }
}
