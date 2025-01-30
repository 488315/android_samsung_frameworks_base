package androidx.savedstate;

import android.os.Bundle;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.savedstate.SavedStateRegistry;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SavedStateRegistryController {
    public static final Companion Companion = new Companion(null);
    public boolean attached;
    public final SavedStateRegistryOwner owner;
    public final SavedStateRegistry savedStateRegistry;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ SavedStateRegistryController(SavedStateRegistryOwner savedStateRegistryOwner, DefaultConstructorMarker defaultConstructorMarker) {
        this(savedStateRegistryOwner);
    }

    public final void performAttach() {
        SavedStateRegistryOwner savedStateRegistryOwner = this.owner;
        LifecycleRegistry lifecycle = savedStateRegistryOwner.getLifecycle();
        if (!(lifecycle.mState == Lifecycle.State.INITIALIZED)) {
            throw new IllegalStateException("Restarter must be created only during owner's initialization stage".toString());
        }
        lifecycle.addObserver(new Recreator(savedStateRegistryOwner));
        final SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        if (!(!savedStateRegistry.attached)) {
            throw new IllegalStateException("SavedStateRegistry was already attached.".toString());
        }
        lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.savedstate.SavedStateRegistry$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                Lifecycle.Event event2 = Lifecycle.Event.ON_START;
                SavedStateRegistry savedStateRegistry2 = SavedStateRegistry.this;
                if (event == event2) {
                    savedStateRegistry2.isAllowingSavingState = true;
                } else if (event == Lifecycle.Event.ON_STOP) {
                    savedStateRegistry2.isAllowingSavingState = false;
                }
            }
        });
        savedStateRegistry.attached = true;
        this.attached = true;
    }

    public final void performRestore(Bundle bundle) {
        if (!this.attached) {
            performAttach();
        }
        LifecycleRegistry lifecycle = this.owner.getLifecycle();
        if (!(!lifecycle.mState.isAtLeast(Lifecycle.State.STARTED))) {
            throw new IllegalStateException(("performRestore cannot be called when owner is " + lifecycle.mState).toString());
        }
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        if (!savedStateRegistry.attached) {
            throw new IllegalStateException("You must call performAttach() before calling performRestore(Bundle).".toString());
        }
        if (!(!savedStateRegistry.isRestored)) {
            throw new IllegalStateException("SavedStateRegistry was already restored.".toString());
        }
        savedStateRegistry.restoredState = bundle != null ? bundle.getBundle("androidx.lifecycle.BundlableSavedStateRegistry.key") : null;
        savedStateRegistry.isRestored = true;
    }

    public final void performSave(Bundle bundle) {
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        savedStateRegistry.getClass();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = savedStateRegistry.restoredState;
        if (bundle3 != null) {
            bundle2.putAll(bundle3);
        }
        SafeIterableMap safeIterableMap = savedStateRegistry.components;
        safeIterableMap.getClass();
        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = safeIterableMap.new IteratorWithAdditions();
        safeIterableMap.mIterators.put(iteratorWithAdditions, Boolean.FALSE);
        while (iteratorWithAdditions.hasNext()) {
            Map.Entry entry = (Map.Entry) iteratorWithAdditions.next();
            bundle2.putBundle((String) entry.getKey(), ((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState());
        }
        if (bundle2.isEmpty()) {
            return;
        }
        bundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle2);
    }

    private SavedStateRegistryController(SavedStateRegistryOwner savedStateRegistryOwner) {
        this.owner = savedStateRegistryOwner;
        this.savedStateRegistry = new SavedStateRegistry();
    }
}
