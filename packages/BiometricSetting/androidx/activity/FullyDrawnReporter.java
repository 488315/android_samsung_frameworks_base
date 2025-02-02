package androidx.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: FullyDrawnReporter.kt */
/* loaded from: classes.dex */
public final class FullyDrawnReporter {
    private final Object lock = new Object();
    private final List<Function0<Unit>> onReportCallbacks = new ArrayList();
    private final Function0<Unit> reportFullyDrawn;
    private boolean reportedFullyDrawn;

    public FullyDrawnReporter(Executor executor, ComponentActivity$$ExternalSyntheticLambda1 componentActivity$$ExternalSyntheticLambda1) {
        this.reportFullyDrawn = componentActivity$$ExternalSyntheticLambda1;
    }

    public final void fullyDrawnReported() {
        synchronized (this.lock) {
            this.reportedFullyDrawn = true;
            Iterator<T> it = this.onReportCallbacks.iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
            ((ArrayList) this.onReportCallbacks).clear();
        }
    }

    public final boolean isFullyDrawnReported() {
        boolean z;
        synchronized (this.lock) {
            z = this.reportedFullyDrawn;
        }
        return z;
    }
}
