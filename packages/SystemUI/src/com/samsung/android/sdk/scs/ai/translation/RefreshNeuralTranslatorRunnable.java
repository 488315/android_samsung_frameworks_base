package com.samsung.android.sdk.scs.ai.translation;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService;
import com.samsung.android.sivs.ai.sdkcommon.translation.LanguageDirection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
class RefreshNeuralTranslatorRunnable extends TaskRunnable {
    public final NeuralTranslationServiceExecutor neuralTranslationServiceExecutor;

    public RefreshNeuralTranslatorRunnable(NeuralTranslationServiceExecutor neuralTranslationServiceExecutor) {
        this.neuralTranslationServiceExecutor = neuralTranslationServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final void execute() {
        try {
            INeuralTranslationService.Stub.Proxy proxy = (INeuralTranslationService.Stub.Proxy) this.neuralTranslationServiceExecutor.translationService;
            proxy.refresh();
            Map languageDirectionStateMap = proxy.getLanguageDirectionStateMap();
            TaskCompletionSource taskCompletionSource = this.mSource;
            final HashMap hashMap = new HashMap();
            languageDirectionStateMap.entrySet().forEach(new Consumer() { // from class: com.samsung.android.sdk.scs.ai.translation.LanguageDirectionStateMapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Map.Entry entry = (Map.Entry) obj;
                    ((HashMap) hashMap).put((LanguageDirection) entry.getKey(), LanguageDirectionState.from(((Integer) entry.getValue()).intValue()));
                }
            });
            taskCompletionSource.setResult(hashMap);
        } catch (RemoteException e) {
            Log.e("ScsApi@NeuralTranslator", "RefreshNeuralTranslatorRunnable -- Exception: " + e);
            e.printStackTrace();
            this.mSource.setException(e);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final String getFeatureName() {
        return "FEATURE_NEURAL_TRANSLATION";
    }
}
