package androidx.activity;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OnBackPressedCallback {
    public final CopyOnWriteArrayList cancellables = new CopyOnWriteArrayList();
    public FunctionReferenceImpl enabledChangedCallback;
    public boolean isEnabled;

    public OnBackPressedCallback(boolean z) {
        this.isEnabled = z;
    }

    public abstract void handleOnBackPressed();

    public final void remove() {
        Iterator it = this.cancellables.iterator();
        while (it.hasNext()) {
            ((Cancellable) it.next()).cancel();
        }
    }

    public void handleOnBackCancelled() {
    }

    public void handleOnBackStarted() {
    }

    public void handleOnBackProgressed(BackEventCompat backEventCompat) {
    }
}
