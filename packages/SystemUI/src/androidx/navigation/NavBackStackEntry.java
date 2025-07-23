package androidx.navigation;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.navigation.NavBackStackEntry;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavBackStackEntry implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner {
    public static final Companion Companion = new Companion(null);
    public final LifecycleRegistry _lifecycle;
    public final Context context;
    public final SavedStateViewModelFactory defaultViewModelProviderFactory;
    public NavDestination destination;
    public Lifecycle.State hostLifecycleState;
    public final String id;
    public final Bundle immutableArgs;
    public Lifecycle.State maxLifecycle;
    public final Bundle savedState;
    public boolean savedStateRegistryAttached;
    public final SavedStateRegistryController savedStateRegistryController;
    public final NavViewModelStoreProvider viewModelStoreProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static NavBackStackEntry create$default(Companion companion, Context context, NavDestination navDestination, Bundle bundle, Lifecycle.State state, NavViewModelStoreProvider navViewModelStoreProvider) {
            String uuid = UUID.randomUUID().toString();
            companion.getClass();
            return new NavBackStackEntry(context, navDestination, bundle, state, navViewModelStoreProvider, uuid, null, null);
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NavResultSavedStateFactory extends AbstractSavedStateViewModelFactory {
        public NavResultSavedStateFactory(SavedStateRegistryOwner savedStateRegistryOwner) {
            super(savedStateRegistryOwner, null);
        }

        @Override // androidx.lifecycle.AbstractSavedStateViewModelFactory
        public final ViewModel create(SavedStateHandle savedStateHandle) {
            return new SavedStateViewModel(savedStateHandle);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SavedStateViewModel extends ViewModel {
        public final SavedStateHandle handle;

        public SavedStateViewModel(SavedStateHandle savedStateHandle) {
            this.handle = savedStateHandle;
        }
    }

    public /* synthetic */ NavBackStackEntry(Context context, NavDestination navDestination, Bundle bundle, Lifecycle.State state, NavViewModelStoreProvider navViewModelStoreProvider, String str, Bundle bundle2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, navDestination, bundle, state, navViewModelStoreProvider, str, bundle2);
    }

    public final boolean equals(Object obj) {
        Set<String> keySet;
        if (obj != null && (obj instanceof NavBackStackEntry)) {
            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
            if (Intrinsics.areEqual(this.id, navBackStackEntry.id) && Intrinsics.areEqual(this.destination, navBackStackEntry.destination) && Intrinsics.areEqual(this._lifecycle, navBackStackEntry._lifecycle) && Intrinsics.areEqual(this.savedStateRegistryController.savedStateRegistry, navBackStackEntry.savedStateRegistryController.savedStateRegistry)) {
                if (Intrinsics.areEqual(this.immutableArgs, navBackStackEntry.immutableArgs)) {
                    return true;
                }
                Bundle bundle = this.immutableArgs;
                if (bundle != null && (keySet = bundle.keySet()) != null) {
                    Set<String> set = keySet;
                    if ((set instanceof Collection) && set.isEmpty()) {
                        return true;
                    }
                    for (String str : set) {
                        Object obj2 = this.immutableArgs.get(str);
                        Bundle bundle2 = navBackStackEntry.immutableArgs;
                        if (!Intrinsics.areEqual(obj2, bundle2 != null ? bundle2.get(str) : null)) {
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public final Bundle getArguments() {
        if (this.immutableArgs == null) {
            return null;
        }
        return new Bundle(this.immutableArgs);
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final CreationExtras getDefaultViewModelCreationExtras() {
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(null, 1, null);
        Context context = this.context;
        Context applicationContext = context != null ? context.getApplicationContext() : null;
        Application application = applicationContext instanceof Application ? (Application) applicationContext : null;
        if (application != null) {
            mutableCreationExtras.set(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY, application);
        }
        mutableCreationExtras.set(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY, this);
        mutableCreationExtras.set(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY, this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mutableCreationExtras.set(SavedStateHandleSupport.DEFAULT_ARGS_KEY, arguments);
        }
        return mutableCreationExtras;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return this.defaultViewModelProviderFactory;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this._lifecycle;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.savedStateRegistryController.savedStateRegistry;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        if (!this.savedStateRegistryAttached) {
            throw new IllegalStateException("You cannot access the NavBackStackEntry's ViewModels until it is added to the NavController's back stack (i.e., the Lifecycle of the NavBackStackEntry reaches the CREATED state).");
        }
        if (this._lifecycle.state == Lifecycle.State.DESTROYED) {
            throw new IllegalStateException("You cannot access the NavBackStackEntry's ViewModels after the NavBackStackEntry is destroyed.");
        }
        NavViewModelStoreProvider navViewModelStoreProvider = this.viewModelStoreProvider;
        if (navViewModelStoreProvider == null) {
            throw new IllegalStateException("You must call setViewModelStore() on your NavHostController before accessing the ViewModelStore of a navigation graph.");
        }
        NavControllerViewModel navControllerViewModel = (NavControllerViewModel) navViewModelStoreProvider;
        LinkedHashMap linkedHashMap = (LinkedHashMap) navControllerViewModel.viewModelStores;
        String str = this.id;
        ViewModelStore viewModelStore = (ViewModelStore) linkedHashMap.get(str);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        navControllerViewModel.viewModelStores.put(str, viewModelStore2);
        return viewModelStore2;
    }

    public final int hashCode() {
        Set<String> keySet;
        int hashCode = this.destination.hashCode() + (this.id.hashCode() * 31);
        Bundle bundle = this.immutableArgs;
        if (bundle != null && (keySet = bundle.keySet()) != null) {
            Iterator<T> it = keySet.iterator();
            while (it.hasNext()) {
                int i = hashCode * 31;
                Object obj = this.immutableArgs.get((String) it.next());
                hashCode = i + (obj != null ? obj.hashCode() : 0);
            }
        }
        return this.savedStateRegistryController.savedStateRegistry.hashCode() + ((this._lifecycle.hashCode() + (hashCode * 31)) * 31);
    }

    public final void setMaxLifecycle(Lifecycle.State state) {
        this.maxLifecycle = state;
        updateState();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NavBackStackEntry");
        sb.append("(" + this.id + ')');
        sb.append(" destination=");
        sb.append(this.destination);
        return sb.toString();
    }

    public final void updateState() {
        if (!this.savedStateRegistryAttached) {
            SavedStateRegistryController savedStateRegistryController = this.savedStateRegistryController;
            savedStateRegistryController.performAttach();
            this.savedStateRegistryAttached = true;
            if (this.viewModelStoreProvider != null) {
                SavedStateHandleSupport.enableSavedStateHandles(this);
            }
            savedStateRegistryController.performRestore(this.savedState);
        }
        int ordinal = this.hostLifecycleState.ordinal();
        int ordinal2 = this.maxLifecycle.ordinal();
        LifecycleRegistry lifecycleRegistry = this._lifecycle;
        if (ordinal < ordinal2) {
            lifecycleRegistry.setCurrentState(this.hostLifecycleState);
        } else {
            lifecycleRegistry.setCurrentState(this.maxLifecycle);
        }
    }

    private NavBackStackEntry(Context context, NavDestination navDestination, Bundle bundle, Lifecycle.State state, NavViewModelStoreProvider navViewModelStoreProvider, String str, Bundle bundle2) {
        this.context = context;
        this.destination = navDestination;
        this.immutableArgs = bundle;
        this.hostLifecycleState = state;
        this.viewModelStoreProvider = navViewModelStoreProvider;
        this.id = str;
        this.savedState = bundle2;
        this._lifecycle = new LifecycleRegistry(this);
        SavedStateRegistryController.Companion.getClass();
        this.savedStateRegistryController = SavedStateRegistryController.Companion.create(this);
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavBackStackEntry$defaultFactory$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = NavBackStackEntry.this.context;
                Context applicationContext = context2 != null ? context2.getApplicationContext() : null;
                Application application = applicationContext instanceof Application ? (Application) applicationContext : null;
                NavBackStackEntry navBackStackEntry = NavBackStackEntry.this;
                return new SavedStateViewModelFactory(application, navBackStackEntry, navBackStackEntry.getArguments());
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavBackStackEntry$savedStateHandle$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NavBackStackEntry navBackStackEntry = NavBackStackEntry.this;
                if (!navBackStackEntry.savedStateRegistryAttached) {
                    throw new IllegalStateException("You cannot access the NavBackStackEntry's SavedStateHandle until it is added to the NavController's back stack (i.e., the Lifecycle of the NavBackStackEntry reaches the CREATED state).");
                }
                if (navBackStackEntry._lifecycle.state != Lifecycle.State.DESTROYED) {
                    return ((NavBackStackEntry.SavedStateViewModel) new ViewModelProvider(NavBackStackEntry.this, new NavBackStackEntry.NavResultSavedStateFactory(NavBackStackEntry.this)).get(NavBackStackEntry.SavedStateViewModel.class)).handle;
                }
                throw new IllegalStateException("You cannot access the NavBackStackEntry's SavedStateHandle after the NavBackStackEntry is destroyed.");
            }
        });
        this.maxLifecycle = Lifecycle.State.INITIALIZED;
        this.defaultViewModelProviderFactory = (SavedStateViewModelFactory) lazy.getValue();
    }

    public /* synthetic */ NavBackStackEntry(Context context, NavDestination navDestination, Bundle bundle, Lifecycle.State state, NavViewModelStoreProvider navViewModelStoreProvider, String str, Bundle bundle2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, navDestination, (i & 4) != 0 ? null : bundle, (i & 8) != 0 ? Lifecycle.State.CREATED : state, (i & 16) != 0 ? null : navViewModelStoreProvider, (i & 32) != 0 ? UUID.randomUUID().toString() : str, (i & 64) != 0 ? null : bundle2);
    }

    public /* synthetic */ NavBackStackEntry(NavBackStackEntry navBackStackEntry, Bundle bundle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(navBackStackEntry, (i & 2) != 0 ? navBackStackEntry.getArguments() : bundle);
    }

    public NavBackStackEntry(NavBackStackEntry navBackStackEntry, Bundle bundle) {
        this(navBackStackEntry.context, navBackStackEntry.destination, bundle, navBackStackEntry.hostLifecycleState, navBackStackEntry.viewModelStoreProvider, navBackStackEntry.id, navBackStackEntry.savedState);
        this.hostLifecycleState = navBackStackEntry.hostLifecycleState;
        setMaxLifecycle(navBackStackEntry.maxLifecycle);
    }
}
