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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class PointerInputScopeExtKt$detectLongPressGesture$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onLongPress;
    final /* synthetic */ PointerEventPass $pass;
    final /* synthetic */ PointerInputScope $this_detectLongPressGesture;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function1 $onLongPress;
        final /* synthetic */ PointerEventPass $pass;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C00891 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ PointerEventPass $pass;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00891(PointerEventPass pointerEventPass, Continuation continuation) {
                super(2, continuation);
                this.$pass = pointerEventPass;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00891 c00891 = new C00891(this.$pass, continuation);
                c00891.L$0 = obj;
                return c00891;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00891) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                Object waitForUpOrCancellation = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, pointerEventPass, this);
                return waitForUpOrCancellation == coroutineSingletons ? coroutineSingletons : waitForUpOrCancellation;
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

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
        
            if (r9 == r0) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x007e, code lost:
        
            if (com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt.access$consumeUntilUp(r2, r10, r9) != r0) goto L25;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.ui.input.pointer.PointerInputChange] */
        /* JADX WARN: Type inference failed for: r1v12 */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v7 */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1, types: [androidx.compose.ui.input.pointer.AwaitPointerEventScope] */
        /* JADX WARN: Type inference failed for: r2v4 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9 */
        /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1, kotlin.coroutines.Continuation, kotlin.coroutines.jvm.internal.BaseContinuationImpl] */
        /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1, kotlin.coroutines.jvm.internal.BaseContinuationImpl] */
        /* JADX WARN: Type inference failed for: r9v5 */
        /* JADX WARN: Type inference failed for: r9v7 */
        /* JADX WARN: Type inference failed for: r9v8 */
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
                if (r1 == 0) goto L32
                if (r1 == r2) goto L29
                if (r1 == r4) goto L1d
                if (r1 != r3) goto L15
                kotlin.ResultKt.throwOnFailure(r10)
                goto L81
            L15:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L1d:
                java.lang.Object r1 = r9.L$1
                androidx.compose.ui.input.pointer.PointerInputChange r1 = (androidx.compose.ui.input.pointer.PointerInputChange) r1
                java.lang.Object r2 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r2 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r2
                kotlin.ResultKt.throwOnFailure(r10)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                goto L81
            L29:
                java.lang.Object r1 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                kotlin.ResultKt.throwOnFailure(r10)
                r2 = r1
                goto L48
            L32:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r10 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r10
                androidx.compose.ui.input.pointer.PointerEventPass r1 = r9.$pass
                r9.L$0 = r10
                r9.label = r2
                java.lang.Object r1 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r10, r1, r9, r2)
                if (r1 != r0) goto L46
                goto L80
            L46:
                r2 = r10
                r10 = r1
            L48:
                r1 = r10
                androidx.compose.ui.input.pointer.PointerInputChange r1 = (androidx.compose.ui.input.pointer.PointerInputChange) r1
                androidx.compose.ui.platform.ViewConfiguration r10 = r2.getViewConfiguration()
                long r6 = r10.getLongPressTimeoutMillis()
                com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1 r10 = new com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                androidx.compose.ui.input.pointer.PointerEventPass r8 = r9.$pass     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                r10.<init>(r8, r5)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                r9.L$0 = r2     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                r9.L$1 = r1     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                r9.label = r4     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                java.lang.Object r9 = r2.withTimeout(r6, r10, r9)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L67
                if (r9 != r0) goto L81
                goto L80
            L67:
                kotlin.jvm.functions.Function1 r10 = r9.$onLongPress
                long r6 = r1.position
                androidx.compose.ui.geometry.Offset r1 = androidx.compose.ui.geometry.Offset.m393boximpl(r6)
                r10.mo779invoke(r1)
                androidx.compose.ui.input.pointer.PointerEventPass r10 = r9.$pass
                r9.L$0 = r5
                r9.L$1 = r5
                r9.label = r3
                java.lang.Object r9 = com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt.access$consumeUntilUp(r2, r10, r9)
                if (r9 != r0) goto L81
            L80:
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
