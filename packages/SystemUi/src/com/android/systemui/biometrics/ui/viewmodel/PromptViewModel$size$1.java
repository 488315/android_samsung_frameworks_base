package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.model.BiometricModalities;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$size$1", m277f = "PromptViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$size$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public PromptViewModel$size$1(Continuation<? super PromptViewModel$size$1> continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        PromptViewModel$size$1 promptViewModel$size$1 = new PromptViewModel$size$1((Continuation) obj6);
        promptViewModel$size$1.Z$0 = booleanValue;
        promptViewModel$size$1.Z$1 = booleanValue2;
        promptViewModel$size$1.L$0 = (BiometricModalities) obj3;
        promptViewModel$size$1.Z$2 = booleanValue3;
        promptViewModel$size$1.L$1 = (FingerprintStartMode) obj5;
        return promptViewModel$size$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0033, code lost:
    
        if ((r1.fingerprintProperties != null) == false) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        BiometricModalities biometricModalities = (BiometricModalities) this.L$0;
        boolean z3 = this.Z$2;
        FingerprintStartMode fingerprintStartMode = (FingerprintStartMode) this.L$1;
        if (z) {
            return PromptSize.LARGE;
        }
        if (z2) {
            return PromptSize.MEDIUM;
        }
        boolean z4 = true;
        if (biometricModalities.faceProperties != null) {
        }
        z4 = false;
        return (!z4 || z3) ? (biometricModalities.getHasFaceAndFingerprint() && !z3 && fingerprintStartMode == FingerprintStartMode.Pending) ? PromptSize.SMALL : PromptSize.MEDIUM : PromptSize.SMALL;
    }
}
