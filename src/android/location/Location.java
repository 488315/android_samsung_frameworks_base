package android.location;

import android.annotation.SystemApi;
import android.content.pm.PackageManager;
import android.hardware.scontext.SContextConstants;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Printer;
import android.util.TimeUtils;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class Location implements Parcelable {

    @SystemApi
    @Deprecated
    public static final String EXTRA_NO_GPS_LOCATION = "noGPSLocation";
    public static final int FORMAT_DEGREES = 0;
    public static final int FORMAT_MINUTES = 1;
    public static final int FORMAT_SECONDS = 2;
    private static final int HAS_ALTITUDE_ACCURACY_MASK = 32;
    private static final int HAS_ALTITUDE_MASK = 1;
    private static final int HAS_BEARING_ACCURACY_MASK = 128;
    private static final int HAS_BEARING_MASK = 4;
    private static final int HAS_ELAPSED_REALTIME_UNCERTAINTY_MASK = 256;
    private static final int HAS_HORIZONTAL_ACCURACY_MASK = 8;
    private static final int HAS_MOCK_PROVIDER_MASK = 16;
    private static final int HAS_MSL_ALTITUDE_ACCURACY_MASK = 1024;
    private static final int HAS_MSL_ALTITUDE_MASK = 512;
    private static final int HAS_SPEED_ACCURACY_MASK = 64;
    private static final int HAS_SPEED_MASK = 2;
    private float mAltitudeAccuracyMeters;
    private double mAltitudeMeters;
    private float mBearingAccuracyDegrees;
    private float mBearingDegrees;
    private long mElapsedRealtimeNs;
    private double mElapsedRealtimeUncertaintyNs;
    private float mHorizontalAccuracyMeters;
    private double mLatitudeDegrees;
    private double mLongitudeDegrees;
    private float mMslAltitudeAccuracyMeters;
    private double mMslAltitudeMeters;
    private String mProvider;
    private float mSpeedAccuracyMetersPerSecond;
    private float mSpeedMetersPerSecond;
    private long mTimeMs;
    private static final ThreadLocal<BearingDistanceCache> sBearingDistanceCache = ThreadLocal.withInitial(new Supplier() { // from class: android.location.Location$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return Location.m2270$r8$lambda$LCoyno7iOKo6n1w2mcfXqv702o();
        }
    });
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() { // from class: android.location.Location.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Location createFromParcel(Parcel parcel) {
            Location location = new Location(parcel.readString8());
            location.mFieldsMask = parcel.readInt();
            location.mTimeMs = parcel.readLong();
            location.mElapsedRealtimeNs = parcel.readLong();
            if (location.hasElapsedRealtimeUncertaintyNanos()) {
                location.mElapsedRealtimeUncertaintyNs = parcel.readDouble();
            }
            location.mLatitudeDegrees = parcel.readDouble();
            location.mLongitudeDegrees = parcel.readDouble();
            if (location.hasAltitude()) {
                location.mAltitudeMeters = parcel.readDouble();
            }
            if (location.hasSpeed()) {
                location.mSpeedMetersPerSecond = parcel.readFloat();
            }
            if (location.hasBearing()) {
                location.mBearingDegrees = parcel.readFloat();
            }
            if (location.hasAccuracy()) {
                location.mHorizontalAccuracyMeters = parcel.readFloat();
            }
            if (location.hasVerticalAccuracy()) {
                location.mAltitudeAccuracyMeters = parcel.readFloat();
            }
            if (location.hasSpeedAccuracy()) {
                location.mSpeedAccuracyMetersPerSecond = parcel.readFloat();
            }
            if (location.hasBearingAccuracy()) {
                location.mBearingAccuracyDegrees = parcel.readFloat();
            }
            if (location.hasMslAltitude()) {
                location.mMslAltitudeMeters = parcel.readDouble();
            }
            if (location.hasMslAltitudeAccuracy()) {
                location.mMslAltitudeAccuracyMeters = parcel.readFloat();
            }
            location.mExtras = Bundle.setDefusable(parcel.readBundle(), true);
            return location;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Location[] newArray(int i) {
            return new Location[i];
        }
    };
    private int mFieldsMask = 0;
    private Bundle mExtras = null;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Format {
    }

    /* renamed from: $r8$lambda$LCoyno7iOKo6n1w2mcfX-qv702o, reason: not valid java name */
    public static /* synthetic */ BearingDistanceCache m2270$r8$lambda$LCoyno7iOKo6n1w2mcfXqv702o() {
        return new BearingDistanceCache();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Location(String str) {
        this.mProvider = str;
    }

    public Location(Location location) {
        set(location);
    }

    public void set(Location location) {
        this.mFieldsMask = location.mFieldsMask;
        this.mProvider = location.mProvider;
        this.mTimeMs = location.mTimeMs;
        this.mElapsedRealtimeNs = location.mElapsedRealtimeNs;
        this.mElapsedRealtimeUncertaintyNs = location.mElapsedRealtimeUncertaintyNs;
        this.mLatitudeDegrees = location.mLatitudeDegrees;
        this.mLongitudeDegrees = location.mLongitudeDegrees;
        this.mHorizontalAccuracyMeters = location.mHorizontalAccuracyMeters;
        this.mAltitudeMeters = location.mAltitudeMeters;
        this.mAltitudeAccuracyMeters = location.mAltitudeAccuracyMeters;
        this.mSpeedMetersPerSecond = location.mSpeedMetersPerSecond;
        this.mSpeedAccuracyMetersPerSecond = location.mSpeedAccuracyMetersPerSecond;
        this.mBearingDegrees = location.mBearingDegrees;
        this.mBearingAccuracyDegrees = location.mBearingAccuracyDegrees;
        this.mMslAltitudeMeters = location.mMslAltitudeMeters;
        this.mMslAltitudeAccuracyMeters = location.mMslAltitudeAccuracyMeters;
        this.mExtras = location.mExtras == null ? null : new Bundle(location.mExtras);
    }

    public void reset() {
        this.mProvider = null;
        this.mTimeMs = 0L;
        this.mElapsedRealtimeNs = 0L;
        this.mElapsedRealtimeUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mFieldsMask = 0;
        this.mLatitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLongitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mAltitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mSpeedMetersPerSecond = 0.0f;
        this.mBearingDegrees = 0.0f;
        this.mHorizontalAccuracyMeters = 0.0f;
        this.mAltitudeAccuracyMeters = 0.0f;
        this.mSpeedAccuracyMetersPerSecond = 0.0f;
        this.mBearingAccuracyDegrees = 0.0f;
        this.mMslAltitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mMslAltitudeAccuracyMeters = 0.0f;
        this.mExtras = null;
    }

    public float distanceTo(Location location) {
        BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        if (this.mLatitudeDegrees != bearingDistanceCache.mLat1 || this.mLongitudeDegrees != bearingDistanceCache.mLon1 || location.mLatitudeDegrees != bearingDistanceCache.mLat2 || location.mLongitudeDegrees != bearingDistanceCache.mLon2) {
            computeDistanceAndBearing(this.mLatitudeDegrees, this.mLongitudeDegrees, location.mLatitudeDegrees, location.mLongitudeDegrees, bearingDistanceCache);
        }
        return bearingDistanceCache.mDistance;
    }

    public float bearingTo(Location location) {
        BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        if (this.mLatitudeDegrees != bearingDistanceCache.mLat1 || this.mLongitudeDegrees != bearingDistanceCache.mLon1 || location.mLatitudeDegrees != bearingDistanceCache.mLat2 || location.mLongitudeDegrees != bearingDistanceCache.mLon2) {
            computeDistanceAndBearing(this.mLatitudeDegrees, this.mLongitudeDegrees, location.mLatitudeDegrees, location.mLongitudeDegrees, bearingDistanceCache);
        }
        return bearingDistanceCache.mInitialBearing;
    }

    public String getProvider() {
        return this.mProvider;
    }

    public void setProvider(String str) {
        this.mProvider = str;
    }

    public long getTime() {
        return this.mTimeMs;
    }

    public void setTime(long j) {
        this.mTimeMs = j;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNs;
    }

    public long getElapsedRealtimeMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.mElapsedRealtimeNs);
    }

    public long getElapsedRealtimeAgeMillis() {
        return getElapsedRealtimeAgeMillis(SystemClock.elapsedRealtime());
    }

    public long getElapsedRealtimeAgeMillis(long j) {
        return j - getElapsedRealtimeMillis();
    }

    public void setElapsedRealtimeNanos(long j) {
        this.mElapsedRealtimeNs = j;
    }

    public double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNs;
    }

    public void setElapsedRealtimeUncertaintyNanos(double d) {
        this.mElapsedRealtimeUncertaintyNs = d;
        this.mFieldsMask |= 256;
    }

    public boolean hasElapsedRealtimeUncertaintyNanos() {
        return (this.mFieldsMask & 256) != 0;
    }

    public void removeElapsedRealtimeUncertaintyNanos() {
        this.mFieldsMask &= -257;
    }

    public double getLatitude() {
        return this.mLatitudeDegrees;
    }

    public void setLatitude(double d) {
        this.mLatitudeDegrees = d;
    }

    public double getLongitude() {
        return this.mLongitudeDegrees;
    }

    public void setLongitude(double d) {
        this.mLongitudeDegrees = d;
    }

    public float getAccuracy() {
        return this.mHorizontalAccuracyMeters;
    }

    public void setAccuracy(float f) {
        this.mHorizontalAccuracyMeters = f;
        this.mFieldsMask |= 8;
    }

    public boolean hasAccuracy() {
        return (this.mFieldsMask & 8) != 0;
    }

    public void removeAccuracy() {
        this.mFieldsMask &= -9;
    }

    public double getAltitude() {
        return this.mAltitudeMeters;
    }

    public void setAltitude(double d) {
        this.mAltitudeMeters = d;
        this.mFieldsMask |= 1;
    }

    public boolean hasAltitude() {
        return (this.mFieldsMask & 1) != 0;
    }

    public void removeAltitude() {
        this.mFieldsMask &= -2;
    }

    public float getVerticalAccuracyMeters() {
        return this.mAltitudeAccuracyMeters;
    }

    public void setVerticalAccuracyMeters(float f) {
        this.mAltitudeAccuracyMeters = f;
        this.mFieldsMask |= 32;
    }

    public boolean hasVerticalAccuracy() {
        return (this.mFieldsMask & 32) != 0;
    }

    public void removeVerticalAccuracy() {
        this.mFieldsMask &= -33;
    }

    public float getSpeed() {
        return this.mSpeedMetersPerSecond;
    }

    public void setSpeed(float f) {
        this.mSpeedMetersPerSecond = f;
        this.mFieldsMask |= 2;
    }

    public boolean hasSpeed() {
        return (this.mFieldsMask & 2) != 0;
    }

    public void removeSpeed() {
        this.mFieldsMask &= -3;
    }

    public float getSpeedAccuracyMetersPerSecond() {
        return this.mSpeedAccuracyMetersPerSecond;
    }

    public void setSpeedAccuracyMetersPerSecond(float f) {
        this.mSpeedAccuracyMetersPerSecond = f;
        this.mFieldsMask |= 64;
    }

    public boolean hasSpeedAccuracy() {
        return (this.mFieldsMask & 64) != 0;
    }

    public void removeSpeedAccuracy() {
        this.mFieldsMask &= -65;
    }

    public float getBearing() {
        return this.mBearingDegrees;
    }

    public void setBearing(float f) {
        Preconditions.checkArgument(Float.isFinite(f));
        float f2 = (f % 360.0f) + 0.0f;
        if (f2 < 0.0f) {
            f2 += 360.0f;
        }
        this.mBearingDegrees = f2;
        this.mFieldsMask |= 4;
    }

    public boolean hasBearing() {
        return (this.mFieldsMask & 4) != 0;
    }

    public void removeBearing() {
        this.mFieldsMask &= -5;
    }

    public float getBearingAccuracyDegrees() {
        return this.mBearingAccuracyDegrees;
    }

    public void setBearingAccuracyDegrees(float f) {
        this.mBearingAccuracyDegrees = f;
        this.mFieldsMask |= 128;
    }

    public boolean hasBearingAccuracy() {
        return (this.mFieldsMask & 128) != 0;
    }

    public void removeBearingAccuracy() {
        this.mFieldsMask &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
    }

    public double getMslAltitudeMeters() {
        return this.mMslAltitudeMeters;
    }

    public void setMslAltitudeMeters(double d) {
        this.mMslAltitudeMeters = d;
        this.mFieldsMask |= 512;
    }

    public boolean hasMslAltitude() {
        return (this.mFieldsMask & 512) != 0;
    }

    public void removeMslAltitude() {
        this.mFieldsMask &= -513;
    }

    public float getMslAltitudeAccuracyMeters() {
        return this.mMslAltitudeAccuracyMeters;
    }

    public void setMslAltitudeAccuracyMeters(float f) {
        this.mMslAltitudeAccuracyMeters = f;
        this.mFieldsMask |= 1024;
    }

    public boolean hasMslAltitudeAccuracy() {
        return (this.mFieldsMask & 1024) != 0;
    }

    public void removeMslAltitudeAccuracy() {
        this.mFieldsMask &= -1025;
    }

    @Deprecated
    public boolean isFromMockProvider() {
        return isMock();
    }

    @SystemApi
    @Deprecated
    public void setIsFromMockProvider(boolean z) {
        setMock(z);
    }

    public boolean isMock() {
        return (this.mFieldsMask & 16) != 0;
    }

    public void setMock(boolean z) {
        if (z) {
            this.mFieldsMask |= 16;
        } else {
            this.mFieldsMask &= -17;
        }
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public void setExtras(Bundle bundle) {
        this.mExtras = bundle == null ? null : new Bundle(bundle);
    }

    public boolean isComplete() {
        return (this.mProvider == null || !hasAccuracy() || this.mTimeMs == 0 || this.mElapsedRealtimeNs == 0) ? false : true;
    }

    @SystemApi
    public void makeComplete() {
        if (this.mProvider == null) {
            this.mProvider = "";
        }
        if (!hasAccuracy()) {
            this.mFieldsMask |= 8;
            this.mHorizontalAccuracyMeters = 100.0f;
        }
        if (this.mTimeMs == 0) {
            this.mTimeMs = System.currentTimeMillis();
        }
        if (this.mElapsedRealtimeNs == 0) {
            this.mElapsedRealtimeNs = SystemClock.elapsedRealtimeNanos();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location location = (Location) obj;
        return this.mTimeMs == location.mTimeMs && this.mElapsedRealtimeNs == location.mElapsedRealtimeNs && hasElapsedRealtimeUncertaintyNanos() == location.hasElapsedRealtimeUncertaintyNanos() && (!hasElapsedRealtimeUncertaintyNanos() || Double.compare(location.mElapsedRealtimeUncertaintyNs, this.mElapsedRealtimeUncertaintyNs) == 0) && Double.compare(location.mLatitudeDegrees, this.mLatitudeDegrees) == 0 && Double.compare(location.mLongitudeDegrees, this.mLongitudeDegrees) == 0 && hasAltitude() == location.hasAltitude() && ((!hasAltitude() || Double.compare(location.mAltitudeMeters, this.mAltitudeMeters) == 0) && hasSpeed() == location.hasSpeed() && ((!hasSpeed() || Float.compare(location.mSpeedMetersPerSecond, this.mSpeedMetersPerSecond) == 0) && hasBearing() == location.hasBearing() && ((!hasBearing() || Float.compare(location.mBearingDegrees, this.mBearingDegrees) == 0) && hasAccuracy() == location.hasAccuracy() && ((!hasAccuracy() || Float.compare(location.mHorizontalAccuracyMeters, this.mHorizontalAccuracyMeters) == 0) && hasVerticalAccuracy() == location.hasVerticalAccuracy() && ((!hasVerticalAccuracy() || Float.compare(location.mAltitudeAccuracyMeters, this.mAltitudeAccuracyMeters) == 0) && hasSpeedAccuracy() == location.hasSpeedAccuracy() && ((!hasSpeedAccuracy() || Float.compare(location.mSpeedAccuracyMetersPerSecond, this.mSpeedAccuracyMetersPerSecond) == 0) && hasBearingAccuracy() == location.hasBearingAccuracy() && ((!hasBearingAccuracy() || Float.compare(location.mBearingAccuracyDegrees, this.mBearingAccuracyDegrees) == 0) && hasMslAltitude() == location.hasMslAltitude() && ((!hasMslAltitude() || Double.compare(location.mMslAltitudeMeters, this.mMslAltitudeMeters) == 0) && hasMslAltitudeAccuracy() == location.hasMslAltitudeAccuracy() && ((!hasMslAltitudeAccuracy() || Float.compare(location.mMslAltitudeAccuracyMeters, this.mMslAltitudeAccuracyMeters) == 0) && Objects.equals(this.mProvider, location.mProvider) && areExtrasEqual(this.mExtras, location.mExtras))))))))));
    }

    private static boolean areExtrasEqual(Bundle bundle, Bundle bundle2) {
        if ((bundle == null || bundle.isEmpty()) && (bundle2 == null || bundle2.isEmpty())) {
            return true;
        }
        if (bundle == null || bundle2 == null) {
            return false;
        }
        return bundle.kindofEquals(bundle2);
    }

    public int hashCode() {
        return Objects.hash(this.mProvider, Long.valueOf(this.mElapsedRealtimeNs), Double.valueOf(this.mLatitudeDegrees), Double.valueOf(this.mLongitudeDegrees));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Location[");
        sb.append(this.mProvider);
        sb.append(" ");
        sb.append(String.format(Locale.ROOT, "%.6f,%.6f", Double.valueOf(this.mLatitudeDegrees), Double.valueOf(this.mLongitudeDegrees)));
        if (hasAccuracy()) {
            sb.append(" hAcc=");
            sb.append(this.mHorizontalAccuracyMeters);
        }
        sb.append(" et=");
        TimeUtils.formatDuration(getElapsedRealtimeMillis(), sb);
        if (hasAltitude()) {
            sb.append(" alt=");
            sb.append(this.mAltitudeMeters);
            if (hasVerticalAccuracy()) {
                sb.append(" vAcc=");
                sb.append(this.mAltitudeAccuracyMeters);
            }
        }
        if (hasMslAltitude()) {
            sb.append(" mslAlt=");
            sb.append(this.mMslAltitudeMeters);
            if (hasMslAltitudeAccuracy()) {
                sb.append(" mslAltAcc=");
                sb.append(this.mMslAltitudeAccuracyMeters);
            }
        }
        if (hasSpeed()) {
            sb.append(" vel=");
            sb.append(this.mSpeedMetersPerSecond);
            if (hasSpeedAccuracy()) {
                sb.append(" sAcc=");
                sb.append(this.mSpeedAccuracyMetersPerSecond);
            }
        }
        if (hasBearing()) {
            sb.append(" bear=");
            sb.append(this.mBearingDegrees);
            if (hasBearingAccuracy()) {
                sb.append(" bAcc=");
                sb.append(this.mBearingAccuracyDegrees);
            }
        }
        if (isMock()) {
            sb.append(" mock");
        }
        Bundle bundle = this.mExtras;
        if (bundle != null && !bundle.isEmpty()) {
            sb.append(" {");
            sb.append(this.mExtras);
            sb.append('}');
        }
        sb.append(']');
        return sb.toString();
    }

    @Deprecated
    public void dump(Printer printer, String str) {
        printer.println(str + this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mProvider);
        parcel.writeInt(this.mFieldsMask);
        parcel.writeLong(this.mTimeMs);
        parcel.writeLong(this.mElapsedRealtimeNs);
        if (hasElapsedRealtimeUncertaintyNanos()) {
            parcel.writeDouble(this.mElapsedRealtimeUncertaintyNs);
        }
        parcel.writeDouble(this.mLatitudeDegrees);
        parcel.writeDouble(this.mLongitudeDegrees);
        if (hasAltitude()) {
            parcel.writeDouble(this.mAltitudeMeters);
        }
        if (hasSpeed()) {
            parcel.writeFloat(this.mSpeedMetersPerSecond);
        }
        if (hasBearing()) {
            parcel.writeFloat(this.mBearingDegrees);
        }
        if (hasAccuracy()) {
            parcel.writeFloat(this.mHorizontalAccuracyMeters);
        }
        if (hasVerticalAccuracy()) {
            parcel.writeFloat(this.mAltitudeAccuracyMeters);
        }
        if (hasSpeedAccuracy()) {
            parcel.writeFloat(this.mSpeedAccuracyMetersPerSecond);
        }
        if (hasBearingAccuracy()) {
            parcel.writeFloat(this.mBearingAccuracyDegrees);
        }
        if (hasMslAltitude()) {
            parcel.writeDouble(this.mMslAltitudeMeters);
        }
        if (hasMslAltitudeAccuracy()) {
            parcel.writeFloat(this.mMslAltitudeAccuracyMeters);
        }
        parcel.writeBundle(this.mExtras);
    }

    public static String convert(double d, int i) {
        double d2 = d;
        Preconditions.checkArgumentInRange(d2, -180.0d, 180.0d, "coordinate");
        Preconditions.checkArgument(i == 0 || i == 1 || i == 2, "%d is an unrecognized format", Integer.valueOf(i));
        StringBuilder sb = new StringBuilder();
        if (d2 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            sb.append('-');
            d2 = -d2;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###.#####");
        if (i == 1 || i == 2) {
            int floor = (int) Math.floor(d2);
            sb.append(floor);
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            d2 = (d2 - floor) * 60.0d;
            if (i == 2) {
                int floor2 = (int) Math.floor(d2);
                sb.append(floor2);
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                d2 = (d2 - floor2) * 60.0d;
            }
        }
        sb.append(decimalFormat.format(d2));
        return sb.toString();
    }

    public static double convert(String str) {
        String str2;
        boolean z;
        double parseDouble;
        boolean z2;
        double d;
        Objects.requireNonNull(str);
        boolean z3 = false;
        if (str.charAt(0) == '-') {
            str2 = str.substring(1);
            z = true;
        } else {
            str2 = str;
            z = false;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str2, ":");
        int countTokens = stringTokenizer.countTokens();
        if (countTokens < 1) {
            throw new IllegalArgumentException("coordinate=" + str2);
        }
        try {
            String nextToken = stringTokenizer.nextToken();
            if (countTokens == 1) {
                double parseDouble2 = Double.parseDouble(nextToken);
                return z ? -parseDouble2 : parseDouble2;
            }
            String nextToken2 = stringTokenizer.nextToken();
            int parseInt = Integer.parseInt(nextToken);
            if (stringTokenizer.hasMoreTokens()) {
                parseDouble = Integer.parseInt(nextToken2);
                d = Double.parseDouble(stringTokenizer.nextToken());
                z2 = true;
            } else {
                parseDouble = Double.parseDouble(nextToken2);
                z2 = false;
                d = 0.0d;
            }
            if (z && parseInt == 180 && parseDouble == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                z3 = true;
            }
            double d2 = parseInt;
            if (d2 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || (parseInt > 179 && !z3)) {
                throw new IllegalArgumentException("coordinate=" + str2);
            }
            if (parseDouble < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || parseDouble >= 60.0d || (z2 && parseDouble > 59.0d)) {
                throw new IllegalArgumentException("coordinate=" + str2);
            }
            if (d >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && d < 60.0d) {
                double d3 = (((d2 * 3600.0d) + (parseDouble * 60.0d)) + d) / 3600.0d;
                return z ? -d3 : d3;
            }
            throw new IllegalArgumentException("coordinate=" + str2);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("coordinate=" + str2, e);
        }
    }

    private static void computeDistanceAndBearing(double d, double d2, double d3, double d4, BearingDistanceCache bearingDistanceCache) {
        double d5;
        double d6 = d * 0.017453292519943295d;
        double d7 = d3 * 0.017453292519943295d;
        double d8 = d2 * 0.017453292519943295d;
        double d9 = 0.017453292519943295d * d4;
        double d10 = d9 - d8;
        double atan = Math.atan(Math.tan(d6) * 0.996647189328169d);
        double atan2 = Math.atan(0.996647189328169d * Math.tan(d7));
        double cos = Math.cos(atan);
        double cos2 = Math.cos(atan2);
        double sin = Math.sin(atan);
        double sin2 = Math.sin(atan2);
        double d11 = cos * cos2;
        double d12 = sin * sin2;
        double d13 = 0.0d;
        double d14 = 0.0d;
        double d15 = 0.0d;
        double d16 = 0.0d;
        double d17 = 0.0d;
        int i = 0;
        double d18 = d10;
        while (true) {
            if (i >= 20) {
                d5 = sin2;
                break;
            }
            d15 = Math.cos(d18);
            d17 = Math.sin(d18);
            double d19 = cos2 * d17;
            double d20 = (cos * sin2) - ((sin * cos2) * d15);
            int i2 = i;
            double sqrt = Math.sqrt((d19 * d19) + (d20 * d20));
            d5 = sin2;
            double d21 = d12 + (d11 * d15);
            d13 = Math.atan2(sqrt, d21);
            double d22 = sqrt == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? 0.0d : (d11 * d17) / sqrt;
            double d23 = 1.0d - (d22 * d22);
            double d24 = d23 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? 0.0d : d21 - ((d12 * 2.0d) / d23);
            double d25 = 0.006739496756586903d * d23;
            double d26 = ((d25 / 16384.0d) * (((((320.0d - (175.0d * d25)) * d25) - 768.0d) * d25) + 4096.0d)) + 1.0d;
            double d27 = (d25 / 1024.0d) * ((d25 * (((74.0d - (47.0d * d25)) * d25) - 128.0d)) + 256.0d);
            double d28 = 2.0955066698943685E-4d * d23 * (((4.0d - (d23 * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            double d29 = d24 * d24;
            double d30 = d27 * sqrt * (d24 + ((d27 / 4.0d) * ((((d29 * 2.0d) - 1.0d) * d21) - ((((d27 / 6.0d) * d24) * (((sqrt * 4.0d) * sqrt) - 3.0d)) * ((d29 * 4.0d) - 3.0d)))));
            double d31 = d10 + ((1.0d - d28) * 0.0033528106718309896d * d22 * (d13 + (sqrt * d28 * (d24 + (d28 * d21 * (((2.0d * d24) * d24) - 1.0d))))));
            if (Math.abs((d31 - d18) / d31) < 1.0E-12d) {
                d14 = d30;
                d16 = d26;
                break;
            } else {
                d18 = d31;
                i = i2 + 1;
                d14 = d30;
                d16 = d26;
                sin2 = d5;
            }
        }
        bearingDistanceCache.mDistance = (float) (6356752.3142d * d16 * (d13 - d14));
        double d32 = cos * d5;
        bearingDistanceCache.mInitialBearing = (float) (((float) Math.atan2(cos2 * d17, d32 - ((sin * cos2) * d15))) * 57.29577951308232d);
        bearingDistanceCache.mFinalBearing = (float) (((float) Math.atan2(cos * d17, ((-sin) * cos2) + (d32 * d15))) * 57.29577951308232d);
        bearingDistanceCache.mLat1 = d6;
        bearingDistanceCache.mLat2 = d7;
        bearingDistanceCache.mLon1 = d8;
        bearingDistanceCache.mLon2 = d9;
    }

    public static void distanceBetween(double d, double d2, double d3, double d4, float[] fArr) {
        if (fArr == null || fArr.length < 1) {
            throw new IllegalArgumentException("results is null or has length < 1");
        }
        BearingDistanceCache bearingDistanceCache = sBearingDistanceCache.get();
        computeDistanceAndBearing(d, d2, d3, d4, bearingDistanceCache);
        fArr[0] = bearingDistanceCache.mDistance;
        if (fArr.length > 1) {
            fArr[1] = bearingDistanceCache.mInitialBearing;
            if (fArr.length > 2) {
                fArr[2] = bearingDistanceCache.mFinalBearing;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BearingDistanceCache {
        float mDistance;
        float mFinalBearing;
        float mInitialBearing;
        double mLat1;
        double mLat2;
        double mLon1;
        double mLon2;

        private BearingDistanceCache() {
            this.mLat1 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.mLon1 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.mLat2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.mLon2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.mDistance = 0.0f;
            this.mInitialBearing = 0.0f;
            this.mFinalBearing = 0.0f;
        }
    }
}
