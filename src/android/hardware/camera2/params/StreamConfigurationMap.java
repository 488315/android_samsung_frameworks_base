package android.hardware.camera2.params;

import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.hardware.camera2.utils.SurfaceUtils;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.renderscript.Allocation;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.camera.flags.Flags;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class StreamConfigurationMap {
    private static final long DURATION_20FPS_NS = 50000000;
    private static final int DURATION_MIN_FRAME = 0;
    private static final int DURATION_STALL = 1;
    public static final int HAL_DATASPACE_ARBITRARY = 1;
    public static final int HAL_DATASPACE_DEPTH = 4096;
    public static final int HAL_DATASPACE_DYNAMIC_DEPTH = 4098;
    public static final int HAL_DATASPACE_HEIF = 4100;
    public static final int HAL_DATASPACE_JFIF = 146931712;
    public static final int HAL_DATASPACE_JPEG_R = 4101;
    private static final int HAL_DATASPACE_RANGE_SHIFT = 27;
    private static final int HAL_DATASPACE_STANDARD_SHIFT = 16;
    private static final int HAL_DATASPACE_TRANSFER_SHIFT = 22;
    private static final int HAL_DATASPACE_UNKNOWN = 0;
    public static final int HAL_DATASPACE_V0_JFIF = 146931712;
    public static final int HAL_PIXEL_FORMAT_BLOB = 33;
    private static final int HAL_PIXEL_FORMAT_IMPLEMENTATION_DEFINED = 34;
    private static final int HAL_PIXEL_FORMAT_RAW10 = 37;
    private static final int HAL_PIXEL_FORMAT_RAW12 = 38;
    private static final int HAL_PIXEL_FORMAT_RAW16 = 32;
    private static final int HAL_PIXEL_FORMAT_RAW_OPAQUE = 36;
    private static final int HAL_PIXEL_FORMAT_Y16 = 540422489;
    private static final int HAL_PIXEL_FORMAT_YCbCr_420_888 = 35;
    private static final int MAX_DIMEN_FOR_ROUNDING = 1920;
    private static final String TAG = "StreamConfigurationMap";
    private final SparseIntArray mAllOutputFormats;
    private final StreamConfiguration[] mConfigurations;
    private final StreamConfiguration[] mDepthConfigurations;
    private final StreamConfigurationDuration[] mDepthMinFrameDurations;
    private final SparseIntArray mDepthOutputFormats;
    private final StreamConfigurationDuration[] mDepthStallDurations;
    private final StreamConfiguration[] mDynamicDepthConfigurations;
    private final StreamConfigurationDuration[] mDynamicDepthMinFrameDurations;
    private final SparseIntArray mDynamicDepthOutputFormats;
    private final StreamConfigurationDuration[] mDynamicDepthStallDurations;
    private final StreamConfiguration[] mHeicConfigurations;
    private final StreamConfigurationDuration[] mHeicMinFrameDurations;
    private final SparseIntArray mHeicOutputFormats;
    private final StreamConfigurationDuration[] mHeicStallDurations;
    private final StreamConfiguration[] mHeicUltraHDRConfigurations;
    private final StreamConfigurationDuration[] mHeicUltraHDRMinFrameDurations;
    private final SparseIntArray mHeicUltraHDROutputFormats;
    private final StreamConfigurationDuration[] mHeicUltraHDRStallDurations;
    private final SparseIntArray mHighResOutputFormats;
    private final HighSpeedVideoConfiguration[] mHighSpeedVideoConfigurations;
    private final HashMap<Range<Integer>, Integer> mHighSpeedVideoFpsRangeMap;
    private final HashMap<Size, Integer> mHighSpeedVideoSizeMap;
    private final SparseIntArray mInputFormats;
    private final ReprocessFormatsMap mInputOutputFormatsMap;
    private final StreamConfiguration[] mJpegRConfigurations;
    private final StreamConfigurationDuration[] mJpegRMinFrameDurations;
    private final SparseIntArray mJpegROutputFormats;
    private final StreamConfigurationDuration[] mJpegRStallDurations;
    private final boolean mListHighResolution;
    private final StreamConfigurationDuration[] mMinFrameDurations;
    private final SparseIntArray mOutputFormats;
    private final StreamConfigurationDuration[] mStallDurations;

    public static int compareSizes(int i, int i2, int i3, int i4) {
        long j = i;
        long j2 = i2 * j;
        long j3 = i3;
        long j4 = i4 * j3;
        if (j2 != j4) {
            j = j2;
            j3 = j4;
        }
        if (j < j3) {
            return -1;
        }
        return j > j3 ? 1 : 0;
    }

    public StreamConfigurationMap(StreamConfiguration[] streamConfigurationArr, StreamConfigurationDuration[] streamConfigurationDurationArr, StreamConfigurationDuration[] streamConfigurationDurationArr2, StreamConfiguration[] streamConfigurationArr2, StreamConfigurationDuration[] streamConfigurationDurationArr3, StreamConfigurationDuration[] streamConfigurationDurationArr4, StreamConfiguration[] streamConfigurationArr3, StreamConfigurationDuration[] streamConfigurationDurationArr5, StreamConfigurationDuration[] streamConfigurationDurationArr6, StreamConfiguration[] streamConfigurationArr4, StreamConfigurationDuration[] streamConfigurationDurationArr7, StreamConfigurationDuration[] streamConfigurationDurationArr8, StreamConfiguration[] streamConfigurationArr5, StreamConfigurationDuration[] streamConfigurationDurationArr9, StreamConfigurationDuration[] streamConfigurationDurationArr10, StreamConfiguration[] streamConfigurationArr6, StreamConfigurationDuration[] streamConfigurationDurationArr11, StreamConfigurationDuration[] streamConfigurationDurationArr12, HighSpeedVideoConfiguration[] highSpeedVideoConfigurationArr, ReprocessFormatsMap reprocessFormatsMap, boolean z) {
        this(streamConfigurationArr, streamConfigurationDurationArr, streamConfigurationDurationArr2, streamConfigurationArr2, streamConfigurationDurationArr3, streamConfigurationDurationArr4, streamConfigurationArr3, streamConfigurationDurationArr5, streamConfigurationDurationArr6, streamConfigurationArr4, streamConfigurationDurationArr7, streamConfigurationDurationArr8, streamConfigurationArr5, streamConfigurationDurationArr9, streamConfigurationDurationArr10, streamConfigurationArr6, streamConfigurationDurationArr11, streamConfigurationDurationArr12, highSpeedVideoConfigurationArr, reprocessFormatsMap, z, true);
    }

    public StreamConfigurationMap(StreamConfiguration[] streamConfigurationArr, StreamConfigurationDuration[] streamConfigurationDurationArr, StreamConfigurationDuration[] streamConfigurationDurationArr2, StreamConfiguration[] streamConfigurationArr2, StreamConfigurationDuration[] streamConfigurationDurationArr3, StreamConfigurationDuration[] streamConfigurationDurationArr4, StreamConfiguration[] streamConfigurationArr3, StreamConfigurationDuration[] streamConfigurationDurationArr5, StreamConfigurationDuration[] streamConfigurationDurationArr6, StreamConfiguration[] streamConfigurationArr4, StreamConfigurationDuration[] streamConfigurationDurationArr7, StreamConfigurationDuration[] streamConfigurationDurationArr8, StreamConfiguration[] streamConfigurationArr5, StreamConfigurationDuration[] streamConfigurationDurationArr9, StreamConfigurationDuration[] streamConfigurationDurationArr10, StreamConfiguration[] streamConfigurationArr6, StreamConfigurationDuration[] streamConfigurationDurationArr11, StreamConfigurationDuration[] streamConfigurationDurationArr12, HighSpeedVideoConfiguration[] highSpeedVideoConfigurationArr, ReprocessFormatsMap reprocessFormatsMap, boolean z, boolean z2) {
        SparseIntArray sparseIntArray;
        long j;
        this.mOutputFormats = new SparseIntArray();
        this.mHighResOutputFormats = new SparseIntArray();
        this.mAllOutputFormats = new SparseIntArray();
        this.mInputFormats = new SparseIntArray();
        this.mDepthOutputFormats = new SparseIntArray();
        this.mDynamicDepthOutputFormats = new SparseIntArray();
        this.mHeicOutputFormats = new SparseIntArray();
        this.mHeicUltraHDROutputFormats = new SparseIntArray();
        this.mJpegROutputFormats = new SparseIntArray();
        this.mHighSpeedVideoSizeMap = new HashMap<>();
        this.mHighSpeedVideoFpsRangeMap = new HashMap<>();
        if (streamConfigurationArr == null && streamConfigurationArr2 == null && streamConfigurationArr4 == null) {
            throw new NullPointerException("At least one of color/depth/heic configurations must not be null");
        }
        if (streamConfigurationArr == null) {
            this.mConfigurations = new StreamConfiguration[0];
            this.mMinFrameDurations = new StreamConfigurationDuration[0];
            this.mStallDurations = new StreamConfigurationDuration[0];
        } else {
            this.mConfigurations = (StreamConfiguration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationArr, "configurations");
            this.mMinFrameDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr, "minFrameDurations");
            this.mStallDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr2, "stallDurations");
        }
        this.mListHighResolution = z;
        if (streamConfigurationArr2 == null) {
            this.mDepthConfigurations = new StreamConfiguration[0];
            this.mDepthMinFrameDurations = new StreamConfigurationDuration[0];
            this.mDepthStallDurations = new StreamConfigurationDuration[0];
        } else {
            this.mDepthConfigurations = (StreamConfiguration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationArr2, "depthConfigurations");
            this.mDepthMinFrameDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr3, "depthMinFrameDurations");
            this.mDepthStallDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr4, "depthStallDurations");
        }
        if (streamConfigurationArr3 == null) {
            this.mDynamicDepthConfigurations = new StreamConfiguration[0];
            this.mDynamicDepthMinFrameDurations = new StreamConfigurationDuration[0];
            this.mDynamicDepthStallDurations = new StreamConfigurationDuration[0];
        } else {
            this.mDynamicDepthConfigurations = (StreamConfiguration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationArr3, "dynamicDepthConfigurations");
            this.mDynamicDepthMinFrameDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr5, "dynamicDepthMinFrameDurations");
            this.mDynamicDepthStallDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr6, "dynamicDepthStallDurations");
        }
        if (streamConfigurationArr4 == null) {
            this.mHeicConfigurations = new StreamConfiguration[0];
            this.mHeicMinFrameDurations = new StreamConfigurationDuration[0];
            this.mHeicStallDurations = new StreamConfigurationDuration[0];
        } else {
            this.mHeicConfigurations = (StreamConfiguration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationArr4, "heicConfigurations");
            this.mHeicMinFrameDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr7, "heicMinFrameDurations");
            this.mHeicStallDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr8, "heicStallDurations");
        }
        if (streamConfigurationArr6 == null || !Flags.cameraHeifGainmap()) {
            this.mHeicUltraHDRConfigurations = new StreamConfiguration[0];
            this.mHeicUltraHDRMinFrameDurations = new StreamConfigurationDuration[0];
            this.mHeicUltraHDRStallDurations = new StreamConfigurationDuration[0];
        } else {
            this.mHeicUltraHDRConfigurations = (StreamConfiguration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationArr6, "heicUltraHDRConfigurations");
            this.mHeicUltraHDRMinFrameDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr11, "heicUltraHDRMinFrameDurations");
            this.mHeicUltraHDRStallDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr12, "heicUltraHDRStallDurations");
        }
        if (streamConfigurationArr5 == null) {
            this.mJpegRConfigurations = new StreamConfiguration[0];
            this.mJpegRMinFrameDurations = new StreamConfigurationDuration[0];
            this.mJpegRStallDurations = new StreamConfigurationDuration[0];
        } else {
            this.mJpegRConfigurations = (StreamConfiguration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationArr5, "jpegRConfigurations");
            this.mJpegRMinFrameDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr9, "jpegRFrameDurations");
            this.mJpegRStallDurations = (StreamConfigurationDuration[]) Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr10, "jpegRStallDurations");
        }
        if (highSpeedVideoConfigurationArr == null) {
            this.mHighSpeedVideoConfigurations = new HighSpeedVideoConfiguration[0];
        } else {
            this.mHighSpeedVideoConfigurations = (HighSpeedVideoConfiguration[]) Preconditions.checkArrayElementsNotNull(highSpeedVideoConfigurationArr, "highSpeedVideoConfigurations");
        }
        for (StreamConfiguration streamConfiguration : this.mConfigurations) {
            int format = streamConfiguration.getFormat();
            if (streamConfiguration.isOutput()) {
                SparseIntArray sparseIntArray2 = this.mAllOutputFormats;
                sparseIntArray2.put(format, sparseIntArray2.get(format) + 1);
                if (this.mListHighResolution) {
                    for (StreamConfigurationDuration streamConfigurationDuration : this.mMinFrameDurations) {
                        if (streamConfigurationDuration.getFormat() == format && streamConfigurationDuration.getWidth() == streamConfiguration.getSize().getWidth() && streamConfigurationDuration.getHeight() == streamConfiguration.getSize().getHeight()) {
                            j = streamConfigurationDuration.getDuration();
                            break;
                        }
                    }
                }
                j = 0;
                sparseIntArray = j <= DURATION_20FPS_NS ? this.mOutputFormats : this.mHighResOutputFormats;
            } else {
                sparseIntArray = this.mInputFormats;
            }
            sparseIntArray.put(format, sparseIntArray.get(format) + 1);
        }
        for (StreamConfiguration streamConfiguration2 : this.mDepthConfigurations) {
            if (streamConfiguration2.isOutput()) {
                this.mDepthOutputFormats.put(streamConfiguration2.getFormat(), this.mDepthOutputFormats.get(streamConfiguration2.getFormat()) + 1);
            }
        }
        for (StreamConfiguration streamConfiguration3 : this.mDynamicDepthConfigurations) {
            if (streamConfiguration3.isOutput()) {
                this.mDynamicDepthOutputFormats.put(streamConfiguration3.getFormat(), this.mDynamicDepthOutputFormats.get(streamConfiguration3.getFormat()) + 1);
            }
        }
        for (StreamConfiguration streamConfiguration4 : this.mHeicConfigurations) {
            if (streamConfiguration4.isOutput()) {
                this.mHeicOutputFormats.put(streamConfiguration4.getFormat(), this.mHeicOutputFormats.get(streamConfiguration4.getFormat()) + 1);
            }
        }
        if (Flags.cameraHeifGainmap()) {
            for (StreamConfiguration streamConfiguration5 : this.mHeicUltraHDRConfigurations) {
                if (streamConfiguration5.isOutput()) {
                    this.mHeicUltraHDROutputFormats.put(streamConfiguration5.getFormat(), this.mHeicUltraHDROutputFormats.get(streamConfiguration5.getFormat()) + 1);
                }
            }
        }
        for (StreamConfiguration streamConfiguration6 : this.mJpegRConfigurations) {
            if (streamConfiguration6.isOutput()) {
                this.mJpegROutputFormats.put(streamConfiguration6.getFormat(), this.mJpegROutputFormats.get(streamConfiguration6.getFormat()) + 1);
            }
        }
        if (streamConfigurationArr != null && z2 && this.mOutputFormats.indexOfKey(34) < 0) {
            throw new AssertionError("At least one stream configuration for IMPLEMENTATION_DEFINED must exist");
        }
        for (HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
            Size size = highSpeedVideoConfiguration.getSize();
            Range<Integer> fpsRange = highSpeedVideoConfiguration.getFpsRange();
            Integer num = this.mHighSpeedVideoSizeMap.get(size);
            this.mHighSpeedVideoSizeMap.put(size, Integer.valueOf((num == null ? 0 : num).intValue() + 1));
            Integer num2 = this.mHighSpeedVideoFpsRangeMap.get(fpsRange);
            if (num2 == null) {
                num2 = 0;
            }
            this.mHighSpeedVideoFpsRangeMap.put(fpsRange, Integer.valueOf(num2.intValue() + 1));
        }
        this.mInputOutputFormatsMap = reprocessFormatsMap;
    }

    public int[] getOutputFormats() {
        return getPublicFormats(true);
    }

    public int[] getValidOutputFormatsForInput(int i) {
        ReprocessFormatsMap reprocessFormatsMap = this.mInputOutputFormatsMap;
        if (reprocessFormatsMap == null) {
            return new int[0];
        }
        int[] outputs = reprocessFormatsMap.getOutputs(i);
        if (this.mHeicOutputFormats.size() <= 0) {
            return outputs;
        }
        int[] copyOf = Arrays.copyOf(outputs, outputs.length + 1);
        copyOf[outputs.length] = 1212500294;
        return copyOf;
    }

    public int[] getInputFormats() {
        return getPublicFormats(false);
    }

    public Size[] getInputSizes(int i) {
        return getPublicFormatSizes(i, false, false);
    }

    public boolean isOutputSupportedFor(int i) {
        checkArgumentFormat(i);
        int imageFormatToInternal = imageFormatToInternal(i);
        int imageFormatToDataspace = imageFormatToDataspace(i);
        return (Flags.cameraHeifGainmap() && imageFormatToDataspace == 4102) ? this.mHeicUltraHDROutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4096 ? this.mDepthOutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4098 ? this.mDynamicDepthOutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4100 ? this.mHeicOutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4101 ? this.mJpegROutputFormats.indexOfKey(imageFormatToInternal) >= 0 : getFormatsMap(true).indexOfKey(imageFormatToInternal) >= 0;
    }

    public static <T> boolean isOutputSupportedFor(Class<T> cls) {
        Objects.requireNonNull(cls, "klass must not be null");
        return cls == ImageReader.class || cls == MediaRecorder.class || cls == MediaCodec.class || cls == Allocation.class || cls == SurfaceHolder.class || cls == SurfaceTexture.class;
    }

    public boolean isOutputSupportedFor(Surface surface) {
        StreamConfiguration[] streamConfigurationArr;
        Objects.requireNonNull(surface, "surface must not be null");
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        int surfaceFormat = SurfaceUtils.getSurfaceFormat(surface);
        int surfaceDataspace = SurfaceUtils.getSurfaceDataspace(surface);
        boolean isFlexibleConsumer = SurfaceUtils.isFlexibleConsumer(surface);
        if (surfaceDataspace == 4096) {
            streamConfigurationArr = this.mDepthConfigurations;
        } else if (surfaceDataspace == 4098) {
            streamConfigurationArr = this.mDynamicDepthConfigurations;
        } else if (surfaceDataspace == 4100) {
            streamConfigurationArr = this.mHeicConfigurations;
        } else if (surfaceDataspace == 4101) {
            streamConfigurationArr = this.mJpegRConfigurations;
        } else {
            streamConfigurationArr = this.mConfigurations;
        }
        if (Flags.cameraHeifGainmap() && surfaceDataspace == 4102) {
            streamConfigurationArr = this.mHeicUltraHDRConfigurations;
        }
        for (StreamConfiguration streamConfiguration : streamConfigurationArr) {
            if (streamConfiguration.getFormat() == surfaceFormat && streamConfiguration.isOutput()) {
                if (streamConfiguration.getSize().equals(surfaceSize)) {
                    return true;
                }
                if (isFlexibleConsumer && streamConfiguration.getSize().getWidth() <= 1920) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOutputSupportedFor(Size size, int i) {
        StreamConfiguration[] streamConfigurationArr;
        int imageFormatToInternal = imageFormatToInternal(i);
        int imageFormatToDataspace = imageFormatToDataspace(i);
        if (imageFormatToDataspace == 4096) {
            streamConfigurationArr = this.mDepthConfigurations;
        } else if (imageFormatToDataspace == 4098) {
            streamConfigurationArr = this.mDynamicDepthConfigurations;
        } else if (imageFormatToDataspace == 4100) {
            streamConfigurationArr = this.mHeicConfigurations;
        } else if (imageFormatToDataspace == 4101) {
            streamConfigurationArr = this.mJpegRConfigurations;
        } else {
            streamConfigurationArr = this.mConfigurations;
        }
        if (Flags.cameraHeifGainmap() && imageFormatToDataspace == 4102) {
            streamConfigurationArr = this.mHeicUltraHDRConfigurations;
        }
        for (StreamConfiguration streamConfiguration : streamConfigurationArr) {
            if (streamConfiguration.getFormat() == imageFormatToInternal && streamConfiguration.isOutput() && streamConfiguration.getSize().equals(size)) {
                return true;
            }
        }
        return false;
    }

    public <T> Size[] getOutputSizes(Class<T> cls) {
        if (isOutputSupportedFor(cls)) {
            return getInternalFormatSizes(34, 0, true, false);
        }
        return null;
    }

    public Size[] getOutputSizes(int i) {
        return getPublicFormatSizes(i, true, false);
    }

    public Size[] getHighSpeedVideoSizes() {
        Set<Size> keySet = this.mHighSpeedVideoSizeMap.keySet();
        return (Size[]) keySet.toArray(new Size[keySet.size()]);
    }

    public Range<Integer>[] getHighSpeedVideoFpsRangesFor(Size size) {
        Integer num = this.mHighSpeedVideoSizeMap.get(size);
        if (num == null || num.intValue() == 0) {
            throw new IllegalArgumentException(String.format("Size %s does not support high speed video recording", size));
        }
        Range<Integer>[] rangeArr = new Range[num.intValue()];
        int i = 0;
        for (HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
            if (size.equals(highSpeedVideoConfiguration.getSize())) {
                rangeArr[i] = highSpeedVideoConfiguration.getFpsRange();
                i++;
            }
        }
        return rangeArr;
    }

    public Range<Integer>[] getHighSpeedVideoFpsRanges() {
        Set<Range<Integer>> keySet = this.mHighSpeedVideoFpsRangeMap.keySet();
        return (Range[]) keySet.toArray(new Range[keySet.size()]);
    }

    public Size[] getHighSpeedVideoSizesFor(Range<Integer> range) {
        Integer num = this.mHighSpeedVideoFpsRangeMap.get(range);
        if (num == null || num.intValue() == 0) {
            throw new IllegalArgumentException(String.format("FpsRange %s does not support high speed video recording", range));
        }
        Size[] sizeArr = new Size[num.intValue()];
        int i = 0;
        for (HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
            if (range.equals(highSpeedVideoConfiguration.getFpsRange())) {
                sizeArr[i] = highSpeedVideoConfiguration.getSize();
                i++;
            }
        }
        return sizeArr;
    }

    public Size[] getHighResolutionOutputSizes(int i) {
        if (this.mListHighResolution) {
            return getPublicFormatSizes(i, true, true);
        }
        return null;
    }

    public long getOutputMinFrameDuration(int i, Size size) {
        Objects.requireNonNull(size, "size must not be null");
        checkArgumentFormatSupported(i, true);
        return getInternalFormatDuration(imageFormatToInternal(i), imageFormatToDataspace(i), size, 0);
    }

    public <T> long getOutputMinFrameDuration(Class<T> cls, Size size) {
        if (!isOutputSupportedFor(cls)) {
            throw new IllegalArgumentException("klass was not supported");
        }
        return getInternalFormatDuration(34, 0, size, 0);
    }

    public long getOutputStallDuration(int i, Size size) {
        checkArgumentFormatSupported(i, true);
        return getInternalFormatDuration(imageFormatToInternal(i), imageFormatToDataspace(i), size, 1);
    }

    public <T> long getOutputStallDuration(Class<T> cls, Size size) {
        if (!isOutputSupportedFor(cls)) {
            throw new IllegalArgumentException("klass was not supported");
        }
        return getInternalFormatDuration(34, 0, size, 1);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof StreamConfigurationMap) {
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) obj;
            if (Arrays.equals(this.mConfigurations, streamConfigurationMap.mConfigurations) && Arrays.equals(this.mMinFrameDurations, streamConfigurationMap.mMinFrameDurations) && Arrays.equals(this.mStallDurations, streamConfigurationMap.mStallDurations) && Arrays.equals(this.mDepthConfigurations, streamConfigurationMap.mDepthConfigurations) && Arrays.equals(this.mDepthMinFrameDurations, streamConfigurationMap.mDepthMinFrameDurations) && Arrays.equals(this.mDepthStallDurations, streamConfigurationMap.mDepthStallDurations) && Arrays.equals(this.mDynamicDepthConfigurations, streamConfigurationMap.mDynamicDepthConfigurations) && Arrays.equals(this.mDynamicDepthMinFrameDurations, streamConfigurationMap.mDynamicDepthMinFrameDurations) && Arrays.equals(this.mDynamicDepthStallDurations, streamConfigurationMap.mDynamicDepthStallDurations) && Arrays.equals(this.mHeicConfigurations, streamConfigurationMap.mHeicConfigurations) && Arrays.equals(this.mHeicMinFrameDurations, streamConfigurationMap.mHeicMinFrameDurations) && Arrays.equals(this.mHeicStallDurations, streamConfigurationMap.mHeicStallDurations) && Arrays.equals(this.mHeicUltraHDRConfigurations, streamConfigurationMap.mHeicUltraHDRConfigurations) && Arrays.equals(this.mHeicUltraHDRMinFrameDurations, streamConfigurationMap.mHeicUltraHDRMinFrameDurations) && Arrays.equals(this.mHeicUltraHDRStallDurations, streamConfigurationMap.mHeicUltraHDRStallDurations) && Arrays.equals(this.mJpegRConfigurations, streamConfigurationMap.mJpegRConfigurations) && Arrays.equals(this.mJpegRMinFrameDurations, streamConfigurationMap.mJpegRMinFrameDurations) && Arrays.equals(this.mJpegRStallDurations, streamConfigurationMap.mJpegRStallDurations) && Arrays.equals(this.mHighSpeedVideoConfigurations, streamConfigurationMap.mHighSpeedVideoConfigurations)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mConfigurations, this.mMinFrameDurations, this.mStallDurations, this.mDepthConfigurations, this.mDepthMinFrameDurations, this.mDepthStallDurations, this.mDynamicDepthConfigurations, this.mDynamicDepthMinFrameDurations, this.mDynamicDepthStallDurations, this.mHeicConfigurations, this.mHeicMinFrameDurations, this.mHeicStallDurations, this.mHeicUltraHDRConfigurations, this.mHeicUltraHDRMinFrameDurations, this.mHeicUltraHDRStallDurations, this.mJpegRConfigurations, this.mJpegRMinFrameDurations, this.mJpegRStallDurations, this.mHighSpeedVideoConfigurations);
    }

    private int checkArgumentFormatSupported(int i, boolean z) {
        checkArgumentFormat(i);
        int imageFormatToInternal = imageFormatToInternal(i);
        int imageFormatToDataspace = imageFormatToDataspace(i);
        if (!z ? this.mInputFormats.indexOfKey(imageFormatToInternal) >= 0 : !(!(Flags.cameraHeifGainmap() && imageFormatToDataspace == 4102 && this.mHeicUltraHDROutputFormats.indexOfKey(imageFormatToInternal) >= 0) && (imageFormatToDataspace != 4096 ? imageFormatToDataspace != 4098 ? imageFormatToDataspace != 4100 ? imageFormatToDataspace != 4101 ? this.mAllOutputFormats.indexOfKey(imageFormatToInternal) < 0 : this.mJpegROutputFormats.indexOfKey(imageFormatToInternal) < 0 : this.mHeicOutputFormats.indexOfKey(imageFormatToInternal) < 0 : this.mDynamicDepthOutputFormats.indexOfKey(imageFormatToInternal) < 0 : this.mDepthOutputFormats.indexOfKey(imageFormatToInternal) < 0))) {
            throw new IllegalArgumentException(String.format("format %x is not supported by this stream configuration map", Integer.valueOf(i)));
        }
        return i;
    }

    static int checkArgumentFormatInternal(int i) {
        if (i != 33 && i != 34 && i != 36) {
            if (i != 256) {
                if (i != 540422489) {
                    if (i != 1212500294) {
                        return checkArgumentFormat(i);
                    }
                }
            }
            throw new IllegalArgumentException("An unknown internal format: " + i);
        }
        return i;
    }

    static int checkArgumentFormat(int i) {
        if (ImageFormat.isPublicFormat(i) || PixelFormat.isPublicFormat(i)) {
            return i;
        }
        throw new IllegalArgumentException(String.format("format 0x%x was not defined in either ImageFormat or PixelFormat", Integer.valueOf(i)));
    }

    public static int imageFormatToPublic(int i) {
        if (i == 33) {
            return 256;
        }
        if (i != 256) {
            return i;
        }
        throw new IllegalArgumentException("ImageFormat.JPEG is an unknown internal format");
    }

    public static int depthFormatToPublic(int i) {
        if (i == 37) {
            return 4099;
        }
        if (i == 256) {
            throw new IllegalArgumentException("ImageFormat.JPEG is an unknown internal format");
        }
        if (i == 540422489) {
            return ImageFormat.DEPTH16;
        }
        switch (i) {
            case 32:
                return 4098;
            case 33:
                return 257;
            case 34:
                throw new IllegalArgumentException("IMPLEMENTATION_DEFINED must not leak to public API");
            default:
                throw new IllegalArgumentException("Unknown DATASPACE_DEPTH format " + i);
        }
    }

    static int[] imageFormatToPublic(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = imageFormatToPublic(iArr[i]);
        }
        return iArr;
    }

    static int imageFormatToInternal(int i) {
        if ((!Flags.cameraHeifGainmap() || i != 4102) && i != 256 && i != 257) {
            if (i == 4098) {
                return 32;
            }
            if (i == 4099) {
                return 37;
            }
            if (i != 4101) {
                if (i == 1144402265) {
                    return 540422489;
                }
                if (i != 1212500294 && i != 1768253795) {
                    return i;
                }
            }
        }
        return 33;
    }

    static int imageFormatToDataspace(int i) {
        if (Flags.cameraHeifGainmap() && i == 4102) {
            return 4102;
        }
        if (i == 32) {
            return 1;
        }
        if (i == 4101) {
            return 4101;
        }
        if (i == 1144402265) {
            return 4096;
        }
        if (i == 1212500294) {
            return 4100;
        }
        if (i == 1768253795) {
            return 4098;
        }
        if (i != 256) {
            if (i == 257 || i == 4098 || i == 4099) {
                return 4096;
            }
            switch (i) {
                case 35:
                    break;
                case 36:
                case 37:
                case 38:
                    return 1;
                default:
                    return 0;
            }
        }
        return 146931712;
    }

    public static int[] imageFormatToInternal(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = imageFormatToInternal(iArr[i]);
        }
        return iArr;
    }

    private Size[] getPublicFormatSizes(int i, boolean z, boolean z2) {
        try {
            checkArgumentFormatSupported(i, z);
            return getInternalFormatSizes(imageFormatToInternal(i), imageFormatToDataspace(i), z, z2);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private Size[] getInternalFormatSizes(int i, int i2, boolean z, boolean z2) {
        SparseIntArray sparseIntArray;
        boolean z3;
        StreamConfiguration[] streamConfigurationArr;
        StreamConfigurationDuration[] streamConfigurationDurationArr;
        long j;
        StreamConfigurationMap streamConfigurationMap = this;
        int i3 = i;
        int i4 = 0;
        if (i2 == 4096 && z2) {
            return new Size[0];
        }
        if (!z) {
            sparseIntArray = streamConfigurationMap.mInputFormats;
        } else if (i2 == 4096) {
            sparseIntArray = streamConfigurationMap.mDepthOutputFormats;
        } else if (i2 == 4098) {
            sparseIntArray = streamConfigurationMap.mDynamicDepthOutputFormats;
        } else if (i2 == 4100) {
            sparseIntArray = streamConfigurationMap.mHeicOutputFormats;
        } else if (i2 == 4101) {
            sparseIntArray = streamConfigurationMap.mJpegROutputFormats;
        } else if (z2) {
            sparseIntArray = streamConfigurationMap.mHighResOutputFormats;
        } else {
            sparseIntArray = streamConfigurationMap.mOutputFormats;
        }
        if (Flags.cameraHeifGainmap() && i2 == 4102) {
            sparseIntArray = streamConfigurationMap.mHeicUltraHDROutputFormats;
            z3 = true;
        } else {
            z3 = false;
        }
        int i5 = sparseIntArray.get(i3);
        if ((!z || i2 == 4096 || i2 == 4101 || i2 == 4098 || i2 == 4100 || z3) && i5 == 0) {
            return null;
        }
        if (z && i2 != 4096 && i2 != 4101 && i2 != 4098 && !z3 && i2 != 4100 && streamConfigurationMap.mAllOutputFormats.get(i3) == 0) {
            return null;
        }
        Size[] sizeArr = new Size[i5];
        if (i2 == 4096) {
            streamConfigurationArr = streamConfigurationMap.mDepthConfigurations;
        } else if (i2 == 4098) {
            streamConfigurationArr = streamConfigurationMap.mDynamicDepthConfigurations;
        } else if (i2 == 4100) {
            streamConfigurationArr = streamConfigurationMap.mHeicConfigurations;
        } else if (i2 == 4101) {
            streamConfigurationArr = streamConfigurationMap.mJpegRConfigurations;
        } else if (z3) {
            streamConfigurationArr = streamConfigurationMap.mHeicUltraHDRConfigurations;
        } else {
            streamConfigurationArr = streamConfigurationMap.mConfigurations;
        }
        if (i2 == 4096) {
            streamConfigurationDurationArr = streamConfigurationMap.mDepthMinFrameDurations;
        } else if (i2 == 4098) {
            streamConfigurationDurationArr = streamConfigurationMap.mDynamicDepthMinFrameDurations;
        } else if (i2 == 4100) {
            streamConfigurationDurationArr = streamConfigurationMap.mHeicMinFrameDurations;
        } else if (i2 == 4101) {
            streamConfigurationDurationArr = streamConfigurationMap.mJpegRMinFrameDurations;
        } else if (z3) {
            streamConfigurationDurationArr = streamConfigurationMap.mHeicUltraHDRMinFrameDurations;
        } else {
            streamConfigurationDurationArr = streamConfigurationMap.mMinFrameDurations;
        }
        int length = streamConfigurationArr.length;
        int i6 = 0;
        while (i4 < length) {
            StreamConfiguration streamConfiguration = streamConfigurationArr[i4];
            int format = streamConfiguration.getFormat();
            if (format == i3 && streamConfiguration.isOutput() == z) {
                if (z && streamConfigurationMap.mListHighResolution) {
                    int i7 = 0;
                    while (true) {
                        if (i7 >= streamConfigurationDurationArr.length) {
                            j = 0;
                            break;
                        }
                        StreamConfigurationDuration streamConfigurationDuration = streamConfigurationDurationArr[i7];
                        if (streamConfigurationDuration.getFormat() == format && streamConfigurationDuration.getWidth() == streamConfiguration.getSize().getWidth() && streamConfigurationDuration.getHeight() == streamConfiguration.getSize().getHeight()) {
                            j = streamConfigurationDuration.getDuration();
                            break;
                        }
                        i7++;
                    }
                    if (i2 != 4096) {
                        if (z2 != (j > DURATION_20FPS_NS)) {
                        }
                    }
                }
                sizeArr[i6] = streamConfiguration.getSize();
                i6++;
            }
            i4++;
            streamConfigurationMap = this;
            i3 = i;
        }
        if ((i6 == i5 || (i2 != 4098 && i2 != 4100)) && i2 != 4101 && !z3) {
            if (i6 == i5) {
                return sizeArr;
            }
            throw new AssertionError("Too few sizes (expected " + i5 + ", actual " + i6 + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (i6 <= i5) {
            if (i6 <= 0) {
                return new Size[0];
            }
            return (Size[]) Arrays.copyOf(sizeArr, i6);
        }
        throw new AssertionError("Too many dynamic depth sizes (expected " + i5 + ", actual " + i6 + NavigationBarInflaterView.KEY_CODE_END);
    }

    private int[] getPublicFormats(boolean z) {
        int publicFormatCount = getPublicFormatCount(z);
        int[] iArr = new int[publicFormatCount];
        SparseIntArray formatsMap = getFormatsMap(z);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < formatsMap.size()) {
            iArr[i3] = imageFormatToPublic(formatsMap.keyAt(i2));
            i2++;
            i3++;
        }
        if (z) {
            while (i < this.mDepthOutputFormats.size()) {
                iArr[i3] = depthFormatToPublic(this.mDepthOutputFormats.keyAt(i));
                i++;
                i3++;
            }
            if (this.mDynamicDepthOutputFormats.size() > 0) {
                iArr[i3] = 1768253795;
                i3++;
            }
            if (this.mHeicOutputFormats.size() > 0) {
                iArr[i3] = 1212500294;
                i3++;
            }
            if (Flags.cameraHeifGainmap() && this.mHeicUltraHDROutputFormats.size() > 0) {
                iArr[i3] = 4102;
                i3++;
            }
            if (this.mJpegROutputFormats.size() > 0) {
                iArr[i3] = 4101;
                i3++;
            }
        }
        if (publicFormatCount == i3) {
            return iArr;
        }
        throw new AssertionError("Too few formats " + i3 + ", expected " + publicFormatCount);
    }

    private SparseIntArray getFormatsMap(boolean z) {
        return z ? this.mAllOutputFormats : this.mInputFormats;
    }

    private long getInternalFormatDuration(int i, int i2, Size size, int i3) {
        if (!isSupportedInternalConfiguration(i, i2, size)) {
            throw new IllegalArgumentException("size was not supported");
        }
        StreamConfigurationDuration[] durations = getDurations(i3, i2);
        for (StreamConfigurationDuration streamConfigurationDuration : durations) {
            if (streamConfigurationDuration.getFormat() == i && streamConfigurationDuration.getWidth() == size.getWidth() && streamConfigurationDuration.getHeight() == size.getHeight()) {
                return streamConfigurationDuration.getDuration();
            }
        }
        return 0L;
    }

    private StreamConfigurationDuration[] getDurations(int i, int i2) {
        boolean z = Flags.cameraHeifGainmap() && i2 == 4102;
        if (i == 0) {
            if (i2 == 4096) {
                return this.mDepthMinFrameDurations;
            }
            if (i2 == 4098) {
                return this.mDynamicDepthMinFrameDurations;
            }
            if (i2 == 4100) {
                return this.mHeicMinFrameDurations;
            }
            if (z) {
                return this.mHeicUltraHDRMinFrameDurations;
            }
            if (i2 == 4101) {
                return this.mJpegRMinFrameDurations;
            }
            return this.mMinFrameDurations;
        }
        if (i != 1) {
            throw new IllegalArgumentException("duration was invalid");
        }
        if (i2 == 4096) {
            return this.mDepthStallDurations;
        }
        if (i2 == 4098) {
            return this.mDynamicDepthStallDurations;
        }
        if (i2 == 4100) {
            return this.mHeicStallDurations;
        }
        if (z) {
            return this.mHeicUltraHDRStallDurations;
        }
        if (i2 == 4101) {
            return this.mJpegRStallDurations;
        }
        return this.mStallDurations;
    }

    private int getPublicFormatCount(boolean z) {
        int size = getFormatsMap(z).size();
        return z ? size + this.mDepthOutputFormats.size() + this.mDynamicDepthOutputFormats.size() + this.mHeicOutputFormats.size() + this.mJpegROutputFormats.size() + this.mHeicUltraHDROutputFormats.size() : size;
    }

    private static <T> boolean arrayContains(T[] tArr, T t) {
        if (tArr == null) {
            return false;
        }
        for (T t2 : tArr) {
            if (Objects.equals(t2, t)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSupportedInternalConfiguration(int i, int i2, Size size) {
        StreamConfiguration[] streamConfigurationArr;
        boolean z = Flags.cameraHeifGainmap() && i2 == 4102;
        if (i2 == 4096) {
            streamConfigurationArr = this.mDepthConfigurations;
        } else if (i2 == 4098) {
            streamConfigurationArr = this.mDynamicDepthConfigurations;
        } else if (i2 == 4100) {
            streamConfigurationArr = this.mHeicConfigurations;
        } else if (i2 == 4101) {
            streamConfigurationArr = this.mJpegRConfigurations;
        } else if (z) {
            streamConfigurationArr = this.mHeicUltraHDRConfigurations;
        } else {
            streamConfigurationArr = this.mConfigurations;
        }
        for (int i3 = 0; i3 < streamConfigurationArr.length; i3++) {
            if (streamConfigurationArr[i3].getFormat() == i && streamConfigurationArr[i3].getSize().equals(size)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StreamConfiguration(");
        appendOutputsString(sb);
        sb.append(", ");
        appendHighResOutputsString(sb);
        sb.append(", ");
        appendInputsString(sb);
        sb.append(", ");
        appendValidOutputFormatsForInputString(sb);
        sb.append(", ");
        appendHighSpeedVideoConfigurationsString(sb);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    private void appendOutputsString(StringBuilder sb) {
        sb.append("Outputs(");
        for (int i : getOutputFormats()) {
            for (Size size : getOutputSizes(i)) {
                sb.append(String.format("[w:%d, h:%d, format:%s(%d), min_duration:%d, stall:%d], ", Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), formatToString(i), Integer.valueOf(i), Long.valueOf(getOutputMinFrameDuration(i, size)), Long.valueOf(getOutputStallDuration(i, size))));
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendHighResOutputsString(StringBuilder sb) {
        sb.append("HighResolutionOutputs(");
        for (int i : getOutputFormats()) {
            Size[] highResolutionOutputSizes = getHighResolutionOutputSizes(i);
            if (highResolutionOutputSizes != null) {
                for (Size size : highResolutionOutputSizes) {
                    sb.append(String.format("[w:%d, h:%d, format:%s(%d), min_duration:%d, stall:%d], ", Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), formatToString(i), Integer.valueOf(i), Long.valueOf(getOutputMinFrameDuration(i, size)), Long.valueOf(getOutputStallDuration(i, size))));
                }
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendInputsString(StringBuilder sb) {
        sb.append("Inputs(");
        for (int i : getInputFormats()) {
            for (Size size : getInputSizes(i)) {
                sb.append(String.format("[w:%d, h:%d, format:%s(%d)], ", Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), formatToString(i), Integer.valueOf(i)));
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendValidOutputFormatsForInputString(StringBuilder sb) {
        sb.append("ValidOutputFormatsForInput(");
        for (int i : getInputFormats()) {
            sb.append(String.format("[in:%s(%d), out:", formatToString(i), Integer.valueOf(i)));
            int[] validOutputFormatsForInput = getValidOutputFormatsForInput(i);
            for (int i2 = 0; i2 < validOutputFormatsForInput.length; i2++) {
                sb.append(String.format("%s(%d)", formatToString(validOutputFormatsForInput[i2]), Integer.valueOf(validOutputFormatsForInput[i2])));
                if (i2 < validOutputFormatsForInput.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("], ");
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendHighSpeedVideoConfigurationsString(StringBuilder sb) {
        sb.append("HighSpeedVideoConfigurations(");
        for (Size size : getHighSpeedVideoSizes()) {
            for (Range<Integer> range : getHighSpeedVideoFpsRangesFor(size)) {
                sb.append(String.format("[w:%d, h:%d, min_fps:%d, max_fps:%d], ", Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), range.getLower(), range.getUpper()));
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
    }

    public static String formatToString(int i) {
        if (Flags.cameraHeifGainmap() && i == 4102) {
            return "HEIC_ULTRAHDR";
        }
        if (i == 1) {
            return "RGBA_8888";
        }
        if (i == 2) {
            return "RGBX_8888";
        }
        if (i == 3) {
            return "RGB_888";
        }
        if (i == 4) {
            return "RGB_565";
        }
        if (i == 16) {
            return "NV16";
        }
        if (i == 17) {
            return "NV21";
        }
        if (i == 256) {
            return "JPEG";
        }
        if (i == 257) {
            return "DEPTH_POINT_CLOUD";
        }
        if (i == 4098) {
            return "RAW_DEPTH";
        }
        if (i != 4099) {
            switch (i) {
                case 20:
                    return "YUY2";
                case 32:
                    return "RAW_SENSOR";
                case 4101:
                    return "JPEG/R";
                case 538982489:
                    return "Y8";
                case 540422489:
                    return "Y16";
                case 842094169:
                    return "YV12";
                case ImageFormat.DEPTH16 /* 1144402265 */:
                    return "DEPTH16";
                case ImageFormat.HEIC /* 1212500294 */:
                    return "HEIC";
                case ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    return "DEPTH_JPEG";
                default:
                    switch (i) {
                        case 34:
                            return "PRIVATE";
                        case 35:
                            return "YUV_420_888";
                        case 36:
                            return "RAW_PRIVATE";
                        case 37:
                            return "RAW10";
                        default:
                            return "UNKNOWN";
                    }
            }
        }
        return "RAW_DEPTH10";
    }
}
