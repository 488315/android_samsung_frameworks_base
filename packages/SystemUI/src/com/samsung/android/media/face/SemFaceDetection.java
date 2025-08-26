package com.samsung.android.media.face;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SemFaceDetection {
    public final long[] mFdPointer = {0};

    public enum ProcessingMode {
        /* JADX INFO: Fake field, exist only in values array */
        HIGH_DETECTION_RATE_MODE(0),
        /* JADX INFO: Fake field, exist only in values array */
        FAST_SPEED_MODE(1);

        private int mValue;

        ProcessingMode(int i) {
            this.mValue = i;
        }
    }

    public class Settings {
        public Settings() {
            ProcessingMode[] processingModeArr = ProcessingMode.$VALUES;
        }
    }

    static {
        try {
            System.loadLibrary("FacePreProcessing_jni.camera.samsung");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static native int detectionCreateSupportMultiInstance(long[] jArr, Settings settings);

    private static native int detectionDestroySupportMultiInstance(long[] jArr);

    private static native int detectionFindFaceOnBitmapSupportMultiInstance(long[] jArr, Bitmap bitmap);

    private static native int detectionGetFacePoseSupportMultiInstance(long[] jArr, int i);

    private static native Rect detectionGetFaceRectSupportMultiInstance(long[] jArr, int i);

    public final void init() {
        long[] jArr = this.mFdPointer;
        if (jArr[0] != 0) {
            Log.d("SemFaceDetection", "function release() is processed before the second function call for init()");
            release();
        }
        detectionCreateSupportMultiInstance(jArr, null);
    }

    public final void release() {
        long[] jArr = this.mFdPointer;
        if (jArr == null) {
            throw new IllegalStateException("Improperly instantiated");
        }
        if (jArr[0] == 0) {
            Log.d("SemFaceDetection", "The function init() is not yet called");
        } else {
            detectionDestroySupportMultiInstance(jArr);
            jArr[0] = 0;
        }
    }

    public final int run(Bitmap bitmap, ArrayList arrayList) {
        long[] jArr = this.mFdPointer;
        if (jArr[0] == 0) {
            throw new IllegalStateException("The function init() is not yet called");
        }
        if (bitmap == null) {
            throw new IllegalArgumentException("Image data is null");
        }
        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            throw new IllegalArgumentException("Invalid dimension of image [" + bitmap.getWidth() + "x" + bitmap.getHeight() + "]");
        }
        arrayList.clear();
        int iDetectionFindFaceOnBitmapSupportMultiInstance = detectionFindFaceOnBitmapSupportMultiInstance(jArr, bitmap);
        if (iDetectionFindFaceOnBitmapSupportMultiInstance > 0) {
            for (int i = 0; i < iDetectionFindFaceOnBitmapSupportMultiInstance; i++) {
                arrayList.add(new SemFace(detectionGetFaceRectSupportMultiInstance(jArr, i), detectionGetFacePoseSupportMultiInstance(jArr, i)));
            }
        }
        return iDetectionFindFaceOnBitmapSupportMultiInstance;
    }
}
