package com.android.systemui.shade;

import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.common.ui.view.ChoreographerUtils;
import com.android.systemui.scene.ui.view.WindowRootView;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class ShadeDisplayChangeLatencyTracker {
    public static final long TIMEOUT;
    public static final TrackTracer t;
    public final CoroutineScope bgScope;
    public final ChoreographerUtils choreographerUtils;
    public final LatencyTracker latencyTracker;
    public final ReadonlyStateFlow onMovedToDisplayFlow;
    public StandaloneCoroutine previousJob;
    public final WindowRootView shadeRootView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$onShadeDisplayChanging$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $displayId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, Continuation continuation) {
            super(2, continuation);
            this.$displayId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeDisplayChangeLatencyTracker.this.new AnonymousClass1(this.$displayId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker = ShadeDisplayChangeLatencyTracker.this;
                int i2 = this.$displayId;
                this.label = 1;
                if (ShadeDisplayChangeLatencyTracker.access$onShadeDisplayChangingAsync(shadeDisplayChangeLatencyTracker, i2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$1, reason: invalid class name and case insensitive filesystem */
    final class C10401 extends ContinuationImpl {
        int I$0;
        int I$1;
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10401(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker = ShadeDisplayChangeLatencyTracker.this;
            TrackTracer trackTracer = ShadeDisplayChangeLatencyTracker.t;
            return shadeDisplayChangeLatencyTracker.waitForOnMovedToDisplayDispatchedToView(0, this);
        }
    }

    /* renamed from: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1, reason: invalid class name and case insensitive filesystem */
    final class C10411 extends ContinuationImpl {
        int I$0;
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C10411(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker = ShadeDisplayChangeLatencyTracker.this;
            TrackTracer trackTracer = ShadeDisplayChangeLatencyTracker.t;
            return shadeDisplayChangeLatencyTracker.waitUntilNextDoFrameDone(this);
        }
    }

    static {
        new Companion(null);
        t = new TrackTracer("ShadeDisplayLatency", 0L, "shade", 2, null);
        Duration.Companion companion = Duration.Companion;
        TIMEOUT = DurationKt.toDuration(3, DurationUnit.SECONDS);
    }

    public ShadeDisplayChangeLatencyTracker(WindowRootView windowRootView, ConfigurationRepository configurationRepository, LatencyTracker latencyTracker, CoroutineScope coroutineScope, ChoreographerUtils choreographerUtils) {
        this.shadeRootView = windowRootView;
        this.latencyTracker = latencyTracker;
        this.bgScope = coroutineScope;
        this.choreographerUtils = choreographerUtils;
        this.onMovedToDisplayFlow = ((ConfigurationRepositoryImpl) configurationRepository).onMovedToDisplay;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0068, code lost:
    
        if (r6.waitUntilNextDoFrameDone(r0) == r1) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$onShadeDisplayChangingAsync(ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, int i, ContinuationImpl continuationImpl) {
        ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1 shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1;
        shadeDisplayChangeLatencyTracker.getClass();
        if (continuationImpl instanceof ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1) {
            shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1 = (ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1) continuationImpl;
            int i2 = shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.label = i2 - Integer.MIN_VALUE;
            } else {
                shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1 = new ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1(shadeDisplayChangeLatencyTracker, continuationImpl);
            }
        }
        Object obj = shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.label;
        try {
        } catch (Exception e) {
            Log.e("ShadeDisplayLatency", e instanceof CancellationException ? BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(i, "Shade move to ", " cancelled as a new move is being done before the previous one finished. Message: ", e.getMessage()) : "Shade move cancelled.", e);
            shadeDisplayChangeLatencyTracker.latencyTracker.onActionCancel(29);
        }
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            shadeDisplayChangeLatencyTracker.latencyTracker.onActionStart(29);
            shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.L$0 = shadeDisplayChangeLatencyTracker;
            shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.I$0 = i;
            shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.label = 1;
            if (shadeDisplayChangeLatencyTracker.waitForOnMovedToDisplayDispatchedToView(i, shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1) == coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.I$0;
            shadeDisplayChangeLatencyTracker = (ShadeDisplayChangeLatencyTracker) shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.L$0;
            ResultKt.throwOnFailure(obj);
            shadeDisplayChangeLatencyTracker.latencyTracker.onActionEnd(29);
            return Unit.INSTANCE;
        }
        i = shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.I$0;
        shadeDisplayChangeLatencyTracker = (ShadeDisplayChangeLatencyTracker) shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.L$0;
        ResultKt.throwOnFailure(obj);
        shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.L$0 = shadeDisplayChangeLatencyTracker;
        shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.I$0 = i;
        shadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1.label = 2;
    }

    public final synchronized void onShadeDisplayChanging(int i) {
        try {
            StandaloneCoroutine standaloneCoroutine = this.previousJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancelInternal(new CancellationException("New shade move in progress to " + i));
            }
            this.previousJob = BuildersKt.launch$default(this.bgScope, null, null, new AnonymousClass1(i, null), 3);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00be, code lost:
    
        if (kotlinx.coroutines.TimeoutKt.m3471withTimeoutKLykuaI(r9, r14, r0) == r1) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008e A[Catch: all -> 0x0047, TryCatch #0 {all -> 0x0047, blocks: (B:16:0x0043, B:27:0x0088, B:29:0x008e, B:30:0x00a1), top: B:45:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitForOnMovedToDisplayDispatchedToView(int i, ContinuationImpl continuationImpl) throws Throwable {
        C10401 c10401;
        int i2;
        long j;
        String str;
        if (continuationImpl instanceof C10401) {
            c10401 = (C10401) continuationImpl;
            int i3 = c10401.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c10401.label = i3 - Integer.MIN_VALUE;
            } else {
                c10401 = new C10401(continuationImpl);
            }
        }
        Object obj = c10401.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = c10401.label;
        TrackTracer trackTracer = t;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            long j2 = trackTracer.traceTag;
            boolean zIsEnabled = Trace.isEnabled();
            long j3 = TIMEOUT;
            if (zIsEnabled) {
                int i5 = TraceUtils.$r8$clinit;
                String strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "waitForOnMovedToDisplayDispatchedToView(newDisplayId=", ")");
                int iNextInt = ThreadLocalRandom.current().nextInt();
                String str2 = trackTracer.trackName;
                Trace.asyncTraceForTrackBegin(j2, str2, strM, iNextInt);
                try {
                    ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1 shadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1 = new ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1(this, i, null);
                    c10401.L$0 = str2;
                    c10401.I$0 = i;
                    c10401.J$0 = j2;
                    c10401.I$1 = iNextInt;
                    c10401.label = 1;
                    if (TimeoutKt.m3471withTimeoutKLykuaI(j3, shadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1, c10401) != coroutineSingletons) {
                        i2 = iNextInt;
                        j = j2;
                        str = str2;
                        if (Trace.isEnabled()) {
                        }
                        Unit unit = Unit.INSTANCE;
                        Trace.asyncTraceForTrackEnd(j, str, i2);
                    }
                } catch (Throwable th) {
                    th = th;
                    i2 = iNextInt;
                    j = j2;
                    str = str2;
                    Trace.asyncTraceForTrackEnd(j, str, i2);
                    throw th;
                }
            } else {
                ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1 shadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$12 = new ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1(this, i, null);
                c10401.I$0 = i;
                c10401.label = 2;
            }
            return coroutineSingletons;
        }
        if (i4 == 1) {
            i2 = c10401.I$1;
            j = c10401.J$0;
            i = c10401.I$0;
            str = (String) c10401.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                if (Trace.isEnabled()) {
                    Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, "onMovedToDisplay received with " + i);
                }
                Unit unit2 = Unit.INSTANCE;
                Trace.asyncTraceForTrackEnd(j, str, i2);
            } catch (Throwable th2) {
                th = th2;
                Trace.asyncTraceForTrackEnd(j, str, i2);
                throw th;
            }
        } else {
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c10401.I$0;
            ResultKt.throwOnFailure(obj);
            if (Trace.isEnabled()) {
                Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onMovedToDisplay received with "));
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitUntilNextDoFrameDone(ContinuationImpl continuationImpl) throws Throwable {
        C10411 c10411;
        String str;
        Throwable th;
        int i;
        long j;
        if (continuationImpl instanceof C10411) {
            c10411 = (C10411) continuationImpl;
            int i2 = c10411.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c10411.label = i2 - Integer.MIN_VALUE;
            } else {
                c10411 = new C10411(continuationImpl);
            }
        }
        Object obj = c10411.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c10411.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            TrackTracer trackTracer = t;
            long j2 = trackTracer.traceTag;
            int iNextInt = ThreadLocalRandom.current().nextInt();
            String str2 = trackTracer.trackName;
            Trace.asyncTraceForTrackBegin(j2, str2, "waitUntilNextDoFrameDone", iNextInt);
            try {
                long j3 = TIMEOUT;
                ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1 shadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1 = new ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1(this, null);
                c10411.L$0 = str2;
                c10411.J$0 = j2;
                c10411.I$0 = iNextInt;
                c10411.label = 1;
                if (TimeoutKt.m3471withTimeoutKLykuaI(j3, shadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1, c10411) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                str = str2;
                i = iNextInt;
                j = j2;
            } catch (Throwable th2) {
                str = str2;
                th = th2;
                i = iNextInt;
                j = j2;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c10411.I$0;
            j = c10411.J$0;
            str = (String) c10411.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Unit unit = Unit.INSTANCE;
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
    }
}
