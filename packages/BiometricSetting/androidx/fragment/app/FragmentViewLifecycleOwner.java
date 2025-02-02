package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

/* loaded from: classes.dex */
final class FragmentViewLifecycleOwner implements HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, ViewModelStoreOwner {
    private final Fragment mFragment;
    private final Runnable mRestoreViewSavedStateRunnable;
    private final ViewModelStore mViewModelStore;
    private LifecycleRegistry mLifecycleRegistry = null;
    private SavedStateRegistryController mSavedStateRegistryController = null;

    FragmentViewLifecycleOwner(Fragment fragment, ViewModelStore viewModelStore, Fragment$$ExternalSyntheticLambda0 fragment$$ExternalSyntheticLambda0) {
        this.mFragment = fragment;
        this.mViewModelStore = viewModelStore;
        this.mRestoreViewSavedStateRunnable = fragment$$ExternalSyntheticLambda0;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final CreationExtras getDefaultViewModelCreationExtras() {
        Application application;
        Fragment fragment = this.mFragment;
        Context applicationContext = fragment.requireContext().getApplicationContext();
        while (true) {
            if (!(applicationContext instanceof ContextWrapper)) {
                application = null;
                break;
            }
            if (applicationContext instanceof Application) {
                application = (Application) applicationContext;
                break;
            }
            applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
        }
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras();
        if (application != null) {
            mutableCreationExtras.getMap$lifecycle_viewmodel_release().put(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY, application);
        }
        mutableCreationExtras.getMap$lifecycle_viewmodel_release().put(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY, fragment);
        mutableCreationExtras.getMap$lifecycle_viewmodel_release().put(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY, this);
        Bundle bundle = fragment.mArguments;
        if (bundle != null) {
            mutableCreationExtras.getMap$lifecycle_viewmodel_release().put(SavedStateHandleSupport.DEFAULT_ARGS_KEY, bundle);
        }
        return mutableCreationExtras;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        initialize();
        return this.mLifecycleRegistry;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public final SavedStateRegistry getSavedStateRegistry() {
        initialize();
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        initialize();
        return this.mViewModelStore;
    }

    final void initialize() {
        if (this.mLifecycleRegistry == null) {
            this.mLifecycleRegistry = new LifecycleRegistry(this);
            SavedStateRegistryController savedStateRegistryController = new SavedStateRegistryController(this);
            this.mSavedStateRegistryController = savedStateRegistryController;
            savedStateRegistryController.performAttach();
            this.mRestoreViewSavedStateRunnable.run();
        }
    }

    final boolean isInitialized() {
        return this.mLifecycleRegistry != null;
    }

    final void performRestore(Bundle bundle) {
        this.mSavedStateRegistryController.performRestore(bundle);
    }

    final void setCurrentState() {
        this.mLifecycleRegistry.setCurrentState();
    }
}
