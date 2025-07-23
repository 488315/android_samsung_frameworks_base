package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.viewmodel.internal.ViewModelImpl;
import androidx.savedstate.SavedStateRegistry;
import java.util.LinkedHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LegacySavedStateHandleController {
    public static final LegacySavedStateHandleController INSTANCE = new LegacySavedStateHandleController();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnRecreation implements SavedStateRegistry.AutoRecreated {
    }

    private LegacySavedStateHandleController() {
    }

    public static final void attachHandleIfNeeded(ViewModel viewModel, SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        AutoCloseable autoCloseable;
        boolean z;
        ViewModelImpl viewModelImpl = viewModel.impl;
        if (viewModelImpl != null) {
            synchronized (viewModelImpl.lock) {
                autoCloseable = (AutoCloseable) ((LinkedHashMap) viewModelImpl.keyToCloseables).get("androidx.lifecycle.savedstate.vm.tag");
            }
        } else {
            autoCloseable = null;
        }
        SavedStateHandleController savedStateHandleController = (SavedStateHandleController) autoCloseable;
        if (savedStateHandleController == null || (z = savedStateHandleController.isAttached)) {
            return;
        }
        if (z) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        savedStateHandleController.isAttached = true;
        lifecycle.addObserver(savedStateHandleController);
        savedStateRegistry.registerSavedStateProvider(savedStateHandleController.key, savedStateHandleController.handle.savedStateProvider);
        INSTANCE.getClass();
        tryToAddRecreator(lifecycle, savedStateRegistry);
    }

    public static void tryToAddRecreator(final Lifecycle lifecycle, final SavedStateRegistry savedStateRegistry) {
        Lifecycle.State currentState = lifecycle.getCurrentState();
        if (currentState == Lifecycle.State.INITIALIZED || currentState.isAtLeast(Lifecycle.State.STARTED)) {
            savedStateRegistry.runOnNextRecreation();
        } else {
            lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.lifecycle.LegacySavedStateHandleController$tryToAddRecreator$1
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
