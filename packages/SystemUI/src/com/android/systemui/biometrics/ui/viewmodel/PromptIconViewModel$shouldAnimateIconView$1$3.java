package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PromptIconViewModel$shouldAnimateIconView$1$3 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
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
    public PromptIconViewModel$shouldAnimateIconView$1$3(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        boolean booleanValue3 = ((Boolean) obj5).booleanValue();
        PromptIconViewModel$shouldAnimateIconView$1$3 promptIconViewModel$shouldAnimateIconView$1$3 = new PromptIconViewModel$shouldAnimateIconView$1$3(this.this$0, (Continuation) obj6);
        promptIconViewModel$shouldAnimateIconView$1$3.L$0 = (FingerprintSensorType) obj;
        promptIconViewModel$shouldAnimateIconView$1$3.L$1 = (PromptAuthState) obj2;
        promptIconViewModel$shouldAnimateIconView$1$3.Z$0 = booleanValue;
        promptIconViewModel$shouldAnimateIconView$1$3.Z$1 = booleanValue2;
        promptIconViewModel$shouldAnimateIconView$1$3.Z$2 = booleanValue3;
        return promptIconViewModel$shouldAnimateIconView$1$3.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0047, code lost:
    
        if (((java.lang.Boolean) r6._previousIconWasError.getValue()).booleanValue() != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004d, code lost:
    
        if (r3 != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002e, code lost:
    
        if (r3 == false) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r6.label
            if (r0 != 0) goto L55
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            com.android.systemui.biometrics.shared.model.FingerprintSensorType r7 = (com.android.systemui.biometrics.shared.model.FingerprintSensorType) r7
            java.lang.Object r0 = r6.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptAuthState r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptAuthState) r0
            boolean r1 = r6.Z$0
            boolean r2 = r6.Z$1
            boolean r3 = r6.Z$2
            int[] r4 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldAnimateIconView$1$3.WhenMappings.$EnumSwitchMapping$0
            int r7 = r7.ordinal()
            r7 = r4[r7]
            r4 = 0
            r5 = 1
            if (r7 != r5) goto L32
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r6 = r6.this$0
            boolean r7 = r0.isAuthenticated
            r6.getClass()
            if (r7 != 0) goto L30
            if (r1 != 0) goto L30
            if (r3 == 0) goto L50
        L30:
            r4 = r5
            goto L50
        L32:
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r6 = r6.this$0
            boolean r7 = r0.isAuthenticated
            r6.getClass()
            if (r1 == 0) goto L49
            kotlinx.coroutines.flow.StateFlowImpl r6 = r6._previousIconWasError
            java.lang.Object r6 = r6.getValue()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 != 0) goto L30
        L49:
            if (r2 != 0) goto L30
            if (r7 != 0) goto L30
            if (r3 == 0) goto L50
            goto L30
        L50:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)
            return r6
        L55:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldAnimateIconView$1$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
