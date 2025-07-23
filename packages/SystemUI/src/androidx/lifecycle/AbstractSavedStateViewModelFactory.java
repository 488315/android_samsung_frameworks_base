package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.internal.ViewModelImpl;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AbstractSavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    public final Bundle defaultArgs;
    public final Lifecycle lifecycle;
    public final SavedStateRegistry savedStateRegistry;

    public AbstractSavedStateViewModelFactory() {
    }

    public abstract ViewModel create(SavedStateHandle savedStateHandle);

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls, CreationExtras creationExtras) {
        String str = (String) creationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        }
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        if (savedStateRegistry == null) {
            return create(SavedStateHandleSupport.createSavedStateHandle(creationExtras));
        }
        savedStateRegistry.getClass();
        Lifecycle lifecycle = this.lifecycle;
        lifecycle.getClass();
        Bundle bundle = this.defaultArgs;
        LegacySavedStateHandleController legacySavedStateHandleController = LegacySavedStateHandleController.INSTANCE;
        Bundle consumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey(str);
        SavedStateHandle.Companion.getClass();
        SavedStateHandleController savedStateHandleController = new SavedStateHandleController(str, SavedStateHandle.Companion.createHandle(consumeRestoredStateForKey, bundle));
        if (savedStateHandleController.isAttached) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        savedStateHandleController.isAttached = true;
        lifecycle.addObserver(savedStateHandleController);
        savedStateRegistry.registerSavedStateProvider(savedStateHandleController.key, savedStateHandleController.handle.savedStateProvider);
        LegacySavedStateHandleController.INSTANCE.getClass();
        LegacySavedStateHandleController.tryToAddRecreator(lifecycle, savedStateRegistry);
        ViewModel create = create(savedStateHandleController.handle);
        ViewModelImpl viewModelImpl = create.impl;
        if (viewModelImpl != null) {
            viewModelImpl.addCloseable("androidx.lifecycle.savedstate.vm.tag", savedStateHandleController);
        }
        return create;
    }

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    public final void onRequery(ViewModel viewModel) {
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        if (savedStateRegistry != null) {
            Lifecycle lifecycle = this.lifecycle;
            lifecycle.getClass();
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, lifecycle);
        }
    }

    public AbstractSavedStateViewModelFactory(SavedStateRegistryOwner savedStateRegistryOwner, Bundle bundle) {
        this.savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
        this.lifecycle = savedStateRegistryOwner.getLifecycle();
        this.defaultArgs = bundle;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        Lifecycle lifecycle = this.lifecycle;
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        if (lifecycle != null) {
            SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
            savedStateRegistry.getClass();
            lifecycle.getClass();
            Bundle bundle = this.defaultArgs;
            LegacySavedStateHandleController legacySavedStateHandleController = LegacySavedStateHandleController.INSTANCE;
            Bundle consumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey(canonicalName);
            SavedStateHandle.Companion.getClass();
            SavedStateHandleController savedStateHandleController = new SavedStateHandleController(canonicalName, SavedStateHandle.Companion.createHandle(consumeRestoredStateForKey, bundle));
            if (!savedStateHandleController.isAttached) {
                savedStateHandleController.isAttached = true;
                lifecycle.addObserver(savedStateHandleController);
                savedStateRegistry.registerSavedStateProvider(savedStateHandleController.key, savedStateHandleController.handle.savedStateProvider);
                LegacySavedStateHandleController.INSTANCE.getClass();
                LegacySavedStateHandleController.tryToAddRecreator(lifecycle, savedStateRegistry);
                ViewModel create = create(savedStateHandleController.handle);
                ViewModelImpl viewModelImpl = create.impl;
                if (viewModelImpl != null) {
                    viewModelImpl.addCloseable("androidx.lifecycle.savedstate.vm.tag", savedStateHandleController);
                }
                return create;
            }
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        throw new UnsupportedOperationException("AbstractSavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
    }
}
