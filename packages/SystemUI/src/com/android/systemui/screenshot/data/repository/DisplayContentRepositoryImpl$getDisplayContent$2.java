package com.android.systemui.screenshot.data.repository;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DisplayContentRepositoryImpl$getDisplayContent$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $displayId;
    int label;
    final /* synthetic */ DisplayContentRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayContentRepositoryImpl$getDisplayContent$2(DisplayContentRepositoryImpl displayContentRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displayContentRepositoryImpl;
        this.$displayId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisplayContentRepositoryImpl$getDisplayContent$2(this.this$0, this.$displayId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayContentRepositoryImpl$getDisplayContent$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        List allRootTaskInfosOnDisplay = this.this$0.atmService.getAllRootTaskInfosOnDisplay(this.$displayId);
        DisplayContentRepositoryImpl displayContentRepositoryImpl = this.this$0;
        int i2 = this.$displayId;
        allRootTaskInfosOnDisplay.getClass();
        this.label = 1;
        Object access$toDisplayTasksModel = DisplayContentRepositoryImpl.access$toDisplayTasksModel(displayContentRepositoryImpl, i2, allRootTaskInfosOnDisplay, this);
        return access$toDisplayTasksModel == coroutineSingletons ? coroutineSingletons : access$toDisplayTasksModel;
    }
}
