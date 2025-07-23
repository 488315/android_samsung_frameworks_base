package androidx.compose.runtime.saveable;

import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.snapshots.SnapshotMutableState;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SaveableHolder<T> implements SaverScope, RememberObserver {
    public SaveableStateRegistry.Entry entry;
    public Object[] inputs;
    public String key;
    public SaveableStateRegistry registry;
    public Saver saver;
    public Object value;
    public final Function0 valueProvider = new SaveableHolder$valueProvider$1(this);

    public SaveableHolder(Saver<T, Object> saver, SaveableStateRegistry saveableStateRegistry, String str, T t, Object[] objArr) {
        this.saver = saver;
        this.registry = saveableStateRegistry;
        this.key = str;
        this.value = t;
        this.inputs = objArr;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
        SaveableStateRegistry.Entry entry = this.entry;
        if (entry != null) {
            ((SaveableStateRegistryImpl$registerProvider$3) entry).unregister();
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        SaveableStateRegistry.Entry entry = this.entry;
        if (entry != null) {
            ((SaveableStateRegistryImpl$registerProvider$3) entry).unregister();
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
        register$1();
    }

    public final void register$1() {
        String generateCannotBeSavedErrorMessage;
        SaveableStateRegistry saveableStateRegistry = this.registry;
        if (this.entry != null) {
            throw new IllegalArgumentException(("entry(" + this.entry + ") is not null").toString());
        }
        if (saveableStateRegistry != null) {
            Function0 function0 = this.valueProvider;
            Object invoke = ((SaveableHolder$valueProvider$1) function0).invoke();
            if (invoke == null || saveableStateRegistry.canBeSaved(invoke)) {
                this.entry = saveableStateRegistry.registerProvider(this.key, function0);
                return;
            }
            if (invoke instanceof SnapshotMutableState) {
                SnapshotMutableState snapshotMutableState = (SnapshotMutableState) invoke;
                if (snapshotMutableState.getPolicy() == SnapshotStateKt.neverEqualPolicy() || snapshotMutableState.getPolicy() == SnapshotStateKt.structuralEqualityPolicy() || snapshotMutableState.getPolicy() == SnapshotStateKt.referentialEqualityPolicy()) {
                    generateCannotBeSavedErrorMessage = "MutableState containing " + snapshotMutableState.getValue() + " cannot be saved using the current SaveableStateRegistry. The default implementation only supports types which can be stored inside the Bundle. Please consider implementing a custom Saver for this class and pass it as a stateSaver parameter to rememberSaveable().";
                } else {
                    generateCannotBeSavedErrorMessage = "If you use a custom SnapshotMutationPolicy for your MutableState you have to write a custom Saver";
                }
            } else {
                generateCannotBeSavedErrorMessage = RememberSaveableKt.generateCannotBeSavedErrorMessage(invoke);
            }
            throw new IllegalArgumentException(generateCannotBeSavedErrorMessage);
        }
    }
}
