package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.internal.ViewModelImpl;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    public final Application application;
    public final Bundle defaultArgs;
    public final ViewModelProvider.AndroidViewModelFactory factory;
    public final Lifecycle lifecycle;
    public final SavedStateRegistry savedStateRegistry;

    public SavedStateViewModelFactory() {
        this.factory = new ViewModelProvider.AndroidViewModelFactory();
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls, CreationExtras creationExtras) {
        String str = (String) creationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        }
        if (creationExtras.get(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY) == null || creationExtras.get(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY) == null) {
            if (this.lifecycle != null) {
                return create$1(cls, str);
            }
            throw new IllegalStateException("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
        }
        Application application = (Application) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
        boolean isAssignableFrom = AndroidViewModel.class.isAssignableFrom(cls);
        Constructor findMatchingConstructor = (!isAssignableFrom || application == null) ? SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE) : SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
        return findMatchingConstructor == null ? this.factory.create(cls, creationExtras) : (!isAssignableFrom || application == null) ? SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, SavedStateHandleSupport.createSavedStateHandle(creationExtras)) : SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, application, SavedStateHandleSupport.createSavedStateHandle(creationExtras));
    }

    public final ViewModel create$1(Class cls, String str) {
        Application application;
        Lifecycle lifecycle = this.lifecycle;
        if (lifecycle == null) {
            throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
        boolean isAssignableFrom = AndroidViewModel.class.isAssignableFrom(cls);
        Constructor findMatchingConstructor = (!isAssignableFrom || this.application == null) ? SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE) : SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
        if (findMatchingConstructor == null) {
            if (this.application != null) {
                return this.factory.create(cls);
            }
            ViewModelProvider.NewInstanceFactory.Companion.getClass();
            if (ViewModelProvider.NewInstanceFactory._instance == null) {
                ViewModelProvider.NewInstanceFactory._instance = new ViewModelProvider.NewInstanceFactory();
            }
            ViewModelProvider.NewInstanceFactory newInstanceFactory = ViewModelProvider.NewInstanceFactory._instance;
            newInstanceFactory.getClass();
            return newInstanceFactory.create(cls);
        }
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        savedStateRegistry.getClass();
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
        ViewModel newInstance = (!isAssignableFrom || (application = this.application) == null) ? SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, savedStateHandleController.handle) : SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, application, savedStateHandleController.handle);
        ViewModelImpl viewModelImpl = newInstance.impl;
        if (viewModelImpl != null) {
            viewModelImpl.addCloseable("androidx.lifecycle.savedstate.vm.tag", savedStateHandleController);
        }
        return newInstance;
    }

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    public final void onRequery(ViewModel viewModel) {
        Lifecycle lifecycle = this.lifecycle;
        if (lifecycle != null) {
            SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
            savedStateRegistry.getClass();
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, lifecycle);
        }
    }

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner savedStateRegistryOwner) {
        this(application, savedStateRegistryOwner, null);
    }

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner savedStateRegistryOwner, Bundle bundle) {
        ViewModelProvider.AndroidViewModelFactory androidViewModelFactory;
        this.savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
        this.lifecycle = savedStateRegistryOwner.getLifecycle();
        this.defaultArgs = bundle;
        this.application = application;
        if (application != null) {
            ViewModelProvider.AndroidViewModelFactory.Companion.getClass();
            if (ViewModelProvider.AndroidViewModelFactory._instance == null) {
                ViewModelProvider.AndroidViewModelFactory._instance = new ViewModelProvider.AndroidViewModelFactory(application);
            }
            androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory._instance;
            androidViewModelFactory.getClass();
        } else {
            androidViewModelFactory = new ViewModelProvider.AndroidViewModelFactory();
        }
        this.factory = androidViewModelFactory;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return create$1(cls, canonicalName);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }
}
