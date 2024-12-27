package com.android.systemui.biometrics.ui.binder;

import android.util.Log;
import com.airbnb.lottie.LottieListener;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PromptIconViewBinderKt$setIconFailureListener$1 implements LottieListener {
    public final /* synthetic */ PromptIconViewModel.AuthType $activeAuthType;
    public final /* synthetic */ int $iconAsset;

    public PromptIconViewBinderKt$setIconFailureListener$1(PromptIconViewModel.AuthType authType, int i) {
        this.$activeAuthType = authType;
        this.$iconAsset = i;
    }

    @Override // com.airbnb.lottie.LottieListener
    public final void onResult(Object obj) {
        int i = this.$iconAsset;
        Log.d("PromptIconViewBinder", "Collecting iconAsset | activeAuthType = " + this.$activeAuthType + " | Invalid resource id: " + i + ", name " + PromptIconViewBinderKt.access$getAssetNameFromId(i), (Throwable) obj);
    }
}
