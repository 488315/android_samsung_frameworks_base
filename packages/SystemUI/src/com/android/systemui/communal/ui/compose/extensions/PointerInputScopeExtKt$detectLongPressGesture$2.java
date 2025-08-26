package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException;
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
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class PointerInputScopeExtKt$detectLongPressGesture$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onLongPress;
    final /* synthetic */ PointerEventPass $pass;
    final /* synthetic */ PointerInputScope $this_detectLongPressGesture;
    int label;

    /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function1 $onLongPress;
        final /* synthetic */ PointerEventPass $pass;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$detectLongPressGesture$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01691 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ PointerEventPass $pass;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01691(PointerEventPass pointerEventPass, Continuation continuation) {
                super(2, continuation);
                this.$pass = pointerEventPass;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01691 c01691 = new C01691(this.$pass, continuation);
                c01691.L$0 = obj;
                return c01691;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01691) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        /* JADX WARN: Code restructure failed: missing block: B:23:0x007e, code lost:
        
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
        */
        public final Object invokeSuspend(Object obj) {
            AwaitPointerEventScope awaitPointerEventScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            ?? r1 = this.label;
            ?? r2 = 1;
            try {
            } catch (PointerEventTimeoutCancellationException unused) {
                this.$onLongPress.mo781invoke(Offset.m395boximpl(r1.position));
                PointerEventPass pointerEventPass = this.$pass;
                this.L$0 = null;
                this.L$1 = null;
                this.label = 3;
            }
            if (r1 == 0) {
                ResultKt.throwOnFailure(obj);
                AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                PointerEventPass pointerEventPass2 = this.$pass;
                this.L$0 = awaitPointerEventScope2;
                this.label = 1;
                Object objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope2, pointerEventPass2, this, 1);
                if (objAwaitFirstDown$default != coroutineSingletons) {
                    awaitPointerEventScope = awaitPointerEventScope2;
                    obj = objAwaitFirstDown$default;
                }
                return coroutineSingletons;
            }
            if (r1 != 1) {
                if (r1 != 2) {
                    if (r1 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                PointerInputChange pointerInputChange = (PointerInputChange) this.L$1;
                AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                r1 = pointerInputChange;
                r2 = awaitPointerEventScope3;
                this = this;
                return Unit.INSTANCE;
            }
            AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            awaitPointerEventScope = awaitPointerEventScope4;
            PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
            long longPressTimeoutMillis = awaitPointerEventScope.getViewConfiguration().getLongPressTimeoutMillis();
            C01691 c01691 = new C01691(this.$pass, null);
            this.L$0 = awaitPointerEventScope;
            this.L$1 = pointerInputChange2;
            this.label = 2;
            Object objWithTimeout = awaitPointerEventScope.withTimeout(longPressTimeoutMillis, c01691, this);
            r1 = pointerInputChange2;
            r2 = awaitPointerEventScope;
            this = objWithTimeout;
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
