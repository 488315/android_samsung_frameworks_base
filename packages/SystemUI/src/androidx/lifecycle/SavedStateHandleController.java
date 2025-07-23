package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.io.Closeable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SavedStateHandleController implements LifecycleEventObserver, Closeable {
    public final SavedStateHandle handle;
    public boolean isAttached;
    public final String key;

    public SavedStateHandleController(String str, SavedStateHandle savedStateHandle) {
        this.key = str;
        this.handle = savedStateHandle;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.isAttached = false;
            lifecycleOwner.getLifecycle().removeObserver(this);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }
}
