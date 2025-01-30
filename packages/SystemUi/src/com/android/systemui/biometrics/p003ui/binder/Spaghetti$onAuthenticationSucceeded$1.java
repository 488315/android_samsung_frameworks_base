package com.android.systemui.biometrics.p003ui.binder;

import com.android.systemui.biometrics.domain.model.BiometricModality;
import com.android.systemui.biometrics.domain.model.BiometricModalityKt;
import com.android.systemui.biometrics.p003ui.viewmodel.PromptViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationSucceeded$1", m277f = "BiometricViewBinder.kt", m278l = {444, 445}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class Spaghetti$onAuthenticationSucceeded$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $modality;
    Object L$0;
    int label;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$onAuthenticationSucceeded$1(int i, Spaghetti spaghetti, Continuation<? super Spaghetti$onAuthenticationSucceeded$1> continuation) {
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
        BiometricModality asBiometricModality;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            asBiometricModality = BiometricModalityKt.asBiometricModality(this.$modality);
            Spaghetti spaghetti = this.this$0;
            this.L$0 = asBiometricModality;
            this.label = 1;
            obj = Spaghetti.access$getHelpForSuccessfulAuthentication(spaghetti, asBiometricModality, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            asBiometricModality = (BiometricModality) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        BiometricModality biometricModality = asBiometricModality;
        Integer num = (Integer) obj;
        Spaghetti spaghetti2 = this.this$0;
        PromptViewModel promptViewModel = spaghetti2.viewModel;
        String string = num != null ? spaghetti2.applicationContext.getString(num.intValue()) : "";
        this.L$0 = null;
        this.label = 2;
        if (promptViewModel.showAuthenticated(biometricModality, 500L, string, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
