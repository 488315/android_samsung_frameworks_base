package androidx.savedstate;

import androidx.lifecycle.LifecycleOwner;

/* compiled from: SavedStateRegistryOwner.kt */
/* loaded from: classes.dex */
public interface SavedStateRegistryOwner extends LifecycleOwner {
    SavedStateRegistry getSavedStateRegistry();
}
