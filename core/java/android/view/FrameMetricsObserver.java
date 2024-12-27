package android.view;

import android.graphics.HardwareRendererObserver;
import android.os.Handler;
import android.view.Window;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class FrameMetricsObserver implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
    private final FrameMetrics mFrameMetrics = new FrameMetrics();
    final Window.OnFrameMetricsAvailableListener mListener;
    private final HardwareRendererObserver mObserver;
    private final WeakReference<Window> mWindow;

    FrameMetricsObserver(Window window, Handler handler, Window.OnFrameMetricsAvailableListener listener) {
        this.mWindow = new WeakReference<>(window);
        this.mListener = listener;
        this.mObserver = new HardwareRendererObserver(this, this.mFrameMetrics.mTimingData, handler, false);
    }

    @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
    public void onFrameMetricsAvailable(int dropCountSinceLastInvocation) {
        if (this.mWindow.get() != null) {
            this.mListener.onFrameMetricsAvailable(this.mWindow.get(), this.mFrameMetrics, dropCountSinceLastInvocation);
        }
    }

    HardwareRendererObserver getRendererObserver() {
        return this.mObserver;
    }
}
