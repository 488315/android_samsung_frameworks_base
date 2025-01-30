package com.android.systemui.biometrics.p003ui.binder;

import com.android.systemui.biometrics.AuthBiometricView;
import com.android.systemui.biometrics.domain.model.BiometricModality;
import com.android.systemui.biometrics.p003ui.viewmodel.PromptViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.Spaghetti$onError$1", m277f = "BiometricViewBinder.kt", m278l = {500, 504}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class Spaghetti$onError$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $error;
    final /* synthetic */ BiometricModality $errorModality;
    int label;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$onError$1(Spaghetti spaghetti, BiometricModality biometricModality, String str, Continuation<? super Spaghetti$onError$1> continuation) {
        super(2, continuation);
        this.this$0 = spaghetti;
        this.$errorModality = biometricModality;
        this.$error = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$onError$1(this.this$0, this.$errorModality, this.$error, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$onError$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x005b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        AuthBiometricView.Callback callback;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.this$0.modalities.getHasFaceAndFingerprint() && this.$errorModality == BiometricModality.Face;
            PromptViewModel promptViewModel = this.this$0.viewModel;
            String str = this.$error;
            this.label = 1;
            int i2 = PromptViewModel.$r8$clinit;
            if (promptViewModel.showTemporaryError(str, "", false, z, BiometricModality.None, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                callback = this.this$0.legacyCallback;
                if (callback != null) {
                    callback.onAction(5);
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        this.label = 2;
        if (DelayKt.delay(2000L, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        callback = this.this$0.legacyCallback;
        if (callback != null) {
        }
        return Unit.INSTANCE;
    }
}
