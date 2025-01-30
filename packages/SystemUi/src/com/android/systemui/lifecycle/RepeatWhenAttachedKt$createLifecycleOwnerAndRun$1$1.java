package com.android.systemui.lifecycle;

import android.view.View;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.lifecycle.RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1", m277f = "RepeatWhenAttached.kt", m278l = {118}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function3 $block;
    final /* synthetic */ ViewLifecycleOwner $this_apply;
    final /* synthetic */ View $view;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(Function3 function3, ViewLifecycleOwner viewLifecycleOwner, View view, Continuation<? super RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1> continuation) {
        super(2, continuation);
        this.$block = function3;
        this.$this_apply = viewLifecycleOwner;
        this.$view = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(this.$block, this.$this_apply, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Function3 function3 = this.$block;
            ViewLifecycleOwner viewLifecycleOwner = this.$this_apply;
            View view = this.$view;
            this.label = 1;
            if (function3.invoke(viewLifecycleOwner, view, this) == coroutineSingletons) {
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
