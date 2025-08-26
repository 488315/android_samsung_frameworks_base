package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$waitForTransitionStart$$inlined$filter$1;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.time.SystemClock;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class NoCooldownDisplaySwitchLatencyTracker implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long SCREEN_EVENT_TIMEOUT;
    public final AnimationStatusRepository animationStatusRepository;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final DeviceStateRepository deviceStateRepository;
    public final DisplaySwitchLatencyLogger displaySwitchLatencyLogger;
    public final KeyguardInteractor keyguardInteractor;
    public final PowerInteractor powerInteractor;
    public final Executor singleThreadBgExecutor;
    public final SystemClock systemClock;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceStateRepository.DeviceState.values().length];
            try {
                iArr[DeviceStateRepository.DeviceState.FOLDED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.HALF_FOLDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.UNFOLDED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NoCooldownDisplaySwitchLatencyTracker.this.new AnonymousClass1(continuation);
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
                final Flow flowPairwise = FlowKt.pairwise(((DeviceStateRepositoryImpl) NoCooldownDisplaySwitchLatencyTracker.this.deviceStateRepository).state);
                ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = kotlinx.coroutines.flow.FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                WithPrev withPrev = (WithPrev) obj;
                                Object previousValue = withPrev.getPreviousValue();
                                DeviceStateRepository.DeviceState deviceState = DeviceStateRepository.DeviceState.FOLDED;
                                if (previousValue == deviceState || withPrev.getNewValue() == deviceState) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, new NoCooldownDisplaySwitchLatencyTracker$start$1$invokeSuspend$$inlined$flatMapLatest$1(null, NoCooldownDisplaySwitchLatencyTracker.this));
                final NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker = NoCooldownDisplaySwitchLatencyTracker.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker.start.1.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        noCooldownDisplaySwitchLatencyTracker.displaySwitchLatencyLogger.getClass();
                        DisplaySwitchLatencyLogger.log((DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent) obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (channelFlowTransformLatestTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
        SCREEN_EVENT_TIMEOUT = Duration.ofMillis(15000L).toMillis();
    }

    public NoCooldownDisplaySwitchLatencyTracker(Context context, DeviceStateRepository deviceStateRepository, PowerInteractor powerInteractor, UnfoldTransitionInteractor unfoldTransitionInteractor, AnimationStatusRepository animationStatusRepository, KeyguardInteractor keyguardInteractor, Executor executor, CoroutineScope coroutineScope, DisplaySwitchLatencyLogger displaySwitchLatencyLogger, SystemClock systemClock, DeviceStateManager deviceStateManager) {
        this.context = context;
        this.deviceStateRepository = deviceStateRepository;
        this.powerInteractor = powerInteractor;
        this.unfoldTransitionInteractor = unfoldTransitionInteractor;
        this.animationStatusRepository = animationStatusRepository;
        this.keyguardInteractor = keyguardInteractor;
        this.singleThreadBgExecutor = executor;
        this.applicationScope = coroutineScope;
        this.displaySwitchLatencyLogger = displaySwitchLatencyLogger;
        this.systemClock = systemClock;
        this.deviceStateManager = deviceStateManager;
        this.backgroundDispatcher = ExecutorsKt.from(executor);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        if (r8 == r1) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ef, code lost:
    
        if (com.android.systemui.util.kotlin.SuspendKt.race(r6, r0) != r1) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForDisplaySwitch(NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, int i, ContinuationImpl continuationImpl) throws Throwable {
        NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1 noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1;
        boolean z;
        Throwable th;
        int i2;
        String str;
        long j;
        noCooldownDisplaySwitchLatencyTracker.getClass();
        if (continuationImpl instanceof NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1) {
            noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1 = (NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1) continuationImpl;
            int i3 = noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label = i3 - Integer.MIN_VALUE;
            } else {
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1 = new NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1(noCooldownDisplaySwitchLatencyTracker, continuationImpl);
            }
        }
        Object objFirst = noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objFirst);
            if (((UnfoldTransitionRepositoryImpl) noCooldownDisplaySwitchLatencyTracker.unfoldTransitionInteractor.repository).unfoldProgressProvider.isPresent()) {
                Flow flowAreAnimationsEnabled = noCooldownDisplaySwitchLatencyTracker.animationStatusRepository.areAnimationsEnabled();
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = noCooldownDisplaySwitchLatencyTracker;
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0 = i;
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 1;
                objFirst = kotlinx.coroutines.flow.FlowKt.first(flowAreAnimationsEnabled, noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1);
            }
            noCooldownDisplaySwitchLatencyTracker.getClass();
            if (i == 1 && z) {
                int i5 = TraceUtils.$r8$clinit;
                int iNextInt = ThreadLocalRandom.current().nextInt();
                Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "waitForTransitionStart()", iNextInt);
                try {
                    UnfoldTransitionInteractor unfoldTransitionInteractor = noCooldownDisplaySwitchLatencyTracker.unfoldTransitionInteractor;
                    noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = "DisplaySwitchLatency";
                    noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.J$0 = 4096L;
                    noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0 = iNextInt;
                    noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 2;
                    Object objFirst2 = kotlinx.coroutines.flow.FlowKt.first(new UnfoldTransitionInteractor$waitForTransitionStart$$inlined$filter$1(((UnfoldTransitionRepositoryImpl) unfoldTransitionInteractor.repository).getTransitionStatus()), noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1);
                    if (objFirst2 != coroutineSingletons) {
                        objFirst2 = Unit.INSTANCE;
                    }
                    if (objFirst2 != coroutineSingletons) {
                        i2 = iNextInt;
                        str = "DisplaySwitchLatency";
                        j = 4096;
                        Unit unit = Unit.INSTANCE;
                        Trace.asyncTraceForTrackEnd(j, str, i2);
                        return Unit.INSTANCE;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    i2 = iNextInt;
                    str = "DisplaySwitchLatency";
                    j = 4096;
                    Trace.asyncTraceForTrackEnd(j, str, i2);
                    throw th;
                }
            } else {
                Function1[] function1Arr = {new NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$3(noCooldownDisplaySwitchLatencyTracker, null), new NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$4(noCooldownDisplaySwitchLatencyTracker, null)};
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = null;
                noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 3;
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 != 2) {
                if (i4 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objFirst);
                return Unit.INSTANCE;
            }
            i2 = noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0;
            j = noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.J$0;
            str = (String) noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0;
            try {
                ResultKt.throwOnFailure(objFirst);
                Unit unit2 = Unit.INSTANCE;
                Trace.asyncTraceForTrackEnd(j, str, i2);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i2);
                throw th;
            }
        }
        i = noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0;
        noCooldownDisplaySwitchLatencyTracker = (NoCooldownDisplaySwitchLatencyTracker) noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0;
        ResultKt.throwOnFailure(objFirst);
        z = ((Boolean) objFirst).booleanValue();
        noCooldownDisplaySwitchLatencyTracker.getClass();
        if (i == 1) {
        }
        Function1[] function1Arr2 = {new NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$3(noCooldownDisplaySwitchLatencyTracker, null), new NoCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$4(noCooldownDisplaySwitchLatencyTracker, null)};
        noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = null;
        noCooldownDisplaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForGoToSleepWithScreenOff(final NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, ContinuationImpl continuationImpl) throws Throwable {
        NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1;
        Throwable th;
        int i;
        String str;
        long j;
        noCooldownDisplaySwitchLatencyTracker.getClass();
        if (continuationImpl instanceof NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1) {
            noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 = (NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1) continuationImpl;
            int i2 = noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label = i2 - Integer.MIN_VALUE;
            } else {
                noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 = new NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1(noCooldownDisplaySwitchLatencyTracker, continuationImpl);
            }
        }
        Object obj = noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int i4 = TraceUtils.$r8$clinit;
            int iNextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "waitForGoToSleepWithScreenOff()", iNextInt);
            try {
                final ReadonlyStateFlow readonlyStateFlow = noCooldownDisplaySwitchLatencyTracker.powerInteractor.detailedWakefulness;
                Flow flow = new Flow() { // from class: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1

                    /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ NoCooldownDisplaySwitchLatencyTracker this$0;

                        /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = noCooldownDisplaySwitchLatencyTracker;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((WakefulnessModel) obj).internalWakefulnessState == WakefulnessState.ASLEEP) {
                                    int i3 = NoCooldownDisplaySwitchLatencyTracker.$r8$clinit;
                                    if (!this.this$0.isAodEnabled$1()) {
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, noCooldownDisplaySwitchLatencyTracker), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.L$0 = "DisplaySwitchLatency";
                noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.J$0 = 4096L;
                noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.I$0 = iNextInt;
                noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label = 1;
                Object objFirst = kotlinx.coroutines.flow.FlowKt.first(flow, noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objFirst;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
            } catch (Throwable th2) {
                th = th2;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.I$0;
            j = noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.J$0;
            str = (String) noCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForScreenTurnedOn(NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, ContinuationImpl continuationImpl) throws Throwable {
        NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1;
        Throwable th;
        int i;
        String str;
        long j;
        noCooldownDisplaySwitchLatencyTracker.getClass();
        if (continuationImpl instanceof NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1) {
            noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 = (NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1) continuationImpl;
            int i2 = noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.label = i2 - Integer.MIN_VALUE;
            } else {
                noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 = new NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1(noCooldownDisplaySwitchLatencyTracker, continuationImpl);
            }
        }
        Object obj = noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int i4 = TraceUtils.$r8$clinit;
            int iNextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "waitForScreenTurnedOn()", iNextInt);
            try {
                final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop = kotlinx.coroutines.flow.FlowKt.drop(noCooldownDisplaySwitchLatencyTracker.powerInteractor.screenPowerState);
                Flow flow = new Flow() { // from class: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1

                    /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((ScreenPowerState) obj) == ScreenPowerState.SCREEN_ON) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.L$0 = "DisplaySwitchLatency";
                noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.J$0 = 4096L;
                noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.I$0 = iNextInt;
                noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.label = 1;
                Object objFirst = kotlinx.coroutines.flow.FlowKt.first(flow, noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objFirst;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
            } catch (Throwable th2) {
                th = th2;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.I$0;
            j = noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.J$0;
            str = (String) noCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
    }

    public final boolean isAodEnabled$1() {
        return ((Boolean) this.keyguardInteractor.isAodAvailable.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isAsleepDueToFold$1() {
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue();
        if (wakefulnessModel.isAsleep()) {
            return wakefulnessModel.lastSleepReason == WakeSleepReason.FOLD;
        }
        return false;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Utils.isDeviceFoldable(this.context.getResources(), this.deviceStateManager)) {
            CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new AnonymousClass1(null), 5);
        }
    }
}
