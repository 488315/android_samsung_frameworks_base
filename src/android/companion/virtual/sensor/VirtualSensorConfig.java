package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorConfig implements Parcelable {
    static final int ADDITIONAL_INFO_MASK = 64;
    public static final Parcelable.Creator<VirtualSensorConfig> CREATOR = new Parcelable.Creator<VirtualSensorConfig>() { // from class: android.companion.virtual.sensor.VirtualSensorConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorConfig createFromParcel(Parcel parcel) {
            return new VirtualSensorConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorConfig[] newArray(int i) {
            return new VirtualSensorConfig[i];
        }
    };
    private static final int DIRECT_CHANNEL_SHIFT = 10;
    private static final int DIRECT_REPORT_MASK = 896;
    private static final int DIRECT_REPORT_SHIFT = 7;
    private static final int FLAG_WAKE_UP_SENSOR = 1;
    private static final int REPORTING_MODE_MASK = 14;
    private static final int REPORTING_MODE_SHIFT = 1;
    private static final String TAG = "VirtualSensorConfig";
    private final int mFlags;
    private final int mMaxDelay;
    private final float mMaximumRange;
    private final int mMinDelay;
    private final String mName;
    private final float mPower;
    private final float mResolution;
    private final int mType;
    private final String mVendor;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ReportingMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualSensorConfig(int i, String str, String str2, float f, float f2, float f3, int i2, int i3, int i4) {
        this.mType = i;
        this.mName = str;
        this.mVendor = str2;
        this.mMaximumRange = f;
        this.mResolution = f2;
        this.mPower = f3;
        this.mMinDelay = i2;
        this.mMaxDelay = i3;
        this.mFlags = i4;
    }

    private VirtualSensorConfig(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
        this.mVendor = parcel.readString8();
        this.mMaximumRange = parcel.readFloat();
        this.mResolution = parcel.readFloat();
        this.mPower = parcel.readFloat();
        this.mMinDelay = parcel.readInt();
        this.mMaxDelay = parcel.readInt();
        this.mFlags = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
        parcel.writeString8(this.mVendor);
        parcel.writeFloat(this.mMaximumRange);
        parcel.writeFloat(this.mResolution);
        parcel.writeFloat(this.mPower);
        parcel.writeInt(this.mMinDelay);
        parcel.writeInt(this.mMaxDelay);
        parcel.writeInt(this.mFlags);
    }

    public String toString() {
        return "VirtualSensorConfig{mType=" + this.mType + ", mName='" + this.mName + "'}";
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public String getVendor() {
        return this.mVendor;
    }

    public float getMaximumRange() {
        return this.mMaximumRange;
    }

    public float getResolution() {
        return this.mResolution;
    }

    public float getPower() {
        return this.mPower;
    }

    public int getMinDelay() {
        return this.mMinDelay;
    }

    public int getMaxDelay() {
        return this.mMaxDelay;
    }

    public int getHighestDirectReportRateLevel() {
        return Math.min((this.mFlags & 896) >> 7, 3);
    }

    public int getDirectChannelTypesSupported() {
        int i = this.mFlags;
        int i2 = (i & 1024) > 0 ? 1 : 0;
        return (i & 2048) > 0 ? i2 | 2 : i2;
    }

    public boolean isWakeUpSensor() {
        return (this.mFlags & 1) > 0;
    }

    public boolean isAdditionalInfoSupported() {
        return (this.mFlags & 64) > 0;
    }

    public int getReportingMode() {
        return (this.mFlags & 14) >> 1;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public static final class Builder {
        private static final int FLAG_MEMORY_FILE_DIRECT_CHANNEL_SUPPORTED = 1024;
        private int mFlags;
        int mHighestDirectReportRateLevel;
        private int mMaxDelay;
        private float mMaximumRange;
        private int mMinDelay;
        private final String mName;
        private float mPower;
        private float mResolution;
        private final int mType;
        private String mVendor;

        public Builder(int i, String str) {
            if (i <= 0) {
                throw new IllegalArgumentException("Virtual sensor type must be positive");
            }
            this.mType = i;
            this.mName = (String) Objects.requireNonNull(str);
        }

        public VirtualSensorConfig build() {
            int i = this.mHighestDirectReportRateLevel;
            if (i > 0) {
                int i2 = this.mFlags;
                if ((i2 & 1024) == 0) {
                    throw new IllegalArgumentException("Setting direct channel type is required for sensors with direct channel support.");
                }
                this.mFlags = i2 | (i << 7);
            }
            if ((this.mFlags & 1024) > 0 && i == 0) {
                throw new IllegalArgumentException("Highest direct report rate level is required for sensors with direct channel support.");
            }
            return new VirtualSensorConfig(this.mType, this.mName, this.mVendor, this.mMaximumRange, this.mResolution, this.mPower, this.mMinDelay, this.mMaxDelay, this.mFlags);
        }

        public Builder setVendor(String str) {
            this.mVendor = str;
            return this;
        }

        public Builder setMaximumRange(float f) {
            this.mMaximumRange = f;
            return this;
        }

        public Builder setResolution(float f) {
            this.mResolution = f;
            return this;
        }

        public Builder setPower(float f) {
            this.mPower = f;
            return this;
        }

        public Builder setMinDelay(int i) {
            this.mMinDelay = i;
            return this;
        }

        public Builder setMaxDelay(int i) {
            this.mMaxDelay = i;
            return this;
        }

        public Builder setHighestDirectReportRateLevel(int i) {
            this.mHighestDirectReportRateLevel = i;
            return this;
        }

        public Builder setDirectChannelTypesSupported(int i) {
            if ((i & 1) > 0) {
                this.mFlags |= 1024;
            } else {
                this.mFlags &= -1025;
            }
            if ((i & (-2)) <= 0) {
                return this;
            }
            throw new IllegalArgumentException("Only TYPE_MEMORY_FILE direct channels can be supported for virtual sensors.");
        }

        public Builder setWakeUpSensor(boolean z) {
            if (z) {
                this.mFlags |= 1;
                return this;
            }
            this.mFlags &= -2;
            return this;
        }

        public Builder setAdditionalInfoSupported(boolean z) {
            if (z) {
                this.mFlags |= 64;
                return this;
            }
            this.mFlags &= -65;
            return this;
        }

        public Builder setReportingMode(int i) {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException("Invalid reporting mode: " + i);
            }
            this.mFlags = (i << 1) | this.mFlags;
            return this;
        }
    }
}
