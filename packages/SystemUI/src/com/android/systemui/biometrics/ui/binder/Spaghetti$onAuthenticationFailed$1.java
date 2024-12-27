package com.android.systemui.biometrics.ui.binder;

import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.ui.viewmodel.PromptHistoryImpl;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class Spaghetti$onAuthenticationFailed$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BiometricModality $failedModality;
    final /* synthetic */ String $failureReason;
    int label;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$onAuthenticationFailed$1(Spaghetti spaghetti, String str, BiometricModality biometricModality, Continuation continuation) {
        super(2, continuation);
        this.this$0 = spaghetti;
        this.$failureReason = str;
        this.$failedModality = biometricModality;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$onAuthenticationFailed$1(this.this$0, this.$failureReason, this.$failedModality, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$onAuthenticationFailed$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Spaghetti spaghetti = this.this$0;
            PromptViewModel promptViewModel = spaghetti.viewModel;
            String str = this.$failureReason;
            String access$asDefaultHelpMessage = BiometricViewBinderKt.access$asDefaultHelpMessage(spaghetti.modalities, spaghetti.applicationContext);
            boolean hasFingerprint = this.this$0.modalities.getHasFingerprint();
            final Spaghetti spaghetti2 = this.this$0;
            final BiometricModality biometricModality = this.$failedModality;
            Function2 function2 = new Function2() { // from class: com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationFailed$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    boolean z;
                    PromptMessage promptMessage = (PromptMessage) obj2;
                    PromptHistoryImpl promptHistoryImpl = (PromptHistoryImpl) obj3;
                    if (Spaghetti.this.modalities.getHasFaceAndFingerprint()) {
                        BiometricModality biometricModality2 = biometricModality;
                        BiometricModality biometricModality3 = BiometricModality.Face;
                        if (biometricModality2 == biometricModality3 && ((promptMessage instanceof PromptMessage.Error) || promptHistoryImpl.failures.contains(biometricModality3))) {
                            z = true;
                            return Boolean.valueOf(z);
                        }
                    }
                    z = false;
                    return Boolean.valueOf(z);
                }
            };
            BiometricModality biometricModality2 = this.$failedModality;
            this.label = 1;
            if (PromptViewModel.showTemporaryError$default(promptViewModel, str, access$asDefaultHelpMessage, hasFingerprint, function2, biometricModality2, this, 16) == coroutineSingletons) {
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
