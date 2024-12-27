package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.util.Log;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

final class ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $displayId;
    int label;
    final /* synthetic */ ScreenshotPolicyImpl this$0;

    public ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenshotPolicyImpl;
        this.$displayId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(this.this$0, this.$displayId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            List allRootTaskInfosOnDisplay = this.this$0.atmService.getAllRootTaskInfosOnDisplay(this.$displayId);
            Intrinsics.checkNotNull(allRootTaskInfosOnDisplay);
            return allRootTaskInfosOnDisplay;
        } catch (RemoteException e) {
            Log.e("ScreenshotPolicyImpl", "getAllRootTaskInfosOnDisplay", e);
            return EmptyList.INSTANCE;
        }
    }
}
