package com.android.systemui.dreams.homecontrols.system;

import android.content.ComponentName;
import com.android.systemui.settings.UserTrackerImpl;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
            final HomeControlsDreamStartable homeControlsDreamStartable = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = homeControlsDreamStartable.homeControlsComponentInteractor.panelComponent;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.homecontrols.system.HomeControlsDreamStartable$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean z = ((ComponentName) obj2) != null;
                    int i2 = HomeControlsDreamStartable.$r8$clinit;
                    HomeControlsDreamStartable homeControlsDreamStartable2 = HomeControlsDreamStartable.this;
                    homeControlsDreamStartable2.getClass();
                    ((UserTrackerImpl) homeControlsDreamStartable2.userContextProvider).getUserContext().getPackageManager().setComponentEnabledSetting(homeControlsDreamStartable2.componentName, z ? 1 : 2, 1);
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
