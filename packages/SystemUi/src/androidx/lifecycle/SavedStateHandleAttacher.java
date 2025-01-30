package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SavedStateHandleAttacher implements LifecycleEventObserver {
    public final SavedStateHandlesProvider provider;

    public SavedStateHandleAttacher(SavedStateHandlesProvider savedStateHandlesProvider) {
        this.provider = savedStateHandlesProvider;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (!(event == Lifecycle.Event.ON_CREATE)) {
            throw new IllegalStateException(("Next event must be ON_CREATE, it was " + event).toString());
        }
        lifecycleOwner.getLifecycle().removeObserver(this);
        SavedStateHandlesProvider savedStateHandlesProvider = this.provider;
        if (savedStateHandlesProvider.restored) {
            return;
        }
        savedStateHandlesProvider.restoredState = savedStateHandlesProvider.savedStateRegistry.consumeRestoredStateForKey("androidx.lifecycle.internal.SavedStateHandlesProvider");
        savedStateHandlesProvider.restored = true;
    }
}
