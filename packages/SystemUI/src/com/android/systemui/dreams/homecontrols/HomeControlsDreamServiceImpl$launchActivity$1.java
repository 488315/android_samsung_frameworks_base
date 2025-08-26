package com.android.systemui.dreams.homecontrols;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.window.WindowContainerTransaction;
import com.android.systemui.dreams.homecontrols.service.TaskFragmentComponent;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsComponentInfo;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class HomeControlsDreamServiceImpl$launchActivity$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HomeControlsDreamServiceImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsDreamServiceImpl$launchActivity$1(HomeControlsDreamServiceImpl homeControlsDreamServiceImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsDreamServiceImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HomeControlsDreamServiceImpl$launchActivity$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsDreamServiceImpl$launchActivity$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow componentInfo = this.this$0.dataSource.getComponentInfo();
            this.label = 1;
            obj = FlowKt.first(componentInfo, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        HomeControlsComponentInfo homeControlsComponentInfo = (HomeControlsComponentInfo) obj;
        ComponentName componentName = homeControlsComponentInfo.componentName;
        Logger.d$default(this.this$0.logger, "Starting embedding " + componentName, null, 2, null);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.putExtra("android.service.controls.extra.LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS", homeControlsComponentInfo.allowTrivialControlsOnLockscreen);
        intent.putExtra("android.service.controls.extra.CONTROLS_SURFACE", 1);
        TaskFragmentComponent taskFragmentComponent = this.this$0.taskFragmentComponent;
        if (taskFragmentComponent == null) {
            taskFragmentComponent = null;
        }
        TaskFragmentComponent.Organizer organizer = taskFragmentComponent.organizer;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Binder binder = taskFragmentComponent.fragmentToken;
        IBinder activityToken = taskFragmentComponent.activity.getActivityToken();
        activityToken.getClass();
        organizer.applyTransaction(windowContainerTransaction.startActivityInTaskFragment(binder, activityToken, intent, (Bundle) null), 1, false);
        return Unit.INSTANCE;
    }
}
