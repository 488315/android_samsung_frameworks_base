package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final ViewModel create(Class cls, MutableCreationExtras mutableCreationExtras) {
        String str = (String) mutableCreationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        }
        if (mutableCreationExtras.get(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY) == null || mutableCreationExtras.get(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY) == null) {
            if (this.lifecycle != null) {
                return create(cls, str);
            }
            throw new IllegalStateException("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
        }
        Application application = (Application) mutableCreationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
        boolean isAssignableFrom = AndroidViewModel.class.isAssignableFrom(cls);
        Constructor findMatchingConstructor = (!isAssignableFrom || application == null) ? SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE) : SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
        return findMatchingConstructor == null ? this.factory.create(cls, mutableCreationExtras) : (!isAssignableFrom || application == null) ? SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, SavedStateHandleSupport.createSavedStateHandle(mutableCreationExtras)) : SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, application, SavedStateHandleSupport.createSavedStateHandle(mutableCreationExtras));
    }

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    public final void onRequery(ViewModel viewModel) {
        Lifecycle lifecycle = this.lifecycle;
        if (lifecycle != null) {
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, this.savedStateRegistry, lifecycle);
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
            if (ViewModelProvider.AndroidViewModelFactory.sInstance == null) {
                ViewModelProvider.AndroidViewModelFactory.sInstance = new ViewModelProvider.AndroidViewModelFactory(application);
            }
            androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.sInstance;
            Intrinsics.checkNotNull(androidViewModelFactory);
        } else {
            androidViewModelFactory = new ViewModelProvider.AndroidViewModelFactory();
        }
        this.factory = androidViewModelFactory;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ViewModel create(Class cls, String str) {
        Constructor findMatchingConstructor;
        ViewModel newInstance;
        Object obj;
        Application application;
        if (this.lifecycle != null) {
            boolean isAssignableFrom = AndroidViewModel.class.isAssignableFrom(cls);
            if (isAssignableFrom && this.application != null) {
                findMatchingConstructor = SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
            } else {
                findMatchingConstructor = SavedStateViewModelFactoryKt.findMatchingConstructor(cls, SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE);
            }
            if (findMatchingConstructor == null) {
                if (this.application != null) {
                    return this.factory.create(cls);
                }
                ViewModelProvider.NewInstanceFactory.Companion.getClass();
                if (ViewModelProvider.NewInstanceFactory.sInstance == null) {
                    ViewModelProvider.NewInstanceFactory.sInstance = new ViewModelProvider.NewInstanceFactory();
                }
                ViewModelProvider.NewInstanceFactory newInstanceFactory = ViewModelProvider.NewInstanceFactory.sInstance;
                Intrinsics.checkNotNull(newInstanceFactory);
                return newInstanceFactory.create(cls);
            }
            SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
            Lifecycle lifecycle = this.lifecycle;
            Bundle bundle = this.defaultArgs;
            Bundle consumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey(str);
            SavedStateHandle.Companion.getClass();
            SavedStateHandleController savedStateHandleController = new SavedStateHandleController(str, SavedStateHandle.Companion.createHandle(consumeRestoredStateForKey, bundle));
            if (!savedStateHandleController.mIsAttached) {
                savedStateHandleController.mIsAttached = true;
                lifecycle.addObserver(savedStateHandleController);
                savedStateRegistry.registerSavedStateProvider(savedStateHandleController.mKey, savedStateHandleController.mHandle.savedStateProvider);
                LegacySavedStateHandleController.tryToAddRecreator(lifecycle, savedStateRegistry);
                if (isAssignableFrom && (application = this.application) != null) {
                    newInstance = SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, application, savedStateHandleController.mHandle);
                } else {
                    newInstance = SavedStateViewModelFactoryKt.newInstance(cls, findMatchingConstructor, savedStateHandleController.mHandle);
                }
                synchronized (newInstance.mBagOfTags) {
                    obj = ((HashMap) newInstance.mBagOfTags).get("androidx.lifecycle.savedstate.vm.tag");
                    if (obj == 0) {
                        ((HashMap) newInstance.mBagOfTags).put("androidx.lifecycle.savedstate.vm.tag", savedStateHandleController);
                    }
                }
                if (obj != 0) {
                    savedStateHandleController = obj;
                }
                if (newInstance.mCleared) {
                    ViewModel.closeWithRuntimeException(savedStateHandleController);
                }
                return newInstance;
            }
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return create(cls, canonicalName);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }
}
