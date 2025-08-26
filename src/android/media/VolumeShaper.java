package android.media;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class VolumeShaper implements AutoCloseable {
    private int mId;
    private final WeakReference<PlayerBase> mWeakPlayerBase;

    VolumeShaper(Configuration configuration, PlayerBase playerBase) {
        this.mWeakPlayerBase = new WeakReference<>(playerBase);
        this.mId = applyPlayer(configuration, new Operation.Builder().defer().build());
    }

    int getId() {
        return this.mId;
    }

    public void apply(Operation operation) {
        applyPlayer(new Configuration(this.mId), operation);
    }

    public void replace(Configuration configuration, Operation operation, boolean z) {
        this.mId = applyPlayer(configuration, new Operation.Builder(operation).replace(this.mId, z).build());
    }

    public float getVolume() {
        return getStatePlayer(this.mId).getVolume();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            applyPlayer(new Configuration(this.mId), new Operation.Builder().terminate().build());
        } catch (IllegalStateException unused) {
        }
        WeakReference<PlayerBase> weakReference = this.mWeakPlayerBase;
        if (weakReference != null) {
            weakReference.clear();
        }
    }

    protected void finalize() {
        close();
    }

    private int applyPlayer(Configuration configuration, Operation operation) {
        WeakReference<PlayerBase> weakReference = this.mWeakPlayerBase;
        if (weakReference != null) {
            PlayerBase playerBase = weakReference.get();
            if (playerBase == null) {
                throw new IllegalStateException("player deallocated");
            }
            int iPlayerApplyVolumeShaper = playerBase.playerApplyVolumeShaper(configuration, operation);
            if (iPlayerApplyVolumeShaper >= 0) {
                return iPlayerApplyVolumeShaper;
            }
            if (iPlayerApplyVolumeShaper == -38) {
                throw new IllegalStateException("player or VolumeShaper deallocated");
            }
            throw new IllegalArgumentException("invalid configuration or operation: " + iPlayerApplyVolumeShaper);
        }
        throw new IllegalStateException("uninitialized shaper");
    }

    private State getStatePlayer(int i) {
        WeakReference<PlayerBase> weakReference = this.mWeakPlayerBase;
        if (weakReference != null) {
            PlayerBase playerBase = weakReference.get();
            if (playerBase == null) {
                throw new IllegalStateException("player deallocated");
            }
            State statePlayerGetVolumeShaperState = playerBase.playerGetVolumeShaperState(i);
            if (statePlayerGetVolumeShaperState != null) {
                return statePlayerGetVolumeShaperState;
            }
            throw new IllegalStateException("shaper cannot be found");
        }
        throw new IllegalStateException("uninitialized shaper");
    }

    public static final class Configuration implements Parcelable {
        public static final Parcelable.Creator<Configuration> CREATOR;
        public static final int INTERPOLATOR_TYPE_CUBIC = 2;
        public static final int INTERPOLATOR_TYPE_CUBIC_MONOTONIC = 3;
        public static final int INTERPOLATOR_TYPE_LINEAR = 1;
        public static final int INTERPOLATOR_TYPE_STEP = 0;
        private static final int MAXIMUM_CURVE_POINTS = 16;
        public static final int OPTION_FLAG_CLOCK_TIME = 2;
        private static final int OPTION_FLAG_PUBLIC_ALL = 3;
        public static final int OPTION_FLAG_VOLUME_IN_DBFS = 1;
        public static final Configuration SCURVE_RAMP;
        public static final Configuration SINE_RAMP;
        static final int TYPE_ID = 0;
        static final int TYPE_SCALE = 1;
        private final double mDurationMs;
        private final int mId;
        private final int mInterpolatorType;
        private final int mOptionFlags;
        private final float[] mTimes;
        private final int mType;
        private final float[] mVolumes;
        public static final Configuration LINEAR_RAMP = new Builder().setInterpolatorType(1).setCurve(new float[]{0.0f, 1.0f}, new float[]{0.0f, 1.0f}).setDuration(1000).build();
        public static final Configuration CUBIC_RAMP = new Builder().setInterpolatorType(2).setCurve(new float[]{0.0f, 1.0f}, new float[]{0.0f, 1.0f}).setDuration(1000).build();

        @Retention(RetentionPolicy.SOURCE)
        public @interface InterpolatorType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface OptionFlag {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        public static int getMaximumCurvePoints() {
            return 16;
        }

        private static int optionFlagsFromAidl(int i) {
            int i2 = (i & 1) != 0 ? 1 : 0;
            return (i & 2) != 0 ? i2 | 2 : i2;
        }

        private static int optionFlagsToAidl(int i) {
            int i2 = (i & 1) != 0 ? 1 : 0;
            return (i & 2) != 0 ? i2 | 2 : i2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        static {
            float[] fArr = new float[16];
            float[] fArr2 = new float[16];
            float[] fArr3 = new float[16];
            for (int i = 0; i < 16; i++) {
                float f = i / 15.0f;
                fArr[i] = f;
                float fSin = (float) Math.sin((f * 3.141592653589793d) / 2.0d);
                fArr2[i] = fSin;
                fArr3[i] = fSin * fSin;
            }
            SINE_RAMP = new Builder().setInterpolatorType(2).setCurve(fArr, fArr2).setDuration(1000L).build();
            SCURVE_RAMP = new Builder().setInterpolatorType(2).setCurve(fArr, fArr3).setDuration(1000L).build();
            CREATOR = new Parcelable.Creator<Configuration>() { // from class: android.media.VolumeShaper.Configuration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Configuration createFromParcel(Parcel parcel) {
                    return Configuration.fromParcelable(VolumeShaperConfiguration.CREATOR.createFromParcel(parcel));
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Configuration[] newArray(int i2) {
                    return new Configuration[i2];
                }
            };
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("VolumeShaper.Configuration{mType = ");
            sb.append(this.mType);
            sb.append(", mId = ");
            sb.append(this.mId);
            String str = "}";
            if (this.mType != 0) {
                str = ", mOptionFlags = 0x" + Integer.toHexString(this.mOptionFlags).toUpperCase() + ", mDurationMs = " + this.mDurationMs + ", mInterpolatorType = " + this.mInterpolatorType + ", mTimes[] = " + Arrays.toString(this.mTimes) + ", mVolumes[] = " + Arrays.toString(this.mVolumes) + "}";
            }
            sb.append(str);
            return sb.toString();
        }

        public int hashCode() {
            int i = this.mType;
            if (i == 0) {
                return Objects.hash(Integer.valueOf(i), Integer.valueOf(this.mId));
            }
            return Objects.hash(Integer.valueOf(i), Integer.valueOf(this.mId), Integer.valueOf(this.mOptionFlags), Double.valueOf(this.mDurationMs), Integer.valueOf(this.mInterpolatorType), Integer.valueOf(Arrays.hashCode(this.mTimes)), Integer.valueOf(Arrays.hashCode(this.mVolumes)));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Configuration)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            Configuration configuration = (Configuration) obj;
            int i = this.mType;
            return i == configuration.mType && this.mId == configuration.mId && (i == 0 || (this.mOptionFlags == configuration.mOptionFlags && this.mDurationMs == configuration.mDurationMs && this.mInterpolatorType == configuration.mInterpolatorType && Arrays.equals(this.mTimes, configuration.mTimes) && Arrays.equals(this.mVolumes, configuration.mVolumes)));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            toParcelable().writeToParcel(parcel, i);
        }

        public VolumeShaperConfiguration toParcelable() {
            VolumeShaperConfiguration volumeShaperConfiguration = new VolumeShaperConfiguration();
            volumeShaperConfiguration.type = typeToAidl(this.mType);
            volumeShaperConfiguration.id = this.mId;
            if (this.mType != 0) {
                volumeShaperConfiguration.optionFlags = optionFlagsToAidl(this.mOptionFlags);
                volumeShaperConfiguration.durationMs = this.mDurationMs;
                volumeShaperConfiguration.interpolatorConfig = toInterpolatorParcelable();
            }
            return volumeShaperConfiguration;
        }

        private InterpolatorConfig toInterpolatorParcelable() {
            InterpolatorConfig interpolatorConfig = new InterpolatorConfig();
            interpolatorConfig.type = interpolatorTypeToAidl(this.mInterpolatorType);
            interpolatorConfig.firstSlope = 0.0f;
            interpolatorConfig.lastSlope = 0.0f;
            interpolatorConfig.xy = new float[this.mTimes.length * 2];
            for (int i = 0; i < this.mTimes.length; i++) {
                int i2 = i * 2;
                interpolatorConfig.xy[i2] = this.mTimes[i];
                interpolatorConfig.xy[i2 + 1] = this.mVolumes[i];
            }
            return interpolatorConfig;
        }

        public static Configuration fromParcelable(VolumeShaperConfiguration volumeShaperConfiguration) {
            int iTypeFromAidl = typeFromAidl(volumeShaperConfiguration.type);
            int i = volumeShaperConfiguration.id;
            if (iTypeFromAidl == 0) {
                return new Configuration(i);
            }
            int iOptionFlagsFromAidl = optionFlagsFromAidl(volumeShaperConfiguration.optionFlags);
            double d = volumeShaperConfiguration.durationMs;
            int iInterpolatorTypeFromAidl = interpolatorTypeFromAidl(volumeShaperConfiguration.interpolatorConfig.type);
            int length = volumeShaperConfiguration.interpolatorConfig.xy.length;
            if (length % 2 != 0) {
                throw new BadParcelableException("xy length must be even");
            }
            int i2 = length / 2;
            float[] fArr = new float[i2];
            float[] fArr2 = new float[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = i3 * 2;
                fArr[i3] = volumeShaperConfiguration.interpolatorConfig.xy[i4];
                fArr2[i3] = volumeShaperConfiguration.interpolatorConfig.xy[i4 + 1];
            }
            return new Configuration(iTypeFromAidl, i, iOptionFlagsFromAidl, d, iInterpolatorTypeFromAidl, fArr, fArr2);
        }

        private static int interpolatorTypeFromAidl(int i) {
            if (i == 0) {
                return 0;
            }
            int i2 = 1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    if (i == 3) {
                        return 3;
                    }
                    throw new BadParcelableException("Unknown interpolator type");
                }
            }
            return i2;
        }

        private static int interpolatorTypeToAidl(int i) {
            if (i == 0) {
                return 0;
            }
            int i2 = 1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    if (i == 3) {
                        return 3;
                    }
                    throw new RuntimeException("Unknown interpolator type");
                }
            }
            return i2;
        }

        private static int typeFromAidl(int i) {
            if (i == 0) {
                return 0;
            }
            if (i == 1) {
                return 1;
            }
            throw new BadParcelableException("Unknown type");
        }

        private static int typeToAidl(int i) {
            if (i == 0) {
                return 0;
            }
            if (i == 1) {
                return 1;
            }
            throw new RuntimeException("Unknown type");
        }

        public Configuration(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("negative id " + i);
            }
            this.mType = 0;
            this.mId = i;
            this.mInterpolatorType = 0;
            this.mOptionFlags = 0;
            this.mDurationMs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.mTimes = null;
            this.mVolumes = null;
        }

        private Configuration(int i, int i2, int i3, double d, int i4, float[] fArr, float[] fArr2) {
            this.mType = i;
            this.mId = i2;
            this.mOptionFlags = i3;
            this.mDurationMs = d;
            this.mInterpolatorType = i4;
            this.mTimes = fArr;
            this.mVolumes = fArr2;
        }

        public int getType() {
            return this.mType;
        }

        public int getId() {
            return this.mId;
        }

        public int getInterpolatorType() {
            return this.mInterpolatorType;
        }

        public int getOptionFlags() {
            return this.mOptionFlags & 3;
        }

        int getAllOptionFlags() {
            return this.mOptionFlags;
        }

        public long getDuration() {
            return (long) this.mDurationMs;
        }

        public float[] getTimes() {
            return this.mTimes;
        }

        public float[] getVolumes() {
            return this.mVolumes;
        }

        private static String checkCurveForErrors(float[] fArr, float[] fArr2, boolean z) {
            if (fArr == null) {
                return "times array must be non-null";
            }
            if (fArr2 == null) {
                return "volumes array must be non-null";
            }
            if (fArr.length != fArr2.length) {
                return "array length must match";
            }
            if (fArr.length < 2) {
                return "array length must be at least 2";
            }
            if (fArr.length > 16) {
                return "array length must be no larger than 16";
            }
            int i = 0;
            if (fArr[0] != 0.0f) {
                return "times must start at 0.f";
            }
            if (fArr[fArr.length - 1] != 1.0f) {
                return "times must end at 1.f";
            }
            for (int i2 = 1; i2 < fArr.length; i2++) {
                if (fArr[i2] <= fArr[i2 - 1]) {
                    return "times not monotonic increasing, check index " + i2;
                }
            }
            if (z) {
                while (i < fArr2.length) {
                    if (fArr2[i] > 0.0f) {
                        return "volumes for log scale cannot be positive, check index " + i;
                    }
                    i++;
                }
                return null;
            }
            while (i < fArr2.length) {
                float f = fArr2[i];
                if (f < 0.0f || f > 1.0f) {
                    return "volumes for linear scale must be between 0.f and 1.f, check index " + i;
                }
                i++;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void checkCurveForErrorsAndThrowException(float[] fArr, float[] fArr2, boolean z, boolean z2) {
            String strCheckCurveForErrors = checkCurveForErrors(fArr, fArr2, z);
            if (strCheckCurveForErrors != null) {
                if (z2) {
                    throw new IllegalStateException(strCheckCurveForErrors);
                }
                throw new IllegalArgumentException(strCheckCurveForErrors);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void checkValidVolumeAndThrowException(float f, boolean z) {
            if (z) {
                if (f > 0.0f) {
                    throw new IllegalArgumentException("dbfs volume must be 0.f or less");
                }
            } else if (f < 0.0f || f > 1.0f) {
                throw new IllegalArgumentException("volume must be >= 0.f and <= 1.f");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void clampVolume(float[] fArr, boolean z) {
            int i = 0;
            if (z) {
                while (i < fArr.length) {
                    if (fArr[i] > 0.0f) {
                        fArr[i] = 0.0f;
                    }
                    i++;
                }
                return;
            }
            while (i < fArr.length) {
                float f = fArr[i];
                if (f < 0.0f) {
                    fArr[i] = 0.0f;
                } else if (f > 1.0f) {
                    fArr[i] = 1.0f;
                }
                i++;
            }
        }

        public static final class Builder {
            private double mDurationMs;
            private int mId;
            private int mInterpolatorType;
            private int mOptionFlags;
            private float[] mTimes;
            private int mType;
            private float[] mVolumes;

            public Builder() {
                this.mType = 1;
                this.mId = -1;
                this.mInterpolatorType = 2;
                this.mOptionFlags = 2;
                this.mDurationMs = 1000.0d;
                this.mTimes = null;
                this.mVolumes = null;
            }

            public Builder(Configuration configuration) {
                this.mType = 1;
                this.mId = -1;
                this.mInterpolatorType = 2;
                this.mOptionFlags = 2;
                this.mDurationMs = 1000.0d;
                this.mTimes = null;
                this.mVolumes = null;
                this.mType = configuration.getType();
                this.mId = configuration.getId();
                this.mOptionFlags = configuration.getAllOptionFlags();
                this.mInterpolatorType = configuration.getInterpolatorType();
                this.mDurationMs = configuration.getDuration();
                this.mTimes = (float[]) configuration.getTimes().clone();
                this.mVolumes = (float[]) configuration.getVolumes().clone();
            }

            public Builder setId(int i) {
                if (i < -1) {
                    throw new IllegalArgumentException("invalid id: " + i);
                }
                this.mId = i;
                return this;
            }

            public Builder setInterpolatorType(int i) {
                if (i == 0 || i == 1 || i == 2 || i == 3) {
                    this.mInterpolatorType = i;
                    return this;
                }
                throw new IllegalArgumentException("invalid interpolatorType: " + i);
            }

            public Builder setOptionFlags(int i) {
                if ((i & (-4)) != 0) {
                    throw new IllegalArgumentException("invalid bits in flag: " + i);
                }
                this.mOptionFlags = i | (this.mOptionFlags & (-4));
                return this;
            }

            public Builder setDuration(long j) {
                if (j <= 0) {
                    throw new IllegalArgumentException("duration: " + j + " not positive");
                }
                this.mDurationMs = j;
                return this;
            }

            public Builder setCurve(float[] fArr, float[] fArr2) {
                Configuration.checkCurveForErrorsAndThrowException(fArr, fArr2, (this.mOptionFlags & 1) != 0, false);
                this.mTimes = (float[]) fArr.clone();
                this.mVolumes = (float[]) fArr2.clone();
                return this;
            }

            public Builder reflectTimes() {
                float[] fArr;
                int i = 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, (this.mOptionFlags & 1) != 0, true);
                while (true) {
                    fArr = this.mTimes;
                    if (i >= fArr.length / 2) {
                        break;
                    }
                    float f = fArr[i];
                    fArr[i] = 1.0f - fArr[(fArr.length - 1) - i];
                    fArr[(fArr.length - 1) - i] = 1.0f - f;
                    float[] fArr2 = this.mVolumes;
                    float f2 = fArr2[i];
                    fArr2[i] = fArr2[(fArr2.length - 1) - i];
                    fArr2[(fArr2.length - 1) - i] = f2;
                    i++;
                }
                if ((1 & fArr.length) != 0) {
                    fArr[i] = 1.0f - fArr[i];
                }
                return this;
            }

            public Builder invertVolumes() {
                int i = 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, (this.mOptionFlags & 1) != 0, true);
                float f = this.mVolumes[0];
                int i2 = 1;
                float f2 = f;
                while (true) {
                    float[] fArr = this.mVolumes;
                    if (i2 >= fArr.length) {
                        break;
                    }
                    float f3 = fArr[i2];
                    if (f3 < f2) {
                        f2 = f3;
                    } else if (f3 > f) {
                        f = f3;
                    }
                    i2++;
                }
                float f4 = f + f2;
                while (true) {
                    float[] fArr2 = this.mVolumes;
                    if (i >= fArr2.length) {
                        return this;
                    }
                    fArr2[i] = f4 - fArr2[i];
                    i++;
                }
            }

            public Builder scaleToEndVolume(float f) {
                int i = 0;
                boolean z = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, z, true);
                Configuration.checkValidVolumeAndThrowException(f, z);
                float[] fArr = this.mVolumes;
                float f2 = fArr[0];
                float f3 = fArr[fArr.length - 1];
                if (f3 != f2) {
                    float f4 = (f - f2) / (f3 - f2);
                    while (true) {
                        float[] fArr2 = this.mVolumes;
                        if (i >= fArr2.length) {
                            break;
                        }
                        fArr2[i] = ((fArr2[i] - f2) * f4) + f2;
                        i++;
                    }
                } else {
                    float f5 = f - f2;
                    while (true) {
                        float[] fArr3 = this.mVolumes;
                        if (i >= fArr3.length) {
                            break;
                        }
                        fArr3[i] = fArr3[i] + (this.mTimes[i] * f5);
                        i++;
                    }
                }
                Configuration.clampVolume(this.mVolumes, z);
                return this;
            }

            public Builder scaleToStartVolume(float f) {
                int i = 0;
                boolean z = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, z, true);
                Configuration.checkValidVolumeAndThrowException(f, z);
                float[] fArr = this.mVolumes;
                float f2 = fArr[0];
                float f3 = fArr[fArr.length - 1];
                if (f3 != f2) {
                    float f4 = (f - f3) / (f2 - f3);
                    while (true) {
                        float[] fArr2 = this.mVolumes;
                        if (i >= fArr2.length) {
                            break;
                        }
                        fArr2[i] = ((fArr2[i] - f3) * f4) + f3;
                        i++;
                    }
                } else {
                    float f5 = f - f2;
                    while (true) {
                        float[] fArr3 = this.mVolumes;
                        if (i >= fArr3.length) {
                            break;
                        }
                        fArr3[i] = fArr3[i] + ((1.0f - this.mTimes[i]) * f5);
                        i++;
                    }
                }
                Configuration.clampVolume(this.mVolumes, z);
                return this;
            }

            public Configuration build() {
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, (this.mOptionFlags & 1) != 0, true);
                return new Configuration(this.mType, this.mId, this.mOptionFlags, this.mDurationMs, this.mInterpolatorType, this.mTimes, this.mVolumes);
            }
        }
    }

    public static final class Operation implements Parcelable {
        private static final int FLAG_CREATE_IF_NEEDED = 16;
        private static final int FLAG_DEFER = 8;
        private static final int FLAG_JOIN = 4;
        private static final int FLAG_NONE = 0;
        private static final int FLAG_PUBLIC_ALL = 3;
        private static final int FLAG_REVERSE = 1;
        private static final int FLAG_TERMINATE = 2;
        private final int mFlags;
        private final int mReplaceId;
        private final float mXOffset;
        public static final Operation PLAY = new Builder().build();
        public static final Operation REVERSE = new Builder().reverse().build();
        public static final Parcelable.Creator<Operation> CREATOR = new Parcelable.Creator<Operation>() { // from class: android.media.VolumeShaper.Operation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Operation createFromParcel(Parcel parcel) {
                return Operation.fromParcelable(VolumeShaperOperation.CREATOR.createFromParcel(parcel));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Operation[] newArray(int i) {
                return new Operation[i];
            }
        };

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flag {
        }

        private static int flagsFromAidl(int i) {
            int i2 = (i & 1) != 0 ? 1 : 0;
            if ((i & 2) != 0) {
                i2 |= 2;
            }
            if ((i & 4) != 0) {
                i2 |= 4;
            }
            if ((i & 8) != 0) {
                i2 |= 8;
            }
            return (i & 16) != 0 ? i2 | 16 : i2;
        }

        private static int flagsToAidl(int i) {
            int i2 = (i & 1) != 0 ? 1 : 0;
            if ((i & 2) != 0) {
                i2 |= 2;
            }
            if ((i & 4) != 0) {
                i2 |= 4;
            }
            if ((i & 8) != 0) {
                i2 |= 8;
            }
            return (i & 16) != 0 ? i2 | 16 : i2;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "VolumeShaper.Operation{mFlags = 0x" + Integer.toHexString(this.mFlags).toUpperCase() + ", mReplaceId = " + this.mReplaceId + ", mXOffset = " + this.mXOffset + "}";
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mFlags), Integer.valueOf(this.mReplaceId), Float.valueOf(this.mXOffset));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Operation)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            Operation operation = (Operation) obj;
            return this.mFlags == operation.mFlags && this.mReplaceId == operation.mReplaceId && Float.compare(this.mXOffset, operation.mXOffset) == 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            toParcelable().writeToParcel(parcel, i);
        }

        public VolumeShaperOperation toParcelable() {
            VolumeShaperOperation volumeShaperOperation = new VolumeShaperOperation();
            volumeShaperOperation.flags = flagsToAidl(this.mFlags);
            volumeShaperOperation.replaceId = this.mReplaceId;
            volumeShaperOperation.xOffset = this.mXOffset;
            return volumeShaperOperation;
        }

        public static Operation fromParcelable(VolumeShaperOperation volumeShaperOperation) {
            return new Operation(flagsFromAidl(volumeShaperOperation.flags), volumeShaperOperation.replaceId, volumeShaperOperation.xOffset);
        }

        private Operation(int i, int i2, float f) {
            this.mFlags = i;
            this.mReplaceId = i2;
            this.mXOffset = f;
        }

        public static final class Builder {
            int mFlags;
            int mReplaceId;
            float mXOffset;

            public Builder() {
                this.mFlags = 0;
                this.mReplaceId = -1;
                this.mXOffset = Float.NaN;
            }

            public Builder(Operation operation) {
                this.mReplaceId = operation.mReplaceId;
                this.mFlags = operation.mFlags;
                this.mXOffset = operation.mXOffset;
            }

            public Builder replace(int i, boolean z) {
                this.mReplaceId = i;
                if (z) {
                    this.mFlags |= 4;
                    return this;
                }
                this.mFlags &= -5;
                return this;
            }

            public Builder defer() {
                this.mFlags |= 8;
                return this;
            }

            public Builder terminate() {
                this.mFlags |= 2;
                return this;
            }

            public Builder reverse() {
                this.mFlags ^= 1;
                return this;
            }

            public Builder createIfNeeded() {
                this.mFlags |= 16;
                return this;
            }

            public Builder setXOffset(float f) {
                if (f < -0.0f) {
                    throw new IllegalArgumentException("Negative xOffset not allowed");
                }
                if (f > 1.0f) {
                    throw new IllegalArgumentException("xOffset > 1.f not allowed");
                }
                this.mXOffset = f;
                return this;
            }

            private Builder setFlags(int i) {
                if ((i & (-4)) != 0) {
                    throw new IllegalArgumentException("flag has unknown bits set: " + i);
                }
                this.mFlags = i | (this.mFlags & (-4));
                return this;
            }

            public Operation build() {
                return new Operation(this.mFlags, this.mReplaceId, this.mXOffset);
            }
        }
    }

    public static final class State implements Parcelable {
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() { // from class: android.media.VolumeShaper.State.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public State createFromParcel(Parcel parcel) {
                return State.fromParcelable(VolumeShaperState.CREATOR.createFromParcel(parcel));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public State[] newArray(int i) {
                return new State[i];
            }
        };
        private float mVolume;
        private float mXOffset;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "VolumeShaper.State{mVolume = " + this.mVolume + ", mXOffset = " + this.mXOffset + "}";
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mVolume), Float.valueOf(this.mXOffset));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof State)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            State state = (State) obj;
            return this.mVolume == state.mVolume && this.mXOffset == state.mXOffset;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            toParcelable().writeToParcel(parcel, i);
        }

        public VolumeShaperState toParcelable() {
            VolumeShaperState volumeShaperState = new VolumeShaperState();
            volumeShaperState.volume = this.mVolume;
            volumeShaperState.xOffset = this.mXOffset;
            return volumeShaperState;
        }

        public static State fromParcelable(VolumeShaperState volumeShaperState) {
            return new State(volumeShaperState.volume, volumeShaperState.xOffset);
        }

        State(float f, float f2) {
            this.mVolume = f;
            this.mXOffset = f2;
        }

        public float getVolume() {
            return this.mVolume;
        }

        public float getXOffset() {
            return this.mXOffset;
        }
    }
}
