package com.samsung.android.sdk.scs.p048ai.translation;

import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.p049ai.sdkcommon.translation.INeuralTranslationService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
class LanguageIdentificationRunnable extends TaskRunnable {
    public final String fallbackLanguage;
    public final NeuralTranslationServiceExecutor neuralTranslationServiceExecutor;
    public final String text;

    public LanguageIdentificationRunnable(NeuralTranslationServiceExecutor neuralTranslationServiceExecutor, String str, String str2) {
        this.neuralTranslationServiceExecutor = neuralTranslationServiceExecutor;
        this.text = str;
        this.fallbackLanguage = str2;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final void execute() {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("text", this.text);
            bundle.putString("fallbackLanguage", this.fallbackLanguage);
            String identifyLanguage = ((INeuralTranslationService.Stub.Proxy) this.neuralTranslationServiceExecutor.translationService).identifyLanguage(bundle);
            Log.m268i("ScsApi@NeuralTranslator", "LanguageIdentificationRunnable -- identified language: " + identifyLanguage);
            this.mSource.task.setResult(identifyLanguage);
        } catch (RemoteException e) {
            Log.m267e("ScsApi@NeuralTranslator", "LanguageIdentificationRunnable -- Exception: " + e);
            e.printStackTrace();
            this.mSource.setException(e);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final String getFeatureName() {
        return "FEATURE_NEURAL_TRANSLATION";
    }
}
