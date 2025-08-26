package com.android.systemui.shade.domain.interactor;

import android.os.Trace;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.shade.ShadeTraceLogger;
import com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.TimeoutKt;

/* loaded from: classes3.dex */
final class ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newDisplayId;
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ ShadeDisplaysInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2(int i, ShadeDisplaysInteractor shadeDisplaysInteractor, Continuation continuation) {
        super(2, continuation);
        this.$newDisplayId = i;
        this.this$0 = shadeDisplaysInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2(this.$newDisplayId, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x008b A[Catch: all -> 0x002f, TryCatch #1 {all -> 0x002f, blocks: (B:10:0x002b, B:21:0x0087, B:23:0x008b, B:24:0x0090), top: B:42:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c0  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        ShadeDisplaysInteractor shadeDisplaysInteractor;
        String str;
        long j;
        int i;
        ShadeDisplaysInteractor shadeDisplaysInteractor2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ShadeTraceLogger.INSTANCE.getClass();
            TrackTracer trackTracer = ShadeTraceLogger.t;
            int i3 = this.$newDisplayId;
            ShadeDisplaysInteractor shadeDisplaysInteractor3 = this.this$0;
            long j2 = trackTracer.traceTag;
            if (Trace.isEnabled()) {
                int i4 = TraceUtils.$r8$clinit;
                String strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i3, "waitForOnMovedToDisplayDispatchedToView(newDisplayId=", ")");
                int iNextInt = ThreadLocalRandom.current().nextInt();
                String str2 = trackTracer.trackName;
                Trace.asyncTraceForTrackBegin(j2, str2, strM, iNextInt);
                try {
                    ShadeDisplaysInteractor.Companion.getClass();
                    long j3 = ShadeDisplaysInteractor.TIMEOUT;
                    ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1 shadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1 = new ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1(shadeDisplaysInteractor3, i3, null);
                    this.L$0 = shadeDisplaysInteractor3;
                    this.L$1 = str2;
                    this.J$0 = j2;
                    this.I$0 = iNextInt;
                    this.label = 1;
                    Object objWithTimeoutOrNull = TimeoutKt.withTimeoutOrNull(DelayKt.m3470toDelayMillisLRDsOJo(j3), shadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1, this);
                    if (objWithTimeoutOrNull != coroutineSingletons) {
                        str = str2;
                        j = j2;
                        i = iNextInt;
                        obj = objWithTimeoutOrNull;
                        shadeDisplaysInteractor2 = shadeDisplaysInteractor3;
                        if (((Unit) obj) == null) {
                        }
                        Unit unit = Unit.INSTANCE;
                        Trace.asyncTraceForTrackEnd(j, str, i);
                    }
                } catch (Throwable th) {
                    th = th;
                    str = str2;
                    j = j2;
                    i = iNextInt;
                    Trace.asyncTraceForTrackEnd(j, str, i);
                    throw th;
                }
            } else {
                ShadeDisplaysInteractor.Companion.getClass();
                long j4 = ShadeDisplaysInteractor.TIMEOUT;
                ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1 shadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$12 = new ShadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$1(shadeDisplaysInteractor3, i3, null);
                this.L$0 = shadeDisplaysInteractor3;
                this.label = 2;
                obj = TimeoutKt.withTimeoutOrNull(DelayKt.m3470toDelayMillisLRDsOJo(j4), shadeDisplaysInteractor$waitForOnMovedToDisplayDispatchedToView$2$2$12, this);
                if (obj != coroutineSingletons) {
                    shadeDisplaysInteractor = shadeDisplaysInteractor3;
                    if (((Unit) obj) == null) {
                    }
                }
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            i = this.I$0;
            j = this.J$0;
            str = (String) this.L$1;
            shadeDisplaysInteractor2 = (ShadeDisplaysInteractor) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                if (((Unit) obj) == null) {
                    ShadeDisplaysInteractor.Companion companion = ShadeDisplaysInteractor.Companion;
                    shadeDisplaysInteractor2.errorLog("Timed out while waiting for onMovedToDisplay to be dispatched to the shade root view in ShadeDisplaysInteractor");
                }
                Unit unit2 = Unit.INSTANCE;
                Trace.asyncTraceForTrackEnd(j, str, i);
            } catch (Throwable th2) {
                th = th2;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            shadeDisplaysInteractor = (ShadeDisplaysInteractor) this.L$0;
            ResultKt.throwOnFailure(obj);
            if (((Unit) obj) == null) {
                ShadeDisplaysInteractor.Companion companion2 = ShadeDisplaysInteractor.Companion;
                shadeDisplaysInteractor.errorLog("Timed out while waiting for onMovedToDisplay to be dispatched to the shade root view in ShadeDisplaysInteractor");
            }
        }
        return Unit.INSTANCE;
    }
}
