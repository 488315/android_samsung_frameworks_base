package com.android.systemui.dreams.homecontrols.domain.interactor;

import android.content.ComponentName;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1 homeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1 = new HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1((Continuation) obj3);
        homeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1.L$0 = (List) obj;
        homeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1.L$1 = (Set) obj2;
        return homeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<ControlsServiceInfo> list = (List) this.L$0;
        Set set = (Set) this.L$1;
        ArrayList arrayList = new ArrayList();
        for (ControlsServiceInfo controlsServiceInfo : list) {
            ComponentName componentName = controlsServiceInfo.panelActivity;
            HomeControlsComponentInteractor.PanelComponent panelComponent = (!set.contains(controlsServiceInfo.componentName.getPackageName()) || componentName == null) ? null : new HomeControlsComponentInteractor.PanelComponent(controlsServiceInfo.componentName, componentName);
            if (panelComponent != null) {
                arrayList.add(panelComponent);
            }
        }
        return arrayList;
    }
}
