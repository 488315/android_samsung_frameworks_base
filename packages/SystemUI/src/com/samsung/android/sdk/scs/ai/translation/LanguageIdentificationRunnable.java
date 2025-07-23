package com.samsung.android.sdk.scs.ai.translation;

import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
            Log.i("ScsApi@NeuralTranslator", "LanguageIdentificationRunnable -- identified language: " + identifyLanguage);
            this.mSource.setResult(identifyLanguage);
        } catch (RemoteException e) {
            Log.e("ScsApi@NeuralTranslator", "LanguageIdentificationRunnable -- Exception: " + e);
            e.printStackTrace();
            this.mSource.setException(e);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final String getFeatureName() {
        return "FEATURE_NEURAL_TRANSLATION";
    }
}
