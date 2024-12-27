package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PointerInputScopeExtKt$detectLongPressGesture$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onLongPress;
    final /* synthetic */ PointerEventPass $pass;
    final /* synthetic */ PointerInputScope $this_detectLongPressGesture;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function1 $onLongPress;
        final /* synthetic */ PointerEventPass $pass;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C00761 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ PointerEventPass $pass;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00761(PointerEventPass pointerEventPass, Continuation continuation) {
                super(2, continuation);
                this.$pass = pointerEventPass;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00761 c00761 = new C00761(this.$pass, continuation);
                c00761.L$0 = obj;
                return c00761;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00761) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AwaitPointerEventScope awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    PointerEventPass pointerEventPass = this.$pass;
                    this.label = 1;
                    obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, pointerEventPass, this);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return obj;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PointerEventPass pointerEventPass, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$pass = pointerEventPass;
            this.$onLongPress = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$pass, this.$onLongPress, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(1:(3:(1:(1:6)(2:10|11))(3:12|13|14)|7|8)(1:20))(2:28|(1:30))|21|22|23|(1:25)|7|8|(1:(0))) */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0066, code lost:
        
            r1 = r10;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0080 A[RETURN] */
        /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r2v3, types: [androidx.compose.ui.input.pointer.AwaitPointerEventScope] */
        /* JADX WARN: Type inference failed for: r2v5, types: [androidx.compose.ui.input.pointer.AwaitPointerEventScope] */
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
                r2 = 1
                r3 = 3
                r4 = 2
                r5 = 0
                if (r1 == 0) goto L30
                if (r1 == r2) goto L28
                if (r1 == r4) goto L1c
                if (r1 != r3) goto L14
                kotlin.ResultKt.throwOnFailure(r10)
                goto L81
            L14:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L1c:
                java.lang.Object r1 = r9.L$1
                androidx.compose.ui.input.pointer.PointerInputChange r1 = (androidx.compose.ui.input.pointer.PointerInputChange) r1
                java.lang.Object r2 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r2 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r2
                kotlin.ResultKt.throwOnFailure(r10)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                goto L81
            L28:
                java.lang.Object r1 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                kotlin.ResultKt.throwOnFailure(r10)
                goto L45
            L30:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                r1 = r10
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                androidx.compose.ui.input.pointer.PointerEventPass r10 = r9.$pass
                r9.L$0 = r1
                r9.label = r2
                java.lang.Object r10 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r10, r9, r2)
                if (r10 != r0) goto L45
                return r0
            L45:
                androidx.compose.ui.input.pointer.PointerInputChange r10 = (androidx.compose.ui.input.pointer.PointerInputChange) r10
                r2 = r1
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine r2 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) r2
                androidx.compose.ui.platform.ViewConfiguration r1 = r2.getViewConfiguration()
                long r6 = r1.getLongPressTimeoutMillis()
                com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1 r1 = new com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                androidx.compose.ui.input.pointer.PointerEventPass r8 = r9.$pass     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                r1.<init>(r8, r5)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                r9.L$0 = r2     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                r9.L$1 = r10     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                r9.label = r4     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                java.lang.Object r9 = r2.withTimeout(r6, r1, r9)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L66
                if (r9 != r0) goto L81
                return r0
            L66:
                r1 = r10
            L67:
                kotlin.jvm.functions.Function1 r10 = r9.$onLongPress
                long r6 = r1.position
                androidx.compose.ui.geometry.Offset r1 = androidx.compose.ui.geometry.Offset.m320boximpl(r6)
                r10.invoke(r1)
                androidx.compose.ui.input.pointer.PointerEventPass r10 = r9.$pass
                r9.L$0 = r5
                r9.L$1 = r5
                r9.label = r3
                java.lang.Object r9 = com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt.access$consumeUntilUp(r2, r10, r9)
                if (r9 != r0) goto L81
                return r0
            L81:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PointerInputScopeExtKt$detectLongPressGesture$2(PointerInputScope pointerInputScope, PointerEventPass pointerEventPass, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$this_detectLongPressGesture = pointerInputScope;
        this.$pass = pointerEventPass;
        this.$onLongPress = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PointerInputScopeExtKt$detectLongPressGesture$2(this.$this_detectLongPressGesture, this.$pass, this.$onLongPress, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PointerInputScopeExtKt$detectLongPressGesture$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = this.$this_detectLongPressGesture;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$pass, this.$onLongPress, null);
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
