package com.android.systemui.biometrics.ui.binder;

import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.shared.model.BiometricModalityKt;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

final class Spaghetti$onAuthenticationSucceeded$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $modality;
    int label;
    final /* synthetic */ Spaghetti this$0;

    public Spaghetti$onAuthenticationSucceeded$1(int i, Spaghetti spaghetti, Continuation continuation) {
        super(2, continuation);
        this.$modality = i;
        this.this$0 = spaghetti;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$onAuthenticationSucceeded$1(this.$modality, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$onAuthenticationSucceeded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BiometricModality asBiometricModality = BiometricModalityKt.asBiometricModality(this.$modality);
            Spaghetti spaghetti = this.this$0;
            spaghetti.getClass();
            Integer num = null;
            if (asBiometricModality == BiometricModality.Face) {
                if (spaghetti.modalities.getHasUdfps()) {
                    num = Integer.valueOf(R.string.biometric_dialog_tap_confirm_with_face_alt_1);
                } else if (spaghetti.modalities.getHasSfps()) {
                    num = Integer.valueOf(R.string.biometric_dialog_tap_confirm_with_face_sfps);
                }
            }
            Spaghetti spaghetti2 = this.this$0;
            PromptViewModel promptViewModel = spaghetti2.viewModel;
            String string = num != null ? spaghetti2.applicationContext.getString(num.intValue()) : "";
            Intrinsics.checkNotNull(string);
            this.label = 1;
            if (promptViewModel.showAuthenticated(asBiometricModality, 500L, string, this) == coroutineSingletons) {
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
