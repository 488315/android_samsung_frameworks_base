package com.android.systemui.keyguard.ui.binder;

import android.util.Log;
import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManager;
import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManagerKt;
import com.android.systemui.keyguard.ui.viewmodel.InWindowLauncherAnimationViewModel;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
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
final class InWindowLauncherAnimationViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ InWindowLauncherUnlockAnimationManager $inWindowLauncherUnlockAnimationManager;
    final /* synthetic */ InWindowLauncherAnimationViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InWindowLauncherAnimationViewBinder$bind$2(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = inWindowLauncherAnimationViewModel;
        this.$inWindowLauncherUnlockAnimationManager = inWindowLauncherUnlockAnimationManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new InWindowLauncherAnimationViewBinder$bind$2(this.$viewModel, this.$inWindowLauncherUnlockAnimationManager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((InWindowLauncherAnimationViewBinder$bind$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow = this.$viewModel.shouldStartInWindowAnimation;
            final InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager = this.$inWindowLauncherUnlockAnimationManager;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder$bind$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager2 = InWindowLauncherUnlockAnimationManager.this;
                    if (booleanValue) {
                        int i2 = InWindowLauncherUnlockAnimationManager.$r8$clinit;
                        if (inWindowLauncherUnlockAnimationManager2.preparedForUnlock) {
                            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = inWindowLauncherUnlockAnimationManager2.launcherAnimationController;
                            if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                                iLauncherUnlockAnimationController$Stub$Proxy.playUnlockAnimation(633L, true, 100L);
                                inWindowLauncherUnlockAnimationManager2.interactor.repository.startedUnlockAnimation.updateState(null, Boolean.TRUE);
                            }
                        } else {
                            Log.e(InWindowLauncherUnlockAnimationManagerKt.TAG, "Attempted to call playUnlockAnimation() before prepareToUnlock().");
                        }
                        inWindowLauncherUnlockAnimationManager2.preparedForUnlock = false;
                    } else {
                        inWindowLauncherUnlockAnimationManager2.interactor.repository.startedUnlockAnimation.updateState(null, Boolean.FALSE);
                    }
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
