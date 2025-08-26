package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
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

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class PointerInputScopeExtKt$observeTaps$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onTap;
    final /* synthetic */ PointerEventPass $pass;
    final /* synthetic */ boolean $shouldConsume;
    final /* synthetic */ PointerInputScope $this_observeTaps;
    int label;

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

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0056, code lost:
        
            if (r8 == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            AwaitPointerEventScope awaitPointerEventScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                PointerEventPass pointerEventPass = this.$pass;
                this.L$0 = awaitPointerEventScope;
                this.label = 1;
                obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, pointerEventPass, this, 1);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                PointerInputChange pointerInputChange = (PointerInputChange) obj;
                if (pointerInputChange != null) {
                    this.$onTap.mo781invoke(Offset.m395boximpl(pointerInputChange.position));
                }
                return Unit.INSTANCE;
            }
            awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
            if (this.$shouldConsume) {
                pointerInputChange2.consume();
            }
            long longPressTimeoutMillis = awaitPointerEventScope.getViewConfiguration().getLongPressTimeoutMillis();
            PointerInputScopeExtKt$observeTaps$2$1$up$1 pointerInputScopeExtKt$observeTaps$2$1$up$1 = new PointerInputScopeExtKt$observeTaps$2$1$up$1(this.$pass, null);
            this.L$0 = null;
            this.label = 2;
            obj = awaitPointerEventScope.withTimeoutOrNull(longPressTimeoutMillis, pointerInputScopeExtKt$observeTaps$2$1$up$1, this);
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
