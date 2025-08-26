package com.android.systemui.keyguard.ui.binder;

import android.graphics.Rect;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
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

/* loaded from: classes2.dex */
public final class InWindowLauncherAnimationViewBinder {

    /* renamed from: com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ InWindowLauncherUnlockAnimationManager $inWindowLauncherUnlockAnimationManager;
        final /* synthetic */ InWindowLauncherAnimationViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = inWindowLauncherAnimationViewModel;
            this.$inWindowLauncherUnlockAnimationManager = inWindowLauncherUnlockAnimationManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$inWindowLauncherUnlockAnimationManager, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlyStateFlow readonlyStateFlow = this.$viewModel.shouldPrepareForInWindowAnimation;
                final InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager = this.$inWindowLauncherUnlockAnimationManager;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder.bind.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager2 = inWindowLauncherUnlockAnimationManager;
                        if (zBooleanValue) {
                            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = inWindowLauncherUnlockAnimationManager2.launcherAnimationController;
                            if (iLauncherUnlockAnimationController$Stub$Proxy != null && !inWindowLauncherUnlockAnimationManager2.preparedForUnlock) {
                                inWindowLauncherUnlockAnimationManager2.preparedForUnlock = true;
                                inWindowLauncherUnlockAnimationManager2.manualUnlockAmount = null;
                                iLauncherUnlockAnimationController$Stub$Proxy.prepareForUnlock(new Rect());
                            }
                        } else {
                            boolean z = inWindowLauncherUnlockAnimationManager2.preparedForUnlock && !((Boolean) inWindowLauncherUnlockAnimationManager2.interactor.startedUnlockAnimation.$$delegate_0.getValue()).booleanValue();
                            Float f = inWindowLauncherUnlockAnimationManager2.manualUnlockAmount;
                            boolean z2 = (f == null || Intrinsics.areEqual(f, 1.0f)) ? false : true;
                            if (z) {
                                Log.e(InWindowLauncherUnlockAnimationManagerKt.TAG, "Called prepareForUnlock(), but not playUnlockAnimation(). Failing-safe by calling setUnlockAmount(1f)");
                                inWindowLauncherUnlockAnimationManager2.setUnlockAmount();
                            } else if (z2) {
                                Log.e(InWindowLauncherUnlockAnimationManagerKt.TAG, "Unlock has ended, but manual unlock amount != 1f. Failing-safe by calling setUnlockAmount(1f)");
                                inWindowLauncherUnlockAnimationManager2.setUnlockAmount();
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ InWindowLauncherUnlockAnimationManager $inWindowLauncherUnlockAnimationManager;
        final /* synthetic */ InWindowLauncherAnimationViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = inWindowLauncherAnimationViewModel;
            this.$inWindowLauncherUnlockAnimationManager = inWindowLauncherUnlockAnimationManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$viewModel, this.$inWindowLauncherUnlockAnimationManager, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlyStateFlow readonlyStateFlow = this.$viewModel.shouldStartInWindowAnimation;
                final InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager = this.$inWindowLauncherUnlockAnimationManager;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.InWindowLauncherAnimationViewBinder.bind.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager2 = inWindowLauncherUnlockAnimationManager;
                        if (zBooleanValue) {
                            int i2 = InWindowLauncherUnlockAnimationManager.$r8$clinit;
                            if (inWindowLauncherUnlockAnimationManager2.preparedForUnlock) {
                                ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = inWindowLauncherUnlockAnimationManager2.launcherAnimationController;
                                if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                                    iLauncherUnlockAnimationController$Stub$Proxy.playUnlockAnimation(633L, 100L);
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

    static {
        new InWindowLauncherAnimationViewBinder();
    }

    private InWindowLauncherAnimationViewBinder() {
    }

    public static final void bind(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, CoroutineScope coroutineScope) {
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(inWindowLauncherAnimationViewModel, inWindowLauncherUnlockAnimationManager, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(inWindowLauncherAnimationViewModel, inWindowLauncherUnlockAnimationManager, null), 7);
    }
}
