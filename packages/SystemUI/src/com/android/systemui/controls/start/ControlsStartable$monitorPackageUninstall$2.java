package com.android.systemui.controls.start;

import com.android.systemui.common.shared.model.PackageChangeModel;
import com.android.systemui.controls.panels.SelectedComponentRepositoryImpl;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class ControlsStartable$monitorPackageUninstall$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ControlsStartable this$0;

    public ControlsStartable$monitorPackageUninstall$2(ControlsStartable controlsStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = controlsStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ControlsStartable$monitorPackageUninstall$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ControlsStartable$monitorPackageUninstall$2) create((PackageChangeModel.Uninstalled) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SelectedComponentRepositoryImpl selectedComponentRepositoryImpl = (SelectedComponentRepositoryImpl) this.this$0.selectedComponentRepository;
        ((UserFileManagerImpl) selectedComponentRepositoryImpl.userFileManager).getSharedPreferences$1(((UserTrackerImpl) selectedComponentRepositoryImpl.userTracker).getUserId(), SystemUIAnalytics.CONTROL_PREF_NAME).edit().remove("controls_component").remove("controls_structure").remove("controls_is_panel").apply();
        return Unit.INSTANCE;
    }
}
