package androidx.activity;

import androidx.core.util.Consumer;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class OnBackPressedCallback {
    public final CopyOnWriteArrayList cancellables = new CopyOnWriteArrayList();
    public Consumer enabledConsumer;
    public boolean isEnabled;

    public OnBackPressedCallback(boolean z) {
        this.isEnabled = z;
    }

    public abstract void handleOnBackPressed();
}
