package android.hardware.camera2.params;

import android.annotation.SystemApi;
import android.graphics.ColorSpace;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.MultiResolutionImageReader;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.android.internal.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/* loaded from: classes2.dex */
public final class OutputConfiguration implements Parcelable {
    private static final int MAX_SURFACES_COUNT = 4;
    public static final int MIRROR_MODE_AUTO = 0;
    public static final int MIRROR_MODE_H = 2;
    public static final int MIRROR_MODE_NONE = 1;
    public static final int MIRROR_MODE_V = 3;

    @SystemApi public static final int ROTATION_0 = 0;

    @SystemApi public static final int ROTATION_180 = 2;

    @SystemApi public static final int ROTATION_270 = 3;

    @SystemApi public static final int ROTATION_90 = 1;
    public static final int SURFACE_GROUP_ID_NONE = -1;
    private static final int SURFACE_TYPE_IMAGE_READER = 4;
    private static final int SURFACE_TYPE_MEDIA_CODEC = 3;
    private static final int SURFACE_TYPE_MEDIA_RECORDER = 2;
    private static final String TAG = "OutputConfiguration";
    public static final int TIMESTAMP_BASE_CHOREOGRAPHER_SYNCED = 4;
    public static final int TIMESTAMP_BASE_DEFAULT = 0;
    public static final int TIMESTAMP_BASE_MONOTONIC = 2;
    public static final int TIMESTAMP_BASE_READOUT_SENSOR = 5;
    public static final int TIMESTAMP_BASE_REALTIME = 3;
    public static final int TIMESTAMP_BASE_SENSOR = 1;
    private final int SURFACE_TYPE_SURFACE_TEXTURE;
    private final int SURFACE_TYPE_SURFACE_VIEW;
    private final int SURFACE_TYPE_UNKNOWN;
    private int mColorSpace;
    private final int mConfiguredDataspace;
    private final int mConfiguredFormat;
    private final int mConfiguredGenerationId;
    private final Size mConfiguredSize;
    private long mDynamicRangeProfile;
    private final boolean mIsDeferredConfig;
    private boolean mIsMultiResolution;
    private boolean mIsReadoutSensorTimestampBase;
    private boolean mIsShared;
    private int mMirrorMode;
    private String mPhysicalCameraId;
    private boolean mReadoutTimestampEnabled;
    private final int mRotation;
    private ArrayList<Integer> mSensorPixelModesUsed;
    private long mStreamUseCase;
    private final int mSurfaceGroupId;
    private final int mSurfaceType;
    private ArrayList<Surface> mSurfaces;
    private int mTimestampBase;
    private long mUsage;
    public static final Parcelable.Creator<OutputConfiguration> CREATOR =
            new Parcelable.Creator<OutputConfiguration>() { // from class:
                // android.hardware.camera2.params.OutputConfiguration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OutputConfiguration createFromParcel(Parcel source) {
                    return new OutputConfiguration(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OutputConfiguration[] newArray(int size) {
                    return new OutputConfiguration[size];
                }
            };
    private static AtomicInteger sNextMultiResolutionGroupId = new AtomicInteger(0);

    @Retention(RetentionPolicy.SOURCE)
    public @interface MirrorMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorPixelMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamUseCase {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimestampBase {}

    public OutputConfiguration(Surface surface) {
        this(-1, surface, 0);
    }

    public OutputConfiguration(int surfaceGroupId, Surface surface) {
        this(surfaceGroupId, surface, 0);
    }

    public void setMultiResolutionOutput() {
        if (this.mIsShared) {
            throw new IllegalStateException(
                    "Multi-resolution output flag must not be set for configuration with surface"
                            + " sharing");
        }
        if (this.mSurfaceGroupId == -1) {
            throw new IllegalStateException(
                    "Multi-resolution output flag should only be set for surface with non-negative"
                            + " group ID");
        }
        this.mIsMultiResolution = true;
    }

    public void setDynamicRangeProfile(long profile) {
        this.mDynamicRangeProfile = profile;
    }

    public long getDynamicRangeProfile() {
        return this.mDynamicRangeProfile;
    }

    public void setColorSpace(ColorSpace.Named colorSpace) {
        this.mColorSpace = colorSpace.ordinal();
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
    }

    public ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return ColorSpace.get(ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }

    @SystemApi
    public OutputConfiguration(Surface surface, int rotation) {
        this(-1, surface, rotation);
    }

    public OutputConfiguration(int surfaceGroupId, Surface surface, int rotation, int option) {
        this(surfaceGroupId, surface, rotation);
        this.mStreamUseCase = Integer.toUnsignedLong(option) << Long.numberOfTrailingZeros(65536L);
    }

    public <T> OutputConfiguration(Size surfaceSize, Class<T> klass, int option) {
        this(surfaceSize, klass);
        this.mStreamUseCase = Integer.toUnsignedLong(option) << Long.numberOfTrailingZeros(65536L);
    }

    public static OutputConfiguration semCreateOutputConfiguration(
            int surfaceGroupId, Surface surface, int rotation, int option) {
        return new OutputConfiguration(surfaceGroupId, surface, rotation, option);
    }

    public static <T> OutputConfiguration semCreateOutputConfiguration(
            Size surfaceSize, Class<T> klass, int option) {
        return new OutputConfiguration(surfaceSize, klass, option);
    }

    @SystemApi
    public OutputConfiguration(int surfaceGroupId, Surface surface, int rotation) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(surface, "Surface must not be null");
        Preconditions.checkArgumentInRange(rotation, 0, 3, "Rotation constant");
        this.mSurfaceGroupId = surfaceGroupId;
        this.mSurfaceType = -1;
        this.mSurfaces = new ArrayList<>();
        this.mSurfaces.add(surface);
        this.mRotation = rotation;
        this.mConfiguredSize = SurfaceUtils.getSurfaceSize(surface);
        this.mConfiguredFormat = SurfaceUtils.getSurfaceFormat(surface);
        this.mConfiguredDataspace = SurfaceUtils.getSurfaceDataspace(surface);
        this.mConfiguredGenerationId = surface.getGenerationId();
        this.mIsDeferredConfig = false;
        this.mIsShared = false;
        this.mPhysicalCameraId = null;
        this.mIsMultiResolution = false;
        this.mSensorPixelModesUsed = new ArrayList<>();
        this.mDynamicRangeProfile = 1L;
        this.mColorSpace = -1;
        this.mStreamUseCase = 0L;
        this.mTimestampBase = 0;
        this.mMirrorMode = 0;
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
        this.mUsage = 0L;
    }

    public static Collection<OutputConfiguration> createInstancesForMultiResolutionOutput(
            MultiResolutionImageReader multiResolutionImageReader) {
        Preconditions.checkNotNull(
                multiResolutionImageReader, "Multi-resolution image reader must not be null");
        int groupId = getAndIncreaseMultiResolutionGroupId();
        ImageReader[] imageReaders = multiResolutionImageReader.getReaders();
        ArrayList<OutputConfiguration> configs = new ArrayList<>();
        for (int i = 0; i < imageReaders.length; i++) {
            MultiResolutionStreamInfo streamInfo =
                    multiResolutionImageReader.getStreamInfoForImageReader(imageReaders[i]);
            OutputConfiguration config =
                    new OutputConfiguration(groupId, imageReaders[i].getSurface());
            config.setPhysicalCameraId(streamInfo.getPhysicalCameraId());
            config.setMultiResolutionOutput();
            configs.add(config);
        }
        return configs;
    }

    public static List<OutputConfiguration> createInstancesForMultiResolutionOutput(
            Collection<MultiResolutionStreamInfo> streams, int format) {
        if (streams == null || streams.size() <= 1) {
            throw new IllegalArgumentException("The streams list must contain at least 2 entries");
        }
        if (format == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        int groupId = getAndIncreaseMultiResolutionGroupId();
        ArrayList<OutputConfiguration> configs = new ArrayList<>();
        for (MultiResolutionStreamInfo stream : streams) {
            Size surfaceSize = new Size(stream.getWidth(), stream.getHeight());
            OutputConfiguration config = new OutputConfiguration(groupId, format, surfaceSize);
            config.setPhysicalCameraId(stream.getPhysicalCameraId());
            config.setMultiResolutionOutput();
            configs.add(config);
        }
        return configs;
    }

    public static void setSurfacesForMultiResolutionOutput(
            Collection<OutputConfiguration> outputConfigurations,
            MultiResolutionImageReader multiResolutionImageReader) {
        Preconditions.checkNotNull(outputConfigurations, "outputConfigurations must not be null");
        Preconditions.checkNotNull(
                multiResolutionImageReader, "multiResolutionImageReader must not be null");
        if (outputConfigurations.size() != multiResolutionImageReader.getReaders().length) {
            throw new IllegalArgumentException(
                    "outputConfigurations and multiResolutionImageReader sizes must match");
        }
        for (OutputConfiguration config : outputConfigurations) {
            String physicalCameraId = config.getPhysicalCameraId();
            if (physicalCameraId == null) {
                physicalCameraId = "";
            }
            Surface surface =
                    multiResolutionImageReader.getSurface(
                            config.getConfiguredSize(), physicalCameraId);
            config.addSurface(surface);
        }
    }

    public <T> OutputConfiguration(Size surfaceSize, Class<T> klass) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(surfaceSize, "surfaceSize must not be null");
        Preconditions.checkNotNull(klass, "klass must not be null");
        if (klass == SurfaceHolder.class) {
            this.mSurfaceType = 0;
            this.mIsDeferredConfig = true;
        } else if (klass == SurfaceTexture.class) {
            this.mSurfaceType = 1;
            this.mIsDeferredConfig = true;
        } else if (klass == MediaRecorder.class) {
            this.mSurfaceType = 2;
            this.mIsDeferredConfig = false;
        } else if (klass == MediaCodec.class) {
            this.mSurfaceType = 3;
            this.mIsDeferredConfig = false;
        } else {
            this.mSurfaceType = -1;
            throw new IllegalArgumentException("Unknown surface source class type");
        }
        if (surfaceSize.getWidth() == 0 || surfaceSize.getHeight() == 0) {
            throw new IllegalArgumentException("Surface size needs to be non-zero");
        }
        this.mSurfaceGroupId = -1;
        this.mSurfaces = new ArrayList<>();
        this.mRotation = 0;
        this.mConfiguredSize = surfaceSize;
        this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(34);
        this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(34);
        this.mConfiguredGenerationId = 0;
        this.mIsShared = false;
        this.mPhysicalCameraId = null;
        this.mIsMultiResolution = false;
        this.mSensorPixelModesUsed = new ArrayList<>();
        this.mDynamicRangeProfile = 1L;
        this.mColorSpace = -1;
        this.mStreamUseCase = 0L;
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
        this.mUsage = 0L;
    }

    public OutputConfiguration(int format, Size surfaceSize) {
        this(format, surfaceSize, format == 34 ? 0L : 3L);
    }

    public OutputConfiguration(int surfaceGroupId, int format, Size surfaceSize) {
        this(surfaceGroupId, format, surfaceSize, format == 34 ? 0L : 3L);
    }

    public OutputConfiguration(int format, Size surfaceSize, long usage) {
        this(-1, format, surfaceSize, usage);
    }

    public OutputConfiguration(int surfaceGroupId, int format, Size surfaceSize, long usage) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(surfaceSize, "surfaceSize must not be null");
        if (surfaceSize.getWidth() == 0 || surfaceSize.getHeight() == 0) {
            throw new IllegalArgumentException("Surface size needs to be non-zero");
        }
        this.mSurfaceType = 4;
        this.mSurfaceGroupId = surfaceGroupId;
        this.mSurfaces = new ArrayList<>();
        this.mRotation = 0;
        this.mConfiguredSize = surfaceSize;
        this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(format);
        this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(format);
        this.mConfiguredGenerationId = 0;
        this.mIsDeferredConfig = false;
        this.mIsShared = false;
        this.mPhysicalCameraId = null;
        this.mIsMultiResolution = false;
        this.mSensorPixelModesUsed = new ArrayList<>();
        this.mDynamicRangeProfile = 1L;
        this.mColorSpace = -1;
        this.mStreamUseCase = 0L;
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
        this.mUsage = usage;
    }

    public void enableSurfaceSharing() {
        if (this.mIsMultiResolution) {
            throw new IllegalStateException(
                    "Cannot enable surface sharing on multi-resolution output configurations");
        }
        this.mIsShared = true;
    }

    public void setPhysicalCameraId(String physicalCameraId) {
        this.mPhysicalCameraId = physicalCameraId;
    }

    public void addSensorPixelModeUsed(int sensorPixelModeUsed) {
        if (sensorPixelModeUsed != 0 && sensorPixelModeUsed != 1) {
            throw new IllegalArgumentException(
                    "Not a valid sensor pixel mode " + sensorPixelModeUsed);
        }
        if (this.mSensorPixelModesUsed.contains(Integer.valueOf(sensorPixelModeUsed))) {
            return;
        }
        this.mSensorPixelModesUsed.add(Integer.valueOf(sensorPixelModeUsed));
    }

    public void removeSensorPixelModeUsed(int sensorPixelModeUsed) {
        if (!this.mSensorPixelModesUsed.remove(Integer.valueOf(sensorPixelModeUsed))) {
            throw new IllegalArgumentException(
                    "sensorPixelMode "
                            + sensorPixelModeUsed
                            + "is not part of this output configuration");
        }
    }

    public boolean isForPhysicalCamera() {
        return this.mPhysicalCameraId != null;
    }

    public boolean isDeferredConfiguration() {
        return this.mIsDeferredConfig;
    }

    public void addSurface(Surface surface) {
        Preconditions.checkNotNull(surface, "Surface must not be null");
        if (this.mSurfaces.contains(surface)) {
            throw new IllegalStateException("Surface is already added!");
        }
        if (this.mSurfaces.size() == 1 && !this.mIsShared) {
            throw new IllegalStateException(
                    "Cannot have 2 surfaces for a non-sharing configuration");
        }
        if (this.mSurfaces.size() + 1 > 4) {
            throw new IllegalArgumentException("Exceeds maximum number of surfaces");
        }
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        if (!surfaceSize.equals(this.mConfiguredSize)) {
            Log.w(
                    TAG,
                    "Added surface size "
                            + surfaceSize
                            + " is different than pre-configured size "
                            + this.mConfiguredSize
                            + ", the pre-configured size will be used.");
        }
        if (this.mConfiguredFormat != SurfaceUtils.getSurfaceFormat(surface)) {
            throw new IllegalArgumentException("The format of added surface format doesn't match");
        }
        if (this.mConfiguredFormat != 34
                && this.mConfiguredDataspace != SurfaceUtils.getSurfaceDataspace(surface)) {
            throw new IllegalArgumentException("The dataspace of added surface doesn't match");
        }
        this.mSurfaces.add(surface);
    }

    public void removeSurface(Surface surface) {
        Preconditions.checkNotNull(surface, "Surface must not be null");
        if (getSurface() == surface) {
            throw new IllegalArgumentException(
                    "Cannot remove surface associated with this output configuration");
        }
        if (!this.mSurfaces.remove(surface)) {
            throw new IllegalArgumentException("Surface is not part of this output configuration");
        }
    }

    public void setStreamUseCase(long streamUseCase) {
        if (streamUseCase > 6 && streamUseCase < 65536) {
            throw new IllegalArgumentException(
                    "Not a valid stream use case value " + streamUseCase);
        }
        this.mStreamUseCase = streamUseCase;
    }

    public long getStreamUseCase() {
        return this.mStreamUseCase;
    }

    public void setTimestampBase(int timestampBase) {
        if (timestampBase < 0 || timestampBase > 5) {
            throw new IllegalArgumentException("Not a valid timestamp base value " + timestampBase);
        }
        if (timestampBase == 5) {
            this.mTimestampBase = 1;
            this.mReadoutTimestampEnabled = true;
            this.mIsReadoutSensorTimestampBase = true;
        } else {
            this.mTimestampBase = timestampBase;
            this.mIsReadoutSensorTimestampBase = false;
        }
    }

    public int getTimestampBase() {
        if (this.mIsReadoutSensorTimestampBase) {
            return 5;
        }
        return this.mTimestampBase;
    }

    public void setMirrorMode(int mirrorMode) {
        if (mirrorMode < 0 || mirrorMode > 3) {
            throw new IllegalArgumentException("Not a valid mirror mode " + mirrorMode);
        }
        this.mMirrorMode = mirrorMode;
    }

    public int getMirrorMode() {
        return this.mMirrorMode;
    }

    public void setReadoutTimestampEnabled(boolean on) {
        this.mReadoutTimestampEnabled = on;
    }

    public boolean isReadoutTimestampEnabled() {
        return this.mReadoutTimestampEnabled;
    }

    public OutputConfiguration(OutputConfiguration other) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        if (other == null) {
            throw new IllegalArgumentException("OutputConfiguration shouldn't be null");
        }
        this.mSurfaces = other.mSurfaces;
        this.mRotation = other.mRotation;
        this.mSurfaceGroupId = other.mSurfaceGroupId;
        this.mSurfaceType = other.mSurfaceType;
        this.mConfiguredDataspace = other.mConfiguredDataspace;
        this.mConfiguredFormat = other.mConfiguredFormat;
        this.mConfiguredSize = other.mConfiguredSize;
        this.mConfiguredGenerationId = other.mConfiguredGenerationId;
        this.mIsDeferredConfig = other.mIsDeferredConfig;
        this.mIsShared = other.mIsShared;
        this.mPhysicalCameraId = other.mPhysicalCameraId;
        this.mIsMultiResolution = other.mIsMultiResolution;
        this.mSensorPixelModesUsed = other.mSensorPixelModesUsed;
        this.mDynamicRangeProfile = other.mDynamicRangeProfile;
        this.mColorSpace = other.mColorSpace;
        this.mStreamUseCase = other.mStreamUseCase;
        this.mTimestampBase = other.mTimestampBase;
        this.mMirrorMode = other.mMirrorMode;
        this.mReadoutTimestampEnabled = other.mReadoutTimestampEnabled;
        this.mUsage = other.mUsage;
    }

    private OutputConfiguration(Parcel source) {
        boolean isDeferred;
        boolean isShared;
        boolean isMultiResolutionOutput;
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        int rotation = source.readInt();
        int surfaceSetId = source.readInt();
        int surfaceType = source.readInt();
        int width = source.readInt();
        int height = source.readInt();
        if (source.readInt() != 1) {
            isDeferred = false;
        } else {
            isDeferred = true;
        }
        if (source.readInt() != 1) {
            isShared = false;
        } else {
            isShared = true;
        }
        ArrayList<Surface> surfaces = new ArrayList<>();
        source.readTypedList(surfaces, Surface.CREATOR);
        String physicalCameraId = source.readString();
        if (source.readInt() != 1) {
            isMultiResolutionOutput = false;
        } else {
            isMultiResolutionOutput = true;
        }
        int[] sensorPixelModesUsed = source.createIntArray();
        Preconditions.checkArgumentInRange(rotation, 0, 3, "Rotation constant");
        long dynamicRangeProfile = source.readLong();
        DynamicRangeProfiles.checkProfileValue(dynamicRangeProfile);
        int colorSpace = source.readInt();
        long streamUseCase = source.readLong();
        int timestampBase = source.readInt();
        int mirrorMode = source.readInt();
        int mirrorMode2 = source.readInt();
        boolean readoutTimestampEnabled = mirrorMode2 == 1;
        int format = source.readInt();
        int dataSpace = source.readInt();
        boolean isMultiResolutionOutput2 = isMultiResolutionOutput;
        long usage = source.readLong();
        this.mSurfaceGroupId = surfaceSetId;
        this.mRotation = rotation;
        this.mSurfaces = surfaces;
        this.mConfiguredSize = new Size(width, height);
        this.mIsDeferredConfig = isDeferred;
        this.mIsShared = isShared;
        this.mSurfaces = surfaces;
        this.mUsage = 0L;
        if (this.mSurfaces.size() > 0) {
            this.mSurfaceType = -1;
            this.mConfiguredFormat = SurfaceUtils.getSurfaceFormat(this.mSurfaces.get(0));
            this.mConfiguredDataspace = SurfaceUtils.getSurfaceDataspace(this.mSurfaces.get(0));
            this.mConfiguredGenerationId = this.mSurfaces.get(0).getGenerationId();
        } else {
            this.mSurfaceType = surfaceType;
            if (this.mSurfaceType != 4) {
                this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(34);
                this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(34);
            } else {
                this.mConfiguredFormat = format;
                this.mConfiguredDataspace = dataSpace;
                this.mUsage = usage;
            }
            this.mConfiguredGenerationId = 0;
        }
        this.mPhysicalCameraId = physicalCameraId;
        this.mIsMultiResolution = isMultiResolutionOutput2;
        this.mSensorPixelModesUsed = convertIntArrayToIntegerList(sensorPixelModesUsed);
        this.mDynamicRangeProfile = dynamicRangeProfile;
        this.mColorSpace = colorSpace;
        this.mStreamUseCase = streamUseCase;
        this.mTimestampBase = timestampBase;
        this.mMirrorMode = mirrorMode;
        this.mReadoutTimestampEnabled = readoutTimestampEnabled;
    }

    public int getMaxSharedSurfaceCount() {
        return 4;
    }

    public Surface getSurface() {
        if (this.mSurfaces.size() == 0) {
            return null;
        }
        return this.mSurfaces.get(0);
    }

    public List<Surface> getSurfaces() {
        return Collections.unmodifiableList(this.mSurfaces);
    }

    @SystemApi
    public int getRotation() {
        return this.mRotation;
    }

    public int getSurfaceGroupId() {
        return this.mSurfaceGroupId;
    }

    public Size getConfiguredSize() {
        return this.mConfiguredSize;
    }

    public String getPhysicalCameraId() {
        return this.mPhysicalCameraId;
    }

    public int getOption() {
        return Long.valueOf(this.mStreamUseCase >>> Long.numberOfTrailingZeros(65536L)).intValue();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static int[] convertIntegerToIntList(List<Integer> integerList) {
        int[] integerArray = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            integerArray[i] = integerList.get(i).intValue();
        }
        return integerArray;
    }

    private static ArrayList<Integer> convertIntArrayToIntegerList(int[] intArray) {
        ArrayList<Integer> integerList = new ArrayList<>();
        if (intArray == null) {
            return integerList;
        }
        for (int i : intArray) {
            integerList.add(Integer.valueOf(i));
        }
        return integerList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mSurfaceGroupId);
        parcel.writeInt(this.mSurfaceType);
        parcel.writeInt(this.mConfiguredSize.getWidth());
        parcel.writeInt(this.mConfiguredSize.getHeight());
        parcel.writeInt(this.mIsDeferredConfig ? 1 : 0);
        parcel.writeInt(this.mIsShared ? 1 : 0);
        parcel.writeTypedList(this.mSurfaces);
        parcel.writeString(this.mPhysicalCameraId);
        parcel.writeInt(this.mIsMultiResolution ? 1 : 0);
        parcel.writeIntArray(convertIntegerToIntList(this.mSensorPixelModesUsed));
        parcel.writeLong(this.mDynamicRangeProfile);
        parcel.writeInt(this.mColorSpace);
        parcel.writeLong(this.mStreamUseCase);
        parcel.writeInt(this.mTimestampBase);
        parcel.writeInt(this.mMirrorMode);
        parcel.writeInt(this.mReadoutTimestampEnabled ? 1 : 0);
        parcel.writeInt(this.mConfiguredFormat);
        parcel.writeInt(this.mConfiguredDataspace);
        parcel.writeLong(this.mUsage);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OutputConfiguration)) {
            return false;
        }
        OutputConfiguration other = (OutputConfiguration) obj;
        if (this.mRotation != other.mRotation
                || !this.mConfiguredSize.equals(other.mConfiguredSize)
                || this.mConfiguredFormat != other.mConfiguredFormat
                || this.mSurfaceGroupId != other.mSurfaceGroupId
                || this.mSurfaceType != other.mSurfaceType
                || this.mIsDeferredConfig != other.mIsDeferredConfig
                || this.mIsShared != other.mIsShared
                || this.mConfiguredDataspace != other.mConfiguredDataspace
                || this.mConfiguredGenerationId != other.mConfiguredGenerationId
                || !Objects.equals(this.mPhysicalCameraId, other.mPhysicalCameraId)
                || this.mIsMultiResolution != other.mIsMultiResolution
                || this.mStreamUseCase != other.mStreamUseCase
                || this.mTimestampBase != other.mTimestampBase
                || this.mMirrorMode != other.mMirrorMode
                || this.mReadoutTimestampEnabled != other.mReadoutTimestampEnabled
                || this.mUsage != other.mUsage
                || this.mSensorPixelModesUsed.size() != other.mSensorPixelModesUsed.size()) {
            return false;
        }
        for (int j = 0; j < this.mSensorPixelModesUsed.size(); j++) {
            if (!Objects.equals(
                    this.mSensorPixelModesUsed.get(j), other.mSensorPixelModesUsed.get(j))) {
                return false;
            }
        }
        int minLen = Math.min(this.mSurfaces.size(), other.mSurfaces.size());
        for (int i = 0; i < minLen; i++) {
            if (this.mSurfaces.get(i) != other.mSurfaces.get(i)) {
                return false;
            }
        }
        if ((!this.mIsDeferredConfig && this.mSurfaces.size() != other.mSurfaces.size())
                || this.mDynamicRangeProfile != other.mDynamicRangeProfile
                || this.mColorSpace != other.mColorSpace) {
            return false;
        }
        return true;
    }

    private static int getAndIncreaseMultiResolutionGroupId() {
        return sNextMultiResolutionGroupId.getAndUpdate(
                new IntUnaryOperator() { // from class:
                    // android.hardware.camera2.params.OutputConfiguration$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntUnaryOperator
                    public final int applyAsInt(int i) {
                        return OutputConfiguration.lambda$getAndIncreaseMultiResolutionGroupId$0(i);
                    }
                });
    }

    static /* synthetic */ int lambda$getAndIncreaseMultiResolutionGroupId$0(int i) {
        return i + 1 == -1 ? i + 2 : i + 1;
    }

    public int hashCode() {
        if (this.mIsDeferredConfig) {
            return HashCodeHelpers.hashCode(
                    this.mRotation,
                    this.mConfiguredSize.hashCode(),
                    this.mConfiguredFormat,
                    this.mConfiguredDataspace,
                    this.mSurfaceGroupId,
                    this.mSurfaceType,
                    this.mIsShared ? 1.0f : 0.0f,
                    this.mPhysicalCameraId == null ? 0.0f : this.mPhysicalCameraId.hashCode(),
                    this.mIsMultiResolution ? 1.0f : 0.0f,
                    this.mSensorPixelModesUsed.hashCode(),
                    this.mDynamicRangeProfile,
                    this.mColorSpace,
                    this.mStreamUseCase,
                    this.mTimestampBase,
                    this.mMirrorMode,
                    this.mReadoutTimestampEnabled ? 1.0f : 0.0f,
                    Long.hashCode(this.mUsage));
        }
        return HashCodeHelpers.hashCode(
                this.mRotation,
                this.mSurfaces.hashCode(),
                this.mConfiguredGenerationId,
                this.mConfiguredSize.hashCode(),
                this.mConfiguredFormat,
                this.mConfiguredDataspace,
                this.mSurfaceGroupId,
                this.mIsShared ? 1.0f : 0.0f,
                this.mPhysicalCameraId == null ? 0.0f : this.mPhysicalCameraId.hashCode(),
                this.mIsMultiResolution ? 1.0f : 0.0f,
                this.mSensorPixelModesUsed.hashCode(),
                this.mDynamicRangeProfile,
                this.mColorSpace,
                this.mStreamUseCase,
                this.mTimestampBase,
                this.mMirrorMode,
                this.mReadoutTimestampEnabled ? 1.0f : 0.0f,
                Long.hashCode(this.mUsage));
    }
}
