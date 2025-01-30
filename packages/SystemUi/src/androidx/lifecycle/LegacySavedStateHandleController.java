package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.savedstate.SavedStateRegistry;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LegacySavedStateHandleController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnRecreation implements SavedStateRegistry.AutoRecreated {
    }

    private LegacySavedStateHandleController() {
    }

    public static void attachHandleIfNeeded(ViewModel viewModel, SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        Object obj;
        boolean z;
        Map map = viewModel.mBagOfTags;
        if (map == null) {
            obj = null;
        } else {
            synchronized (map) {
                obj = ((HashMap) viewModel.mBagOfTags).get("androidx.lifecycle.savedstate.vm.tag");
            }
        }
        SavedStateHandleController savedStateHandleController = (SavedStateHandleController) obj;
        if (savedStateHandleController == null || (z = savedStateHandleController.mIsAttached)) {
            return;
        }
        if (z) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        savedStateHandleController.mIsAttached = true;
        lifecycle.addObserver(savedStateHandleController);
        savedStateRegistry.registerSavedStateProvider(savedStateHandleController.mKey, savedStateHandleController.mHandle.savedStateProvider);
        tryToAddRecreator(lifecycle, savedStateRegistry);
    }

    public static void tryToAddRecreator(final Lifecycle lifecycle, final SavedStateRegistry savedStateRegistry) {
        Lifecycle.State currentState = lifecycle.getCurrentState();
        if (currentState == Lifecycle.State.INITIALIZED || currentState.isAtLeast(Lifecycle.State.STARTED)) {
            savedStateRegistry.runOnNextRecreation();
        } else {
            lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.lifecycle.LegacySavedStateHandleController.1
                @Override // androidx.lifecycle.LifecycleEventObserver
                public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_START) {
                        Lifecycle.this.removeObserver(this);
                        savedStateRegistry.runOnNextRecreation();
                    }
                }
            });
        }
    }
}
