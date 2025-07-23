package android.app.time;

import android.annotation.SystemApi;
import android.app.time.TimeZoneConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeZoneCapabilities implements Parcelable {
    public static final Parcelable.Creator<TimeZoneCapabilities> CREATOR = new Parcelable.Creator<TimeZoneCapabilities>() { // from class: android.app.time.TimeZoneCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneCapabilities createFromParcel(Parcel parcel) {
            return TimeZoneCapabilities.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneCapabilities[] newArray(int i) {
            return new TimeZoneCapabilities[i];
        }
    };
    private final int mConfigureAutoDetectionEnabledCapability;
    private final int mConfigureGeoDetectionEnabledCapability;
    private final int mConfigureNotificationsEnabledCapability;
    private final int mSetManualTimeZoneCapability;
    private final boolean mUseLocationEnabled;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeZoneCapabilities(Builder builder) {
        this.mUserHandle = (UserHandle) Objects.requireNonNull(builder.mUserHandle);
        this.mConfigureAutoDetectionEnabledCapability = builder.mConfigureAutoDetectionEnabledCapability;
        this.mUseLocationEnabled = builder.mUseLocationEnabled.booleanValue();
        this.mConfigureGeoDetectionEnabledCapability = builder.mConfigureGeoDetectionEnabledCapability;
        this.mSetManualTimeZoneCapability = builder.mSetManualTimeZoneCapability;
        this.mConfigureNotificationsEnabledCapability = builder.mConfigureNotificationsEnabledCapability;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeZoneCapabilities createFromParcel(Parcel parcel) {
        return new Builder(UserHandle.readFromParcel(parcel)).setConfigureAutoDetectionEnabledCapability(parcel.readInt()).setUseLocationEnabled(parcel.readBoolean()).setConfigureGeoDetectionEnabledCapability(parcel.readInt()).setSetManualTimeZoneCapability(parcel.readInt()).setConfigureNotificationsEnabledCapability(parcel.readInt()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        UserHandle.writeToParcel(this.mUserHandle, parcel);
        parcel.writeInt(this.mConfigureAutoDetectionEnabledCapability);
        parcel.writeBoolean(this.mUseLocationEnabled);
        parcel.writeInt(this.mConfigureGeoDetectionEnabledCapability);
        parcel.writeInt(this.mSetManualTimeZoneCapability);
        parcel.writeInt(this.mConfigureNotificationsEnabledCapability);
    }

    public int getConfigureAutoDetectionEnabledCapability() {
        return this.mConfigureAutoDetectionEnabledCapability;
    }

    public boolean isUseLocationEnabled() {
        return this.mUseLocationEnabled;
    }

    public int getConfigureGeoDetectionEnabledCapability() {
        return this.mConfigureGeoDetectionEnabledCapability;
    }

    public int getSetManualTimeZoneCapability() {
        return this.mSetManualTimeZoneCapability;
    }

    public int getConfigureNotificationsEnabledCapability() {
        return this.mConfigureNotificationsEnabledCapability;
    }

    public TimeZoneConfiguration tryApplyConfigChanges(TimeZoneConfiguration timeZoneConfiguration, TimeZoneConfiguration timeZoneConfiguration2) {
        TimeZoneConfiguration.Builder builder = new TimeZoneConfiguration.Builder(timeZoneConfiguration);
        if (timeZoneConfiguration2.hasIsAutoDetectionEnabled()) {
            if (getConfigureAutoDetectionEnabledCapability() < 30) {
                return null;
            }
            builder.setAutoDetectionEnabled(timeZoneConfiguration2.isAutoDetectionEnabled());
        }
        if (timeZoneConfiguration2.hasIsGeoDetectionEnabled()) {
            if (getConfigureGeoDetectionEnabledCapability() < 30) {
                return null;
            }
            builder.setGeoDetectionEnabled(timeZoneConfiguration2.isGeoDetectionEnabled());
        }
        if (timeZoneConfiguration2.hasIsNotificationsEnabled()) {
            if (getConfigureNotificationsEnabledCapability() < 30) {
                return null;
            }
            builder.setNotificationsEnabled(timeZoneConfiguration2.areNotificationsEnabled());
        }
        return builder.build();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneCapabilities timeZoneCapabilities = (TimeZoneCapabilities) obj;
            if (this.mUserHandle.equals(timeZoneCapabilities.mUserHandle) && this.mConfigureAutoDetectionEnabledCapability == timeZoneCapabilities.mConfigureAutoDetectionEnabledCapability && this.mUseLocationEnabled == timeZoneCapabilities.mUseLocationEnabled && this.mConfigureGeoDetectionEnabledCapability == timeZoneCapabilities.mConfigureGeoDetectionEnabledCapability && this.mSetManualTimeZoneCapability == timeZoneCapabilities.mSetManualTimeZoneCapability && this.mConfigureNotificationsEnabledCapability == timeZoneCapabilities.mConfigureNotificationsEnabledCapability) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mUserHandle, Integer.valueOf(this.mConfigureAutoDetectionEnabledCapability), Integer.valueOf(this.mConfigureGeoDetectionEnabledCapability), Integer.valueOf(this.mSetManualTimeZoneCapability), Integer.valueOf(this.mConfigureNotificationsEnabledCapability));
    }

    public String toString() {
        return "TimeZoneDetectorCapabilities{mUserHandle=" + this.mUserHandle + ", mConfigureAutoDetectionEnabledCapability=" + this.mConfigureAutoDetectionEnabledCapability + ", mUseLocationEnabled=" + this.mUseLocationEnabled + ", mConfigureGeoDetectionEnabledCapability=" + this.mConfigureGeoDetectionEnabledCapability + ", mSetManualTimeZoneCapability=" + this.mSetManualTimeZoneCapability + ", mConfigureNotificationsEnabledCapability=" + this.mConfigureNotificationsEnabledCapability + '}';
    }

    public static class Builder {
        private int mConfigureAutoDetectionEnabledCapability;
        private int mConfigureGeoDetectionEnabledCapability;
        private int mConfigureNotificationsEnabledCapability;
        private int mSetManualTimeZoneCapability;
        private Boolean mUseLocationEnabled;
        private UserHandle mUserHandle;

        public Builder(UserHandle userHandle) {
            this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        }

        public Builder(TimeZoneCapabilities timeZoneCapabilities) {
            Objects.requireNonNull(timeZoneCapabilities);
            this.mUserHandle = timeZoneCapabilities.mUserHandle;
            this.mConfigureAutoDetectionEnabledCapability = timeZoneCapabilities.mConfigureAutoDetectionEnabledCapability;
            this.mUseLocationEnabled = Boolean.valueOf(timeZoneCapabilities.mUseLocationEnabled);
            this.mConfigureGeoDetectionEnabledCapability = timeZoneCapabilities.mConfigureGeoDetectionEnabledCapability;
            this.mSetManualTimeZoneCapability = timeZoneCapabilities.mSetManualTimeZoneCapability;
            this.mConfigureNotificationsEnabledCapability = timeZoneCapabilities.mConfigureNotificationsEnabledCapability;
        }

        public Builder setConfigureAutoDetectionEnabledCapability(int i) {
            this.mConfigureAutoDetectionEnabledCapability = i;
            return this;
        }

        public Builder setUseLocationEnabled(boolean z) {
            this.mUseLocationEnabled = Boolean.valueOf(z);
            return this;
        }

        public Builder setConfigureGeoDetectionEnabledCapability(int i) {
            this.mConfigureGeoDetectionEnabledCapability = i;
            return this;
        }

        public Builder setSetManualTimeZoneCapability(int i) {
            this.mSetManualTimeZoneCapability = i;
            return this;
        }

        public Builder setConfigureNotificationsEnabledCapability(int i) {
            this.mConfigureNotificationsEnabledCapability = i;
            return this;
        }

        public TimeZoneCapabilities build() {
            verifyCapabilitySet(this.mConfigureAutoDetectionEnabledCapability, "configureAutoDetectionEnabledCapability");
            Objects.requireNonNull(this.mUseLocationEnabled, "useLocationEnabled");
            verifyCapabilitySet(this.mConfigureGeoDetectionEnabledCapability, "configureGeoDetectionEnabledCapability");
            verifyCapabilitySet(this.mSetManualTimeZoneCapability, "setManualTimeZoneCapability");
            verifyCapabilitySet(this.mConfigureNotificationsEnabledCapability, "configureNotificationsEnabledCapability");
            return new TimeZoneCapabilities(this);
        }

        private void verifyCapabilitySet(int i, String str) {
            if (i != 0) {
                return;
            }
            throw new IllegalStateException(str + " not set");
        }
    }
}
