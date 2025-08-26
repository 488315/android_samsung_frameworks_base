package com.android.systemui.brightness.data.repository;

import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.brightness.shared.model.LinearBrightness;
import com.android.systemui.brightness.shared.model.LinearBrightnessKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class ScreenBrightnessDisplayManagerRepository implements ScreenBrightnessRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BufferedChannel apiQueue = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
    public final CoroutineContext backgroundContext;
    public final ReadonlyStateFlow brightnessInfo;
    public final int displayId;
    public final DisplayManager displayManager;
    public final ReadonlyStateFlow isBrightnessOverriddenByWindow;
    public final ReadonlyStateFlow linearBrightness;
    public final LogBuffer logBuffer;
    public final ReadonlyStateFlow maxLinearBrightness;
    public final ReadonlyStateFlow minLinearBrightness;

    /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ScreenBrightnessDisplayManagerRepository.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0061, code lost:
        
            if (r10 == r0) goto L19;
         */
        /* JADX WARN: Removed duplicated region for block: B:10:0x0028 A[PHI: r1 r10
          0x0028: PHI (r1v2 kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator) = 
          (r1v5 kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator)
          (r1v14 kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator)
         binds: [B:13:0x0043, B:9:0x0021] A[DONT_GENERATE, DONT_INLINE]
          0x0028: PHI (r10v3 java.lang.Object) = (r10v9 java.lang.Object), (r10v0 java.lang.Object) binds: [B:13:0x0043, B:9:0x0021] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00d2  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0061 -> B:20:0x0064). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
            BufferedChannel.BufferedChannelIterator bufferedChannelIterator2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BufferedChannel bufferedChannel = ScreenBrightnessDisplayManagerRepository.this.apiQueue;
                bufferedChannel.getClass();
                bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
                this.L$0 = bufferedChannelIterator;
                this.L$1 = null;
                this.label = 1;
                obj = bufferedChannelIterator.hasNext(this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                SetBrightnessMethod setBrightnessMethod = (SetBrightnessMethod) this.L$1;
                bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) this.L$0;
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) obj;
                float fMo1060getValuefoLk1o = setBrightnessMethod.mo1060getValuefoLk1o();
                float f = ((LinearBrightness) pair.getFirst()).floatValue;
                float f2 = ((LinearBrightness) pair.getSecond()).floatValue;
                if (fMo1060getValuefoLk1o < f) {
                    fMo1060getValuefoLk1o = f;
                } else if (fMo1060getValuefoLk1o > f2) {
                    fMo1060getValuefoLk1o = f2;
                }
                if (setBrightnessMethod instanceof SetBrightnessMethod.Temporary) {
                    ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = ScreenBrightnessDisplayManagerRepository.this;
                    screenBrightnessDisplayManagerRepository.displayManager.setTemporaryBrightness(screenBrightnessDisplayManagerRepository.displayId, fMo1060getValuefoLk1o);
                } else {
                    if (!(setBrightnessMethod instanceof SetBrightnessMethod.Permanent)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository2 = ScreenBrightnessDisplayManagerRepository.this;
                    screenBrightnessDisplayManagerRepository2.displayManager.setBrightness(screenBrightnessDisplayManagerRepository2.displayId, fMo1060getValuefoLk1o);
                }
                ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository3 = ScreenBrightnessDisplayManagerRepository.this;
                boolean z = setBrightnessMethod instanceof SetBrightnessMethod.Permanent;
                int i2 = ScreenBrightnessDisplayManagerRepository.$r8$clinit;
                screenBrightnessDisplayManagerRepository3.getClass();
                LogLevel logLevel = z ? LogLevel.DEBUG : LogLevel.VERBOSE;
                ScreenBrightnessDisplayManagerRepository$$ExternalSyntheticLambda0 screenBrightnessDisplayManagerRepository$$ExternalSyntheticLambda0 = new ScreenBrightnessDisplayManagerRepository$$ExternalSyntheticLambda0();
                LogBuffer logBuffer = screenBrightnessDisplayManagerRepository3.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("BrightnessChange", logLevel, screenBrightnessDisplayManagerRepository$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = LinearBrightnessKt.formatBrightness(fMo1060getValuefoLk1o);
                logBuffer.commit(logMessageObtain);
                bufferedChannelIterator = bufferedChannelIterator2;
                this.L$0 = bufferedChannelIterator;
                this.L$1 = null;
                this.label = 1;
                obj = bufferedChannelIterator.hasNext(this);
                if (obj != coroutineSingletons) {
                    bufferedChannelIterator2 = bufferedChannelIterator;
                    if (((Boolean) obj).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    setBrightnessMethod = (SetBrightnessMethod) bufferedChannelIterator2.next();
                    ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository4 = ScreenBrightnessDisplayManagerRepository.this;
                    this.L$0 = bufferedChannelIterator2;
                    this.L$1 = setBrightnessMethod;
                    this.label = 2;
                    obj = screenBrightnessDisplayManagerRepository4.getMinMaxLinearBrightness(this);
                }
                return coroutineSingletons;
            }
            bufferedChannelIterator = (BufferedChannel.BufferedChannelIterator) this.L$0;
            ResultKt.throwOnFailure(obj);
            bufferedChannelIterator2 = bufferedChannelIterator;
            if (((Boolean) obj).booleanValue()) {
            }
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface SetBrightnessMethod {

        public final class Permanent implements SetBrightnessMethod {
            public final float value;

            private /* synthetic */ Permanent(float f) {
                this.value = f;
            }

            /* renamed from: box-impl, reason: not valid java name */
            public static final /* synthetic */ Permanent m1061boximpl(float f) {
                return new Permanent(f);
            }

            public final boolean equals(Object obj) {
                if (obj instanceof Permanent) {
                    return Float.compare(this.value, ((Permanent) obj).value) == 0;
                }
                return false;
            }

            @Override // com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod
            /* renamed from: getValue--foLk1o */
            public final float mo1060getValuefoLk1o() {
                return this.value;
            }

            public final int hashCode() {
                return Float.hashCode(this.value);
            }

            public final String toString() {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Permanent(value=", LinearBrightness.m1070toStringimpl(this.value), ")");
            }
        }

        public final class Temporary implements SetBrightnessMethod {
            public final float value;

            private /* synthetic */ Temporary(float f) {
                this.value = f;
            }

            /* renamed from: box-impl, reason: not valid java name */
            public static final /* synthetic */ Temporary m1062boximpl(float f) {
                return new Temporary(f);
            }

            public final boolean equals(Object obj) {
                if (obj instanceof Temporary) {
                    return Float.compare(this.value, ((Temporary) obj).value) == 0;
                }
                return false;
            }

            @Override // com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod
            /* renamed from: getValue--foLk1o */
            public final float mo1060getValuefoLk1o() {
                return this.value;
            }

            public final int hashCode() {
                return Float.hashCode(this.value);
            }

            public final String toString() {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Temporary(value=", LinearBrightness.m1070toStringimpl(this.value), ")");
            }
        }

        /* renamed from: getValue--foLk1o, reason: not valid java name */
        float mo1060getValuefoLk1o();
    }

    /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1, reason: invalid class name and case insensitive filesystem */
    final class C08221 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08221(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ScreenBrightnessDisplayManagerRepository.this.getMinMaxLinearBrightness(this);
        }
    }

    static {
        new Companion(null);
    }

    public ScreenBrightnessDisplayManagerRepository(int i, DisplayManager displayManager, LogBuffer logBuffer, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.displayId = i;
        this.displayManager = displayManager;
        this.logBuffer = logBuffer;
        this.backgroundContext = coroutineContext;
        CoroutineTracingKt.launchTraced$default(coroutineScope, coroutineContext, null, new AnonymousClass1(null), 5);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ScreenBrightnessDisplayManagerRepository$brightnessInfo$2(null), FlowConflatedKt.conflatedCallbackFlow(new ScreenBrightnessDisplayManagerRepository$brightnessInfo$1(this, null)));
        Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ScreenBrightnessDisplayManagerRepository this$0;

                /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
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

                public AnonymousClass2(FlowCollector flowCollector, ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = screenBrightnessDisplayManagerRepository;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        int i3 = ScreenBrightnessDisplayManagerRepository.$r8$clinit;
                        ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = this.this$0;
                        screenBrightnessDisplayManagerRepository.getClass();
                        objWithContext = BuildersKt.withContext(screenBrightnessDisplayManagerRepository.backgroundContext, new ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2(screenBrightnessDisplayManagerRepository, null), anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineContext);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 1), null);
        this.brightnessInfo = readonlyStateFlowStateIn;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowStateIn);
        this.minLinearBrightness = FlowKt.stateIn(LinearBrightnessKt.m1071logDiffForTableCVGC8U(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2

            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
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
                        LinearBrightness linearBrightnessM1069boximpl = LinearBrightness.m1069boximpl(((BrightnessInfo) obj).brightnessMinimum);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(linearBrightnessM1069boximpl, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "min"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), LinearBrightness.m1069boximpl(0.0f));
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowStateIn);
        this.maxLinearBrightness = FlowKt.stateIn(LinearBrightnessKt.m1071logDiffForTableCVGC8U(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3

            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
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
                        LinearBrightness linearBrightnessM1069boximpl = LinearBrightness.m1069boximpl(((BrightnessInfo) obj).brightnessMaximum);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(linearBrightnessM1069boximpl, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "max"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), LinearBrightness.m1069boximpl(1.0f));
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$13 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowStateIn);
        this.linearBrightness = FlowKt.stateIn(LinearBrightnessKt.m1071logDiffForTableCVGC8U(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4

            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
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
                        LinearBrightness linearBrightnessM1069boximpl = LinearBrightness.m1069boximpl(((BrightnessInfo) obj).brightness);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(linearBrightnessM1069boximpl, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$13.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "brightness"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), LinearBrightness.m1069boximpl(0.0f));
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$14 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowStateIn);
        this.isBrightnessOverriddenByWindow = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$5

            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$5$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
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
                        Boolean boolValueOf = Boolean.valueOf(((BrightnessInfo) obj).isBrightnessOverrideByWindow);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$14.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getMinMaxLinearBrightness(ContinuationImpl continuationImpl) throws Throwable {
        C08221 c08221;
        BrightnessInfo brightnessInfo;
        if (continuationImpl instanceof C08221) {
            c08221 = (C08221) continuationImpl;
            int i = c08221.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08221.label = i - Integer.MIN_VALUE;
            } else {
                c08221 = new C08221(continuationImpl);
            }
        }
        Object objWithContext = c08221.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08221.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            brightnessInfo = (BrightnessInfo) this.brightnessInfo.$$delegate_0.getValue();
            if (brightnessInfo == null) {
                c08221.label = 1;
                objWithContext = BuildersKt.withContext(this.backgroundContext, new ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2(this, null), c08221);
                if (objWithContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return new Pair(LinearBrightness.m1069boximpl(brightnessInfo == null ? brightnessInfo.brightnessMinimum : 0.0f), LinearBrightness.m1069boximpl(brightnessInfo == null ? brightnessInfo.brightnessMaximum : 1.0f));
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(objWithContext);
        brightnessInfo = (BrightnessInfo) objWithContext;
        return new Pair(LinearBrightness.m1069boximpl(brightnessInfo == null ? brightnessInfo.brightnessMinimum : 0.0f), LinearBrightness.m1069boximpl(brightnessInfo == null ? brightnessInfo.brightnessMaximum : 1.0f));
    }
}
