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
import android.util.IntArray;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.camera.flags.Flags;
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

    @SystemApi
    public static final int ROTATION_0 = 0;

    @SystemApi
    public static final int ROTATION_180 = 2;

    @SystemApi
    public static final int ROTATION_270 = 3;

    @SystemApi
    public static final int ROTATION_90 = 1;
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
    private IntArray mMirrorModeForSurfaces;
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
    public static final Parcelable.Creator<OutputConfiguration> CREATOR = new Parcelable.Creator<OutputConfiguration>() { // from class: android.hardware.camera2.params.OutputConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OutputConfiguration createFromParcel(Parcel parcel) {
            return new OutputConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OutputConfiguration[] newArray(int i) {
            return new OutputConfiguration[i];
        }
    };
    private static AtomicInteger sNextMultiResolutionGroupId = new AtomicInteger(0);

    @Retention(RetentionPolicy.SOURCE)
    public @interface MirrorMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorPixelMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamUseCase {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimestampBase {
    }

    static /* synthetic */ int lambda$getAndIncreaseMultiResolutionGroupId$0(int i) {
        int i2 = i + 1;
        return i2 == -1 ? i + 2 : i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getMaxSharedSurfaceCount() {
        return 4;
    }

    public OutputConfiguration(Surface surface) {
        this(-1, surface, 0);
    }

    public OutputConfiguration(int i, Surface surface) {
        this(i, surface, 0);
    }

    public void setMultiResolutionOutput() {
        if (this.mIsShared) {
            throw new IllegalStateException("Multi-resolution output flag must not be set for configuration with surface sharing");
        }
        if (this.mSurfaceGroupId == -1) {
            throw new IllegalStateException("Multi-resolution output flag should only be set for surface with non-negative group ID");
        }
        this.mIsMultiResolution = true;
    }

    public void setDynamicRangeProfile(long j) {
        this.mDynamicRangeProfile = j;
    }

    public long getDynamicRangeProfile() {
        return this.mDynamicRangeProfile;
    }

    public void setColorSpace(ColorSpace.Named named) {
        this.mColorSpace = named.ordinal();
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
    public OutputConfiguration(Surface surface, int i) {
        this(-1, surface, i);
    }

    public OutputConfiguration(int i, Surface surface, int i2, int i3) {
        this(i, surface, i2);
        this.mStreamUseCase = Integer.toUnsignedLong(i3) << Long.numberOfTrailingZeros(65536L);
    }

    public <T> OutputConfiguration(Size size, Class<T> cls, int i) {
        this(size, cls);
        this.mStreamUseCase = Integer.toUnsignedLong(i) << Long.numberOfTrailingZeros(65536L);
    }

    public static OutputConfiguration semCreateOutputConfiguration(int i, Surface surface, int i2, int i3) {
        return new OutputConfiguration(i, surface, i2, i3);
    }

    public static <T> OutputConfiguration semCreateOutputConfiguration(Size size, Class<T> cls, int i) {
        return new OutputConfiguration(size, cls, i);
    }

    @SystemApi
    public OutputConfiguration(int i, Surface surface, int i2) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(surface, "Surface must not be null");
        Preconditions.checkArgumentInRange(i2, 0, 3, "Rotation constant");
        this.mSurfaceGroupId = i;
        this.mSurfaceType = -1;
        ArrayList<Surface> arrayList = new ArrayList<>();
        this.mSurfaces = arrayList;
        arrayList.add(surface);
        this.mRotation = i2;
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
        this.mMirrorModeForSurfaces = new IntArray();
        if (Flags.mirrorModeSharedSurfaces()) {
            this.mMirrorModeForSurfaces.add(this.mMirrorMode);
        }
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
        this.mUsage = 0L;
    }

    public static Collection<OutputConfiguration> createInstancesForMultiResolutionOutput(MultiResolutionImageReader multiResolutionImageReader) {
        Preconditions.checkNotNull(multiResolutionImageReader, "Multi-resolution image reader must not be null");
        int andIncreaseMultiResolutionGroupId = getAndIncreaseMultiResolutionGroupId();
        ImageReader[] readers = multiResolutionImageReader.getReaders();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < readers.length; i++) {
            MultiResolutionStreamInfo streamInfoForImageReader = multiResolutionImageReader.getStreamInfoForImageReader(readers[i]);
            OutputConfiguration outputConfiguration = new OutputConfiguration(andIncreaseMultiResolutionGroupId, readers[i].getSurface());
            outputConfiguration.setPhysicalCameraId(streamInfoForImageReader.getPhysicalCameraId());
            outputConfiguration.setMultiResolutionOutput();
            arrayList.add(outputConfiguration);
        }
        return arrayList;
    }

    public static List<OutputConfiguration> createInstancesForMultiResolutionOutput(Collection<MultiResolutionStreamInfo> collection, int i) {
        if (collection == null || collection.size() <= 1) {
            throw new IllegalArgumentException("The streams list must contain at least 2 entries");
        }
        if (i == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        int andIncreaseMultiResolutionGroupId = getAndIncreaseMultiResolutionGroupId();
        ArrayList arrayList = new ArrayList();
        for (MultiResolutionStreamInfo multiResolutionStreamInfo : collection) {
            OutputConfiguration outputConfiguration = new OutputConfiguration(andIncreaseMultiResolutionGroupId, i, new Size(multiResolutionStreamInfo.getWidth(), multiResolutionStreamInfo.getHeight()));
            outputConfiguration.setPhysicalCameraId(multiResolutionStreamInfo.getPhysicalCameraId());
            outputConfiguration.setMultiResolutionOutput();
            arrayList.add(outputConfiguration);
        }
        return arrayList;
    }

    public static void setSurfacesForMultiResolutionOutput(Collection<OutputConfiguration> collection, MultiResolutionImageReader multiResolutionImageReader) {
        Preconditions.checkNotNull(collection, "outputConfigurations must not be null");
        Preconditions.checkNotNull(multiResolutionImageReader, "multiResolutionImageReader must not be null");
        if (collection.size() != multiResolutionImageReader.getReaders().length) {
            throw new IllegalArgumentException("outputConfigurations and multiResolutionImageReader sizes must match");
        }
        for (OutputConfiguration outputConfiguration : collection) {
            String physicalCameraId = outputConfiguration.getPhysicalCameraId();
            if (physicalCameraId == null) {
                physicalCameraId = "";
            }
            outputConfiguration.addSurface(multiResolutionImageReader.getSurface(outputConfiguration.getConfiguredSize(), physicalCameraId));
        }
    }

    public <T> OutputConfiguration(Size size, Class<T> cls) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(size, "surfaceSize must not be null");
        Preconditions.checkNotNull(cls, "klass must not be null");
        if (cls == SurfaceHolder.class) {
            this.mSurfaceType = 0;
            this.mIsDeferredConfig = true;
        } else if (cls == SurfaceTexture.class) {
            this.mSurfaceType = 1;
            this.mIsDeferredConfig = true;
        } else if (cls == MediaRecorder.class) {
            this.mSurfaceType = 2;
            this.mIsDeferredConfig = false;
        } else if (cls == MediaCodec.class) {
            this.mSurfaceType = 3;
            this.mIsDeferredConfig = false;
        } else {
            this.mSurfaceType = -1;
            throw new IllegalArgumentException("Unknown surface source class type");
        }
        if (size.getWidth() == 0 || size.getHeight() == 0) {
            throw new IllegalArgumentException("Surface size needs to be non-zero");
        }
        this.mSurfaceGroupId = -1;
        this.mSurfaces = new ArrayList<>();
        this.mMirrorModeForSurfaces = new IntArray();
        this.mRotation = 0;
        this.mConfiguredSize = size;
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

    public OutputConfiguration(int i, Size size) {
        this(i, size, i == 34 ? 0L : 3L);
    }

    public OutputConfiguration(int i, int i2, Size size) {
        this(i, i2, size, i2 == 34 ? 0L : 3L);
    }

    public OutputConfiguration(int i, Size size, long j) {
        this(-1, i, size, j);
    }

    public OutputConfiguration(int i, int i2, Size size, long j) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(size, "surfaceSize must not be null");
        if (size.getWidth() == 0 || size.getHeight() == 0) {
            throw new IllegalArgumentException("Surface size needs to be non-zero");
        }
        this.mSurfaceType = 4;
        this.mSurfaceGroupId = i;
        this.mSurfaces = new ArrayList<>();
        this.mRotation = 0;
        this.mConfiguredSize = size;
        this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(i2);
        this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(i2);
        this.mConfiguredGenerationId = 0;
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
        this.mMirrorModeForSurfaces = new IntArray();
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
        this.mUsage = j;
    }

    public void enableSurfaceSharing() {
        if (this.mIsMultiResolution) {
            throw new IllegalStateException("Cannot enable surface sharing on multi-resolution output configurations");
        }
        this.mIsShared = true;
    }

    public void setPhysicalCameraId(String str) {
        this.mPhysicalCameraId = str;
    }

    public void addSensorPixelModeUsed(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Not a valid sensor pixel mode " + i);
        }
        if (this.mSensorPixelModesUsed.contains(Integer.valueOf(i))) {
            return;
        }
        this.mSensorPixelModesUsed.add(Integer.valueOf(i));
    }

    public void removeSensorPixelModeUsed(int i) {
        if (this.mSensorPixelModesUsed.remove(Integer.valueOf(i))) {
            return;
        }
        throw new IllegalArgumentException("sensorPixelMode " + i + "is not part of this output configuration");
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
            throw new IllegalStateException("Cannot have 2 surfaces for a non-sharing configuration");
        }
        if (this.mSurfaces.size() + 1 > 4) {
            throw new IllegalArgumentException("Exceeds maximum number of surfaces");
        }
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        if (!surfaceSize.equals(this.mConfiguredSize)) {
            Log.w(TAG, "Added surface size " + surfaceSize + " is different than pre-configured size " + this.mConfiguredSize + ", the pre-configured size will be used.");
        }
        if (this.mConfiguredFormat != SurfaceUtils.getSurfaceFormat(surface)) {
            throw new IllegalArgumentException("The format of added surface format doesn't match");
        }
        if (this.mConfiguredFormat != 34 && this.mConfiguredDataspace != SurfaceUtils.getSurfaceDataspace(surface)) {
            throw new IllegalArgumentException("The dataspace of added surface doesn't match");
        }
        this.mSurfaces.add(surface);
        if (Flags.mirrorModeSharedSurfaces()) {
            this.mMirrorModeForSurfaces.add(this.mMirrorMode);
        }
    }

    public void removeSurface(Surface surface) {
        Preconditions.checkNotNull(surface, "Surface must not be null");
        if (getSurface() == surface) {
            throw new IllegalArgumentException("Cannot remove surface associated with this output configuration");
        }
        int iIndexOf = this.mSurfaces.indexOf(surface);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Surface is not part of this output configuration");
        }
        this.mSurfaces.remove(iIndexOf);
        if (Flags.mirrorModeSharedSurfaces()) {
            this.mMirrorModeForSurfaces.remove(iIndexOf);
        }
    }

    public void setStreamUseCase(long j) {
        if (j > 6 && j < 65536) {
            throw new IllegalArgumentException("Not a valid stream use case value " + j);
        }
        this.mStreamUseCase = j;
    }

    public long getStreamUseCase() {
        return this.mStreamUseCase;
    }

    public void setTimestampBase(int i) {
        if (i < 0 || i > 5) {
            throw new IllegalArgumentException("Not a valid timestamp base value " + i);
        }
        if (i == 5) {
            this.mTimestampBase = 1;
            this.mReadoutTimestampEnabled = true;
            this.mIsReadoutSensorTimestampBase = true;
        } else {
            this.mTimestampBase = i;
            this.mIsReadoutSensorTimestampBase = false;
        }
    }

    public int getTimestampBase() {
        if (this.mIsReadoutSensorTimestampBase) {
            return 5;
        }
        return this.mTimestampBase;
    }

    public void setMirrorMode(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("Not a valid mirror mode " + i);
        }
        this.mMirrorMode = i;
        for (int i2 = 0; i2 < this.mMirrorModeForSurfaces.size(); i2++) {
            this.mMirrorModeForSurfaces.set(i2, i);
        }
    }

    public int getMirrorMode() {
        return this.mMirrorMode;
    }

    public void setMirrorMode(Surface surface, int i) {
        Preconditions.checkNotNull(surface, "Surface must not be null");
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("Not a valid mirror mode " + i);
        }
        int iIndexOf = this.mSurfaces.indexOf(surface);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Surface not part of the OutputConfiguration");
        }
        this.mMirrorModeForSurfaces.set(iIndexOf, i);
    }

    public int getMirrorMode(Surface surface) {
        Preconditions.checkNotNull(surface, "Surface must not be null");
        int iIndexOf = this.mSurfaces.indexOf(surface);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Surface not part of the OutputConfiguration");
        }
        return this.mMirrorModeForSurfaces.get(iIndexOf);
    }

    public void setReadoutTimestampEnabled(boolean z) {
        this.mReadoutTimestampEnabled = z;
    }

    public boolean isReadoutTimestampEnabled() {
        return this.mReadoutTimestampEnabled;
    }

    public OutputConfiguration(OutputConfiguration outputConfiguration) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        if (outputConfiguration == null) {
            throw new IllegalArgumentException("OutputConfiguration shouldn't be null");
        }
        this.mSurfaces = outputConfiguration.mSurfaces;
        this.mRotation = outputConfiguration.mRotation;
        this.mSurfaceGroupId = outputConfiguration.mSurfaceGroupId;
        this.mSurfaceType = outputConfiguration.mSurfaceType;
        this.mConfiguredDataspace = outputConfiguration.mConfiguredDataspace;
        this.mConfiguredFormat = outputConfiguration.mConfiguredFormat;
        this.mConfiguredSize = outputConfiguration.mConfiguredSize;
        this.mConfiguredGenerationId = outputConfiguration.mConfiguredGenerationId;
        this.mIsDeferredConfig = outputConfiguration.mIsDeferredConfig;
        this.mIsShared = outputConfiguration.mIsShared;
        this.mPhysicalCameraId = outputConfiguration.mPhysicalCameraId;
        this.mIsMultiResolution = outputConfiguration.mIsMultiResolution;
        this.mSensorPixelModesUsed = outputConfiguration.mSensorPixelModesUsed;
        this.mDynamicRangeProfile = outputConfiguration.mDynamicRangeProfile;
        this.mColorSpace = outputConfiguration.mColorSpace;
        this.mStreamUseCase = outputConfiguration.mStreamUseCase;
        this.mTimestampBase = outputConfiguration.mTimestampBase;
        this.mMirrorMode = outputConfiguration.mMirrorMode;
        this.mMirrorModeForSurfaces = outputConfiguration.mMirrorModeForSurfaces.m5509clone();
        this.mReadoutTimestampEnabled = outputConfiguration.mReadoutTimestampEnabled;
        this.mUsage = outputConfiguration.mUsage;
    }

    private OutputConfiguration(Parcel parcel) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        int i4 = parcel.readInt();
        int i5 = parcel.readInt();
        boolean z = parcel.readInt() == 1;
        boolean z2 = parcel.readInt() == 1;
        ArrayList<Surface> arrayList = new ArrayList<>();
        parcel.readTypedList(arrayList, Surface.CREATOR);
        String string = parcel.readString();
        boolean z3 = parcel.readInt() == 1;
        int[] iArrCreateIntArray = parcel.createIntArray();
        Preconditions.checkArgumentInRange(i, 0, 3, "Rotation constant");
        long j = parcel.readLong();
        DynamicRangeProfiles.checkProfileValue(j);
        int i6 = parcel.readInt();
        long j2 = parcel.readLong();
        int i7 = parcel.readInt();
        int i8 = parcel.readInt();
        int[] iArrCreateIntArray2 = parcel.createIntArray();
        boolean z4 = parcel.readInt() == 1;
        int i9 = parcel.readInt();
        boolean z5 = z4;
        int i10 = parcel.readInt();
        long j3 = parcel.readLong();
        this.mSurfaceGroupId = i2;
        this.mRotation = i;
        this.mSurfaces = arrayList;
        this.mConfiguredSize = new Size(i4, i5);
        this.mIsDeferredConfig = z;
        this.mIsShared = z2;
        this.mUsage = 0L;
        if (this.mSurfaces.size() > 0) {
            this.mSurfaceType = -1;
            this.mConfiguredFormat = SurfaceUtils.getSurfaceFormat(this.mSurfaces.get(0));
            this.mConfiguredDataspace = SurfaceUtils.getSurfaceDataspace(this.mSurfaces.get(0));
            this.mConfiguredGenerationId = this.mSurfaces.get(0).getGenerationId();
        } else {
            this.mSurfaceType = i3;
            if (i3 != 4) {
                this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(34);
                this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(34);
            } else {
                this.mConfiguredFormat = i9;
                this.mConfiguredDataspace = i10;
                this.mUsage = j3;
            }
            this.mConfiguredGenerationId = 0;
        }
        this.mPhysicalCameraId = string;
        this.mIsMultiResolution = z3;
        this.mSensorPixelModesUsed = convertIntArrayToIntegerList(iArrCreateIntArray);
        this.mDynamicRangeProfile = j;
        this.mColorSpace = i6;
        this.mStreamUseCase = j2;
        this.mTimestampBase = i7;
        this.mMirrorMode = i8;
        this.mMirrorModeForSurfaces = IntArray.wrap(iArrCreateIntArray2);
        this.mReadoutTimestampEnabled = z5;
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

    public int getConfiguredFormat() {
        return this.mConfiguredFormat;
    }

    public long getUsage() {
        return this.mUsage;
    }

    public int getSurfaceType() {
        return this.mSurfaceType;
    }

    public List<Integer> getSensorPixelModes() {
        return this.mSensorPixelModesUsed;
    }

    public boolean isShared() {
        return this.mIsShared;
    }

    public int getConfiguredDataspace() {
        return this.mConfiguredDataspace;
    }

    public boolean isMultiResolution() {
        return this.mIsMultiResolution;
    }

    public String getPhysicalCameraId() {
        return this.mPhysicalCameraId;
    }

    public int getOption() {
        return Long.valueOf(this.mStreamUseCase >>> Long.numberOfTrailingZeros(65536L)).intValue();
    }

    private static int[] convertIntegerToIntList(List<Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    private static ArrayList<Integer> convertIntArrayToIntegerList(int[] iArr) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (iArr != null) {
            for (int i : iArr) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
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
        parcel.writeIntArray(this.mMirrorModeForSurfaces.toArray());
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
        if (obj instanceof OutputConfiguration) {
            OutputConfiguration outputConfiguration = (OutputConfiguration) obj;
            if (this.mRotation != outputConfiguration.mRotation || !this.mConfiguredSize.equals(outputConfiguration.mConfiguredSize) || this.mConfiguredFormat != outputConfiguration.mConfiguredFormat || this.mSurfaceGroupId != outputConfiguration.mSurfaceGroupId || this.mSurfaceType != outputConfiguration.mSurfaceType || this.mIsDeferredConfig != outputConfiguration.mIsDeferredConfig || this.mIsShared != outputConfiguration.mIsShared || this.mConfiguredDataspace != outputConfiguration.mConfiguredDataspace || this.mConfiguredGenerationId != outputConfiguration.mConfiguredGenerationId || !Objects.equals(this.mPhysicalCameraId, outputConfiguration.mPhysicalCameraId) || this.mIsMultiResolution != outputConfiguration.mIsMultiResolution || this.mStreamUseCase != outputConfiguration.mStreamUseCase || this.mTimestampBase != outputConfiguration.mTimestampBase || this.mMirrorMode != outputConfiguration.mMirrorMode || this.mReadoutTimestampEnabled != outputConfiguration.mReadoutTimestampEnabled || this.mUsage != outputConfiguration.mUsage || this.mSensorPixelModesUsed.size() != outputConfiguration.mSensorPixelModesUsed.size()) {
                return false;
            }
            for (int i = 0; i < this.mSensorPixelModesUsed.size(); i++) {
                if (!Objects.equals(this.mSensorPixelModesUsed.get(i), outputConfiguration.mSensorPixelModesUsed.get(i))) {
                    return false;
                }
            }
            if (Flags.mirrorModeSharedSurfaces()) {
                if (this.mMirrorModeForSurfaces.size() != outputConfiguration.mMirrorModeForSurfaces.size()) {
                    return false;
                }
                for (int i2 = 0; i2 < this.mMirrorModeForSurfaces.size(); i2++) {
                    if (this.mMirrorModeForSurfaces.get(i2) != outputConfiguration.mMirrorModeForSurfaces.get(i2)) {
                        return false;
                    }
                }
            }
            int iMin = Math.min(this.mSurfaces.size(), outputConfiguration.mSurfaces.size());
            for (int i3 = 0; i3 < iMin; i3++) {
                if (this.mSurfaces.get(i3) != outputConfiguration.mSurfaces.get(i3)) {
                    return false;
                }
            }
            return (this.mIsDeferredConfig || this.mSurfaces.size() == outputConfiguration.mSurfaces.size()) && this.mDynamicRangeProfile == outputConfiguration.mDynamicRangeProfile && this.mColorSpace == outputConfiguration.mColorSpace;
        }
        return false;
    }

    private static int getAndIncreaseMultiResolutionGroupId() {
        return sNextMultiResolutionGroupId.getAndUpdate(new IntUnaryOperator() { // from class: android.hardware.camera2.params.OutputConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return OutputConfiguration.lambda$getAndIncreaseMultiResolutionGroupId$0(i);
            }
        });
    }

    public int hashCode() {
        if (this.mIsDeferredConfig) {
            return HashCodeHelpers.hashCode(this.mRotation, this.mConfiguredSize.hashCode(), this.mConfiguredFormat, this.mConfiguredDataspace, this.mSurfaceGroupId, this.mSurfaceType, this.mIsShared ? 1.0f : 0.0f, this.mPhysicalCameraId == null ? 0.0f : r9.hashCode(), this.mIsMultiResolution ? 1.0f : 0.0f, this.mSensorPixelModesUsed.hashCode(), this.mDynamicRangeProfile, this.mColorSpace, this.mStreamUseCase, this.mTimestampBase, this.mMirrorMode, HashCodeHelpers.hashCode(this.mMirrorModeForSurfaces.toArray()), this.mReadoutTimestampEnabled ? 1.0f : 0.0f, Long.hashCode(this.mUsage));
        }
        return HashCodeHelpers.hashCode(this.mRotation, this.mSurfaces.hashCode(), this.mConfiguredGenerationId, this.mConfiguredSize.hashCode(), this.mConfiguredFormat, this.mConfiguredDataspace, this.mSurfaceGroupId, this.mIsShared ? 1.0f : 0.0f, this.mPhysicalCameraId == null ? 0.0f : r9.hashCode(), this.mIsMultiResolution ? 1.0f : 0.0f, this.mSensorPixelModesUsed.hashCode(), this.mDynamicRangeProfile, this.mColorSpace, this.mStreamUseCase, this.mTimestampBase, this.mMirrorMode, HashCodeHelpers.hashCode(this.mMirrorModeForSurfaces.toArray()), this.mReadoutTimestampEnabled ? 1.0f : 0.0f, Long.hashCode(this.mUsage));
    }
}
