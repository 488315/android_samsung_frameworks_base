package androidx.compose.foundation.lazy.layout;

import android.view.Choreographer;
import android.view.View;
import androidx.compose.foundation.lazy.layout.PrefetchHandleProvider;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.collection.MutableVector;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidPrefetchScheduler implements PrefetchScheduler, RememberObserver, Runnable, Choreographer.FrameCallback {
    public static final Companion Companion = new Companion(null);
    public static long frameIntervalNs;
    public long frameStartTimeNanos;
    public boolean isActive;
    public boolean prefetchScheduled;
    public final View view;
    public final MutableVector prefetchRequests = new MutableVector(new PrefetchRequest[16], 0);
    public final Choreographer choreographer = Choreographer.getInstance();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PrefetchRequestScopeImpl {
        public final long nextFrameTimeNs;

        public PrefetchRequestScopeImpl(long j) {
            this.nextFrameTimeNs = j;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0038, code lost:
    
        if (r4 >= 30.0f) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AndroidPrefetchScheduler(android.view.View r5) {
        /*
            r4 = this;
            r4.<init>()
            r4.view = r5
            androidx.compose.runtime.collection.MutableVector r0 = new androidx.compose.runtime.collection.MutableVector
            r1 = 16
            androidx.compose.foundation.lazy.layout.PrefetchRequest[] r1 = new androidx.compose.foundation.lazy.layout.PrefetchRequest[r1]
            r2 = 0
            r0.<init>(r1, r2)
            r4.prefetchRequests = r0
            android.view.Choreographer r0 = android.view.Choreographer.getInstance()
            r4.choreographer = r0
            androidx.compose.foundation.lazy.layout.AndroidPrefetchScheduler$Companion r4 = androidx.compose.foundation.lazy.layout.AndroidPrefetchScheduler.Companion
            r4.getClass()
            long r0 = androidx.compose.foundation.lazy.layout.AndroidPrefetchScheduler.frameIntervalNs
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L45
            android.view.Display r4 = r5.getDisplay()
            boolean r5 = r5.isInEditMode()
            if (r5 != 0) goto L3b
            if (r4 == 0) goto L3b
            float r4 = r4.getRefreshRate()
            r5 = 1106247680(0x41f00000, float:30.0)
            int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r5 < 0) goto L3b
            goto L3d
        L3b:
            r4 = 1114636288(0x42700000, float:60.0)
        L3d:
            r5 = 1000000000(0x3b9aca00, float:0.0047237873)
            float r5 = (float) r5
            float r5 = r5 / r4
            long r4 = (long) r5
            androidx.compose.foundation.lazy.layout.AndroidPrefetchScheduler.frameIntervalNs = r4
        L45:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.AndroidPrefetchScheduler.<init>(android.view.View):void");
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        if (this.isActive) {
            this.frameStartTimeNanos = j;
            this.view.post(this);
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        this.isActive = false;
        this.view.removeCallbacks(this);
        this.choreographer.removeFrameCallback(this);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
        this.isActive = true;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.prefetchRequests.size == 0 || !this.prefetchScheduled || !this.isActive || this.view.getWindowVisibility() != 0) {
            this.prefetchScheduled = false;
            return;
        }
        PrefetchRequestScopeImpl prefetchRequestScopeImpl = new PrefetchRequestScopeImpl(Math.max(this.frameStartTimeNanos, TimeUnit.MILLISECONDS.toNanos(this.view.getDrawingTime())) + frameIntervalNs);
        boolean z = false;
        while (this.prefetchRequests.size != 0 && !z) {
            if (Math.max(0L, prefetchRequestScopeImpl.nextFrameTimeNs - System.nanoTime()) <= 0 || ((PrefetchHandleProvider.HandleAndRequestImpl) ((PrefetchRequest) this.prefetchRequests.content[0])).execute(prefetchRequestScopeImpl)) {
                z = true;
            } else {
                this.prefetchRequests.removeAt(0);
            }
        }
        if (z) {
            this.choreographer.postFrameCallback(this);
        } else {
            this.prefetchScheduled = false;
        }
    }

    @Override // androidx.compose.foundation.lazy.layout.PrefetchScheduler
    public final void schedulePrefetch(PrefetchRequest prefetchRequest) {
        this.prefetchRequests.add(prefetchRequest);
        if (this.prefetchScheduled) {
            return;
        }
        this.prefetchScheduled = true;
        this.view.post(this);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
    }
}
