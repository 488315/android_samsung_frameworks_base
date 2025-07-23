package androidx.savedstate;

import android.os.Bundle;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Recreator implements LifecycleEventObserver {
    public final SavedStateRegistryOwner owner;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int size = stringArrayList.size();
        int i = 0;
        while (i < size) {
            String str = stringArrayList.get(i);
            i++;
            String str2 = str;
            try {
                Class<? extends U> asSubclass = Class.forName(str2, false, Recreator.class.getClassLoader()).asSubclass(SavedStateRegistry.AutoRecreated.class);
                try {
                    Class[] clsArr = new Class[0];
                    Constructor declaredConstructor = asSubclass.getDeclaredConstructor(null);
                    declaredConstructor.setAccessible(true);
                    try {
                        ((LegacySavedStateHandleController.OnRecreation) ((SavedStateRegistry.AutoRecreated) declaredConstructor.newInstance(null))).getClass();
                        if (!(savedStateRegistryOwner instanceof ViewModelStoreOwner)) {
                            throw new IllegalStateException("Internal error: OnRecreation should be registered only on components that implement ViewModelStoreOwner");
                        }
                        ViewModelStore viewModelStore = ((ViewModelStoreOwner) savedStateRegistryOwner).getViewModelStore();
                        SavedStateRegistry savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
                        viewModelStore.getClass();
                        Iterator it = new HashSet(((LinkedHashMap) viewModelStore.map).keySet()).iterator();
                        while (it.hasNext()) {
                            ViewModel viewModel = (ViewModel) ((LinkedHashMap) viewModelStore.map).get((String) it.next());
                            viewModel.getClass();
                            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, savedStateRegistryOwner.getLifecycle());
                        }
                        if (!new HashSet(((LinkedHashMap) viewModelStore.map).keySet()).isEmpty()) {
                            savedStateRegistry.runOnNextRecreation();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed to instantiate ", str2), e);
                    }
                } catch (NoSuchMethodException e2) {
                    throw new IllegalStateException("Class " + asSubclass.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
                }
            } catch (ClassNotFoundException e3) {
                throw new RuntimeException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Class ", str2, " wasn't found"), e3);
            }
        }
    }
}
