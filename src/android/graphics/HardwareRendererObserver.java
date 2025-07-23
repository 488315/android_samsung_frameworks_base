package android.graphics;

import android.os.Handler;
import com.android.internal.util.VirtualRefBasePtr;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class HardwareRendererObserver {
    private final long[] mFrameMetrics;
    private final Handler mHandler;
    private final OnFrameMetricsAvailableListener mListener;
    private VirtualRefBasePtr mNativePtr;

    public interface OnFrameMetricsAvailableListener {
        void onFrameMetricsAvailable(int i);
    }

    private static native long nCreateObserver(WeakReference<HardwareRendererObserver> weakReference, boolean z);

    private static native int nGetNextBuffer(long j, long[] jArr);

    public HardwareRendererObserver(OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, long[] jArr, Handler handler, boolean z) {
        if (handler == null || handler.getLooper() == null) {
            throw new NullPointerException("handler and its looper cannot be null");
        }
        if (handler.getLooper().getQueue() == null) {
            throw new IllegalStateException("invalid looper, null message queue\n");
        }
        this.mFrameMetrics = jArr;
        this.mHandler = handler;
        this.mListener = onFrameMetricsAvailableListener;
        this.mNativePtr = new VirtualRefBasePtr(nCreateObserver(new WeakReference(this), z));
    }

    long getNativeInstance() {
        return this.mNativePtr.get();
    }

    private void notifyDataAvailable() {
        this.mHandler.post(new Runnable() { // from class: android.graphics.HardwareRendererObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HardwareRendererObserver.this.lambda$notifyDataAvailable$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyDataAvailable$0() {
        boolean z = true;
        while (z) {
            int nGetNextBuffer = nGetNextBuffer(this.mNativePtr.get(), this.mFrameMetrics);
            if (nGetNextBuffer >= 0) {
                this.mListener.onFrameMetricsAvailable(nGetNextBuffer);
            } else {
                z = false;
            }
        }
    }

    static boolean invokeDataAvailable(WeakReference<HardwareRendererObserver> weakReference) {
        HardwareRendererObserver hardwareRendererObserver = weakReference.get();
        if (hardwareRendererObserver == null) {
            return false;
        }
        hardwareRendererObserver.notifyDataAvailable();
        return true;
    }
}
