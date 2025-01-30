package com.android.systemui.biometrics.p003ui.viewmodel;

import com.android.systemui.biometrics.domain.model.BiometricModalities;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$isTryAgainButtonVisible$1", m277f = "PromptViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$isTryAgainButtonVisible$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isTryAgainButtonVisible$1(Continuation<? super PromptViewModel$isTryAgainButtonVisible$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        PromptViewModel$isTryAgainButtonVisible$1 promptViewModel$isTryAgainButtonVisible$1 = new PromptViewModel$isTryAgainButtonVisible$1((Continuation) obj3);
        promptViewModel$isTryAgainButtonVisible$1.Z$0 = booleanValue;
        promptViewModel$isTryAgainButtonVisible$1.L$0 = (BiometricModalities) obj2;
        return promptViewModel$isTryAgainButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z2 = this.Z$0;
        BiometricModalities biometricModalities = (BiometricModalities) this.L$0;
        boolean z3 = false;
        if (z2) {
            if (biometricModalities.faceProperties != null) {
                if (!(biometricModalities.fingerprintProperties != null)) {
                    z = true;
                    if (z) {
                        z3 = true;
                    }
                }
            }
            z = false;
            if (z) {
            }
        }
        return Boolean.valueOf(z3);
    }
}
