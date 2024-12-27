package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptIconViewModel$iconAsset$1$2 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$iconAsset$1$2(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        PromptIconViewModel$iconAsset$1$2 promptIconViewModel$iconAsset$1$2 = new PromptIconViewModel$iconAsset$1$2(this.this$0, (Continuation) obj5);
        promptIconViewModel$iconAsset$1$2.L$0 = (PromptAuthState) obj;
        promptIconViewModel$iconAsset$1$2.Z$0 = booleanValue;
        promptIconViewModel$iconAsset$1$2.Z$1 = booleanValue2;
        promptIconViewModel$iconAsset$1$2.Z$2 = booleanValue3;
        return promptIconViewModel$iconAsset$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromptAuthState promptAuthState = (PromptAuthState) this.L$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        PromptIconViewModel promptIconViewModel = this.this$0;
        promptIconViewModel.getClass();
        boolean z4 = promptAuthState.isAuthenticated;
        return new Integer((z4 && z2) ? R.drawable.face_dialog_wink_from_dark : z4 ? R.drawable.face_dialog_dark_to_checkmark : z ? R.raw.face_dialog_authenticating : z3 ? R.drawable.face_dialog_dark_to_error : ((Boolean) promptIconViewModel._previousIconWasError.getValue()).booleanValue() ? R.drawable.face_dialog_error_to_idle : R.drawable.face_dialog_idle_static);
    }
}
