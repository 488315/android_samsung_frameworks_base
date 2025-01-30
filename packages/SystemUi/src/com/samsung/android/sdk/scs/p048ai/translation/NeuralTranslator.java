package com.samsung.android.sdk.scs.p048ai.translation;

import android.content.Context;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.base.tasks.TaskImpl;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.p049ai.sdkcommon.translation.LanguageDirection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NeuralTranslator {
    public Map languageDirectionStateMap = new HashMap();
    public final NeuralTranslationRunnableExecutor neuralTranslationRunnableExecutor;

    public NeuralTranslator(Context context) {
        this.neuralTranslationRunnableExecutor = new NeuralTranslationRunnableExecutor(new NeuralTranslationServiceExecutor(context));
        context.getApplicationContext();
    }

    public final List getAvailableLanguageDirectionStringList(final LanguageDirectionState languageDirectionState) {
        final int i = 0;
        Stream map = this.languageDirectionStateMap.entrySet().stream().filter(new Predicate() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslator$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Map.Entry) obj).getValue() == LanguageDirectionState.this;
            }
        }).map(new Function() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslator$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        return (LanguageDirection) ((Map.Entry) obj).getKey();
                    default:
                        LanguageDirection languageDirection = (LanguageDirection) obj;
                        return String.format("(%s, %s)", languageDirection.getSourceLanguage(), languageDirection.getTargetLanguage());
                }
            }
        });
        final int i2 = 1;
        return (List) map.map(new Function() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslator$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        return (LanguageDirection) ((Map.Entry) obj).getKey();
                    default:
                        LanguageDirection languageDirection = (LanguageDirection) obj;
                        return String.format("(%s, %s)", languageDirection.getSourceLanguage(), languageDirection.getTargetLanguage());
                }
            }
        }).distinct().sorted().collect(Collectors.toList());
    }

    public final TaskImpl identifyLanguage(String str) {
        Log.m268i("ScsApi@NeuralTranslator", "NeuralTranslator -- identifyLanguage() executed - default");
        Log.m268i("ScsApi@NeuralTranslator", "NeuralTranslator -- identifyLanguage() executed - fallbackLanguage: en");
        NeuralTranslationRunnableExecutor neuralTranslationRunnableExecutor = this.neuralTranslationRunnableExecutor;
        neuralTranslationRunnableExecutor.getClass();
        NeuralTranslationServiceExecutor neuralTranslationServiceExecutor = neuralTranslationRunnableExecutor.serviceExecutor;
        LanguageIdentificationRunnable languageIdentificationRunnable = new LanguageIdentificationRunnable(neuralTranslationServiceExecutor, str, "en");
        neuralTranslationServiceExecutor.execute(languageIdentificationRunnable);
        return languageIdentificationRunnable.mSource.task;
    }

    public final TaskImpl refresh() {
        Log.m268i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() executed");
        NeuralTranslationRunnableExecutor neuralTranslationRunnableExecutor = this.neuralTranslationRunnableExecutor;
        neuralTranslationRunnableExecutor.getClass();
        NeuralTranslationServiceExecutor neuralTranslationServiceExecutor = neuralTranslationRunnableExecutor.serviceExecutor;
        RefreshNeuralTranslatorRunnable refreshNeuralTranslatorRunnable = new RefreshNeuralTranslatorRunnable(neuralTranslationServiceExecutor);
        neuralTranslationServiceExecutor.execute(refreshNeuralTranslatorRunnable);
        TaskImpl taskImpl = refreshNeuralTranslatorRunnable.mSource.task;
        taskImpl.addOnCompleteListener(new OnCompleteListener() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslator$$ExternalSyntheticLambda0
            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                NeuralTranslator neuralTranslator = NeuralTranslator.this;
                neuralTranslator.getClass();
                try {
                    neuralTranslator.languageDirectionStateMap = (Map) task.getResult();
                    Log.m268i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() - Available LanguageDirection list [(source, target)]: " + neuralTranslator.getAvailableLanguageDirectionStringList(LanguageDirectionState.AVAILABLE));
                    Log.m268i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() - Available by pivot LanguageDirection list [(source, target)]: " + neuralTranslator.getAvailableLanguageDirectionStringList(LanguageDirectionState.AVAILABLE_BY_PIVOT));
                    Log.m268i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() - Available downloadable LanguageDirection list [(source, target)]: " + neuralTranslator.getAvailableLanguageDirectionStringList(LanguageDirectionState.DOWNLOADABLE));
                } catch (RuntimeException e) {
                    Log.m267e("ScsApi@NeuralTranslator", "NeuralTranslator -- Exception: " + e);
                    e.printStackTrace();
                }
            }
        });
        return taskImpl;
    }
}
