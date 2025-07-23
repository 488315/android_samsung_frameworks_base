package com.android.systemui.common.ui.compose.gestures;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class EagerTapKt$detectEagerTapGestures$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $doubleTapEnabled;
    final /* synthetic */ Function1 $onDoubleTap;
    final /* synthetic */ Function0 $onTap;
    final /* synthetic */ PointerInputScope $this_detectEagerTapGestures;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.common.ui.compose.gestures.EagerTapKt$detectEagerTapGestures$2$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function0 $doubleTapEnabled;
        final /* synthetic */ Function1 $onDoubleTap;
        final /* synthetic */ Function0 $onTap;
        private /* synthetic */ Object L$0;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function0 function0, Function0 function02, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$doubleTapEnabled = function0;
            this.$onTap = function02;
            this.$onDoubleTap = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$doubleTapEnabled, this.$onTap, this.$onDoubleTap, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x00ab, code lost:
        
            if (r10 == r0) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x004b, code lost:
        
            if (r10 == r0) goto L33;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x009f  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
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
                r3 = 3
                r4 = 4
                r5 = 2
                r6 = 1
                if (r1 == 0) goto L3b
                if (r1 == r6) goto L33
                if (r1 == r5) goto L29
                if (r1 == r3) goto L20
                if (r1 != r4) goto L18
                kotlin.ResultKt.throwOnFailure(r10)
                goto Lae
            L18:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L20:
                java.lang.Object r1 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                kotlin.ResultKt.throwOnFailure(r10)
                goto L9b
            L29:
                boolean r1 = r9.Z$0
                java.lang.Object r5 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r5 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                goto L74
            L33:
                java.lang.Object r1 = r9.L$0
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                kotlin.ResultKt.throwOnFailure(r10)
                goto L4e
            L3b:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                r1 = r10
                androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                r9.L$0 = r1
                r9.label = r6
                java.lang.Object r10 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r2, r9, r3)
                if (r10 != r0) goto L4e
                goto Lad
            L4e:
                androidx.compose.ui.input.pointer.PointerInputChange r10 = (androidx.compose.ui.input.pointer.PointerInputChange) r10
                r10.consume()
                kotlin.jvm.functions.Function0 r10 = r9.$doubleTapEnabled
                java.lang.Object r10 = r10.invoke()
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                r9.L$0 = r1
                r9.Z$0 = r10
                r9.label = r5
                kotlin.jvm.functions.Function3 r5 = androidx.compose.foundation.gestures.TapGestureDetectorKt.NoPressGesture
                androidx.compose.ui.input.pointer.PointerEventPass r5 = androidx.compose.ui.input.pointer.PointerEventPass.Main
                java.lang.Object r5 = androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForUpOrCancellation(r1, r5, r9)
                if (r5 != r0) goto L70
                goto Lad
            L70:
                r8 = r1
                r1 = r10
                r10 = r5
                r5 = r8
            L74:
                androidx.compose.ui.input.pointer.PointerInputChange r10 = (androidx.compose.ui.input.pointer.PointerInputChange) r10
                if (r10 == 0) goto Lc0
                r10.consume()
                kotlin.jvm.functions.Function0 r6 = r9.$onTap
                r6.invoke()
                if (r1 == 0) goto Lc0
                androidx.compose.ui.platform.ViewConfiguration r1 = r5.getViewConfiguration()
                long r6 = r1.getDoubleTapTimeoutMillis()
                com.android.systemui.common.ui.compose.gestures.EagerTapKt$detectEagerTapGestures$2$1$secondDown$1 r1 = new com.android.systemui.common.ui.compose.gestures.EagerTapKt$detectEagerTapGestures$2$1$secondDown$1
                r1.<init>(r10, r2)
                r9.L$0 = r5
                r9.label = r3
                java.lang.Object r10 = r5.withTimeoutOrNull(r6, r1, r9)
                if (r10 != r0) goto L9a
                goto Lad
            L9a:
                r1 = r5
            L9b:
                androidx.compose.ui.input.pointer.PointerInputChange r10 = (androidx.compose.ui.input.pointer.PointerInputChange) r10
                if (r10 == 0) goto Lc0
                r9.L$0 = r2
                r9.label = r4
                kotlin.jvm.functions.Function3 r10 = androidx.compose.foundation.gestures.TapGestureDetectorKt.NoPressGesture
                androidx.compose.ui.input.pointer.PointerEventPass r10 = androidx.compose.ui.input.pointer.PointerEventPass.Main
                java.lang.Object r10 = androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForUpOrCancellation(r1, r10, r9)
                if (r10 != r0) goto Lae
            Lad:
                return r0
            Lae:
                androidx.compose.ui.input.pointer.PointerInputChange r10 = (androidx.compose.ui.input.pointer.PointerInputChange) r10
                if (r10 == 0) goto Lc0
                r10.consume()
                kotlin.jvm.functions.Function1 r9 = r9.$onDoubleTap
                long r0 = r10.position
                androidx.compose.ui.geometry.Offset r10 = androidx.compose.ui.geometry.Offset.m393boximpl(r0)
                r9.mo779invoke(r10)
            Lc0:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.compose.gestures.EagerTapKt$detectEagerTapGestures$2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EagerTapKt$detectEagerTapGestures$2(PointerInputScope pointerInputScope, Function0 function0, Function0 function02, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$this_detectEagerTapGestures = pointerInputScope;
        this.$doubleTapEnabled = function0;
        this.$onTap = function02;
        this.$onDoubleTap = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EagerTapKt$detectEagerTapGestures$2(this.$this_detectEagerTapGestures, this.$doubleTapEnabled, this.$onTap, this.$onDoubleTap, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EagerTapKt$detectEagerTapGestures$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = this.$this_detectEagerTapGestures;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$doubleTapEnabled, this.$onTap, this.$onDoubleTap, null);
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
