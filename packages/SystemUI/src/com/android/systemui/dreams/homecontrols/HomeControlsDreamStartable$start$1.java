package com.android.systemui.dreams.homecontrols;

import android.content.ComponentName;
import com.android.internal.hidden_from_bootclasspath.android.service.controls.flags.Flags;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class HomeControlsDreamStartable$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HomeControlsDreamStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsDreamStartable$start$1(HomeControlsDreamStartable homeControlsDreamStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = homeControlsDreamStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HomeControlsDreamStartable$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeControlsDreamStartable$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (!Flags.homePanelDream()) {
                HomeControlsDreamStartable homeControlsDreamStartable = this.this$0;
                homeControlsDreamStartable.getClass();
                homeControlsDreamStartable.packageManager.setComponentEnabledSetting(homeControlsDreamStartable.componentName, 2, 1);
                return Unit.INSTANCE;
            }
            final HomeControlsDreamStartable homeControlsDreamStartable2 = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = homeControlsDreamStartable2.homeControlsComponentInteractor.panelComponent;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.homecontrols.HomeControlsDreamStartable$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean z = ((ComponentName) obj2) != null;
                    HomeControlsDreamStartable homeControlsDreamStartable3 = HomeControlsDreamStartable.this;
                    homeControlsDreamStartable3.getClass();
                    homeControlsDreamStartable3.packageManager.setComponentEnabledSetting(homeControlsDreamStartable3.componentName, z ? 1 : 2, 1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
