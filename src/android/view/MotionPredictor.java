package android.view;

import android.content.Context;
import com.android.internal.R;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public final class MotionPredictor {
    private final boolean mIsPredictionEnabled;
    private final long mPtr;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetNativeMotionPredictorFinalizer();

    private static native long nativeInitialize(int i);

    private static native boolean nativeIsPredictionAvailable(long j, int i, int i2);

    private static native MotionEvent nativePredict(long j, long j2);

    private static native void nativeRecord(long j, MotionEvent motionEvent);

    private static class RegistryHolder {
        public static final NativeAllocationRegistry REGISTRY = NativeAllocationRegistry.createMalloced(MotionPredictor.class.getClassLoader(), MotionPredictor.nativeGetNativeMotionPredictorFinalizer());

        private RegistryHolder() {
        }
    }

    public MotionPredictor(Context context) {
        this(context.getResources().getBoolean(R.bool.config_enableMotionPrediction), context.getResources().getInteger(R.integer.config_motionPredictionOffsetNanos));
    }

    public MotionPredictor(boolean z, int i) {
        this.mIsPredictionEnabled = z;
        long jNativeInitialize = nativeInitialize(i);
        this.mPtr = jNativeInitialize;
        RegistryHolder.REGISTRY.registerNativeAllocation(this, jNativeInitialize);
    }

    public void record(MotionEvent motionEvent) {
        if (this.mIsPredictionEnabled) {
            nativeRecord(this.mPtr, motionEvent);
        }
    }

    public MotionEvent predict(long j) {
        if (this.mIsPredictionEnabled) {
            return nativePredict(this.mPtr, j);
        }
        return null;
    }

    public boolean isPredictionAvailable(int i, int i2) {
        return this.mIsPredictionEnabled && nativeIsPredictionAvailable(this.mPtr, i, i2);
    }
}
