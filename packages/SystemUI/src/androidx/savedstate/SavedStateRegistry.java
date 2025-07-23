package androidx.savedstate;

import android.os.Bundle;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LegacySavedStateHandleController;
import androidx.savedstate.Recreator;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SavedStateRegistry {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean attached;
    public final SafeIterableMap components = new SafeIterableMap();
    public boolean isAllowingSavingState = true;
    public boolean isRestored;
    public Recreator.SavedStateProvider recreatorProvider;
    public Bundle restoredState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface AutoRecreated {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SavedStateProvider {
        Bundle saveState();
    }

    static {
        new Companion(null);
    }

    public final Bundle consumeRestoredStateForKey(String str) {
        if (!this.isRestored) {
            throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
        }
        Bundle bundle = this.restoredState;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = bundle.getBundle(str);
        Bundle bundle3 = this.restoredState;
        if (bundle3 != null) {
            bundle3.remove(str);
        }
        Bundle bundle4 = this.restoredState;
        if (bundle4 != null && !bundle4.isEmpty()) {
            return bundle2;
        }
        this.restoredState = null;
        return bundle2;
    }

    public final SavedStateProvider getSavedStateProvider() {
        String str;
        SavedStateProvider savedStateProvider;
        Iterator it = this.components.iterator();
        do {
            SafeIterableMap.ListIterator listIterator = (SafeIterableMap.ListIterator) it;
            if (!listIterator.hasNext()) {
                return null;
            }
            Map.Entry entry = (Map.Entry) listIterator.next();
            str = (String) entry.getKey();
            savedStateProvider = (SavedStateProvider) entry.getValue();
        } while (!Intrinsics.areEqual(str, "androidx.lifecycle.internal.SavedStateHandlesProvider"));
        return savedStateProvider;
    }

    public final void registerSavedStateProvider(String str, SavedStateProvider savedStateProvider) {
        if (((SavedStateProvider) this.components.putIfAbsent(str, savedStateProvider)) != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
        }
    }

    public final void runOnNextRecreation() {
        if (!this.isAllowingSavingState) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        Recreator.SavedStateProvider savedStateProvider = this.recreatorProvider;
        if (savedStateProvider == null) {
            savedStateProvider = new Recreator.SavedStateProvider(this);
        }
        this.recreatorProvider = savedStateProvider;
        try {
            Class[] clsArr = new Class[0];
            LegacySavedStateHandleController.OnRecreation.class.getDeclaredConstructor(null);
            Recreator.SavedStateProvider savedStateProvider2 = this.recreatorProvider;
            if (savedStateProvider2 != null) {
                savedStateProvider2.classes.add(LegacySavedStateHandleController.OnRecreation.class.getName());
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class " + LegacySavedStateHandleController.OnRecreation.class.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
        }
    }
}
