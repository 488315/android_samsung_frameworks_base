package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.util.HashMap;

/* loaded from: classes.dex */
class CompositeGeneratedAdaptersObserver implements LifecycleEventObserver {
    private final GeneratedAdapter[] mGeneratedAdapters;

    CompositeGeneratedAdaptersObserver(GeneratedAdapter[] generatedAdapterArr) {
        this.mGeneratedAdapters = generatedAdapterArr;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        new HashMap();
        GeneratedAdapter[] generatedAdapterArr = this.mGeneratedAdapters;
        for (GeneratedAdapter generatedAdapter : generatedAdapterArr) {
            generatedAdapter.callMethods();
        }
        for (GeneratedAdapter generatedAdapter2 : generatedAdapterArr) {
            generatedAdapter2.callMethods();
        }
    }
}
