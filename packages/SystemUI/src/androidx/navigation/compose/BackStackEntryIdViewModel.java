package androidx.navigation.compose;

import androidx.compose.runtime.saveable.SaveableStateHolder;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import java.lang.ref.WeakReference;
import java.util.UUID;

/* loaded from: classes.dex */
public final class BackStackEntryIdViewModel extends ViewModel {
    public final UUID id;
    public WeakReference saveableStateHolderRef;

    public BackStackEntryIdViewModel(SavedStateHandle savedStateHandle) {
        UUID uuidRandomUUID = (UUID) savedStateHandle.get("SaveableStateHolder_BackStackEntryKey");
        if (uuidRandomUUID == null) {
            uuidRandomUUID = UUID.randomUUID();
            savedStateHandle.set(uuidRandomUUID, "SaveableStateHolder_BackStackEntryKey");
        }
        this.id = uuidRandomUUID;
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
