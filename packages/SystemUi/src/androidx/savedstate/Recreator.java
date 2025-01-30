package androidx.savedstate;

import android.os.Bundle;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.lifecycle.LegacySavedStateHandleController;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Recreator implements LifecycleEventObserver {
    public final SavedStateRegistryOwner owner;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedStateProvider implements SavedStateRegistry.SavedStateProvider {
        public final Set classes = new LinkedHashSet();

        public SavedStateProvider(SavedStateRegistry savedStateRegistry) {
            savedStateRegistry.registerSavedStateProvider("androidx.savedstate.Restarter", this);
        }

        @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
        public final Bundle saveState() {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("classes_to_restore", new ArrayList<>(this.classes));
            return bundle;
        }
    }

    static {
        new Companion(null);
    }

    public Recreator(SavedStateRegistryOwner savedStateRegistryOwner) {
        this.owner = savedStateRegistryOwner;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        HashMap hashMap;
        if (event != Lifecycle.Event.ON_CREATE) {
            throw new AssertionError("Next event must be ON_CREATE");
        }
        lifecycleOwner.getLifecycle().removeObserver(this);
        SavedStateRegistryOwner savedStateRegistryOwner = this.owner;
        Bundle consumeRestoredStateForKey = savedStateRegistryOwner.getSavedStateRegistry().consumeRestoredStateForKey("androidx.savedstate.Restarter");
        if (consumeRestoredStateForKey == null) {
            return;
        }
        ArrayList<String> stringArrayList = consumeRestoredStateForKey.getStringArrayList("classes_to_restore");
        if (stringArrayList == null) {
            throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
        }
        for (String str : stringArrayList) {
            try {
                Class<? extends U> asSubclass = Class.forName(str, false, Recreator.class.getClassLoader()).asSubclass(SavedStateRegistry.AutoRecreated.class);
                try {
                    Constructor declaredConstructor = asSubclass.getDeclaredConstructor(new Class[0]);
                    declaredConstructor.setAccessible(true);
                    try {
                        if (!(savedStateRegistryOwner instanceof ViewModelStoreOwner)) {
                            throw new IllegalStateException("Internal error: OnRecreation should be registered only on components that implement ViewModelStoreOwner");
                        }
                        ViewModelStore viewModelStore = ((ViewModelStoreOwner) savedStateRegistryOwner).getViewModelStore();
                        SavedStateRegistry savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
                        viewModelStore.getClass();
                        Iterator it = new HashSet(viewModelStore.mMap.keySet()).iterator();
                        while (true) {
                            boolean hasNext = it.hasNext();
                            hashMap = viewModelStore.mMap;
                            if (!hasNext) {
                                break;
                            } else {
                                LegacySavedStateHandleController.attachHandleIfNeeded((ViewModel) hashMap.get((String) it.next()), savedStateRegistry, savedStateRegistryOwner.getLifecycle());
                            }
                        }
                        if (!new HashSet(hashMap.keySet()).isEmpty()) {
                            savedStateRegistry.runOnNextRecreation();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Failed to instantiate ", str), e);
                    }
                } catch (NoSuchMethodException e2) {
                    throw new IllegalStateException("Class " + asSubclass.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
                }
            } catch (ClassNotFoundException e3) {
                throw new RuntimeException(PathParser$$ExternalSyntheticOutline0.m29m("Class ", str, " wasn't found"), e3);
            }
        }
    }
}
