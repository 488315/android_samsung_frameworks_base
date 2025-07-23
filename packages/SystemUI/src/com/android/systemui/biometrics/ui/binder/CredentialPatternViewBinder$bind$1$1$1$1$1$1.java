package com.android.systemui.biometrics.ui.binder;

import com.android.internal.widget.LockPatternView;
import com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CredentialPatternViewBinder$bind$1$1$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CredentialHeaderViewModel $header;
    final /* synthetic */ List<LockPatternView.Cell> $pattern;
    final /* synthetic */ CredentialViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialPatternViewBinder$bind$1$1$1$1$1$1(CredentialViewModel credentialViewModel, List<LockPatternView.Cell> list, CredentialHeaderViewModel credentialHeaderViewModel, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = credentialViewModel;
        this.$pattern = list;
        this.$header = credentialHeaderViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CredentialPatternViewBinder$bind$1$1$1$1$1$1(this.$viewModel, this.$pattern, this.$header, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CredentialPatternViewBinder$bind$1$1$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CredentialViewModel credentialViewModel = this.$viewModel;
            List<LockPatternView.Cell> list = this.$pattern;
            CredentialHeaderViewModel credentialHeaderViewModel = this.$header;
            this.label = 1;
            if (credentialViewModel.checkCredential(list, credentialHeaderViewModel, this) == coroutineSingletons) {
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
