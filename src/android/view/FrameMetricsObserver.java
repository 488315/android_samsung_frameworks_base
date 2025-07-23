package android.view;

import android.graphics.HardwareRendererObserver;
import android.os.Handler;
import android.view.Window;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class FrameMetricsObserver implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
    private final FrameMetrics mFrameMetrics;
    final Window.OnFrameMetricsAvailableListener mListener;
    private final HardwareRendererObserver mObserver;
    private final WeakReference<Window> mWindow;

    FrameMetricsObserver(Window window, Handler handler, Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        this.mWindow = new WeakReference<>(window);
        this.mListener = onFrameMetricsAvailableListener;
        FrameMetrics frameMetrics = new FrameMetrics();
        this.mFrameMetrics = frameMetrics;
        this.mObserver = new HardwareRendererObserver(this, frameMetrics.mTimingData, handler, false);
    }

    @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
    public void onFrameMetricsAvailable(int i) {
        if (this.mWindow.get() != null) {
            this.mListener.onFrameMetricsAvailable(this.mWindow.get(), this.mFrameMetrics, i);
        }
    }

    HardwareRendererObserver getRendererObserver() {
        return this.mObserver;
    }
}
