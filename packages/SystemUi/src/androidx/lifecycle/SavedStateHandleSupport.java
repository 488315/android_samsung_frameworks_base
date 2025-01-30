package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactory;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SavedStateHandleSupport {
    public static final SavedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1 SAVED_STATE_REGISTRY_OWNER_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.SavedStateHandleSupport$SAVED_STATE_REGISTRY_OWNER_KEY$1
    };
    public static final SavedStateHandleSupport$VIEW_MODEL_STORE_OWNER_KEY$1 VIEW_MODEL_STORE_OWNER_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.SavedStateHandleSupport$VIEW_MODEL_STORE_OWNER_KEY$1
    };
    public static final SavedStateHandleSupport$DEFAULT_ARGS_KEY$1 DEFAULT_ARGS_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.SavedStateHandleSupport$DEFAULT_ARGS_KEY$1
    };

    public static final SavedStateHandle createSavedStateHandle(MutableCreationExtras mutableCreationExtras) {
        SavedStateRegistryOwner savedStateRegistryOwner = (SavedStateRegistryOwner) mutableCreationExtras.get(SAVED_STATE_REGISTRY_OWNER_KEY);
        if (savedStateRegistryOwner == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`");
        }
        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) mutableCreationExtras.get(VIEW_MODEL_STORE_OWNER_KEY);
        if (viewModelStoreOwner == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_STORE_OWNER_KEY`");
        }
        Bundle bundle = (Bundle) mutableCreationExtras.get(DEFAULT_ARGS_KEY);
        String str = (String) mutableCreationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_KEY`");
        }
        SavedStateRegistry.SavedStateProvider savedStateProvider = savedStateRegistryOwner.getSavedStateRegistry().getSavedStateProvider();
        SavedStateHandlesProvider savedStateHandlesProvider = savedStateProvider instanceof SavedStateHandlesProvider ? (SavedStateHandlesProvider) savedStateProvider : null;
        if (savedStateHandlesProvider == null) {
            throw new IllegalStateException("enableSavedStateHandles() wasn't called prior to createSavedStateHandle() call");
        }
        SavedStateHandlesVM savedStateHandlesVM = getSavedStateHandlesVM(viewModelStoreOwner);
        SavedStateHandle savedStateHandle = (SavedStateHandle) ((LinkedHashMap) savedStateHandlesVM.handles).get(str);
        if (savedStateHandle != null) {
            return savedStateHandle;
        }
        SavedStateHandle.Companion companion = SavedStateHandle.Companion;
        if (!savedStateHandlesProvider.restored) {
            savedStateHandlesProvider.restoredState = savedStateHandlesProvider.savedStateRegistry.consumeRestoredStateForKey("androidx.lifecycle.internal.SavedStateHandlesProvider");
            savedStateHandlesProvider.restored = true;
        }
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
        Lifecycle.State state = savedStateRegistryOwner.getLifecycle().mState;
        if (!(state == Lifecycle.State.INITIALIZED || state == Lifecycle.State.CREATED)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (savedStateRegistryOwner.getSavedStateRegistry().getSavedStateProvider() == null) {
            SavedStateHandlesProvider savedStateHandlesProvider = new SavedStateHandlesProvider(savedStateRegistryOwner.getSavedStateRegistry(), (ViewModelStoreOwner) savedStateRegistryOwner);
            savedStateRegistryOwner.getSavedStateRegistry().registerSavedStateProvider("androidx.lifecycle.internal.SavedStateHandlesProvider", savedStateHandlesProvider);
            savedStateRegistryOwner.getLifecycle().addObserver(new SavedStateHandleAttacher(savedStateHandlesProvider));
        }
    }

    public static final SavedStateHandlesVM getSavedStateHandlesVM(ViewModelStoreOwner viewModelStoreOwner) {
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder = new InitializerViewModelFactoryBuilder();
        SavedStateHandleSupport$savedStateHandlesVM$1$1 savedStateHandleSupport$savedStateHandlesVM$1$1 = new Function1() { // from class: androidx.lifecycle.SavedStateHandleSupport$savedStateHandlesVM$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return new SavedStateHandlesVM();
            }
        };
        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(SavedStateHandlesVM.class);
        ArrayList arrayList = (ArrayList) initializerViewModelFactoryBuilder.initializers;
        arrayList.add(new ViewModelInitializer(orCreateKotlinClass.getJClass(), savedStateHandleSupport$savedStateHandlesVM$1$1));
        ViewModelInitializer[] viewModelInitializerArr = (ViewModelInitializer[]) arrayList.toArray(new ViewModelInitializer[0]);
        return (SavedStateHandlesVM) new ViewModelProvider(viewModelStoreOwner, new InitializerViewModelFactory((ViewModelInitializer[]) Arrays.copyOf(viewModelInitializerArr, viewModelInitializerArr.length))).get(SavedStateHandlesVM.class, "androidx.lifecycle.internal.SavedStateHandlesVM");
    }
}
