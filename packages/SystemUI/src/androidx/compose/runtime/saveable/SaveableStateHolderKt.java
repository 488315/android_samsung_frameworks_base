package androidx.compose.runtime.saveable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class SaveableStateHolderKt {
    public static final SaveableStateHolder rememberSaveableStateHolder(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.saveable.rememberSaveableStateHolder (SaveableStateHolder.kt:56)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-796079677);
        SaveableStateHolderImpl.Companion.getClass();
        SaveableStateHolderImpl saveableStateHolderImpl = (SaveableStateHolderImpl) RememberSaveableKt.rememberSaveable(new Object[0], SaveableStateHolderImpl.Saver, null, new Function0() { // from class: androidx.compose.runtime.saveable.SaveableStateHolderKt.rememberSaveableStateHolder.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SaveableStateHolderImpl(null, 1, null);
            }
        }, composerImpl, 3072, 4);
        saveableStateHolderImpl.parentSaveableStateRegistry = (SaveableStateRegistry) composerImpl.consume(SaveableStateRegistryKt.LocalSaveableStateRegistry);
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return saveableStateHolderImpl;
    }
}
