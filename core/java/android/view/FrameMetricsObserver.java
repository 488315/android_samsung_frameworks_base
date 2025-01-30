package android.view;

import android.graphics.HardwareRendererObserver;
import android.os.Handler;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class FrameMetricsObserver
    implements HardwareRendererObserver.OnFrameMetricsAvailableListener {
  private final FrameMetrics mFrameMetrics;
  final Window.OnFrameMetricsAvailableListener mListener;
  private final HardwareRendererObserver mObserver;
  private final WeakReference<Window> mWindow;

  FrameMetricsObserver(
      Window window, Handler handler, Window.OnFrameMetricsAvailableListener listener) {
    this.mWindow = new WeakReference<>(window);
    this.mListener = listener;
    FrameMetrics frameMetrics = new FrameMetrics();
    this.mFrameMetrics = frameMetrics;
    this.mObserver = new HardwareRendererObserver(this, frameMetrics.mTimingData, handler, false);
  }

  @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
  public void onFrameMetricsAvailable(int dropCountSinceLastInvocation) {
    if (this.mWindow.get() != null) {
      this.mListener.onFrameMetricsAvailable(
          this.mWindow.get(), this.mFrameMetrics, dropCountSinceLastInvocation);
    }
  }

  HardwareRendererObserver getRendererObserver() {
    return this.mObserver;
  }
}
