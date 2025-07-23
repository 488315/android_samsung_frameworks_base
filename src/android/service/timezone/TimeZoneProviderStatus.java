package android.service.timezone;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.server.SecureKeyConst;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SystemApi
/* loaded from: classes3.dex */
public final class TimeZoneProviderStatus implements Parcelable {
    public static final Parcelable.Creator<TimeZoneProviderStatus> CREATOR = new Parcelable.Creator<TimeZoneProviderStatus>() { // from class: android.service.timezone.TimeZoneProviderStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneProviderStatus createFromParcel(Parcel parcel) {
            return new TimeZoneProviderStatus(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneProviderStatus[] newArray(int i) {
            return new TimeZoneProviderStatus[i];
        }
    };
    public static final int DEPENDENCY_STATUS_BLOCKED_BY_ENVIRONMENT = 4;
    public static final int DEPENDENCY_STATUS_BLOCKED_BY_SETTINGS = 6;
    public static final int DEPENDENCY_STATUS_DEGRADED_BY_SETTINGS = 5;
    public static final int DEPENDENCY_STATUS_NOT_APPLICABLE = 1;
    public static final int DEPENDENCY_STATUS_OK = 2;
    public static final int DEPENDENCY_STATUS_TEMPORARILY_UNAVAILABLE = 3;
    public static final int DEPENDENCY_STATUS_UNKNOWN = 0;
    public static final int OPERATION_STATUS_FAILED = 3;
    public static final int OPERATION_STATUS_NOT_APPLICABLE = 1;
    public static final int OPERATION_STATUS_OK = 2;
    public static final int OPERATION_STATUS_UNKNOWN = 0;
    private final int mConnectivityDependencyStatus;
    private final int mLocationDetectionDependencyStatus;
    private final int mTimeZoneResolutionOperationStatus;

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DependencyStatus {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OperationStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeZoneProviderStatus(int i, int i2, int i3) {
        this.mLocationDetectionDependencyStatus = i;
        this.mConnectivityDependencyStatus = i2;
        this.mTimeZoneResolutionOperationStatus = i3;
    }

    public int getLocationDetectionDependencyStatus() {
        return this.mLocationDetectionDependencyStatus;
    }

    public int getConnectivityDependencyStatus() {
        return this.mConnectivityDependencyStatus;
    }

    public int getTimeZoneResolutionOperationStatus() {
        return this.mTimeZoneResolutionOperationStatus;
    }

    public String toString() {
        return "TimeZoneProviderStatus{mLocationDetectionDependencyStatus=" + dependencyStatusToString(this.mLocationDetectionDependencyStatus) + ", mConnectivityDependencyStatus=" + dependencyStatusToString(this.mConnectivityDependencyStatus) + ", mTimeZoneResolutionOperationStatus=" + operationStatusToString(this.mTimeZoneResolutionOperationStatus) + '}';
    }

    public static TimeZoneProviderStatus parseProviderStatus(String str) {
        Matcher matcher = Pattern.compile("TimeZoneProviderStatus\\{mLocationDetectionDependencyStatus=([^,]+), mConnectivityDependencyStatus=([^,]+), mTimeZoneResolutionOperationStatus=([^\\}]+)\\}").matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unable to parse provider status: " + str);
        }
        return new TimeZoneProviderStatus(dependencyStatusFromString(matcher.group(1)), dependencyStatusFromString(matcher.group(2)), operationStatusFromString(matcher.group(3)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLocationDetectionDependencyStatus);
        parcel.writeInt(this.mConnectivityDependencyStatus);
        parcel.writeInt(this.mTimeZoneResolutionOperationStatus);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneProviderStatus timeZoneProviderStatus = (TimeZoneProviderStatus) obj;
            if (this.mLocationDetectionDependencyStatus == timeZoneProviderStatus.mLocationDetectionDependencyStatus && this.mConnectivityDependencyStatus == timeZoneProviderStatus.mConnectivityDependencyStatus && this.mTimeZoneResolutionOperationStatus == timeZoneProviderStatus.mTimeZoneResolutionOperationStatus) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mLocationDetectionDependencyStatus), Integer.valueOf(this.mConnectivityDependencyStatus), Integer.valueOf(this.mTimeZoneResolutionOperationStatus));
    }

    public boolean couldEnableTelephonyFallback() {
        int i;
        int i2 = this.mLocationDetectionDependencyStatus;
        return i2 == 4 || i2 == 6 || (i = this.mConnectivityDependencyStatus) == 4 || i == 6;
    }

    public static final class Builder {
        private int mConnectivityDependencyStatus;
        private int mLocationDetectionDependencyStatus;
        private int mTimeZoneResolutionOperationStatus;

        public Builder() {
            this.mLocationDetectionDependencyStatus = 0;
            this.mConnectivityDependencyStatus = 0;
            this.mTimeZoneResolutionOperationStatus = 0;
        }

        public Builder(TimeZoneProviderStatus timeZoneProviderStatus) {
            this.mLocationDetectionDependencyStatus = 0;
            this.mConnectivityDependencyStatus = 0;
            this.mTimeZoneResolutionOperationStatus = 0;
            this.mLocationDetectionDependencyStatus = timeZoneProviderStatus.mLocationDetectionDependencyStatus;
            this.mConnectivityDependencyStatus = timeZoneProviderStatus.mConnectivityDependencyStatus;
            this.mTimeZoneResolutionOperationStatus = timeZoneProviderStatus.mTimeZoneResolutionOperationStatus;
        }

        public Builder setLocationDetectionDependencyStatus(int i) {
            this.mLocationDetectionDependencyStatus = i;
            return this;
        }

        public Builder setConnectivityDependencyStatus(int i) {
            this.mConnectivityDependencyStatus = i;
            return this;
        }

        public Builder setTimeZoneResolutionOperationStatus(int i) {
            this.mTimeZoneResolutionOperationStatus = i;
            return this;
        }

        public TimeZoneProviderStatus build() {
            return new TimeZoneProviderStatus(TimeZoneProviderStatus.requireValidDependencyStatus(this.mLocationDetectionDependencyStatus), TimeZoneProviderStatus.requireValidDependencyStatus(this.mConnectivityDependencyStatus), TimeZoneProviderStatus.requireValidOperationStatus(this.mTimeZoneResolutionOperationStatus));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int requireValidOperationStatus(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException(Integer.toString(i));
        }
        return i;
    }

    public static String operationStatusToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "NOT_APPLICABLE";
        }
        if (i == 2) {
            return SecureKeyConst.AT_RESPONSE_OK;
        }
        if (i == 3) {
            return "FAILED";
        }
        throw new IllegalArgumentException("Unknown status: " + i);
    }

    public static int operationStatusFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty status: " + str);
        }
        str.hashCode();
        switch (str) {
            case "OK":
                return 2;
            case "UNKNOWN":
                return 0;
            case "NOT_APPLICABLE":
                return 1;
            case "FAILED":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown status: " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int requireValidDependencyStatus(int i) {
        if (i < 0 || i > 6) {
            throw new IllegalArgumentException(Integer.toString(i));
        }
        return i;
    }

    public static String dependencyStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "NOT_APPLICABLE";
            case 2:
                return SecureKeyConst.AT_RESPONSE_OK;
            case 3:
                return "TEMPORARILY_UNAVAILABLE";
            case 4:
                return "BLOCKED_BY_ENVIRONMENT";
            case 5:
                return "DEGRADED_BY_SETTINGS";
            case 6:
                return "BLOCKED_BY_SETTINGS";
            default:
                throw new IllegalArgumentException("Unknown status: " + i);
        }
    }

    public static int dependencyStatusFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty status: " + str);
        }
        str.hashCode();
        switch (str) {
            case "BLOCKED_BY_SETTINGS":
                return 6;
            case "TEMPORARILY_UNAVAILABLE":
                return 3;
            case "BLOCKED_BY_ENVIRONMENT":
                return 4;
            case "OK":
                return 2;
            case "UNKNOWN":
                return 0;
            case "NOT_APPLICABLE":
                return 1;
            case "DEGRADED_BY_SETTINGS":
                return 5;
            default:
                throw new IllegalArgumentException("Unknown status: " + str);
        }
    }
}
