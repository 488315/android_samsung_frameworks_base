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
        Flow flowCombine;
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
                ReadonlyStateFlow readonlyStateFlow2 = displayStateInteractorImpl.isInRearDisplayMode;
                StateFlow stateFlow = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).fingerprintSensorType;
                PromptViewModel promptViewModel = this.$promptViewModel$inlined;
                final Flow[] flowArr = {readonlyStateFlow, readonlyStateFlow2, stateFlow, promptViewModel.isAuthenticated, promptViewModel.isAuthenticating, promptViewModel.showingError};
                flowCombine = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1

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

                        /* JADX WARN: Removed duplicated region for block: B:37:0x00c7  */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Object[] objArr = (Object[]) this.L$1;
                                Object obj2 = objArr[0];
                                Object obj3 = objArr[1];
                                Object obj4 = objArr[2];
                                Object obj5 = objArr[3];
                                Object obj6 = objArr[4];
                                boolean zBooleanValue = ((Boolean) objArr[5]).booleanValue();
                                boolean zBooleanValue2 = ((Boolean) obj6).booleanValue();
                                PromptAuthState promptAuthState = (PromptAuthState) obj5;
                                boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
                                DisplayRotation displayRotation = (DisplayRotation) obj2;
                                int sfpsAsset_fingerprintToError = -1;
                                if (PromptIconViewModel.WhenMappings.$EnumSwitchMapping$0[((FingerprintSensorType) obj4).ordinal()] == 1) {
                                    PromptIconViewModel promptIconViewModel = this.this$0;
                                    boolean z = promptAuthState.isAuthenticated;
                                    StateFlowImpl stateFlowImpl = promptIconViewModel._previousIconWasError;
                                    if (z) {
                                        sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl.getValue()).booleanValue() ? R.raw.biometricprompt_sfps_error_to_success : PromptIconViewModel.getSfpsAsset_fingerprintToSuccess(displayRotation, zBooleanValue3);
                                    } else if (zBooleanValue2) {
                                        if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                            sfpsAsset_fingerprintToError = PromptIconViewModel.getSfpsAsset_errorToFingerprint(displayRotation, zBooleanValue3);
                                        } else {
                                            sfpsAsset_fingerprintToError = zBooleanValue3 ? R.raw.biometricprompt_sfps_rear_display_fingerprint_authenticating : R.raw.biometricprompt_sfps_fingerprint_authenticating;
                                        }
                                    } else if (zBooleanValue) {
                                        sfpsAsset_fingerprintToError = PromptIconViewModel.getSfpsAsset_fingerprintToError(displayRotation, zBooleanValue3);
                                    }
                                } else {
                                    PromptIconViewModel promptIconViewModel2 = this.this$0;
                                    boolean z2 = promptAuthState.isAuthenticated;
                                    StateFlowImpl stateFlowImpl2 = promptIconViewModel2._previousIconWasError;
                                    if (z2) {
                                        sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl2.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_success_lottie : R.raw.fingerprint_dialogue_fingerprint_to_success_lottie;
                                    } else if (zBooleanValue2) {
                                        sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl2.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_fingerprint_lottie : R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
                                    } else if (zBooleanValue) {
                                    }
                                }
                                Integer num = new Integer(sfpsAsset_fingerprintToError);
                                this.label = 1;
                                if (flowCollector.emit(num, this) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr2.length];
                            }
                        }, new AnonymousClass3(null, promptIconViewModel), flowCollector2, continuation);
                        return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                    }
                };
            } else if (i2 == 2) {
                flowCombine = FlowKt.combine(FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isAuthenticated), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isAuthenticating), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isPendingConfirmation), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.showingError), new PromptIconViewModel$iconAsset$1$2(this.this$0, null));
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                final PromptIconViewModel promptIconViewModel2 = this.this$0;
                DisplayStateInteractorImpl displayStateInteractorImpl2 = (DisplayStateInteractorImpl) promptIconViewModel2.displayStateInteractor;
                ReadonlyStateFlow readonlyStateFlow3 = displayStateInteractorImpl2.currentRotation;
                ReadonlyStateFlow readonlyStateFlow4 = displayStateInteractorImpl2.isInRearDisplayMode;
                StateFlow stateFlow2 = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).fingerprintSensorType;
                PromptViewModel promptViewModel2 = this.$promptViewModel$inlined;
                final Flow[] flowArr2 = {readonlyStateFlow3, readonlyStateFlow4, stateFlow2, promptViewModel2.isAuthenticated, promptViewModel2.isAuthenticating, promptViewModel2.isPendingConfirmation, promptViewModel2.showingError};
                flowCombine = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2

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

                        /* JADX WARN: Removed duplicated region for block: B:77:0x016d  */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Object[] objArr = (Object[]) this.L$1;
                                Object obj2 = objArr[0];
                                Object obj3 = objArr[1];
                                Object obj4 = objArr[2];
                                Object obj5 = objArr[3];
                                Object obj6 = objArr[4];
                                Object obj7 = objArr[5];
                                boolean zBooleanValue = ((Boolean) objArr[6]).booleanValue();
                                boolean zBooleanValue2 = ((Boolean) obj7).booleanValue();
                                boolean zBooleanValue3 = ((Boolean) obj6).booleanValue();
                                PromptAuthState promptAuthState = (PromptAuthState) obj5;
                                boolean zBooleanValue4 = ((Boolean) obj3).booleanValue();
                                DisplayRotation displayRotation = (DisplayRotation) obj2;
                                int sfpsAsset_fingerprintToError = -1;
                                if (PromptIconViewModel.WhenMappings.$EnumSwitchMapping$0[((FingerprintSensorType) obj4).ordinal()] == 1) {
                                    PromptIconViewModel promptIconViewModel = this.this$0;
                                    promptIconViewModel.getClass();
                                    if (promptAuthState.isAuthenticatedAndExplicitlyConfirmed()) {
                                        sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_unlock_to_success;
                                    } else {
                                        StateFlowImpl stateFlowImpl = promptIconViewModel._previousIconWasError;
                                        if (zBooleanValue2) {
                                            if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                                sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_error_to_unlock;
                                            } else if (zBooleanValue4) {
                                                int i2 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                                                if (i2 == 1) {
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock;
                                                } else if (i2 == 2) {
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock_90;
                                                } else if (i2 == 3) {
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock_180;
                                                } else {
                                                    if (i2 != 4) {
                                                        throw new NoWhenBranchMatchedException();
                                                    }
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_rear_display_fingerprint_to_unlock_270;
                                                }
                                            } else {
                                                int i3 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                                                if (i3 == 1) {
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_fingerprint_to_unlock;
                                                } else if (i3 == 2) {
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_fingerprint_to_unlock_90;
                                                } else if (i3 == 3) {
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_fingerprint_to_unlock_180;
                                                } else {
                                                    if (i3 != 4) {
                                                        throw new NoWhenBranchMatchedException();
                                                    }
                                                    sfpsAsset_fingerprintToError = R.raw.biometricprompt_sfps_fingerprint_to_unlock_270;
                                                }
                                            }
                                        } else if (promptAuthState.isAuthenticated) {
                                            sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl.getValue()).booleanValue() ? R.raw.biometricprompt_sfps_error_to_success : PromptIconViewModel.getSfpsAsset_fingerprintToSuccess(displayRotation, zBooleanValue4);
                                        } else if (zBooleanValue3) {
                                            if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                                                sfpsAsset_fingerprintToError = PromptIconViewModel.getSfpsAsset_errorToFingerprint(displayRotation, zBooleanValue4);
                                            } else {
                                                sfpsAsset_fingerprintToError = zBooleanValue4 ? R.raw.biometricprompt_sfps_rear_display_fingerprint_authenticating : R.raw.biometricprompt_sfps_fingerprint_authenticating;
                                            }
                                        } else if (zBooleanValue) {
                                            sfpsAsset_fingerprintToError = PromptIconViewModel.getSfpsAsset_fingerprintToError(displayRotation, zBooleanValue4);
                                        }
                                    }
                                } else {
                                    PromptIconViewModel promptIconViewModel2 = this.this$0;
                                    promptIconViewModel2.getClass();
                                    if (promptAuthState.isAuthenticatedAndExplicitlyConfirmed()) {
                                        sfpsAsset_fingerprintToError = R.raw.fingerprint_dialogue_unlocked_to_checkmark_success_lottie;
                                    } else {
                                        StateFlowImpl stateFlowImpl2 = promptIconViewModel2._previousIconWasError;
                                        if (zBooleanValue2) {
                                            sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl2.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_unlock_lottie : R.raw.fingerprint_dialogue_fingerprint_to_unlock_lottie;
                                        } else if (promptAuthState.isAuthenticated) {
                                            sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl2.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_success_lottie : R.raw.fingerprint_dialogue_fingerprint_to_success_lottie;
                                        } else if (zBooleanValue3) {
                                            sfpsAsset_fingerprintToError = ((Boolean) stateFlowImpl2.getValue()).booleanValue() ? R.raw.fingerprint_dialogue_error_to_fingerprint_lottie : R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
                                        } else if (zBooleanValue) {
                                        }
                                    }
                                }
                                Integer num = new Integer(sfpsAsset_fingerprintToError);
                                this.label = 1;
                                if (flowCollector.emit(num, this) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr3 = flowArr2;
                        Object objCombineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr3.length];
                            }
                        }, new AnonymousClass3(null, promptIconViewModel2), flowCollector2, continuation);
                        return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowCombine, this) == coroutineSingletons) {
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
