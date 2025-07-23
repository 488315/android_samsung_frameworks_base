package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SavedStateHandleSupport {
    public static final SavedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1 SAVED_STATE_REGISTRY_OWNER_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.SavedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1
    };
    public static final SavedStateHandleSupport$VIEW_MODEL_STORE_OWNER_KEY$1 VIEW_MODEL_STORE_OWNER_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.SavedStateHandleSupport$VIEW_MODEL_STORE_OWNER_KEY$1
    };
    public static final SavedStateHandleSupport$DEFAULT_ARGS_KEY$1 DEFAULT_ARGS_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.SavedStateHandleSupport$DEFAULT_ARGS_KEY$1
    };

    public static final SavedStateHandle createSavedStateHandle(CreationExtras creationExtras) {
        SavedStateRegistryOwner savedStateRegistryOwner = (SavedStateRegistryOwner) creationExtras.get(SAVED_STATE_REGISTRY_OWNER_KEY);
        if (savedStateRegistryOwner == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`");
        }
        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) creationExtras.get(VIEW_MODEL_STORE_OWNER_KEY);
        if (viewModelStoreOwner == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_STORE_OWNER_KEY`");
        }
        Bundle bundle = (Bundle) creationExtras.get(DEFAULT_ARGS_KEY);
        String str = (String) creationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_KEY`");
        }
        SavedStateRegistry.SavedStateProvider savedStateProvider = savedStateRegistryOwner.getSavedStateRegistry().getSavedStateProvider();
        SavedStateHandlesProvider savedStateHandlesProvider = savedStateProvider instanceof SavedStateHandlesProvider ? (SavedStateHandlesProvider) savedStateProvider : null;
        if (savedStateHandlesProvider == null) {
            throw new IllegalStateException("enableSavedStateHandles() wasn't called prior to createSavedStateHandle() call");
        }
        SavedStateHandlesVM savedStateHandlesVM = (SavedStateHandlesVM) new ViewModelProvider(viewModelStoreOwner, new SavedStateHandleSupport$savedStateHandlesVM$1()).impl.getViewModel$lifecycle_viewmodel_release(Reflection.getOrCreateKotlinClass(SavedStateHandlesVM.class), "androidx.lifecycle.internal.SavedStateHandlesVM");
        SavedStateHandle savedStateHandle = (SavedStateHandle) ((LinkedHashMap) savedStateHandlesVM.handles).get(str);
        if (savedStateHandle != null) {
            return savedStateHandle;
        }
        SavedStateHandle.Companion companion = SavedStateHandle.Companion;
        savedStateHandlesProvider.performRestore();
        Bundle bundle2 = savedStateHandlesProvider.restoredState;
        Bundle bundle3 = bundle2 != null ? bundle2.getBundle(str) : null;
        Bundle bundle4 = savedStateHandlesProvider.restoredState;
        if (bundle4 != null) {
            bundle4.remove(str);
        }
        Bundle bundle5 = savedStateHandlesProvider.restoredState;
        if (bundle5 != null && bundle5.isEmpty()) {
            savedStateHandlesProvider.restoredState = null;
        }
        companion.getClass();
        SavedStateHandle createHandle = SavedStateHandle.Companion.createHandle(bundle3, bundle);
        savedStateHandlesVM.handles.put(str, createHandle);
        return createHandle;
    }

    public static final void enableSavedStateHandles(SavedStateRegistryOwner savedStateRegistryOwner) {
        Lifecycle.State currentState = savedStateRegistryOwner.getLifecycle().getCurrentState();
        if (currentState != Lifecycle.State.INITIALIZED && currentState != Lifecycle.State.CREATED) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (savedStateRegistryOwner.getSavedStateRegistry().getSavedStateProvider() == null) {
            SavedStateHandlesProvider savedStateHandlesProvider = new SavedStateHandlesProvider(savedStateRegistryOwner.getSavedStateRegistry(), (ViewModelStoreOwner) savedStateRegistryOwner);
            savedStateRegistryOwner.getSavedStateRegistry().registerSavedStateProvider("androidx.lifecycle.internal.SavedStateHandlesProvider", savedStateHandlesProvider);
            savedStateRegistryOwner.getLifecycle().addObserver(new SavedStateHandleAttacher(savedStateHandlesProvider));
        }
    }
}
