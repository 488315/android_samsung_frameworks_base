package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.CoreStartable;
import com.android.systemui.unfold.data.repository.FoldStateRepository;
import com.android.systemui.unfold.data.repository.FoldStateRepositoryImpl;
import com.android.systemui.unfold.system.DeviceStateRepository;
import com.android.systemui.unfold.system.DeviceStateRepositoryImpl;
import com.android.systemui.util.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes3.dex */
public final class UnfoldTraceLogger implements CoreStartable {
    public final ContextScope bgScope;
    public final DeviceStateRepository deviceStateRepository;
    public final FoldStateRepository foldStateRepository;
    public final boolean isFoldable;

    /* renamed from: com.android.systemui.unfold.UnfoldTraceLogger$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UnfoldTraceLogger.this.new AnonymousClass1(continuation);
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
                final TraceStateLogger traceStateLogger = new TraceStateLogger("FoldUpdate", false, false, false, 14, null);
                Flow foldUpdate = ((FoldStateRepositoryImpl) UnfoldTraceLogger.this.foldStateRepository).getFoldUpdate();
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.unfold.UnfoldTraceLogger.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        traceStateLogger.log(((FoldStateRepository.FoldUpdate) obj2).name());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (foldUpdate.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.unfold.UnfoldTraceLogger$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UnfoldTraceLogger.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow hingeAngle = ((FoldStateRepositoryImpl) UnfoldTraceLogger.this.foldStateRepository).getHingeAngle();
                AnonymousClass1 anonymousClass1 = new FlowCollector() { // from class: com.android.systemui.unfold.UnfoldTraceLogger.start.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        float fFloatValue = ((Number) obj2).floatValue();
                        TrackTracer.Companion.getClass();
                        TrackTracer.Companion.instantForGroup((int) fFloatValue, "unfold", "hingeAngle");
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (hingeAngle.collect(anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.unfold.UnfoldTraceLogger$start$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UnfoldTraceLogger.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final TraceStateLogger traceStateLogger = new TraceStateLogger("FoldedState", false, false, false, 14, null);
                Flow flowIsFolded = ((DeviceStateRepositoryImpl) UnfoldTraceLogger.this.deviceStateRepository).isFolded();
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.unfold.UnfoldTraceLogger.start.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        traceStateLogger.log(((Boolean) obj2).booleanValue() ? "folded" : "unfolded");
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowIsFolded.collect(flowCollector, this) == coroutineSingletons) {
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

    public UnfoldTraceLogger(Context context, FoldStateRepository foldStateRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext, DeviceStateRepository deviceStateRepository, DeviceStateManager deviceStateManager) {
        this.foldStateRepository = foldStateRepository;
        this.deviceStateRepository = deviceStateRepository;
        this.isFoldable = Utils.isDeviceFoldable(context.getResources(), deviceStateManager);
        this.bgScope = new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.isFoldable) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
            ContextScope contextScope = this.bgScope;
            CoroutineTracingKt.launchTraced$default(contextScope, null, null, anonymousClass1, 7);
            CoroutineTracingKt.launchTraced$default(contextScope, null, null, new AnonymousClass2(null), 7);
            CoroutineTracingKt.launchTraced$default(contextScope, null, null, new AnonymousClass3(null), 7);
        }
    }
}
