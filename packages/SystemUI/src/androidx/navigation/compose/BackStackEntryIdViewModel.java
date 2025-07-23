package androidx.navigation.compose;

import androidx.compose.runtime.saveable.SaveableStateHolder;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import java.lang.ref.WeakReference;
import java.util.UUID;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BackStackEntryIdViewModel extends ViewModel {
    public final UUID id;
    public WeakReference saveableStateHolderRef;

    public BackStackEntryIdViewModel(SavedStateHandle savedStateHandle) {
        UUID uuid = (UUID) savedStateHandle.get("SaveableStateHolder_BackStackEntryKey");
        if (uuid == null) {
            uuid = UUID.randomUUID();
            savedStateHandle.set(uuid, "SaveableStateHolder_BackStackEntryKey");
        }
        this.id = uuid;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        WeakReference weakReference = this.saveableStateHolderRef;
        if (weakReference == null) {
            weakReference = null;
        }
        SaveableStateHolder saveableStateHolder = (SaveableStateHolder) weakReference.get();
        if (saveableStateHolder != null) {
            saveableStateHolder.removeState(this.id);
        }
        WeakReference weakReference2 = this.saveableStateHolderRef;
        (weakReference2 != null ? weakReference2 : null).clear();
    }
}
