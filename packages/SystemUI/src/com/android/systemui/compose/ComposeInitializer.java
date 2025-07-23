package com.android.systemui.compose;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import com.android.systemui.lifecycle.ViewLifecycleOwner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposeInitializer {
    public static final ComposeInitializer INSTANCE = new ComposeInitializer();

    private ComposeInitializer() {
    }

    public static void onAttachedToWindow(View view) {
        if (ViewTreeLifecycleOwner.get(view) != null) {
            throw new IllegalStateException(("root " + view + " already has a LifecycleOwner").toString());
        }
        Object parent = view.getParent();
        if ((parent instanceof View) && ((View) parent).getId() != 16908290) {
            throw new IllegalStateException("ComposeInitializer.onAttachedToWindow(View) must be called on the content child.Outside of activities and dialogs, this is usually the top-most View of a window.");
        }
        final ViewLifecycleOwner viewLifecycleOwner = new ViewLifecycleOwner(view);
        Object obj = new SavedStateRegistryOwner() { // from class: com.android.systemui.compose.ComposeInitializer$onAttachedToWindow$savedStateRegistryOwner$1
            public final SavedStateRegistry savedStateRegistry;

            {
                SavedStateRegistryController.Companion.getClass();
                SavedStateRegistryController create = SavedStateRegistryController.Companion.create(this);
                create.performRestore(null);
                this.savedStateRegistry = create.savedStateRegistry;
            }

            @Override // androidx.lifecycle.LifecycleOwner
            public final Lifecycle getLifecycle() {
                return ViewLifecycleOwner.this.registry;
            }

            @Override // androidx.savedstate.SavedStateRegistryOwner
            public final SavedStateRegistry getSavedStateRegistry() {
                return this.savedStateRegistry;
            }
        };
        viewLifecycleOwner.onCreate();
        view.setTag(R.id.view_tree_lifecycle_owner, viewLifecycleOwner);
        view.setTag(R.id.view_tree_saved_state_registry_owner, obj);
    }

    public static void onDetachedFromWindow(View view) {
        ((ViewLifecycleOwner) ViewTreeLifecycleOwner.get(view)).onDestroy();
        ViewTreeLifecycleOwner.set(null, view);
        view.setTag(R.id.view_tree_saved_state_registry_owner, null);
    }
}
