package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.gestures.ForEachGestureKt;
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

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PointerInputScopeExtKt$observeTaps$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onTap;
    final /* synthetic */ PointerEventPass $pass;
    final /* synthetic */ boolean $shouldConsume;
    final /* synthetic */ PointerInputScope $this_observeTaps;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$observeTaps$2$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function1 $onTap;
        final /* synthetic */ PointerEventPass $pass;
        final /* synthetic */ boolean $shouldConsume;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PointerEventPass pointerEventPass, boolean z, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$pass = pointerEventPass;
            this.$shouldConsume = z;
            this.$onTap = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$pass, this.$shouldConsume, this.$onTap, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x005f  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r7.label
                r2 = 1
                r3 = 2
                if (r1 == 0) goto L20
                if (r1 == r2) goto L18
                if (r1 != r3) goto L10
                kotlin.ResultKt.throwOnFailure(r8)
                goto L5b
            L10:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L18:
                java.lang.Object r1 = r7.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                kotlin.ResultKt.throwOnFailure(r8)
                goto L35
            L20:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                r1 = r8
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                androidx.compose.ui.input.pointer.PointerEventPass r8 = r7.$pass
                r7.L$0 = r1
                r7.label = r2
                java.lang.Object r8 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r8, r7, r2)
                if (r8 != r0) goto L35
                return r0
            L35:
                androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8
                boolean r2 = r7.$shouldConsume
                if (r2 == 0) goto L3e
                r8.consume()
            L3e:
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine r1 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) r1
                androidx.compose.ui.platform.ViewConfiguration r8 = r1.getViewConfiguration()
                long r4 = r8.getLongPressTimeoutMillis()
                com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$observeTaps$2$1$up$1 r8 = new com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$observeTaps$2$1$up$1
                androidx.compose.ui.input.pointer.PointerEventPass r2 = r7.$pass
                r6 = 0
                r8.<init>(r2, r6)
                r7.L$0 = r6
                r7.label = r3
                java.lang.Object r8 = r1.withTimeoutOrNull(r4, r8, r7)
                if (r8 != r0) goto L5b
                return r0
            L5b:
                androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8
                if (r8 == 0) goto L6a
                kotlin.jvm.functions.Function1 r7 = r7.$onTap
                long r0 = r8.position
                androidx.compose.ui.geometry.Offset r8 = androidx.compose.ui.geometry.Offset.m320boximpl(r0)
                r7.invoke(r8)
            L6a:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt$observeTaps$2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PointerInputScopeExtKt$observeTaps$2(Function1 function1, PointerInputScope pointerInputScope, PointerEventPass pointerEventPass, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$onTap = function1;
        this.$this_observeTaps = pointerInputScope;
        this.$pass = pointerEventPass;
        this.$shouldConsume = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PointerInputScopeExtKt$observeTaps$2(this.$onTap, this.$this_observeTaps, this.$pass, this.$shouldConsume, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PointerInputScopeExtKt$observeTaps$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Function1 function1 = this.$onTap;
            if (function1 == null) {
                return Unit.INSTANCE;
            }
            PointerInputScope pointerInputScope = this.$this_observeTaps;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$pass, this.$shouldConsume, function1, null);
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
