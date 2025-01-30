package android.view;

import android.content.Context;
import com.android.internal.C4337R;
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
    public static final NativeAllocationRegistry REGISTRY =
        NativeAllocationRegistry.createMalloced(
            MotionPredictor.class.getClassLoader(),
            MotionPredictor.nativeGetNativeMotionPredictorFinalizer());

    private RegistryHolder() {}
  }

  public MotionPredictor(Context context) {
    this.mIsPredictionEnabled =
        context.getResources().getBoolean(C4337R.bool.config_enableMotionPrediction);
    int offsetNanos =
        context.getResources().getInteger(C4337R.integer.config_motionPredictionOffsetNanos);
    long nativeInitialize = nativeInitialize(offsetNanos);
    this.mPtr = nativeInitialize;
    RegistryHolder.REGISTRY.registerNativeAllocation(this, nativeInitialize);
  }

  public void record(MotionEvent event) {
    if (!this.mIsPredictionEnabled) {
      return;
    }
    nativeRecord(this.mPtr, event);
  }

  public MotionEvent predict(long predictionTimeNanos) {
    if (!this.mIsPredictionEnabled) {
      return null;
    }
    return nativePredict(this.mPtr, predictionTimeNanos);
  }

  public boolean isPredictionAvailable(int deviceId, int source) {
    return this.mIsPredictionEnabled && nativeIsPredictionAvailable(this.mPtr, deviceId, source);
  }
}
