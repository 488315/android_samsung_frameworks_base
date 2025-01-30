package com.android.systemui.keyboard.backlight.p007ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyboard.backlight.ui.viewmodel.BacklightDialogViewModel$timeout$1$1", m277f = "BacklightDialogViewModel.kt", m278l = {62, 63, 64}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class BacklightDialogViewModel$timeout$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $emitAfterTimeout;
    final /* synthetic */ Object $it;
    final /* synthetic */ long $timeoutMillis;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BacklightDialogViewModel$timeout$1$1(Object obj, long j, Object obj2, Continuation<? super BacklightDialogViewModel$timeout$1$1> continuation) {
        super(2, continuation);
        this.$it = obj;
        this.$timeoutMillis = j;
        this.$emitAfterTimeout = obj2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BacklightDialogViewModel$timeout$1$1 backlightDialogViewModel$timeout$1$1 = new BacklightDialogViewModel$timeout$1$1(this.$it, this.$timeoutMillis, this.$emitAfterTimeout, continuation);
        backlightDialogViewModel$timeout$1$1.L$0 = obj;
        return backlightDialogViewModel$timeout$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BacklightDialogViewModel$timeout$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005a A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            Object obj3 = this.$it;
            this.L$0 = flowCollector2;
            this.label = 1;
            if (flowCollector2.emit(obj3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            flowCollector = flowCollector2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                obj2 = this.$emitAfterTimeout;
                this.L$0 = null;
                this.label = 3;
                if (flowCollector.emit(obj2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        long j = this.$timeoutMillis;
        this.L$0 = flowCollector;
        this.label = 2;
        if (DelayKt.delay(j, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        obj2 = this.$emitAfterTimeout;
        this.L$0 = null;
        this.label = 3;
        if (flowCollector.emit(obj2, this) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
