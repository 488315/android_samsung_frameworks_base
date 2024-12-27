package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptIconViewModel$iconOverlayAsset$1$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FingerprintSensorType.values().length];
            try {
                iArr[FingerprintSensorType.POWER_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$iconOverlayAsset$1$1(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        boolean booleanValue2 = ((Boolean) obj5).booleanValue();
        PromptIconViewModel$iconOverlayAsset$1$1 promptIconViewModel$iconOverlayAsset$1$1 = new PromptIconViewModel$iconOverlayAsset$1$1(this.this$0, (Continuation) obj6);
        promptIconViewModel$iconOverlayAsset$1$1.L$0 = (DisplayRotation) obj;
        promptIconViewModel$iconOverlayAsset$1$1.L$1 = (FingerprintSensorType) obj2;
        promptIconViewModel$iconOverlayAsset$1$1.L$2 = (PromptAuthState) obj3;
        promptIconViewModel$iconOverlayAsset$1$1.Z$0 = booleanValue;
        promptIconViewModel$iconOverlayAsset$1$1.Z$1 = booleanValue2;
        return promptIconViewModel$iconOverlayAsset$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DisplayRotation displayRotation = (DisplayRotation) this.L$0;
        FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) this.L$1;
        PromptAuthState promptAuthState = (PromptAuthState) this.L$2;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        int i = -1;
        if (WhenMappings.$EnumSwitchMapping$0[fingerprintSensorType.ordinal()] == 1) {
            PromptIconViewModel promptIconViewModel = this.this$0;
            boolean z3 = promptAuthState.isAuthenticated;
            StateFlowImpl stateFlowImpl = promptIconViewModel._previousIconOverlayWasError;
            if (z3) {
                if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                    int i2 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                    if (i2 == 1) {
                        i = R.raw.biometricprompt_symbol_error_to_success_portrait_topleft;
                    } else if (i2 != 2) {
                        i = R.raw.biometricprompt_symbol_error_to_success_landscape;
                        if (i2 != 3 && i2 != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                    } else {
                        i = R.raw.biometricprompt_symbol_error_to_success_portrait_bottomright;
                    }
                } else {
                    int i3 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                    if (i3 == 1) {
                        i = R.raw.biometricprompt_symbol_fingerprint_to_success_portrait_topleft;
                    } else if (i3 != 2) {
                        i = R.raw.biometricprompt_symbol_fingerprint_to_success_landscape;
                        if (i3 != 3 && i3 != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                    } else {
                        i = R.raw.biometricprompt_symbol_fingerprint_to_success_portrait_bottomright;
                    }
                }
            } else if (z) {
                if (((Boolean) stateFlowImpl.getValue()).booleanValue()) {
                    int i4 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                    if (i4 == 1) {
                        i = R.raw.biometricprompt_symbol_error_to_fingerprint_portrait_topleft;
                    } else if (i4 != 2) {
                        i = R.raw.biometricprompt_symbol_error_to_fingerprint_landscape;
                        if (i4 != 3 && i4 != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                    } else {
                        i = R.raw.biometricprompt_symbol_error_to_fingerprint_portrait_bottomright;
                    }
                } else {
                    int i5 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                    if (i5 != 1) {
                        if (i5 != 2) {
                            if (i5 != 3 && i5 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            i = R.raw.biometricprompt_fingerprint_to_error_landscape;
                        }
                        i = R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_bottomright;
                    }
                    i = R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft;
                }
            } else if (z2) {
                int i6 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
                if (i6 != 1) {
                    if (i6 != 2) {
                        if (i6 != 3 && i6 != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        i = R.raw.biometricprompt_fingerprint_to_error_landscape;
                    }
                    i = R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_bottomright;
                }
                i = R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft;
            }
        }
        return new Integer(i);
    }
}
