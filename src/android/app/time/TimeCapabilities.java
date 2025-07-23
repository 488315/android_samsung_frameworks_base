package android.app.time;

import android.annotation.SystemApi;
import android.app.time.TimeConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeCapabilities implements Parcelable {
    public static final Parcelable.Creator<TimeCapabilities> CREATOR = new Parcelable.Creator<TimeCapabilities>() { // from class: android.app.time.TimeCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeCapabilities createFromParcel(Parcel parcel) {
            return TimeCapabilities.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeCapabilities[] newArray(int i) {
            return new TimeCapabilities[i];
        }
    };
    private final int mConfigureAutoDetectionEnabledCapability;
    private final int mSetManualTimeCapability;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeCapabilities(Builder builder) {
        this.mUserHandle = (UserHandle) Objects.requireNonNull(builder.mUserHandle);
        this.mConfigureAutoDetectionEnabledCapability = builder.mConfigureAutoDetectionEnabledCapability;
        this.mSetManualTimeCapability = builder.mSetManualTimeCapability;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeCapabilities createFromParcel(Parcel parcel) {
        return new Builder(UserHandle.readFromParcel(parcel)).setConfigureAutoDetectionEnabledCapability(parcel.readInt()).setSetManualTimeCapability(parcel.readInt()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        UserHandle.writeToParcel(this.mUserHandle, parcel);
        parcel.writeInt(this.mConfigureAutoDetectionEnabledCapability);
        parcel.writeInt(this.mSetManualTimeCapability);
    }

    public int getConfigureAutoDetectionEnabledCapability() {
        return this.mConfigureAutoDetectionEnabledCapability;
    }

    public int getSetManualTimeCapability() {
        return this.mSetManualTimeCapability;
    }

    public TimeConfiguration tryApplyConfigChanges(TimeConfiguration timeConfiguration, TimeConfiguration timeConfiguration2) {
        TimeConfiguration.Builder builder = new TimeConfiguration.Builder(timeConfiguration);
        if (timeConfiguration2.hasIsAutoDetectionEnabled()) {
            if (getConfigureAutoDetectionEnabledCapability() < 30) {
                return null;
            }
            builder.setAutoDetectionEnabled(timeConfiguration2.isAutoDetectionEnabled());
        }
        return builder.build();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeCapabilities timeCapabilities = (TimeCapabilities) obj;
            if (this.mConfigureAutoDetectionEnabledCapability == timeCapabilities.mConfigureAutoDetectionEnabledCapability && this.mSetManualTimeCapability == timeCapabilities.mSetManualTimeCapability && this.mUserHandle.equals(timeCapabilities.mUserHandle)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mUserHandle, Integer.valueOf(this.mConfigureAutoDetectionEnabledCapability), Integer.valueOf(this.mSetManualTimeCapability));
    }

    public String toString() {
        return "TimeCapabilities{mUserHandle=" + this.mUserHandle + ", mConfigureAutoDetectionEnabledCapability=" + this.mConfigureAutoDetectionEnabledCapability + ", mSetManualTimeCapability=" + this.mSetManualTimeCapability + '}';
    }

    public static class Builder {
        private int mConfigureAutoDetectionEnabledCapability;
        private int mSetManualTimeCapability;
        private final UserHandle mUserHandle;

        public Builder(UserHandle userHandle) {
            this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        }

        public Builder(TimeCapabilities timeCapabilities) {
            Objects.requireNonNull(timeCapabilities);
            this.mUserHandle = timeCapabilities.mUserHandle;
            this.mConfigureAutoDetectionEnabledCapability = timeCapabilities.mConfigureAutoDetectionEnabledCapability;
            this.mSetManualTimeCapability = timeCapabilities.mSetManualTimeCapability;
        }

        public Builder setConfigureAutoDetectionEnabledCapability(int i) {
            this.mConfigureAutoDetectionEnabledCapability = i;
            return this;
        }

        public Builder setSetManualTimeCapability(int i) {
            this.mSetManualTimeCapability = i;
            return this;
        }

        public TimeCapabilities build() {
            verifyCapabilitySet(this.mConfigureAutoDetectionEnabledCapability, "configureAutoDetectionEnabledCapability");
            verifyCapabilitySet(this.mSetManualTimeCapability, "mSetManualTimeCapability");
            return new TimeCapabilities(this);
        }

        private void verifyCapabilitySet(int i, String str) {
            if (i != 0) {
                return;
            }
            throw new IllegalStateException(str + " was not set");
        }
    }
}
