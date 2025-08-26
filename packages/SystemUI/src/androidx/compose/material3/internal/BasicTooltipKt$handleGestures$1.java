package androidx.compose.material3.internal;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.material3.TooltipState;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
final class BasicTooltipKt$handleGestures$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TooltipState $state;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$handleGestures$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ PointerInputScope $$this$pointerInput;
        final /* synthetic */ TooltipState $state;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$handleGestures$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00311 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ TooltipState $state;
            long J$0;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            int label;

            /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$handleGestures$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C00321 extends RestrictedSuspendLambda implements Function2 {
                final /* synthetic */ PointerEventPass $pass;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00321(PointerEventPass pointerEventPass, Continuation continuation) {
                    super(2, continuation);
                    this.$pass = pointerEventPass;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00321 c00321 = new C00321(this.$pass, continuation);
                    c00321.L$0 = obj;
                    return c00321;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00321) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i != 0) {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return obj;
                    }
                    ResultKt.throwOnFailure(obj);
                    AwaitPointerEventScope awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    PointerEventPass pointerEventPass = this.$pass;
                    this.label = 1;
                    Object objWaitForUpOrCancellation = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, pointerEventPass, this);
                    return objWaitForUpOrCancellation == coroutineSingletons ? coroutineSingletons : objWaitForUpOrCancellation;
                }
            }

            /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$handleGestures$1$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $isLongPressedFlow;
                final /* synthetic */ TooltipState $state;
                Object L$0;
                int label;

                /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$handleGestures$1$1$1$2$1, reason: invalid class name and collision with other inner class name */
                final class C00331 extends SuspendLambda implements Function2 {
                    final /* synthetic */ TooltipState $state;
                    /* synthetic */ boolean Z$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C00331(TooltipState tooltipState, Continuation continuation) {
                        super(2, continuation);
                        this.$state = tooltipState;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        C00331 c00331 = new C00331(this.$state, continuation);
                        c00331.Z$0 = ((Boolean) obj).booleanValue();
                        return c00331;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Boolean bool = (Boolean) obj;
                        bool.booleanValue();
                        return ((C00331) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        if (!this.Z$0) {
                            this.$state.dismiss();
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(MutableStateFlow mutableStateFlow, TooltipState tooltipState, Continuation continuation) {
                    super(2, continuation);
                    this.$isLongPressedFlow = mutableStateFlow;
                    this.$state = tooltipState;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$isLongPressedFlow, this.$state, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:22:0x0058, code lost:
                
                    if (kotlinx.coroutines.flow.FlowKt.collectLatest(r7, r1, r6) == r0) goto L30;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) throws Throwable {
                    Throwable th;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    try {
                    } catch (Throwable th2) {
                        if (!this.$state.isVisible()) {
                            throw th2;
                        }
                        MutableStateFlow mutableStateFlow = this.$isLongPressedFlow;
                        C00331 c00331 = new C00331(this.$state, null);
                        this.L$0 = th2;
                        this.label = 3;
                        if (FlowKt.collectLatest(mutableStateFlow, c00331, this) != coroutineSingletons) {
                            th = th2;
                        }
                    }
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        this.$isLongPressedFlow.tryEmit(Boolean.TRUE);
                        TooltipState tooltipState = this.$state;
                        MutatePriority mutatePriority = MutatePriority.PreventUserInput;
                        this.label = 1;
                        if (tooltipState.show(mutatePriority, this) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i != 1) {
                        if (i == 2) {
                            ResultKt.throwOnFailure(obj);
                            return Unit.INSTANCE;
                        }
                        if (i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        th = (Throwable) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        throw th;
                    }
                    ResultKt.throwOnFailure(obj);
                    if (this.$state.isVisible()) {
                        MutableStateFlow mutableStateFlow2 = this.$isLongPressedFlow;
                        C00331 c003312 = new C00331(this.$state, null);
                        this.label = 2;
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00311(CoroutineScope coroutineScope, TooltipState tooltipState, Continuation continuation) {
                super(2, continuation);
                this.$$this$coroutineScope = coroutineScope;
                this.$state = tooltipState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00311 c00311 = new C00311(this.$$this$coroutineScope, this.$state, continuation);
                c00311.L$0 = obj;
                return c00311;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00311) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:42:0x00cf  */
            /* JADX WARN: Removed duplicated region for block: B:45:0x00d4 A[Catch: all -> 0x0019, TRY_LEAVE, TryCatch #4 {all -> 0x0019, blocks: (B:8:0x0014, B:43:0x00d0, B:45:0x00d4), top: B:57:0x0014 }] */
            /* JADX WARN: Type inference failed for: r1v0, types: [int] */
            /* JADX WARN: Type inference failed for: r1v1 */
            /* JADX WARN: Type inference failed for: r1v11 */
            /* JADX WARN: Type inference failed for: r1v14 */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            /* JADX WARN: Type inference failed for: r1v21 */
            /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.Object] */
            /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Object, kotlinx.coroutines.flow.MutableStateFlow] */
            /* JADX WARN: Type inference failed for: r1v7 */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) throws Throwable {
                MutableStateFlow mutableStateFlow;
                PointerEventPass pointerEventPass;
                long j;
                AwaitPointerEventScope awaitPointerEventScope;
                ?? r1;
                C00321 c00321;
                MutableStateFlow mutableStateFlow2;
                PointerEventPass pointerEventPass2;
                boolean z;
                PointerInputChange pointerInputChange;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                ?? r12 = this.label;
                try {
                    if (r12 == 0) {
                        ResultKt.throwOnFailure(obj);
                        AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
                        long longPressTimeoutMillis = awaitPointerEventScope2.getViewConfiguration().getLongPressTimeoutMillis();
                        pointerEventPass = PointerEventPass.Initial;
                        this.L$0 = awaitPointerEventScope2;
                        this.L$1 = stateFlowImplMutableStateFlow;
                        this.L$2 = pointerEventPass;
                        this.J$0 = longPressTimeoutMillis;
                        this.label = 1;
                        Object objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope2, pointerEventPass, this, 1);
                        if (objAwaitFirstDown$default != coroutineSingletons) {
                            j = longPressTimeoutMillis;
                            awaitPointerEventScope = awaitPointerEventScope2;
                            obj = objAwaitFirstDown$default;
                            r1 = stateFlowImplMutableStateFlow;
                        }
                        return coroutineSingletons;
                    }
                    if (r12 != 1) {
                        if (r12 != 2) {
                            if (r12 != 3) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            mutableStateFlow = (MutableStateFlow) this.L$0;
                            try {
                                ResultKt.throwOnFailure(obj);
                                z = r12;
                                pointerInputChange = (PointerInputChange) obj;
                                if (pointerInputChange != null) {
                                    pointerInputChange.consume();
                                }
                                mutableStateFlow.tryEmit(Boolean.FALSE);
                                r12 = z;
                                return Unit.INSTANCE;
                            } catch (Throwable th) {
                                th = th;
                                mutableStateFlow.tryEmit(Boolean.FALSE);
                                throw th;
                            }
                        }
                        PointerEventPass pointerEventPass3 = (PointerEventPass) this.L$2;
                        mutableStateFlow2 = (MutableStateFlow) this.L$1;
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            pointerEventPass2 = pointerEventPass3;
                            mutableStateFlow2.tryEmit(Boolean.FALSE);
                            r12 = pointerEventPass2;
                        } catch (PointerEventTimeoutCancellationException unused) {
                            pointerEventPass = pointerEventPass3;
                            r1 = mutableStateFlow2;
                            BuildersKt.launch$default(this.$$this$coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass2(r1, this.$state, null), 1);
                            this.L$0 = r1;
                            this.L$1 = null;
                            this.L$2 = null;
                            this.label = 3;
                            obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, pointerEventPass, this);
                            if (obj != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        } catch (Throwable th2) {
                            th = th2;
                            mutableStateFlow = mutableStateFlow2;
                            mutableStateFlow.tryEmit(Boolean.FALSE);
                            throw th;
                        }
                        return Unit.INSTANCE;
                    }
                    long j2 = this.J$0;
                    PointerEventPass pointerEventPass4 = (PointerEventPass) this.L$2;
                    MutableStateFlow mutableStateFlow3 = (MutableStateFlow) this.L$1;
                    AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    pointerEventPass = pointerEventPass4;
                    r1 = mutableStateFlow3;
                    j = j2;
                    awaitPointerEventScope = awaitPointerEventScope3;
                    long j3 = j;
                    int i = ((PointerInputChange) obj).type;
                    PointerType.Companion.getClass();
                    if (i == PointerType.Touch || i == PointerType.Stylus) {
                        try {
                            c00321 = new C00321(pointerEventPass, null);
                            this.L$0 = awaitPointerEventScope;
                            this.L$1 = r1;
                            this.L$2 = pointerEventPass;
                            this.label = 2;
                        } catch (PointerEventTimeoutCancellationException unused2) {
                            BuildersKt.launch$default(this.$$this$coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass2(r1, this.$state, null), 1);
                            this.L$0 = r1;
                            this.L$1 = null;
                            this.L$2 = null;
                            this.label = 3;
                            obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, pointerEventPass, this);
                            if (obj != coroutineSingletons) {
                                mutableStateFlow = r1;
                                z = r1;
                                pointerInputChange = (PointerInputChange) obj;
                                if (pointerInputChange != null) {
                                }
                                mutableStateFlow.tryEmit(Boolean.FALSE);
                                r12 = z;
                                return Unit.INSTANCE;
                            }
                            return coroutineSingletons;
                        }
                        if (awaitPointerEventScope.withTimeout(j3, c00321, this) != coroutineSingletons) {
                            mutableStateFlow2 = r1;
                            pointerEventPass2 = r1;
                            mutableStateFlow2.tryEmit(Boolean.FALSE);
                            r12 = pointerEventPass2;
                        }
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                } catch (Throwable th3) {
                    th = th3;
                    mutableStateFlow = r12;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PointerInputScope pointerInputScope, TooltipState tooltipState, Continuation continuation) {
            super(2, continuation);
            this.$$this$pointerInput = pointerInputScope;
            this.$state = tooltipState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$pointerInput, this.$state, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                PointerInputScope pointerInputScope = this.$$this$pointerInput;
                C00311 c00311 = new C00311(coroutineScope, this.$state, null);
                this.label = 1;
                if (ForEachGestureKt.awaitEachGesture(pointerInputScope, c00311, this) == coroutineSingletons) {
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
    public BasicTooltipKt$handleGestures$1(TooltipState tooltipState, Continuation continuation) {
        super(2, continuation);
        this.$state = tooltipState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BasicTooltipKt$handleGestures$1 basicTooltipKt$handleGestures$1 = new BasicTooltipKt$handleGestures$1(this.$state, continuation);
        basicTooltipKt$handleGestures$1.L$0 = obj;
        return basicTooltipKt$handleGestures$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicTooltipKt$handleGestures$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((PointerInputScope) this.L$0, this.$state, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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
