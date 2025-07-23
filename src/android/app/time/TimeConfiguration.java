package android.app.time;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeConfiguration implements Parcelable {
    public static final Parcelable.Creator<TimeConfiguration> CREATOR = new Parcelable.Creator<TimeConfiguration>() { // from class: android.app.time.TimeConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeConfiguration createFromParcel(Parcel parcel) {
            return TimeConfiguration.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeConfiguration[] newArray(int i) {
            return new TimeConfiguration[i];
        }
    };
    private static final String SETTING_AUTO_DETECTION_ENABLED = "autoDetectionEnabled";
    private final Bundle mBundle;

    @Retention(RetentionPolicy.SOURCE)
    @interface Setting {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeConfiguration(Builder builder) {
        this.mBundle = builder.mBundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeConfiguration readFromParcel(Parcel parcel) {
        return new Builder().setPropertyBundleInternal(parcel.readBundle()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public boolean isComplete() {
        return hasIsAutoDetectionEnabled();
    }

    public boolean isAutoDetectionEnabled() {
        enforceSettingPresent(SETTING_AUTO_DETECTION_ENABLED);
        return this.mBundle.getBoolean(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean hasIsAutoDetectionEnabled() {
        return this.mBundle.containsKey(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mBundle.kindofEquals(((TimeConfiguration) obj).mBundle);
    }

    public int hashCode() {
        return Objects.hash(this.mBundle);
    }

    public String toString() {
        return "TimeConfiguration{mBundle=" + this.mBundle + '}';
    }

    private void enforceSettingPresent(String str) {
        if (this.mBundle.containsKey(str)) {
            return;
        }
        throw new IllegalStateException(str + " is not set");
    }

    @SystemApi
    public static final class Builder {
        private final Bundle mBundle = new Bundle();

        public Builder() {
        }

        public Builder(TimeConfiguration timeConfiguration) {
            mergeProperties(timeConfiguration);
        }

        public Builder mergeProperties(TimeConfiguration timeConfiguration) {
            this.mBundle.putAll(timeConfiguration.mBundle);
            return this;
        }

        Builder setPropertyBundleInternal(Bundle bundle) {
            this.mBundle.putAll(bundle);
            return this;
        }

        public Builder setAutoDetectionEnabled(boolean z) {
            this.mBundle.putBoolean(TimeConfiguration.SETTING_AUTO_DETECTION_ENABLED, z);
            return this;
        }

        public TimeConfiguration build() {
            return new TimeConfiguration(this);
        }
    }
}
