package com.android.systemui.biometrics.p003ui.viewmodel;

import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.AuthBiometricFingerprintViewModel$iconAsset$1", m277f = "AuthBiometricFingerprintViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class AuthBiometricFingerprintViewModel$iconAsset$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ AuthBiometricFingerprintViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthBiometricFingerprintViewModel$iconAsset$1(AuthBiometricFingerprintViewModel authBiometricFingerprintViewModel, Continuation<? super AuthBiometricFingerprintViewModel$iconAsset$1> continuation) {
        super(3, continuation);
        this.this$0 = authBiometricFingerprintViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        AuthBiometricFingerprintViewModel$iconAsset$1 authBiometricFingerprintViewModel$iconAsset$1 = new AuthBiometricFingerprintViewModel$iconAsset$1(this.this$0, (Continuation) obj3);
        authBiometricFingerprintViewModel$iconAsset$1.Z$0 = booleanValue;
        authBiometricFingerprintViewModel$iconAsset$1.Z$1 = booleanValue2;
        return authBiometricFingerprintViewModel$iconAsset$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        int i = this.this$0.rotation;
        return new Integer(i != 1 ? i != 3 ? z2 ? R.raw.biometricprompt_rear_landscape_base : z ? R.raw.biometricprompt_folded_base_default : R.raw.biometricprompt_landscape_base : z2 ? R.raw.biometricprompt_rear_portrait_base : z ? R.raw.biometricprompt_folded_base_bottomright : R.raw.biometricprompt_portrait_base_bottomright : z2 ? R.raw.biometricprompt_rear_portrait_reverse_base : z ? R.raw.biometricprompt_folded_base_topleft : R.raw.biometricprompt_portrait_base_topleft);
    }
}
