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
            final HashMap map = new HashMap();
            languageDirectionStateMap.entrySet().forEach(new Consumer() { // from class: com.samsung.android.sdk.scs.ai.translation.LanguageDirectionStateMapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Map.Entry entry = (Map.Entry) obj;
                    ((HashMap) map).put((LanguageDirection) entry.getKey(), LanguageDirectionState.from(((Integer) entry.getValue()).intValue()));
                }
            });
            taskCompletionSource.setResult(map);
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
