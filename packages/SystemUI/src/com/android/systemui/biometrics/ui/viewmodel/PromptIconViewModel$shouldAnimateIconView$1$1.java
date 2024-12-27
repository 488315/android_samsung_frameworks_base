package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PromptIconViewModel$shouldAnimateIconView$1$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
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
    public PromptIconViewModel$shouldAnimateIconView$1$1(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        PromptIconViewModel$shouldAnimateIconView$1$1 promptIconViewModel$shouldAnimateIconView$1$1 = new PromptIconViewModel$shouldAnimateIconView$1$1(this.this$0, (Continuation) obj5);
        promptIconViewModel$shouldAnimateIconView$1$1.L$0 = (FingerprintSensorType) obj;
        promptIconViewModel$shouldAnimateIconView$1$1.L$1 = (PromptAuthState) obj2;
        promptIconViewModel$shouldAnimateIconView$1$1.Z$0 = booleanValue;
        promptIconViewModel$shouldAnimateIconView$1$1.Z$1 = booleanValue2;
        return promptIconViewModel$shouldAnimateIconView$1$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0045, code lost:
    
        if (((java.lang.Boolean) r5._previousIconWasError.getValue()).booleanValue() != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0049, code lost:
    
        if (r2 != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002c, code lost:
    
        if (r2 == false) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r5.label
            if (r0 != 0) goto L51
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            com.android.systemui.biometrics.shared.model.FingerprintSensorType r6 = (com.android.systemui.biometrics.shared.model.FingerprintSensorType) r6
            java.lang.Object r0 = r5.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptAuthState r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptAuthState) r0
            boolean r1 = r5.Z$0
            boolean r2 = r5.Z$1
            int[] r3 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldAnimateIconView$1$1.WhenMappings.$EnumSwitchMapping$0
            int r6 = r6.ordinal()
            r6 = r3[r6]
            r3 = 0
            r4 = 1
            if (r6 != r4) goto L30
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r5 = r5.this$0
            boolean r6 = r0.isAuthenticated
            r5.getClass()
            if (r6 != 0) goto L2e
            if (r1 != 0) goto L2e
            if (r2 == 0) goto L4c
        L2e:
            r3 = r4
            goto L4c
        L30:
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r5 = r5.this$0
            boolean r6 = r0.isAuthenticated
            r5.getClass()
            if (r1 == 0) goto L47
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5._previousIconWasError
            java.lang.Object r5 = r5.getValue()
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L2e
        L47:
            if (r6 != 0) goto L2e
            if (r2 == 0) goto L4c
            goto L2e
        L4c:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)
            return r5
        L51:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldAnimateIconView$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
