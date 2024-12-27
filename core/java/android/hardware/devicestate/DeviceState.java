package android.hardware.devicestate;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.ArraySet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@SystemApi
public final class DeviceState {
    public static final int PROPERTY_APP_INACCESSIBLE = 9;
    public static final int PROPERTY_EMULATED_ONLY = 10;
    public static final int PROPERTY_EXTENDED_DEVICE_STATE_EXTERNAL_DISPLAY = 15;
    public static final int PROPERTY_FEATURE_DUAL_DISPLAY_INTERNAL_DEFAULT = 17;
    public static final int PROPERTY_FEATURE_REAR_DISPLAY = 16;
    public static final int PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_INNER_PRIMARY = 12;
    public static final int PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_OUTER_PRIMARY = 11;
    public static final int PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_CLOSED = 1;
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
    public @interface DeviceStateProperties {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PhysicalDeviceStateProperties {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemDeviceStateProperties {}

    public DeviceState(Configuration deviceStateConfiguration) {
        Objects.requireNonNull(deviceStateConfiguration, "Device StateConfiguration is null");
        this.mDeviceStateConfiguration = deviceStateConfiguration;
    }

    public int getIdentifier() {
        return this.mDeviceStateConfiguration.getIdentifier();
    }

    public String getName() {
        return this.mDeviceStateConfiguration.getName();
    }

    public String toString() {
        return "DeviceState{identifier="
                + this.mDeviceStateConfiguration.getIdentifier()
                + ", name='"
                + this.mDeviceStateConfiguration.getName()
                + DateFormat.QUOTE
                + ", app_accessible="
                + (!this.mDeviceStateConfiguration.getSystemProperties().contains(9))
                + ", cancel_when_requester_not_on_top="
                + this.mDeviceStateConfiguration.getSystemProperties().contains(5)
                + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceState that = (DeviceState) o;
        return Objects.equals(this.mDeviceStateConfiguration, that.mDeviceStateConfiguration);
    }

    public int hashCode() {
        return Objects.hash(this.mDeviceStateConfiguration);
    }

    public boolean hasProperty(int propertyToCheckFor) {
        return this.mDeviceStateConfiguration.mSystemProperties.contains(
                        Integer.valueOf(propertyToCheckFor))
                || this.mDeviceStateConfiguration.mPhysicalProperties.contains(
                        Integer.valueOf(propertyToCheckFor));
    }

    public boolean hasProperties(int... properties) {
        for (int i : properties) {
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
        public static final Parcelable.Creator<Configuration> CREATOR =
                new Parcelable.Creator<Configuration>() { // from class:
                    // android.hardware.devicestate.DeviceState.Configuration.1
                    @Override // android.os.Parcelable.Creator
                    public Configuration createFromParcel(Parcel source) {
                        int identifier = source.readInt();
                        String name = source.readString8();
                        return new Configuration(
                                identifier,
                                name,
                                source.readArraySet(null),
                                source.readArraySet(null));
                    }

                    @Override // android.os.Parcelable.Creator
                    public Configuration[] newArray(int size) {
                        return new Configuration[size];
                    }
                };
        private final int mIdentifier;
        private final String mName;
        private final ArraySet<Integer> mPhysicalProperties;
        private final ArraySet<Integer> mSystemProperties;

        private Configuration(
                int identifier,
                String name,
                ArraySet<Integer> systemProperties,
                ArraySet<Integer> physicalProperties) {
            this.mIdentifier = identifier;
            this.mName = name;
            this.mSystemProperties = systemProperties;
            this.mPhysicalProperties = physicalProperties;
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
            return "DeviceState{identifier="
                    + this.mIdentifier
                    + ", name='"
                    + this.mName
                    + DateFormat.QUOTE
                    + ", app_accessible="
                    + this.mSystemProperties.contains(9)
                    + ", cancel_when_requester_not_on_top="
                    + this.mSystemProperties.contains(5)
                    + "}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Configuration that = (Configuration) o;
            if (this.mIdentifier == that.mIdentifier
                    && Objects.equals(this.mName, that.mName)
                    && Objects.equals(this.mSystemProperties, that.mSystemProperties)
                    && Objects.equals(this.mPhysicalProperties, that.mPhysicalProperties)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mIdentifier),
                    this.mName,
                    this.mSystemProperties,
                    this.mPhysicalProperties);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mIdentifier);
            dest.writeString8(this.mName);
            dest.writeArraySet(this.mSystemProperties);
            dest.writeArraySet(this.mPhysicalProperties);
        }

        public static final class Builder {
            private final int mIdentifier;
            private final String mName;
            private Set<Integer> mSystemProperties = Collections.emptySet();
            private Set<Integer> mPhysicalProperties = Collections.emptySet();

            public Builder(int identifier, String name) {
                this.mIdentifier = identifier;
                this.mName = name;
            }

            public Builder setSystemProperties(Set<Integer> systemProperties) {
                this.mSystemProperties = systemProperties;
                return this;
            }

            public Builder setPhysicalProperties(Set<Integer> physicalProperties) {
                this.mPhysicalProperties = physicalProperties;
                return this;
            }

            public Configuration build() {
                return new Configuration(
                        this.mIdentifier,
                        this.mName,
                        new ArraySet(this.mSystemProperties),
                        new ArraySet(this.mPhysicalProperties));
            }
        }
    }
}
