package android.util;

import android.graphics.Path;
import dalvik.annotation.optimization.FastNative;

/* loaded from: classes4.dex */
public class PathParser {
    static final String LOGTAG = "PathParser";

    @FastNative
    private static native boolean nCanMorph(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreateEmptyPathData();

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native long nCreatePathData(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nCreatePathDataFromString(String str, int i);

    @FastNative
    private static native void nCreatePathFromPathData(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nFinalize(long j);

    @FastNative
    private static native boolean nInterpolatePathData(long j, long j2, long j3, float f);

    private static native void nParseStringForPath(long j, String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nSetPathData(long j, long j2);

    public static Path createPathFromPathData(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Path string can not be null.");
        }
        Path path = new Path();
        nParseStringForPath(path.mNativePath, str, str.length());
        return path;
    }

    public static void createPathFromPathData(Path path, PathData pathData) {
        nCreatePathFromPathData(path.mNativePath, pathData.mNativePathData);
    }

    public static boolean canMorph(PathData pathData, PathData pathData2) {
        return nCanMorph(pathData.mNativePathData, pathData2.mNativePathData);
    }

    public static class PathData {
        long mNativePathData;

        public PathData() {
            this.mNativePathData = 0L;
            this.mNativePathData = PathParser.nCreateEmptyPathData();
        }

        public PathData(PathData pathData) {
            this.mNativePathData = 0L;
            this.mNativePathData = PathParser.nCreatePathData(pathData.mNativePathData);
        }

        public PathData(String str) {
            this.mNativePathData = 0L;
            long nCreatePathDataFromString = PathParser.nCreatePathDataFromString(str, str.length());
            this.mNativePathData = nCreatePathDataFromString;
            if (nCreatePathDataFromString != 0) {
                return;
            }
            throw new IllegalArgumentException("Invalid pathData: " + str);
        }

        public long getNativePtr() {
            return this.mNativePathData;
        }

        public void setPathData(PathData pathData) {
            PathParser.nSetPathData(this.mNativePathData, pathData.mNativePathData);
        }

        protected void finalize() throws Throwable {
            long j = this.mNativePathData;
            if (j != 0) {
                PathParser.nFinalize(j);
                this.mNativePathData = 0L;
            }
            super.finalize();
        }
    }

    public static boolean interpolatePathData(PathData pathData, PathData pathData2, PathData pathData3, float f) {
        return nInterpolatePathData(pathData.mNativePathData, pathData2.mNativePathData, pathData3.mNativePathData, f);
    }
}
