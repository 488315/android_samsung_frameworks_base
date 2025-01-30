package com.android.systemui.biometrics.p003ui.binder;

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
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.Spaghetti$onHelp$1", m277f = "BiometricViewBinder.kt", m278l = {515}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class Spaghetti$onHelp$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $help;
    int label;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$onHelp$1(Spaghetti spaghetti, String str, Continuation<? super Spaghetti$onHelp$1> continuation) {
        super(2, continuation);
        this.this$0 = spaghetti;
        this.$help = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$onHelp$1(this.this$0, this.$help, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$onHelp$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Spaghetti spaghetti = this.this$0;
            PromptViewModel promptViewModel = spaghetti.viewModel;
            String str = this.$help;
            String access$asDefaultHelpMessage = BiometricViewBinderKt.access$asDefaultHelpMessage(spaghetti.modalities, spaghetti.applicationContext);
            this.label = 1;
            if (promptViewModel.showTemporaryHelp(str, access$asDefaultHelpMessage, this) == coroutineSingletons) {
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
