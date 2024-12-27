package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PromptIconViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptSelectorInteractor $promptSelectorInteractor$inlined;
    final /* synthetic */ PromptViewModel $promptViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, PromptIconViewModel promptIconViewModel, PromptSelectorInteractor promptSelectorInteractor, PromptViewModel promptViewModel) {
        super(3, continuation);
        this.this$0 = promptIconViewModel;
        this.$promptSelectorInteractor$inlined = promptSelectorInteractor;
        this.$promptViewModel$inlined = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$1 promptIconViewModel$special$$inlined$flatMapLatest$1 = new PromptIconViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$promptSelectorInteractor$inlined, this.$promptViewModel$inlined);
        promptIconViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int i2 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$1[((PromptIconViewModel.AuthType) this.L$1).ordinal()];
            if (i2 == 1) {
                final PromptIconViewModel promptIconViewModel = this.this$0;
                DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) promptIconViewModel.displayStateInteractor;
                ReadonlyStateFlow readonlyStateFlow = displayStateInteractorImpl.currentRotation;
                ReadonlyStateFlow readonlyStateFlow2 = displayStateInteractorImpl.isFolded;
                ReadonlyStateFlow readonlyStateFlow3 = displayStateInteractorImpl.isInRearDisplayMode;
                StateFlow stateFlow = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).sensorType;
                PromptViewModel promptViewModel = this.$promptViewModel$inlined;
                final Flow[] flowArr = {readonlyStateFlow, readonlyStateFlow2, readonlyStateFlow3, stateFlow, promptViewModel.isAuthenticated, promptViewModel.isAuthenticating, promptViewModel.showingError};
                flow = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;
                        final /* synthetic */ PromptIconViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass3(Continuation continuation, PromptIconViewModel promptIconViewModel) {
                            super(3, continuation);
                            this.this$0 = promptIconViewModel;
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            int i;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = this.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Object[] objArr = (Object[]) this.L$1;
                                Object obj2 = objArr[0];
                                Object obj3 = objArr[1];
                                Object obj4 = objArr[2];
                                Object obj5 = objArr[3];
                                Object obj6 = objArr[4];
                                Object obj7 = objArr[5];
                                boolean booleanValue = ((Boolean) objArr[6]).booleanValue();
                                boolean booleanValue2 = ((Boolean) obj7).booleanValue();
                                PromptAuthState promptAuthState = (PromptAuthState) obj6;
                                boolean booleanValue3 = ((Boolean) obj4).booleanValue();
                                boolean booleanValue4 = ((Boolean) obj3).booleanValue();
                                DisplayRotation displayRotation = (DisplayRotation) obj2;
                                if (PromptIconViewModel.WhenMappings.$EnumSwitchMapping$0[((FingerprintSensorType) obj5).ordinal()] == 1) {
                                    i = PromptIconViewModel.access$getSfpsIconViewAsset(this.this$0, displayRotation, booleanValue4, booleanValue3);
                                } else {
                                    PromptIconViewModel promptIconViewModel = this.this$0;
                                    boolean z = promptAuthState.isAuthenticated;
                                    StateFlowImpl stateFlowImpl = promptIconViewModel._previousIconWasError;
                                    if (z) {
                                        i = ((Boolean) stateFlowImpl.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_success_lottie : R.raw.fingerprint_dialogue_fingerprint_to_success_lottie;
                                    } else if (booleanValue2) {
                                        if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                            i = R.raw.fingerprint_dialogue_error_to_fingerprint_lottie;
                                        }
                                        i = R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
                                    } else {
                                        if (!booleanValue) {
                                            i = -1;
                                        }
                                        i = R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
                                    }
                                }
                                Integer num = new Integer(i);
                                this.label = 1;
                                if (flowCollector.emit(num, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr2.length];
                            }
                        }, new AnonymousClass3(null, promptIconViewModel), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
            } else if (i2 == 2) {
                flow = FlowKt.combine(FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isAuthenticated), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isAuthenticating), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isPendingConfirmation), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.showingError), new PromptIconViewModel$iconAsset$1$2(this.this$0, null));
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                final PromptIconViewModel promptIconViewModel2 = this.this$0;
                DisplayStateInteractorImpl displayStateInteractorImpl2 = (DisplayStateInteractorImpl) promptIconViewModel2.displayStateInteractor;
                ReadonlyStateFlow readonlyStateFlow4 = displayStateInteractorImpl2.currentRotation;
                ReadonlyStateFlow readonlyStateFlow5 = displayStateInteractorImpl2.isFolded;
                ReadonlyStateFlow readonlyStateFlow6 = displayStateInteractorImpl2.isInRearDisplayMode;
                StateFlow stateFlow2 = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).sensorType;
                PromptViewModel promptViewModel2 = this.$promptViewModel$inlined;
                final Flow[] flowArr2 = {readonlyStateFlow4, readonlyStateFlow5, readonlyStateFlow6, stateFlow2, promptViewModel2.isAuthenticated, promptViewModel2.isAuthenticating, promptViewModel2.isPendingConfirmation, promptViewModel2.showingError};
                flow = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;
                        final /* synthetic */ PromptIconViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass3(Continuation continuation, PromptIconViewModel promptIconViewModel) {
                            super(3, continuation);
                            this.this$0 = promptIconViewModel;
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            int i;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = this.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Object[] objArr = (Object[]) this.L$1;
                                Object obj2 = objArr[0];
                                Object obj3 = objArr[1];
                                Object obj4 = objArr[2];
                                Object obj5 = objArr[3];
                                Object obj6 = objArr[4];
                                Object obj7 = objArr[5];
                                Object obj8 = objArr[6];
                                boolean booleanValue = ((Boolean) objArr[7]).booleanValue();
                                boolean booleanValue2 = ((Boolean) obj8).booleanValue();
                                boolean booleanValue3 = ((Boolean) obj7).booleanValue();
                                PromptAuthState promptAuthState = (PromptAuthState) obj6;
                                boolean booleanValue4 = ((Boolean) obj4).booleanValue();
                                boolean booleanValue5 = ((Boolean) obj3).booleanValue();
                                DisplayRotation displayRotation = (DisplayRotation) obj2;
                                if (PromptIconViewModel.WhenMappings.$EnumSwitchMapping$0[((FingerprintSensorType) obj5).ordinal()] == 1) {
                                    i = PromptIconViewModel.access$getSfpsIconViewAsset(this.this$0, displayRotation, booleanValue5, booleanValue4);
                                } else {
                                    PromptIconViewModel promptIconViewModel = this.this$0;
                                    promptIconViewModel.getClass();
                                    boolean z = promptAuthState.isAuthenticated;
                                    if (z && promptAuthState.wasConfirmed) {
                                        i = R.raw.fingerprint_dialogue_unlocked_to_checkmark_success_lottie;
                                    } else {
                                        StateFlowImpl stateFlowImpl = promptIconViewModel._previousIconWasError;
                                        if (booleanValue2) {
                                            i = ((Boolean) stateFlowImpl.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_unlock_lottie : R.raw.fingerprint_dialogue_fingerprint_to_unlock_lottie;
                                        } else if (z) {
                                            i = ((Boolean) stateFlowImpl.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_success_lottie : R.raw.fingerprint_dialogue_fingerprint_to_success_lottie;
                                        } else if (booleanValue3) {
                                            if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                                i = R.raw.fingerprint_dialogue_error_to_fingerprint_lottie;
                                            }
                                            i = R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
                                        } else {
                                            if (!booleanValue) {
                                                i = -1;
                                            }
                                            i = R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
                                        }
                                    }
                                }
                                Integer num = new Integer(i);
                                this.label = 1;
                                if (flowCollector.emit(num, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr3 = flowArr2;
                        Object combineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr3.length];
                            }
                        }, new AnonymousClass3(null, promptIconViewModel2), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
