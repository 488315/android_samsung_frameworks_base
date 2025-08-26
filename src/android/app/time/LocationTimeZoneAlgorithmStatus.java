package android.app.time;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.timezone.TimeZoneProviderStatus;
import android.text.TextUtils;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class LocationTimeZoneAlgorithmStatus implements Parcelable {
    public static final int PROVIDER_STATUS_IS_CERTAIN = 3;
    public static final int PROVIDER_STATUS_IS_UNCERTAIN = 4;
    public static final int PROVIDER_STATUS_NOT_PRESENT = 1;
    public static final int PROVIDER_STATUS_NOT_READY = 2;
    private final TimeZoneProviderStatus mPrimaryProviderReportedStatus;
    private final int mPrimaryProviderStatus;
    private final TimeZoneProviderStatus mSecondaryProviderReportedStatus;
    private final int mSecondaryProviderStatus;
    private final int mStatus;
    public static final LocationTimeZoneAlgorithmStatus NOT_SUPPORTED = new LocationTimeZoneAlgorithmStatus(1, 1, null, 1, null);
    public static final LocationTimeZoneAlgorithmStatus RUNNING_NOT_REPORTED = new LocationTimeZoneAlgorithmStatus(2, 2, null, 2, null);
    public static final LocationTimeZoneAlgorithmStatus NOT_RUNNING = new LocationTimeZoneAlgorithmStatus(2, 2, null, 2, null);
    public static final Parcelable.Creator<LocationTimeZoneAlgorithmStatus> CREATOR = new Parcelable.Creator<LocationTimeZoneAlgorithmStatus>() { // from class: android.app.time.LocationTimeZoneAlgorithmStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocationTimeZoneAlgorithmStatus createFromParcel(Parcel parcel) {
            return new LocationTimeZoneAlgorithmStatus(parcel.readInt(), parcel.readInt(), (TimeZoneProviderStatus) parcel.readParcelable(getClass().getClassLoader(), TimeZoneProviderStatus.class), parcel.readInt(), (TimeZoneProviderStatus) parcel.readParcelable(getClass().getClassLoader(), TimeZoneProviderStatus.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocationTimeZoneAlgorithmStatus[] newArray(int i) {
            return new LocationTimeZoneAlgorithmStatus[i];
        }
    };

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProviderStatus {
    }

    private static boolean hasProviderReported(int i) {
        return i == 3 || i == 4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocationTimeZoneAlgorithmStatus(int i, int i2, TimeZoneProviderStatus timeZoneProviderStatus, int i3, TimeZoneProviderStatus timeZoneProviderStatus2) {
        this.mStatus = DetectorStatusTypes.requireValidDetectionAlgorithmStatus(i);
        this.mPrimaryProviderStatus = requireValidProviderStatus(i2);
        this.mPrimaryProviderReportedStatus = timeZoneProviderStatus;
        this.mSecondaryProviderStatus = requireValidProviderStatus(i3);
        this.mSecondaryProviderReportedStatus = timeZoneProviderStatus2;
        boolean zHasProviderReported = hasProviderReported(i2);
        boolean z = timeZoneProviderStatus != null;
        if (!zHasProviderReported && z) {
            throw new IllegalArgumentException("primaryProviderReportedStatus=" + timeZoneProviderStatus + ", primaryProviderStatus=" + providerStatusToString(i2));
        }
        boolean zHasProviderReported2 = hasProviderReported(i3);
        boolean z2 = timeZoneProviderStatus2 != null;
        if (!zHasProviderReported2 && z2) {
            throw new IllegalArgumentException("secondaryProviderReportedStatus=" + timeZoneProviderStatus2 + ", secondaryProviderStatus=" + providerStatusToString(i3));
        }
        if (i != 3) {
            if (zHasProviderReported || zHasProviderReported2) {
                throw new IllegalArgumentException("algorithmStatus=" + DetectorStatusTypes.detectionAlgorithmStatusToString(i) + ", primaryProviderReportedStatus=" + timeZoneProviderStatus + ", secondaryProviderReportedStatus=" + timeZoneProviderStatus2);
            }
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getPrimaryProviderStatus() {
        return this.mPrimaryProviderStatus;
    }

    public TimeZoneProviderStatus getPrimaryProviderReportedStatus() {
        return this.mPrimaryProviderReportedStatus;
    }

    public int getSecondaryProviderStatus() {
        return this.mSecondaryProviderStatus;
    }

    public TimeZoneProviderStatus getSecondaryProviderReportedStatus() {
        return this.mSecondaryProviderReportedStatus;
    }

    public String toString() {
        return "LocationTimeZoneAlgorithmStatus{mAlgorithmStatus=" + DetectorStatusTypes.detectionAlgorithmStatusToString(this.mStatus) + ", mPrimaryProviderStatus=" + providerStatusToString(this.mPrimaryProviderStatus) + ", mPrimaryProviderReportedStatus=" + this.mPrimaryProviderReportedStatus + ", mSecondaryProviderStatus=" + providerStatusToString(this.mSecondaryProviderStatus) + ", mSecondaryProviderReportedStatus=" + this.mSecondaryProviderReportedStatus + '}';
    }

    public static LocationTimeZoneAlgorithmStatus parseCommandlineArg(String str) {
        Matcher matcher = Pattern.compile("LocationTimeZoneAlgorithmStatus\\{mAlgorithmStatus=(.+), mPrimaryProviderStatus=([^,]+), mPrimaryProviderReportedStatus=(null|TimeZoneProviderStatus\\{[^}]+\\}), mSecondaryProviderStatus=([^,]+), mSecondaryProviderReportedStatus=(null|TimeZoneProviderStatus\\{[^}]+\\})\\}").matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unable to parse algorithm status arg: " + str);
        }
        return new LocationTimeZoneAlgorithmStatus(DetectorStatusTypes.detectionAlgorithmStatusFromString(matcher.group(1)), providerStatusFromString(matcher.group(2)), parseTimeZoneProviderStatusOrNull(matcher.group(3)), providerStatusFromString(matcher.group(4)), parseTimeZoneProviderStatusOrNull(matcher.group(5)));
    }

    private static TimeZoneProviderStatus parseTimeZoneProviderStatusOrNull(String str) {
        if (PerfettoProtoLogImpl.NULL_STRING.equals(str)) {
            return null;
        }
        return TimeZoneProviderStatus.parseProviderStatus(str);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mPrimaryProviderStatus);
        parcel.writeParcelable(this.mPrimaryProviderReportedStatus, i);
        parcel.writeInt(this.mSecondaryProviderStatus);
        parcel.writeParcelable(this.mSecondaryProviderReportedStatus, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus = (LocationTimeZoneAlgorithmStatus) obj;
            if (this.mStatus == locationTimeZoneAlgorithmStatus.mStatus && this.mPrimaryProviderStatus == locationTimeZoneAlgorithmStatus.mPrimaryProviderStatus && Objects.equals(this.mPrimaryProviderReportedStatus, locationTimeZoneAlgorithmStatus.mPrimaryProviderReportedStatus) && this.mSecondaryProviderStatus == locationTimeZoneAlgorithmStatus.mSecondaryProviderStatus && Objects.equals(this.mSecondaryProviderReportedStatus, locationTimeZoneAlgorithmStatus.mSecondaryProviderReportedStatus)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus), Integer.valueOf(this.mPrimaryProviderStatus), this.mPrimaryProviderReportedStatus, Integer.valueOf(this.mSecondaryProviderStatus), this.mSecondaryProviderReportedStatus);
    }

    public boolean couldEnableTelephonyFallback() {
        boolean zCouldEnableTelephonyFallback;
        TimeZoneProviderStatus timeZoneProviderStatus;
        boolean zCouldEnableTelephonyFallback2;
        TimeZoneProviderStatus timeZoneProviderStatus2;
        int i = this.mStatus;
        if (i != 0 && i != 2 && i != 1) {
            int i2 = this.mPrimaryProviderStatus;
            if (i2 == 1) {
                zCouldEnableTelephonyFallback = true;
            } else {
                zCouldEnableTelephonyFallback = (i2 != 4 || (timeZoneProviderStatus = this.mPrimaryProviderReportedStatus) == null) ? false : timeZoneProviderStatus.couldEnableTelephonyFallback();
            }
            int i3 = this.mSecondaryProviderStatus;
            if (i3 == 1) {
                zCouldEnableTelephonyFallback2 = true;
            } else {
                zCouldEnableTelephonyFallback2 = (i3 != 4 || (timeZoneProviderStatus2 = this.mSecondaryProviderReportedStatus) == null) ? false : timeZoneProviderStatus2.couldEnableTelephonyFallback();
            }
            if (zCouldEnableTelephonyFallback && zCouldEnableTelephonyFallback2) {
                return true;
            }
        }
        return false;
    }

    public static String providerStatusToString(int i) {
        if (i == 1) {
            return "NOT_PRESENT";
        }
        if (i == 2) {
            return "NOT_READY";
        }
        if (i == 3) {
            return "IS_CERTAIN";
        }
        if (i == 4) {
            return "IS_UNCERTAIN";
        }
        throw new IllegalArgumentException("Unknown status: " + i);
    }

    public static int providerStatusFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty status: " + str);
        }
        str.hashCode();
        switch (str) {
            case "IS_CERTAIN":
                return 3;
            case "NOT_PRESENT":
                return 1;
            case "NOT_READY":
                return 2;
            case "IS_UNCERTAIN":
                return 4;
            default:
                throw new IllegalArgumentException("Unknown status: " + str);
        }
    }

    public static int requireValidProviderStatus(int i) {
        if (i >= 1 && i <= 4) {
            return i;
        }
        throw new IllegalArgumentException("Invalid provider status: " + i);
    }
}
