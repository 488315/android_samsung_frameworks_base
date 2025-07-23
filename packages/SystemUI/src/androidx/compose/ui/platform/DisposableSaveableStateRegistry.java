package androidx.compose.ui.platform;

import androidx.compose.runtime.saveable.SaveableStateRegistry;
import java.util.Map;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DisposableSaveableStateRegistry implements SaveableStateRegistry {
    public final /* synthetic */ SaveableStateRegistry $$delegate_0;
    public final Function0 onDispose;

    public DisposableSaveableStateRegistry(SaveableStateRegistry saveableStateRegistry, Function0 function0) {
        this.onDispose = function0;
        this.$$delegate_0 = saveableStateRegistry;
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final boolean canBeSaved(Object obj) {
        return this.$$delegate_0.canBeSaved(obj);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final Object consumeRestored(String str) {
        return this.$$delegate_0.consumeRestored(str);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final Map performSave() {
        return this.$$delegate_0.performSave();
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final SaveableStateRegistry.Entry registerProvider(String str, Function0 function0) {
        return this.$$delegate_0.registerProvider(str, function0);
    }
}
