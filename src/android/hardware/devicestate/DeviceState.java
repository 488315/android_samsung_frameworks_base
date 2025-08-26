package android.hardware.devicestate;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes2.dex */
public final class DeviceState {
    public static final int PROPERTY_APP_INACCESSIBLE = 9;
    public static final int PROPERTY_EMULATED_ONLY = 10;
    public static final int PROPERTY_EXTENDED_DEVICE_STATE_EXTERNAL_DISPLAY = 15;
    public static final int PROPERTY_FEATURE_DUAL_DISPLAY_INTERNAL_DEFAULT = 17;
    public static final int PROPERTY_FEATURE_REAR_DISPLAY = 16;
    public static final int PROPERTY_FEATURE_REAR_DISPLAY_OUTER_DEFAULT = 1001;
    public static final int PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_INNER_PRIMARY = 12;
    public static final int PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_OUTER_PRIMARY = 11;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_CLOSED = 1;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_HALF_CLOSED = 101;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_HALF_OPEN = 2;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_OPEN = 3;
    public static final int PROPERTY_POLICY_AVAILABLE_FOR_APP_REQUEST = 8;
    public static final int PROPERTY_POLICY_CANCEL_OVERRIDE_REQUESTS = 4;
    public static final int PROPERTY_POLICY_CANCEL_WHEN_REQUESTER_NOT_ON_TOP = 5;
    public static final int PROPERTY_POLICY_UNSUPPORTED_WHEN_POWER_SAVE_MODE = 7;
    public static final int PROPERTY_POLICY_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL = 6;
    public static final int PROPERTY_POWER_CONFIGURATION_TRIGGER_SLEEP = 13;
    public static final int PROPERTY_POWER_CONFIGURATION_TRIGGER_WAKE = 14;
    private final Configuration mDeviceStateConfiguration;

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceStateProperties {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PhysicalDeviceStateProperties {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemDeviceStateProperties {
    }

    public DeviceState(Configuration configuration) {
        Objects.requireNonNull(configuration, "Device StateConfiguration is null");
        this.mDeviceStateConfiguration = configuration;
    }

    public int getIdentifier() {
        return this.mDeviceStateConfiguration.getIdentifier();
    }

    public String getName() {
        return this.mDeviceStateConfiguration.getName();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DeviceState{identifier=");
        sb.append(this.mDeviceStateConfiguration.getIdentifier());
        sb.append(", name='");
        sb.append(this.mDeviceStateConfiguration.getName());
        sb.append("', app_accessible=");
        sb.append(!this.mDeviceStateConfiguration.getSystemProperties().contains(9));
        sb.append(", cancel_when_requester_not_on_top=");
        sb.append(this.mDeviceStateConfiguration.getSystemProperties().contains(5));
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mDeviceStateConfiguration, ((DeviceState) obj).mDeviceStateConfiguration);
    }

    public int hashCode() {
        return Objects.hash(this.mDeviceStateConfiguration);
    }

    public boolean hasProperty(int i) {
        return this.mDeviceStateConfiguration.mSystemProperties.contains(Integer.valueOf(i)) || this.mDeviceStateConfiguration.mPhysicalProperties.contains(Integer.valueOf(i));
    }

    public boolean hasProperties(int... iArr) {
        for (int i : iArr) {
            if (!hasProperty(i)) {
                return false;
            }
        }
        return true;
    }

    public Configuration getConfiguration() {
        return this.mDeviceStateConfiguration;
    }

    public static final class Configuration implements Parcelable {
        public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() { // from class: android.hardware.devicestate.DeviceState.Configuration.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Configuration createFromParcel(Parcel parcel) {
                return new Configuration(parcel.readInt(), parcel.readString8(), parcel.readArraySet(null), parcel.readArraySet(null));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Configuration[] newArray(int i) {
                return new Configuration[i];
            }
        };
        private final int mIdentifier;
        private final String mName;
        private final ArraySet<Integer> mPhysicalProperties;
        private final ArraySet<Integer> mSystemProperties;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Configuration(int i, String str, ArraySet<Integer> arraySet, ArraySet<Integer> arraySet2) {
            this.mIdentifier = i;
            this.mName = str;
            this.mSystemProperties = arraySet;
            this.mPhysicalProperties = arraySet2;
        }

        public int getIdentifier() {
            return this.mIdentifier;
        }

        public String getName() {
            return this.mName;
        }

        public Set<Integer> getSystemProperties() {
            return this.mSystemProperties;
        }

        public Set<Integer> getPhysicalProperties() {
            return this.mPhysicalProperties;
        }

        public String toString() {
            return "DeviceState{identifier=" + this.mIdentifier + ", name='" + this.mName + "', app_accessible=" + this.mSystemProperties.contains(9) + ", cancel_when_requester_not_on_top=" + this.mSystemProperties.contains(5) + "}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Configuration configuration = (Configuration) obj;
                if (this.mIdentifier == configuration.mIdentifier && Objects.equals(this.mName, configuration.mName) && Objects.equals(this.mSystemProperties, configuration.mSystemProperties) && Objects.equals(this.mPhysicalProperties, configuration.mPhysicalProperties)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mIdentifier), this.mName, this.mSystemProperties, this.mPhysicalProperties);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) throws IOException {
            parcel.writeInt(this.mIdentifier);
            parcel.writeString8(this.mName);
            parcel.writeArraySet(this.mSystemProperties);
            parcel.writeArraySet(this.mPhysicalProperties);
        }

        public static final class Builder {
            private final int mIdentifier;
            private final String mName;
            private Set<Integer> mSystemProperties = Collections.EMPTY_SET;
            private Set<Integer> mPhysicalProperties = Collections.EMPTY_SET;

            public Builder(int i, String str) {
                this.mIdentifier = i;
                this.mName = str;
            }

            public Builder setSystemProperties(Set<Integer> set) {
                this.mSystemProperties = set;
                return this;
            }

            public Builder setPhysicalProperties(Set<Integer> set) {
                this.mPhysicalProperties = set;
                return this;
            }

            public Configuration build() {
                return new Configuration(this.mIdentifier, this.mName, new ArraySet(this.mSystemProperties), new ArraySet(this.mPhysicalProperties));
            }
        }
    }
}
