package com.android.systemui.dreams.homecontrols.domain.interactor;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor$packageChangedInternal$$inlined$filter$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ HomeControlsComponentInteractor this$0;

    public HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1(Continuation continuation, HomeControlsComponentInteractor homeControlsComponentInteractor) {
        super(3, continuation);
        this.this$0 = homeControlsComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1 homeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1 = new HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        homeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        homeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1.L$1 = obj2;
        return homeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow transformLatest;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ComponentName componentName = (ComponentName) this.L$1;
            if (componentName == null) {
                transformLatest = EmptyFlow.INSTANCE;
            } else {
                PackageChangeInteractor packageChangeInteractor = this.this$0.packageChangeInteractor;
                UserHandle userHandle = UserHandle.CURRENT;
                String packageName = componentName.getPackageName();
                packageChangeInteractor.getClass();
                transformLatest = userHandle.equals(userHandle) ? FlowKt.transformLatest(packageChangeInteractor.userInteractor.selectedUser, new PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1(null, packageChangeInteractor, packageName)) : new PackageChangeInteractor$packageChangedInternal$$inlined$filter$1(((PackageChangeRepositoryImpl) packageChangeInteractor.packageChangeRepository).packageChanged(userHandle), packageName);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, transformLatest, flowCollector) == coroutineSingletons) {
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
