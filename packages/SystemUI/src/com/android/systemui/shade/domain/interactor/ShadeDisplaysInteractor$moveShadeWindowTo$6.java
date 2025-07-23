package com.android.systemui.shade.domain.interactor;

import android.os.Trace;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.shade.ShadeDisplayChangeLatencyTracker;
import com.android.systemui.shade.ShadeTraceLogger;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeDisplaysInteractor$moveShadeWindowTo$6 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $destinationId;
    int label;
    final /* synthetic */ ShadeDisplaysInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$moveShadeWindowTo$6$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function1 {
        final /* synthetic */ int $destinationId;
        int label;
        final /* synthetic */ ShadeDisplaysInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShadeDisplaysInteractor shadeDisplaysInteractor, int i, Continuation continuation) {
            super(1, continuation);
            this.this$0 = shadeDisplaysInteractor;
            this.$destinationId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$destinationId, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            return ((AnonymousClass1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ShadeDisplaysInteractor shadeDisplaysInteractor = this.this$0;
                final int i2 = this.$destinationId;
                ?? r3 = new Function0() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ShadeDisplaysInteractor shadeDisplaysInteractor2 = ShadeDisplaysInteractor.this;
                        ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker = shadeDisplaysInteractor2.shadeDisplayChangeLatencyTracker;
                        int i3 = i2;
                        shadeDisplayChangeLatencyTracker.onShadeDisplayChanging(i3);
                        ShadeTraceLogger.INSTANCE.getClass();
                        TrackTracer trackTracer = ShadeTraceLogger.t;
                        trackTracer.getClass();
                        if (Trace.isEnabled()) {
                            ShadeDisplaysInteractor.Companion companion = ShadeDisplaysInteractor.Companion;
                            String m = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i3, "reparentToDisplayId(id=", ")");
                            boolean isEnabled = Trace.isEnabled();
                            if (isEnabled) {
                                TraceUtilsKt.beginSlice(m);
                            }
                            try {
                                long j = trackTracer.traceTag;
                                String str = trackTracer.trackName;
                                int nextInt = ThreadLocalRandom.current().nextInt();
                                Trace.asyncTraceForTrackBegin(j, str, m, nextInt);
                                try {
                                    shadeDisplaysInteractor2.shadeContext.reparentToDisplay(i3);
                                    Unit unit = Unit.INSTANCE;
                                } finally {
                                    Trace.asyncTraceForTrackEnd(j, str, nextInt);
                                }
                            } finally {
                                if (isEnabled) {
                                    TraceUtilsKt.endSlice();
                                }
                            }
                        } else {
                            shadeDisplaysInteractor2.shadeContext.reparentToDisplay(i3);
                            Unit unit2 = Unit.INSTANCE;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ShadeDisplaysInteractor.access$collapseAndExpandShadeIfNeeded(shadeDisplaysInteractor, i2, r3, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ShadeDisplaysInteractor shadeDisplaysInteractor2 = this.this$0;
            int i3 = this.$destinationId;
            if (shadeDisplaysInteractor2.shadeContext.getDisplayId() != i3) {
                Log.wtf("ShadeDisplaysInteractor", MutableVectorKt$$ExternalSyntheticOutline0.m(shadeDisplaysInteractor2.shadeContext.getDisplayId(), i3, "Shade context display id doesn't match the expected one after the move. actual=", " expected=", ". This means something wrong happened while trying to move the shade. Flag reparentWindowTokenApi=true"));
            }
            ((ShadeDisplaysRepositoryImpl) this.this$0.shadePositionRepository)._committedDisplayId.updateState(null, Integer.valueOf(this.$destinationId));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplaysInteractor$moveShadeWindowTo$6(ShadeDisplaysInteractor shadeDisplaysInteractor, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeDisplaysInteractor;
        this.$destinationId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeDisplaysInteractor$moveShadeWindowTo$6(this.this$0, this.$destinationId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeDisplaysInteractor$moveShadeWindowTo$6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ShadeTraceLogger shadeTraceLogger = ShadeTraceLogger.INSTANCE;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$destinationId, null);
            this.label = 1;
            if (shadeTraceLogger.traceReparenting(anonymousClass1, this) == coroutineSingletons) {
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
