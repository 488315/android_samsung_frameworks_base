package com.android.systemui.dreams.homecontrols.domain.interactor;

import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class HomeControlsComponentInteractor$panelComponent$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public HomeControlsComponentInteractor$panelComponent$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HomeControlsComponentInteractor$panelComponent$1 homeControlsComponentInteractor$panelComponent$1 = new HomeControlsComponentInteractor$panelComponent$1((Continuation) obj3);
        homeControlsComponentInteractor$panelComponent$1.L$0 = (List) obj;
        homeControlsComponentInteractor$panelComponent$1.L$1 = (SelectedComponentRepository.SelectedComponent) obj2;
        return homeControlsComponentInteractor$panelComponent$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        SelectedComponentRepository.SelectedComponent selectedComponent = (SelectedComponentRepository.SelectedComponent) this.L$1;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            if (Intrinsics.areEqual(((HomeControlsComponentInteractor.PanelComponent) obj2).componentName, selectedComponent != null ? selectedComponent.componentName : null)) {
                break;
            }
        }
        HomeControlsComponentInteractor.PanelComponent panelComponent = (HomeControlsComponentInteractor.PanelComponent) obj2;
        if (panelComponent == null) {
            panelComponent = (HomeControlsComponentInteractor.PanelComponent) CollectionsKt___CollectionsKt.firstOrNull(list);
        }
        if (panelComponent != null) {
            return panelComponent.panelActivity;
        }
        return null;
    }
}
