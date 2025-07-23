package com.android.systemui.shade;

import android.content.res.Configuration;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TrackGroupUtils;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeStateTraceLogger$start$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ShadeStateTraceLogger this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeStateTraceLogger$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeStateTraceLogger this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShadeStateTraceLogger shadeStateTraceLogger, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeStateTraceLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
                ShadeStateTraceLogger shadeStateTraceLogger = this.this$0;
                int i2 = ShadeStateTraceLogger.$r8$clinit;
                shadeStateTraceLogger.getClass();
                final TraceStateLogger traceStateLogger = new TraceStateLogger(TrackGroupUtils.trackGroup("shade", "isShadeLayoutWide"), false, false, false, 14, null);
                ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) this.this$0.shadeModeInteractor).isShadeLayoutWide;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeStateTraceLogger.start.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        TraceStateLogger.this.log(String.valueOf(((Boolean) obj2).booleanValue()));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeStateTraceLogger$start$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeStateTraceLogger this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ShadeStateTraceLogger shadeStateTraceLogger, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeStateTraceLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
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
                ShadeStateTraceLogger shadeStateTraceLogger = this.this$0;
                int i2 = ShadeStateTraceLogger.$r8$clinit;
                shadeStateTraceLogger.getClass();
                final TraceStateLogger traceStateLogger = new TraceStateLogger(TrackGroupUtils.trackGroup("shade", "shadeMode"), false, false, false, 14, null);
                ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) this.this$0.shadeModeInteractor).shadeMode;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeStateTraceLogger.start.1.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        TraceStateLogger.this.log(((ShadeMode) obj2).toString());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeStateTraceLogger$start$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeStateTraceLogger this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ShadeStateTraceLogger shadeStateTraceLogger, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeStateTraceLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, continuation);
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
                StateFlow shadeExpansion = ((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.getShadeExpansion();
                AnonymousClass1 anonymousClass1 = new FlowCollector() { // from class: com.android.systemui.shade.ShadeStateTraceLogger.start.1.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        float floatValue = ((Number) obj2).floatValue();
                        TrackTracer.Companion.getClass();
                        TrackTracer.Companion.instantForGroup((int) (floatValue * 100), "shade", "shadeExpansion");
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (shadeExpansion.collect(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeStateTraceLogger$start$1$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeStateTraceLogger this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(ShadeStateTraceLogger shadeStateTraceLogger, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeStateTraceLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl = ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) this.this$0.shadeDisplaysRepository.get())).displayId;
                AnonymousClass1 anonymousClass1 = new FlowCollector() { // from class: com.android.systemui.shade.ShadeStateTraceLogger.start.1.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        int intValue = ((Number) obj2).intValue();
                        TrackTracer.Companion.getClass();
                        TrackTracer.Companion.instantForGroup(intValue, "shade", "displayId");
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowImpl.collect(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeStateTraceLogger$start$1$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeStateTraceLogger this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(ShadeStateTraceLogger shadeStateTraceLogger, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeStateTraceLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass5(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ShadeStateTraceLogger shadeStateTraceLogger = this.this$0;
                Flow flow = ((ConfigurationRepositoryImpl) shadeStateTraceLogger.configurationRepository).configurationValues;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeStateTraceLogger.start.1.5.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Configuration configuration = (Configuration) obj2;
                        TrackTracer.Companion companion = TrackTracer.Companion;
                        int i2 = configuration.smallestScreenWidthDp;
                        companion.getClass();
                        TrackTracer.Companion.instantForGroup(i2, "shade", "configurationChange#smallestScreenWidthDp");
                        LogBuffer logBuffer = ShadeStateTraceLogger.this.logBuffer;
                        LogMessage obtain = logBuffer.obtain("ShadeStateTraceLogger", LogLevel.DEBUG, new ShadeStateTraceLogger$start$1$5$1$$ExternalSyntheticLambda0(), null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.int1 = configuration.smallestScreenWidthDp;
                        logMessageImpl.int2 = configuration.densityDpi;
                        logBuffer.commit(obtain);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeStateTraceLogger$start$1(ShadeStateTraceLogger shadeStateTraceLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeStateTraceLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShadeStateTraceLogger$start$1 shadeStateTraceLogger$start$1 = new ShadeStateTraceLogger$start$1(this.this$0, continuation);
        shadeStateTraceLogger$start$1.L$0 = obj;
        return shadeStateTraceLogger$start$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeStateTraceLogger$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 3);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.this$0, null), 3);
        return Unit.INSTANCE;
    }
}
