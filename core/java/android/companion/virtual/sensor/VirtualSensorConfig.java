package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Objects;

@SystemApi
public final class VirtualSensorConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualSensorConfig> CREATOR =
            new Parcelable.Creator<VirtualSensorConfig>() { // from class:
                // android.companion.virtual.sensor.VirtualSensorConfig.1
                @Override // android.os.Parcelable.Creator
                public VirtualSensorConfig createFromParcel(Parcel source) {
                    return new VirtualSensorConfig(source);
                }

                @Override // android.os.Parcelable.Creator
                public VirtualSensorConfig[] newArray(int size) {
                    return new VirtualSensorConfig[size];
                }
            };
    private static final int DIRECT_CHANNEL_SHIFT = 10;
    private static final int DIRECT_REPORT_MASK = 896;
    private static final int DIRECT_REPORT_SHIFT = 7;
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

    private VirtualSensorConfig(
            int type,
            String name,
            String vendor2,
            float maximumRange,
            float resolution,
            float power,
            int minDelay,
            int maxDelay,
            int flags) {
        this.mType = type;
        this.mName = name;
        this.mVendor = vendor2;
        this.mMaximumRange = maximumRange;
        this.mResolution = resolution;
        this.mPower = power;
        this.mMinDelay = minDelay;
        this.mMaxDelay = maxDelay;
        this.mFlags = flags;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
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
        return "VirtualSensorConfig{mType="
                + this.mType
                + ", mName='"
                + this.mName
                + DateFormat.QUOTE
                + '}';
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
        int rateLevel = (this.mFlags & 896) >> 7;
        if (rateLevel > 3) {
            return 3;
        }
        return rateLevel;
    }

    public int getDirectChannelTypesSupported() {
        int memoryTypes = 0;
        if ((this.mFlags & 1024) > 0) {
            memoryTypes = 0 | 1;
        }
        if ((this.mFlags & 2048) > 0) {
            return memoryTypes | 2;
        }
        return memoryTypes;
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

        public Builder(int type, String name) {
            if (type <= 0) {
                throw new IllegalArgumentException("Virtual sensor type must be positive");
            }
            this.mType = type;
            this.mName = (String) Objects.requireNonNull(name);
        }

        public VirtualSensorConfig build() {
            if (this.mHighestDirectReportRateLevel > 0) {
                if ((this.mFlags & 1024) == 0) {
                    throw new IllegalArgumentException(
                            "Setting direct channel type is required for sensors with direct"
                                    + " channel support.");
                }
                this.mFlags |= this.mHighestDirectReportRateLevel << 7;
            }
            if ((this.mFlags & 1024) > 0 && this.mHighestDirectReportRateLevel == 0) {
                throw new IllegalArgumentException(
                        "Highest direct report rate level is required for sensors with direct"
                                + " channel support.");
            }
            return new VirtualSensorConfig(
                    this.mType,
                    this.mName,
                    this.mVendor,
                    this.mMaximumRange,
                    this.mResolution,
                    this.mPower,
                    this.mMinDelay,
                    this.mMaxDelay,
                    this.mFlags);
        }

        public Builder setVendor(String vendor2) {
            this.mVendor = vendor2;
            return this;
        }

        public Builder setMaximumRange(float maximumRange) {
            this.mMaximumRange = maximumRange;
            return this;
        }

        public Builder setResolution(float resolution) {
            this.mResolution = resolution;
            return this;
        }

        public Builder setPower(float power) {
            this.mPower = power;
            return this;
        }

        public Builder setMinDelay(int minDelay) {
            this.mMinDelay = minDelay;
            return this;
        }

        public Builder setMaxDelay(int maxDelay) {
            this.mMaxDelay = maxDelay;
            return this;
        }

        public Builder setHighestDirectReportRateLevel(int rateLevel) {
            this.mHighestDirectReportRateLevel = rateLevel;
            return this;
        }

        public Builder setDirectChannelTypesSupported(int memoryTypes) {
            if ((memoryTypes & 1) > 0) {
                this.mFlags |= 1024;
            } else {
                this.mFlags &= -1025;
            }
            if ((memoryTypes & (-2)) > 0) {
                throw new IllegalArgumentException(
                        "Only TYPE_MEMORY_FILE direct channels can be supported for virtual"
                                + " sensors.");
            }
            return this;
        }
    }
}
