package com.android.systemui.keyguard.ui.binder;

import android.graphics.Rect;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class InWindowLauncherAnimationViewBinder$bind$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ InWindowLauncherUnlockAnimationManager $inWindowLauncherUnlockAnimationManager;
    final /* synthetic */ InWindowLauncherAnimationViewModel $viewModel;
    int label;

    public InWindowLauncherAnimationViewBinder$bind$1(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = inWindowLauncherAnimationViewModel;
        this.$inWindowLauncherUnlockAnimationManager = inWindowLauncherUnlockAnimationManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new InWindowLauncherAnimationViewBinder$bind$1(this.$viewModel, this.$inWindowLauncherUnlockAnimationManager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((InWindowLauncherAnimationViewBinder$bind$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow = this.$viewModel.shouldPrepareForInWindowAnimation;
            final InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager = this.$inWindowLauncherUnlockAnimationManager;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder$bind$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager2 = InWindowLauncherUnlockAnimationManager.this;
                    if (booleanValue) {
                        ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = inWindowLauncherUnlockAnimationManager2.launcherAnimationController;
                        if (iLauncherUnlockAnimationController$Stub$Proxy != null && !inWindowLauncherUnlockAnimationManager2.preparedForUnlock) {
                            inWindowLauncherUnlockAnimationManager2.preparedForUnlock = true;
                            inWindowLauncherUnlockAnimationManager2.manualUnlockAmount = null;
                            iLauncherUnlockAnimationController$Stub$Proxy.prepareForUnlock(0, false, new Rect());
                        }
                    } else {
                        boolean z = inWindowLauncherUnlockAnimationManager2.preparedForUnlock && !((Boolean) inWindowLauncherUnlockAnimationManager2.interactor.startedUnlockAnimation.$$delegate_0.getValue()).booleanValue();
                        Float f = inWindowLauncherUnlockAnimationManager2.manualUnlockAmount;
                        boolean z2 = (f == null || Intrinsics.areEqual(f, 1.0f)) ? false : true;
                        if (z) {
                            Log.e(InWindowLauncherUnlockAnimationManagerKt.TAG, "Called prepareForUnlock(), but not playUnlockAnimation(). Failing-safe by calling setUnlockAmount(1f)");
                            inWindowLauncherUnlockAnimationManager2.preparedForUnlock = false;
                            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy2 = inWindowLauncherUnlockAnimationManager2.launcherAnimationController;
                            if (iLauncherUnlockAnimationController$Stub$Proxy2 != null) {
                                inWindowLauncherUnlockAnimationManager2.manualUnlockAmount = Float.valueOf(1.0f);
                                iLauncherUnlockAnimationController$Stub$Proxy2.setUnlockAmount(1.0f, true);
                            }
                        } else if (z2) {
                            Log.e(InWindowLauncherUnlockAnimationManagerKt.TAG, "Unlock has ended, but manual unlock amount != 1f. Failing-safe by calling setUnlockAmount(1f)");
                            inWindowLauncherUnlockAnimationManager2.preparedForUnlock = false;
                            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy3 = inWindowLauncherUnlockAnimationManager2.launcherAnimationController;
                            if (iLauncherUnlockAnimationController$Stub$Proxy3 != null) {
                                inWindowLauncherUnlockAnimationManager2.manualUnlockAmount = Float.valueOf(1.0f);
                                iLauncherUnlockAnimationController$Stub$Proxy3.setUnlockAmount(1.0f, true);
                            }
                        }
                        inWindowLauncherUnlockAnimationManager2.manualUnlockAmount = null;
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
