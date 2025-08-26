package com.samsung.android.sdk.scs.ai.translation;

import android.content.Context;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.ai.sdkcommon.translation.LanguageDirection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class NeuralTranslator {
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
                return ((Map.Entry) obj).getValue() == languageDirectionState;
            }
        }).map(new Function() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslator$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        return (LanguageDirection) ((Map.Entry) obj).getKey();
                    default:
                        LanguageDirection languageDirection = (LanguageDirection) obj;
                        return MotionLayout$$ExternalSyntheticOutline0.m("(", languageDirection.getSourceLanguage(), ", ", languageDirection.getTargetLanguage(), ")");
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
                        return MotionLayout$$ExternalSyntheticOutline0.m("(", languageDirection.getSourceLanguage(), ", ", languageDirection.getTargetLanguage(), ")");
                }
            }
        }).distinct().sorted().collect(Collectors.toList());
    }

    public final Task identifyLanguage(String str) {
        Log.i("ScsApi@NeuralTranslator", "NeuralTranslator -- identifyLanguage() executed - default");
        Log.i("ScsApi@NeuralTranslator", "NeuralTranslator -- identifyLanguage() executed - fallbackLanguage: en");
        NeuralTranslationRunnableExecutor neuralTranslationRunnableExecutor = this.neuralTranslationRunnableExecutor;
        neuralTranslationRunnableExecutor.getClass();
        LanguageIdentificationRunnable languageIdentificationRunnable = new LanguageIdentificationRunnable(neuralTranslationRunnableExecutor.serviceExecutor, str, "en");
        neuralTranslationRunnableExecutor.serviceExecutor.execute(languageIdentificationRunnable);
        return languageIdentificationRunnable.getTask();
    }

    public final Task refresh() {
        Log.i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() executed");
        NeuralTranslationRunnableExecutor neuralTranslationRunnableExecutor = this.neuralTranslationRunnableExecutor;
        neuralTranslationRunnableExecutor.getClass();
        RefreshNeuralTranslatorRunnable refreshNeuralTranslatorRunnable = new RefreshNeuralTranslatorRunnable(neuralTranslationRunnableExecutor.serviceExecutor);
        neuralTranslationRunnableExecutor.serviceExecutor.execute(refreshNeuralTranslatorRunnable);
        Task task = refreshNeuralTranslatorRunnable.getTask();
        task.addOnCompleteListener(new OnCompleteListener() { // from class: com.samsung.android.sdk.scs.ai.translation.NeuralTranslator$$ExternalSyntheticLambda0
            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
            public final void onComplete(Task task2) {
                NeuralTranslator neuralTranslator = this.f$0;
                neuralTranslator.getClass();
                try {
                    neuralTranslator.languageDirectionStateMap = (Map) task2.getResult();
                    Log.i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() - Available LanguageDirection list [(source, target)]: " + neuralTranslator.getAvailableLanguageDirectionStringList(LanguageDirectionState.AVAILABLE));
                    Log.i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() - Available by pivot LanguageDirection list [(source, target)]: " + neuralTranslator.getAvailableLanguageDirectionStringList(LanguageDirectionState.AVAILABLE_BY_PIVOT));
                    Log.i("ScsApi@NeuralTranslator", "NeuralTranslator -- refresh() - Available downloadable LanguageDirection list [(source, target)]: " + neuralTranslator.getAvailableLanguageDirectionStringList(LanguageDirectionState.DOWNLOADABLE));
                } catch (RuntimeException e) {
                    Log.e("ScsApi@NeuralTranslator", "NeuralTranslator -- Exception: " + e);
                    e.printStackTrace();
                }
            }
        });
        return task;
    }
}
