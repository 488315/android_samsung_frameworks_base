package androidx.compose.foundation.gestures;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TapGestureDetectorKt$detectTapAndPress$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function3 $onPress;
    final /* synthetic */ Function1 $onTap;
    final /* synthetic */ PressGestureScopeImpl $pressScope;
    final /* synthetic */ PointerInputScope $this_detectTapAndPress;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $$this$coroutineScope;
        final /* synthetic */ Function3 $onPress;
        final /* synthetic */ Function1 $onTap;
        final /* synthetic */ PressGestureScopeImpl $pressScope;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C00051 extends SuspendLambda implements Function2 {
            final /* synthetic */ PointerInputChange $down;
            final /* synthetic */ Function3 $onPress;
            final /* synthetic */ PressGestureScopeImpl $pressScope;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00051(Function3 function3, PressGestureScopeImpl pressGestureScopeImpl, PointerInputChange pointerInputChange, Continuation continuation) {
                super(2, continuation);
                this.$onPress = function3;
                this.$pressScope = pressGestureScopeImpl;
                this.$down = pointerInputChange;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00051(this.$onPress, this.$pressScope, this.$down, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00051) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Function3 function3 = this.$onPress;
                    PressGestureScopeImpl pressGestureScopeImpl = this.$pressScope;
                    Offset m393boximpl = Offset.m393boximpl(this.$down.position);
                    this.label = 1;
                    if (function3.invoke(pressGestureScopeImpl, m393boximpl, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ PressGestureScopeImpl $pressScope;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                super(2, continuation);
                this.$pressScope = pressGestureScopeImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$pressScope, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$pressScope.cancel();
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ PressGestureScopeImpl $pressScope;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                super(2, continuation);
                this.$pressScope = pressGestureScopeImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$pressScope, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$pressScope.release();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CoroutineScope coroutineScope, Function3 function3, Function1 function1, PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
            super(2, continuation);
            this.$$this$coroutineScope = coroutineScope;
            this.$onPress = function3;
            this.$onTap = function1;
            this.$pressScope = pressGestureScopeImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$coroutineScope, this.$onPress, this.$onTap, this.$pressScope, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0092  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0085  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r11) {
            /*
                r10 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r10.label
                r2 = 1
                r3 = 0
                r4 = 2
                if (r1 == 0) goto L2a
                if (r1 == r2) goto L1e
                if (r1 != r4) goto L16
                java.lang.Object r0 = r10.L$0
                kotlinx.coroutines.Job r0 = (kotlinx.coroutines.Job) r0
                kotlin.ResultKt.throwOnFailure(r11)
                goto L81
            L16:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r11)
                throw r10
            L1e:
                java.lang.Object r1 = r10.L$1
                kotlinx.coroutines.Job r1 = (kotlinx.coroutines.Job) r1
                java.lang.Object r2 = r10.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r2 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r2
                kotlin.ResultKt.throwOnFailure(r11)
                goto L5a
            L2a:
                kotlin.ResultKt.throwOnFailure(r11)
                java.lang.Object r11 = r10.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r11 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r11
                kotlinx.coroutines.CoroutineScope r1 = r10.$$this$coroutineScope
                kotlin.jvm.functions.Function3 r5 = androidx.compose.foundation.gestures.TapGestureDetectorKt.NoPressGesture
                boolean r5 = androidx.compose.foundation.ComposeFoundationFlags.isDetectTapGesturesImmediateCoroutineDispatchEnabled
                if (r5 == 0) goto L3c
                kotlinx.coroutines.CoroutineStart r5 = kotlinx.coroutines.CoroutineStart.UNDISPATCHED
                goto L3e
            L3c:
                kotlinx.coroutines.CoroutineStart r5 = kotlinx.coroutines.CoroutineStart.DEFAULT
            L3e:
                androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$resetJob$1 r6 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$resetJob$1
                androidx.compose.foundation.gestures.PressGestureScopeImpl r7 = r10.$pressScope
                r6.<init>(r7, r3)
                kotlinx.coroutines.StandaloneCoroutine r1 = kotlinx.coroutines.BuildersKt.launch$default(r1, r3, r5, r6, r2)
                r10.L$0 = r11
                r10.L$1 = r1
                r10.label = r2
                r2 = 3
                java.lang.Object r2 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r11, r3, r10, r2)
                if (r2 != r0) goto L57
                goto L7f
            L57:
                r9 = r2
                r2 = r11
                r11 = r9
            L5a:
                androidx.compose.ui.input.pointer.PointerInputChange r11 = (androidx.compose.ui.input.pointer.PointerInputChange) r11
                r11.consume()
                kotlin.jvm.functions.Function3 r5 = r10.$onPress
                kotlin.jvm.functions.Function3 r6 = androidx.compose.foundation.gestures.TapGestureDetectorKt.NoPressGesture
                if (r5 == r6) goto L71
                kotlinx.coroutines.CoroutineScope r6 = r10.$$this$coroutineScope
                androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$1 r7 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$1
                androidx.compose.foundation.gestures.PressGestureScopeImpl r8 = r10.$pressScope
                r7.<init>(r5, r8, r11, r3)
                androidx.compose.foundation.gestures.TapGestureDetectorKt.launchAwaitingReset$default(r6, r1, r7)
            L71:
                r10.L$0 = r1
                r10.L$1 = r3
                r10.label = r4
                androidx.compose.ui.input.pointer.PointerEventPass r11 = androidx.compose.ui.input.pointer.PointerEventPass.Main
                java.lang.Object r11 = androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForUpOrCancellation(r2, r11, r10)
                if (r11 != r0) goto L80
            L7f:
                return r0
            L80:
                r0 = r1
            L81:
                androidx.compose.ui.input.pointer.PointerInputChange r11 = (androidx.compose.ui.input.pointer.PointerInputChange) r11
                if (r11 != 0) goto L92
                kotlinx.coroutines.CoroutineScope r11 = r10.$$this$coroutineScope
                androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$2 r1 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$2
                androidx.compose.foundation.gestures.PressGestureScopeImpl r10 = r10.$pressScope
                r1.<init>(r10, r3)
                androidx.compose.foundation.gestures.TapGestureDetectorKt.launchAwaitingReset$default(r11, r0, r1)
                goto Lae
            L92:
                r11.consume()
                kotlinx.coroutines.CoroutineScope r1 = r10.$$this$coroutineScope
                androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$3 r2 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$3
                androidx.compose.foundation.gestures.PressGestureScopeImpl r4 = r10.$pressScope
                r2.<init>(r4, r3)
                androidx.compose.foundation.gestures.TapGestureDetectorKt.launchAwaitingReset$default(r1, r0, r2)
                kotlin.jvm.functions.Function1 r10 = r10.$onTap
                if (r10 == 0) goto Lae
                long r0 = r11.position
                androidx.compose.ui.geometry.Offset r11 = androidx.compose.ui.geometry.Offset.m393boximpl(r0)
                r10.mo779invoke(r11)
            Lae:
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TapGestureDetectorKt$detectTapAndPress$2(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
        super(2, continuation);
        this.$this_detectTapAndPress = pointerInputScope;
        this.$onPress = function3;
        this.$onTap = function1;
        this.$pressScope = pressGestureScopeImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TapGestureDetectorKt$detectTapAndPress$2 tapGestureDetectorKt$detectTapAndPress$2 = new TapGestureDetectorKt$detectTapAndPress$2(this.$this_detectTapAndPress, this.$onPress, this.$onTap, this.$pressScope, continuation);
        tapGestureDetectorKt$detectTapAndPress$2.L$0 = obj;
        return tapGestureDetectorKt$detectTapAndPress$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TapGestureDetectorKt$detectTapAndPress$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            PointerInputScope pointerInputScope = this.$this_detectTapAndPress;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(coroutineScope, this.$onPress, this.$onTap, this.$pressScope, null);
            this.label = 1;
            if (ForEachGestureKt.awaitEachGesture(pointerInputScope, anonymousClass1, this) == coroutineSingletons) {
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
