package android.hardware.camera2.utils;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.system.OsConstants;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SurfaceUtils {
    private static final int BAD_VALUE = -OsConstants.EINVAL;
    private static final int BGRA_8888 = 5;
    private static final int USAGE_HW_COMPOSER = 2048;
    private static final int USAGE_RENDERSCRIPT = 1048576;

    private static native int nativeDetectSurfaceDataspace(Surface surface);

    private static native int nativeDetectSurfaceDimens(Surface surface, int[] iArr);

    private static native int nativeDetectSurfaceType(Surface surface);

    private static native long nativeDetectSurfaceUsageFlags(Surface surface);

    private static native long nativeGetSurfaceId(Surface surface);

    public static boolean isSurfaceForPreview(Surface surface) {
        Preconditions.checkNotNull(surface);
        long jNativeDetectSurfaceUsageFlags = nativeDetectSurfaceUsageFlags(surface);
        boolean z = (1114115 & jNativeDetectSurfaceUsageFlags) == 0 && (jNativeDetectSurfaceUsageFlags & 2816) != 0;
        getSurfaceFormat(surface);
        return z;
    }

    public static boolean isSurfaceForHwVideoEncoder(Surface surface) {
        Preconditions.checkNotNull(surface);
        long jNativeDetectSurfaceUsageFlags = nativeDetectSurfaceUsageFlags(surface);
        boolean z = (1050627 & jNativeDetectSurfaceUsageFlags) == 0 && (jNativeDetectSurfaceUsageFlags & 65536) != 0;
        getSurfaceFormat(surface);
        return z;
    }

    public static long getSurfaceId(Surface surface) {
        Preconditions.checkNotNull(surface);
        try {
            return nativeGetSurfaceId(surface);
        } catch (IllegalArgumentException unused) {
            return 0L;
        }
    }

    public static long getSurfaceUsage(Surface surface) {
        Preconditions.checkNotNull(surface);
        try {
            return nativeDetectSurfaceUsageFlags(surface);
        } catch (IllegalArgumentException unused) {
            return 0L;
        }
    }

    public static Size getSurfaceSize(Surface surface) {
        Preconditions.checkNotNull(surface);
        int[] iArr = new int[2];
        if (nativeDetectSurfaceDimens(surface, iArr) == BAD_VALUE) {
            throw new IllegalArgumentException("Surface was abandoned");
        }
        return new Size(iArr[0], iArr[1]);
    }

    public static int getSurfaceFormat(Surface surface) {
        Preconditions.checkNotNull(surface);
        int iNativeDetectSurfaceType = nativeDetectSurfaceType(surface);
        if (iNativeDetectSurfaceType == BAD_VALUE) {
            throw new IllegalArgumentException("Surface was abandoned");
        }
        if (iNativeDetectSurfaceType < 1 || iNativeDetectSurfaceType > 5) {
            return iNativeDetectSurfaceType;
        }
        return 34;
    }

    public static int detectSurfaceFormat(Surface surface) {
        Preconditions.checkNotNull(surface);
        int iNativeDetectSurfaceType = nativeDetectSurfaceType(surface);
        if (iNativeDetectSurfaceType != BAD_VALUE) {
            return iNativeDetectSurfaceType;
        }
        throw new IllegalArgumentException("Surface was abandoned");
    }

    public static int getSurfaceDataspace(Surface surface) {
        Preconditions.checkNotNull(surface);
        int iNativeDetectSurfaceDataspace = nativeDetectSurfaceDataspace(surface);
        if (iNativeDetectSurfaceDataspace != BAD_VALUE) {
            return iNativeDetectSurfaceDataspace;
        }
        throw new IllegalArgumentException("Surface was abandoned");
    }

    public static boolean isFlexibleConsumer(Surface surface) {
        Preconditions.checkNotNull(surface);
        long jNativeDetectSurfaceUsageFlags = nativeDetectSurfaceUsageFlags(surface);
        return (1114112 & jNativeDetectSurfaceUsageFlags) == 0 && (jNativeDetectSurfaceUsageFlags & 2307) != 0;
    }

    private static void checkHighSpeedSurfaceFormat(Surface surface) {
        int surfaceFormat = getSurfaceFormat(surface);
        if (surfaceFormat == 34) {
            return;
        }
        throw new IllegalArgumentException("Surface format(" + surfaceFormat + ") is not for preview or hardware video encoding!");
    }

    public static void checkConstrainedHighSpeedSurfaces(Collection<Surface> collection, Range<Integer> range, StreamConfigurationMap streamConfigurationMap) {
        List listAsList;
        if (collection == null || collection.size() == 0 || collection.size() > 2) {
            throw new IllegalArgumentException("Output target surface list must not be null and the size must be 1 or 2");
        }
        if (range == null) {
            listAsList = Arrays.asList(streamConfigurationMap.getHighSpeedVideoSizes());
        } else {
            Range<Integer>[] highSpeedVideoFpsRanges = streamConfigurationMap.getHighSpeedVideoFpsRanges();
            if (!Arrays.asList(highSpeedVideoFpsRanges).contains(range)) {
                throw new IllegalArgumentException("Fps range " + range.toString() + " in the request is not a supported high speed fps range " + Arrays.toString(highSpeedVideoFpsRanges));
            }
            listAsList = Arrays.asList(streamConfigurationMap.getHighSpeedVideoSizesFor(range));
        }
        for (Surface surface : collection) {
            checkHighSpeedSurfaceFormat(surface);
            Size surfaceSize = getSurfaceSize(surface);
            if (!listAsList.contains(surfaceSize)) {
                throw new IllegalArgumentException("Surface size " + surfaceSize.toString() + " is not part of the high speed supported size list " + Arrays.toString(listAsList.toArray()));
            }
            if (!isSurfaceForPreview(surface) && !isSurfaceForHwVideoEncoder(surface)) {
                throw new IllegalArgumentException("This output surface is neither preview nor hardware video encoding surface");
            }
            if (isSurfaceForPreview(surface) && isSurfaceForHwVideoEncoder(surface)) {
                throw new IllegalArgumentException("This output surface can not be both preview and hardware video encoding surface");
            }
        }
        if (collection.size() == 2) {
            Iterator<Surface> it = collection.iterator();
            if (isSurfaceForPreview(it.next()) == isSurfaceForPreview(it.next())) {
                throw new IllegalArgumentException("The 2 output surfaces must have different type");
            }
        }
    }
}
