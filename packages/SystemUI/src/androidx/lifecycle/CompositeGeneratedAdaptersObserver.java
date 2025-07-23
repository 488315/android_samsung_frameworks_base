package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CompositeGeneratedAdaptersObserver implements LifecycleEventObserver {
    public final GeneratedAdapter[] generatedAdapters;

    public CompositeGeneratedAdaptersObserver(GeneratedAdapter[] generatedAdapterArr) {
        this.generatedAdapters = generatedAdapterArr;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        new MethodCallsLogger();
        GeneratedAdapter[] generatedAdapterArr = this.generatedAdapters;
        if (generatedAdapterArr.length > 0) {
            GeneratedAdapter generatedAdapter = generatedAdapterArr[0];
            throw null;
        }
        if (generatedAdapterArr.length <= 0) {
            return;
        }
        GeneratedAdapter generatedAdapter2 = generatedAdapterArr[0];
        throw null;
    }
}
