package android.window;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Singleton;

/* loaded from: classes5.dex */
public class TransitionMetrics {
    private static final Singleton<TransitionMetrics> sTransitionMetrics = new Singleton<TransitionMetrics>() { // from class: android.window.TransitionMetrics.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public TransitionMetrics create() {
            return new TransitionMetrics(WindowOrganizer.getTransitionMetricsReporter());
        }
    };
    private final ITransitionMetricsReporter mTransitionMetricsReporter;

    private TransitionMetrics(ITransitionMetricsReporter iTransitionMetricsReporter) {
        this.mTransitionMetricsReporter = iTransitionMetricsReporter;
    }

    public void reportAnimationStart(IBinder iBinder) {
        try {
            this.mTransitionMetricsReporter.reportAnimationStart(iBinder, SystemClock.elapsedRealtime());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static TransitionMetrics getInstance() {
        return sTransitionMetrics.get();
    }
}
