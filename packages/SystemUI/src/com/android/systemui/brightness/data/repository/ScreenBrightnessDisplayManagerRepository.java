package com.android.systemui.brightness.data.repository;

import android.hardware.display.DisplayManager;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.brightness.shared.model.LinearBrightness;
import com.android.systemui.brightness.shared.model.LinearBrightnessKt;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
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
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ScreenBrightnessDisplayManagerRepository implements ScreenBrightnessRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BufferedChannel apiQueue = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6);
    public final CoroutineContext backgroundContext;
    public final ReadonlyStateFlow brightnessInfo;
    public final int displayId;
    public final DisplayManager displayManager;
    public final ReadonlyStateFlow linearBrightness;
    public final LogBuffer logBuffer;
    public final ReadonlyStateFlow maxLinearBrightness;
    public final ReadonlyStateFlow minLinearBrightness;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        /* JADX WARN: Removed duplicated region for block: B:11:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x00ab  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0045 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00ae  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0093  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x007e  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0061 -> B:6:0x0064). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
            /*
                r9 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r9.label
                r2 = 0
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L2a
                if (r1 == r4) goto L21
                if (r1 != r3) goto L19
                java.lang.Object r1 = r9.L$1
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$SetBrightnessMethod r1 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod) r1
                java.lang.Object r5 = r9.L$0
                kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r5 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r5
                kotlin.ResultKt.throwOnFailure(r10)
                goto L64
            L19:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L21:
                java.lang.Object r1 = r9.L$0
                kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
                kotlin.ResultKt.throwOnFailure(r10)
            L28:
                r5 = r1
                goto L46
            L2a:
                kotlin.ResultKt.throwOnFailure(r10)
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r10 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.this
                kotlinx.coroutines.channels.BufferedChannel r10 = r10.apiQueue
                r10.getClass()
                kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = new kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator
                r1.<init>()
            L39:
                r9.L$0 = r1
                r9.L$1 = r2
                r9.label = r4
                java.lang.Object r10 = r1.hasNext(r9)
                if (r10 != r0) goto L28
                return r0
            L46:
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 == 0) goto Lc9
                java.lang.Object r10 = r5.next()
                r1 = r10
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$SetBrightnessMethod r1 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod) r1
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r10 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.this
                r9.L$0 = r5
                r9.L$1 = r1
                r9.label = r3
                java.lang.Object r10 = r10.getMinMaxLinearBrightness(r9)
                if (r10 != r0) goto L64
                return r0
            L64:
                kotlin.Pair r10 = (kotlin.Pair) r10
                float r6 = r1.mo932getValuefoLk1o()
                java.lang.Object r7 = r10.getFirst()
                com.android.systemui.brightness.shared.model.LinearBrightness r7 = (com.android.systemui.brightness.shared.model.LinearBrightness) r7
                float r7 = r7.floatValue
                java.lang.Object r10 = r10.getSecond()
                com.android.systemui.brightness.shared.model.LinearBrightness r10 = (com.android.systemui.brightness.shared.model.LinearBrightness) r10
                float r10 = r10.floatValue
                int r8 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                if (r8 >= 0) goto L80
                r6 = r7
                goto L85
            L80:
                int r7 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
                if (r7 <= 0) goto L85
                r6 = r10
            L85:
                boolean r10 = r1 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Temporary
                if (r10 == 0) goto L93
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r10 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.this
                android.hardware.display.DisplayManager r7 = r10.displayManager
                int r10 = r10.displayId
                r7.setTemporaryBrightness(r10, r6)
                goto La0
            L93:
                boolean r10 = r1 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Permanent
                if (r10 == 0) goto La0
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r10 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.this
                android.hardware.display.DisplayManager r7 = r10.displayManager
                int r10 = r10.displayId
                r7.setBrightness(r10, r6)
            La0:
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r10 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.this
                boolean r1 = r1 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Permanent
                int r7 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.$r8$clinit
                r10.getClass()
                if (r1 == 0) goto Lae
                com.android.systemui.log.core.LogLevel r1 = com.android.systemui.log.core.LogLevel.DEBUG
                goto Lb0
            Lae:
                com.android.systemui.log.core.LogLevel r1 = com.android.systemui.log.core.LogLevel.VERBOSE
            Lb0:
                com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2 r7 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2
                    static {
                        /*
                            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2 r0 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2) com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2.INSTANCE com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 1
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2.<init>():void");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final java.lang.Object invoke(java.lang.Object r1) {
                        /*
                            r0 = this;
                            com.android.systemui.log.core.LogMessage r1 = (com.android.systemui.log.core.LogMessage) r1
                            java.lang.String r0 = r1.getStr1()
                            java.lang.String r1 = "Change requested: "
                            java.lang.String r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(r1, r0)
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$logBrightnessChange$2.invoke(java.lang.Object):java.lang.Object");
                    }
                }
                java.lang.String r8 = "BrightnessChange"
                com.android.systemui.log.LogBuffer r10 = r10.logBuffer
                com.android.systemui.log.core.LogMessage r1 = r10.obtain(r8, r1, r7, r2)
                java.lang.String r6 = com.android.systemui.brightness.shared.model.LinearBrightnessKt.formatBrightness(r6)
                r7 = r1
                com.android.systemui.log.LogMessageImpl r7 = (com.android.systemui.log.LogMessageImpl) r7
                r7.str1 = r6
                r10.commit(r1)
                r1 = r5
                goto L39
            Lc9:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SetBrightnessMethod {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Permanent implements SetBrightnessMethod {
            public final float value;

            public final boolean equals(Object obj) {
                if (obj instanceof Permanent) {
                    return Float.compare(this.value, ((Permanent) obj).value) == 0;
                }
                return false;
            }

            @Override // com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod
            /* renamed from: getValue--foLk1o */
            public final float mo932getValuefoLk1o() {
                return this.value;
            }

            public final int hashCode() {
                return Float.hashCode(this.value);
            }

            public final String toString() {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Permanent(value=", LinearBrightness.m936toStringimpl(this.value), ")");
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Temporary implements SetBrightnessMethod {
            public final float value;

            public final boolean equals(Object obj) {
                if (obj instanceof Temporary) {
                    return Float.compare(this.value, ((Temporary) obj).value) == 0;
                }
                return false;
            }

            @Override // com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod
            /* renamed from: getValue--foLk1o */
            public final float mo932getValuefoLk1o() {
                return this.value;
            }

            public final int hashCode() {
                return Float.hashCode(this.value);
            }

            public final String toString() {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Temporary(value=", LinearBrightness.m936toStringimpl(this.value), ")");
            }
        }

        /* renamed from: getValue--foLk1o, reason: not valid java name */
        float mo932getValuefoLk1o();
    }

    static {
        new Companion(null);
    }

    public ScreenBrightnessDisplayManagerRepository(int i, DisplayManager displayManager, LogBuffer logBuffer, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.displayId = i;
        this.displayManager = displayManager;
        this.logBuffer = logBuffer;
        this.backgroundContext = coroutineContext;
        BuildersKt.launch$default(coroutineScope, coroutineContext, null, new AnonymousClass1(null), 2);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ScreenBrightnessDisplayManagerRepository$brightnessInfo$1 screenBrightnessDisplayManagerRepository$brightnessInfo$1 = new ScreenBrightnessDisplayManagerRepository$brightnessInfo$1(this, null);
        conflatedCallbackFlow.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ScreenBrightnessDisplayManagerRepository$brightnessInfo$2(null), FlowConflatedKt.conflatedCallbackFlow(screenBrightnessDisplayManagerRepository$brightnessInfo$1));
        Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ScreenBrightnessDisplayManagerRepository this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:19:0x0066 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L67
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5c
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Unit r7 = (kotlin.Unit) r7
                        kotlinx.coroutines.flow.FlowCollector r7 = r6.$this_unsafeFlow
                        r0.L$0 = r7
                        r0.label = r5
                        int r8 = com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.$r8$clinit
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository r6 = r6.this$0
                        r6.getClass()
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2 r8 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2
                        r8.<init>(r6, r3)
                        kotlin.coroutines.CoroutineContext r6 = r6.backgroundContext
                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
                        if (r8 != r1) goto L5b
                        return r1
                    L5b:
                        r6 = r7
                    L5c:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 1), null);
        this.brightnessInfo = stateIn;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateIn);
        this.minLinearBrightness = FlowKt.stateIn(LinearBrightnessKt.m937logDiffForTableCVGC8U(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2$2$1 r0 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2$2$1 r0 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.hardware.display.BrightnessInfo r5 = (android.hardware.display.BrightnessInfo) r5
                        float r5 = r5.brightnessMinimum
                        com.android.systemui.brightness.shared.model.LinearBrightness r5 = com.android.systemui.brightness.shared.model.LinearBrightness.m935boximpl(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "min"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), LinearBrightness.m935boximpl(0.0f));
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateIn);
        this.maxLinearBrightness = FlowKt.stateIn(LinearBrightnessKt.m937logDiffForTableCVGC8U(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3$2$1 r0 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3$2$1 r0 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.hardware.display.BrightnessInfo r5 = (android.hardware.display.BrightnessInfo) r5
                        float r5 = r5.brightnessMaximum
                        com.android.systemui.brightness.shared.model.LinearBrightness r5 = com.android.systemui.brightness.shared.model.LinearBrightness.m935boximpl(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "max"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), LinearBrightness.m935boximpl(1.0f));
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$13 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateIn);
        this.linearBrightness = FlowKt.stateIn(LinearBrightnessKt.m937logDiffForTableCVGC8U(new Flow() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4$2$1 r0 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4$2$1 r0 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.hardware.display.BrightnessInfo r5 = (android.hardware.display.BrightnessInfo) r5
                        float r5 = r5.brightness
                        com.android.systemui.brightness.shared.model.LinearBrightness r5 = com.android.systemui.brightness.shared.model.LinearBrightness.m935boximpl(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "brightness"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), LinearBrightness.m935boximpl(0.0f));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getMinMaxLinearBrightness(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1 r0 = (com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1 r0 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4f
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r4.brightnessInfo
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            android.hardware.display.BrightnessInfo r5 = (android.hardware.display.BrightnessInfo) r5
            if (r5 != 0) goto L51
            r0.label = r3
            com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2 r5 = new com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfoValue$2
            r2 = 0
            r5.<init>(r4, r2)
            kotlin.coroutines.CoroutineContext r4 = r4.backgroundContext
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r5 != r1) goto L4f
            return r1
        L4f:
            android.hardware.display.BrightnessInfo r5 = (android.hardware.display.BrightnessInfo) r5
        L51:
            if (r5 == 0) goto L56
            float r4 = r5.brightnessMinimum
            goto L57
        L56:
            r4 = 0
        L57:
            if (r5 == 0) goto L5c
            float r5 = r5.brightnessMaximum
            goto L5e
        L5c:
            r5 = 1065353216(0x3f800000, float:1.0)
        L5e:
            com.android.systemui.brightness.shared.model.LinearBrightness r4 = com.android.systemui.brightness.shared.model.LinearBrightness.m935boximpl(r4)
            com.android.systemui.brightness.shared.model.LinearBrightness r5 = com.android.systemui.brightness.shared.model.LinearBrightness.m935boximpl(r5)
            kotlin.Pair r0 = new kotlin.Pair
            r0.<init>(r4, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository.getMinMaxLinearBrightness(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
