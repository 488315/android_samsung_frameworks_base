package android.location;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.TimeUtils;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Geofence implements Parcelable {
    public static final Parcelable.Creator<Geofence> CREATOR = new Parcelable.Creator<Geofence>() { // from class: android.location.Geofence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Geofence createFromParcel(Parcel parcel) {
            return new Geofence(parcel.readDouble(), parcel.readDouble(), parcel.readFloat(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Geofence[] newArray(int i) {
            return new Geofence[i];
        }
    };
    private long mExpirationRealtimeMs;
    private final double mLatitude;
    private final double mLongitude;
    private final float mRadius;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static Geofence createCircle(double d, double d2, float f, long j) {
        return new Geofence(d, d2, f, j);
    }

    Geofence(double d, double d2, float f, long j) {
        Preconditions.checkArgumentInRange(d, -90.0d, 90.0d, "latitude");
        Preconditions.checkArgumentInRange(d2, -180.0d, 180.0d, "latitude");
        Preconditions.checkArgument(f > 0.0f, "invalid radius: %f", Float.valueOf(f));
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = f;
        this.mExpirationRealtimeMs = j;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public boolean isExpired() {
        return isExpired(SystemClock.elapsedRealtime());
    }

    public boolean isExpired(long j) {
        return j >= this.mExpirationRealtimeMs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeFloat(this.mRadius);
        parcel.writeLong(this.mExpirationRealtimeMs);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Geofence)) {
            return false;
        }
        Geofence geofence = (Geofence) obj;
        return Double.compare(geofence.mLatitude, this.mLatitude) == 0 && Double.compare(geofence.mLongitude, this.mLongitude) == 0 && Float.compare(geofence.mRadius, this.mRadius) == 0 && this.mExpirationRealtimeMs == geofence.mExpirationRealtimeMs;
    }

    public int hashCode() {
        return Objects.hash(Double.valueOf(this.mLatitude), Double.valueOf(this.mLongitude), Float.valueOf(this.mRadius));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Geofence[(");
        sb.append(this.mLatitude);
        sb.append(", ");
        sb.append(this.mLongitude);
        sb.append(") ");
        sb.append(this.mRadius);
        sb.append("m");
        if (this.mExpirationRealtimeMs < Long.MAX_VALUE) {
            if (isExpired()) {
                sb.append(" expired");
            } else {
                sb.append(" expires=");
                TimeUtils.formatDuration(this.mExpirationRealtimeMs, sb);
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
