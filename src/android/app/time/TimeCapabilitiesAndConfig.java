package android.app.time;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeCapabilitiesAndConfig implements Parcelable {
    public static final Parcelable.Creator<TimeCapabilitiesAndConfig> CREATOR = new Parcelable.Creator<TimeCapabilitiesAndConfig>() { // from class: android.app.time.TimeCapabilitiesAndConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeCapabilitiesAndConfig createFromParcel(Parcel parcel) {
            return TimeCapabilitiesAndConfig.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeCapabilitiesAndConfig[] newArray(int i) {
            return new TimeCapabilitiesAndConfig[i];
        }
    };
    private final TimeCapabilities mCapabilities;
    private final TimeConfiguration mConfiguration;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimeCapabilitiesAndConfig(TimeCapabilities timeCapabilities, TimeConfiguration timeConfiguration) {
        this.mCapabilities = (TimeCapabilities) Objects.requireNonNull(timeCapabilities);
        this.mConfiguration = (TimeConfiguration) Objects.requireNonNull(timeConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeCapabilitiesAndConfig readFromParcel(Parcel parcel) {
        return new TimeCapabilitiesAndConfig((TimeCapabilities) parcel.readParcelable(null, TimeCapabilities.class), (TimeConfiguration) parcel.readParcelable(null, TimeConfiguration.class));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mCapabilities, i);
        parcel.writeParcelable(this.mConfiguration, i);
    }

    public TimeCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public TimeConfiguration getConfiguration() {
        return this.mConfiguration;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeCapabilitiesAndConfig timeCapabilitiesAndConfig = (TimeCapabilitiesAndConfig) obj;
            if (this.mCapabilities.equals(timeCapabilitiesAndConfig.mCapabilities) && this.mConfiguration.equals(timeCapabilitiesAndConfig.mConfiguration)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mCapabilities, this.mConfiguration);
    }

    public String toString() {
        return "TimeCapabilitiesAndConfig{mCapabilities=" + this.mCapabilities + ", mConfiguration=" + this.mConfiguration + '}';
    }
}
