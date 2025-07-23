package android.hardware.camera2.params;

import android.util.ArraySet;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes2.dex */
public final class RecommendedStreamConfigurationMap {
    public static final int MAX_USECASE_COUNT = 32;
    private static final String TAG = "RecommendedStreamConfigurationMap";
    public static final int USECASE_10BIT_OUTPUT = 8;
    public static final int USECASE_LOW_LATENCY_SNAPSHOT = 6;
    public static final int USECASE_PREVIEW = 0;
    public static final int USECASE_RAW = 5;
    public static final int USECASE_RECORD = 1;
    public static final int USECASE_SNAPSHOT = 3;
    public static final int USECASE_VENDOR_START = 24;
    public static final int USECASE_VIDEO_SNAPSHOT = 2;
    public static final int USECASE_ZSL = 4;
    private StreamConfigurationMap mRecommendedMap;
    private boolean mSupportsPrivate;
    private int mUsecase;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecommendedUsecase {
    }

    public RecommendedStreamConfigurationMap(StreamConfigurationMap streamConfigurationMap, int i, boolean z) {
        this.mRecommendedMap = streamConfigurationMap;
        this.mUsecase = i;
        this.mSupportsPrivate = z;
    }

    public int getRecommendedUseCase() {
        return this.mUsecase;
    }

    private Set<Integer> getUnmodifiableIntegerSet(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return null;
        }
        ArraySet arraySet = new ArraySet();
        arraySet.ensureCapacity(iArr.length);
        for (int i : iArr) {
            arraySet.add(Integer.valueOf(i));
        }
        return Collections.unmodifiableSet(arraySet);
    }

    public Set<Integer> getOutputFormats() {
        return getUnmodifiableIntegerSet(this.mRecommendedMap.getOutputFormats());
    }

    public Set<Integer> getValidOutputFormatsForInput(int i) {
        return getUnmodifiableIntegerSet(this.mRecommendedMap.getValidOutputFormatsForInput(i));
    }

    public Set<Integer> getInputFormats() {
        return getUnmodifiableIntegerSet(this.mRecommendedMap.getInputFormats());
    }

    private Set<Size> getUnmodifiableSizeSet(Size[] sizeArr) {
        if (sizeArr == null || sizeArr.length <= 0) {
            return null;
        }
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(Arrays.asList(sizeArr));
        return Collections.unmodifiableSet(arraySet);
    }

    public Set<Size> getInputSizes(int i) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getInputSizes(i));
    }

    public boolean isOutputSupportedFor(int i) {
        return this.mRecommendedMap.isOutputSupportedFor(i);
    }

    public Set<Size> getOutputSizes(int i) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getOutputSizes(i));
    }

    public Set<Size> getHighSpeedVideoSizes() {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getHighSpeedVideoSizes());
    }

    private Set<Range<Integer>> getUnmodifiableRangeSet(Range<Integer>[] rangeArr) {
        if (rangeArr == null || rangeArr.length <= 0) {
            return null;
        }
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(Arrays.asList(rangeArr));
        return Collections.unmodifiableSet(arraySet);
    }

    public Set<Range<Integer>> getHighSpeedVideoFpsRangesFor(Size size) {
        return getUnmodifiableRangeSet(this.mRecommendedMap.getHighSpeedVideoFpsRangesFor(size));
    }

    public Set<Range<Integer>> getHighSpeedVideoFpsRanges() {
        return getUnmodifiableRangeSet(this.mRecommendedMap.getHighSpeedVideoFpsRanges());
    }

    public Set<Size> getHighSpeedVideoSizesFor(Range<Integer> range) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getHighSpeedVideoSizesFor(range));
    }

    public Set<Size> getHighResolutionOutputSizes(int i) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getHighResolutionOutputSizes(i));
    }

    public long getOutputMinFrameDuration(int i, Size size) {
        return this.mRecommendedMap.getOutputMinFrameDuration(i, size);
    }

    public long getOutputStallDuration(int i, Size size) {
        return this.mRecommendedMap.getOutputStallDuration(i, size);
    }

    public <T> Set<Size> getOutputSizes(Class<T> cls) {
        if (this.mSupportsPrivate) {
            return getUnmodifiableSizeSet(this.mRecommendedMap.getOutputSizes(cls));
        }
        return null;
    }

    public <T> long getOutputMinFrameDuration(Class<T> cls, Size size) {
        if (this.mSupportsPrivate) {
            return this.mRecommendedMap.getOutputMinFrameDuration(cls, size);
        }
        return 0L;
    }

    public <T> long getOutputStallDuration(Class<T> cls, Size size) {
        if (this.mSupportsPrivate) {
            return this.mRecommendedMap.getOutputStallDuration(cls, size);
        }
        return 0L;
    }

    public boolean isOutputSupportedFor(Surface surface) {
        return this.mRecommendedMap.isOutputSupportedFor(surface);
    }
}
