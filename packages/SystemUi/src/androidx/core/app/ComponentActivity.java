package androidx.core.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ComponentActivity extends Activity implements LifecycleOwner, KeyEventDispatcher$Component {
    public final LifecycleRegistry mLifecycleRegistry;

    public ComponentActivity() {
        new SimpleArrayMap();
        this.mLifecycleRegistry = new LifecycleRegistry(this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (getWindow().getDecorView() != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        }
        return superDispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (getWindow().getDecorView() != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        }
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    public LifecycleRegistry getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public void onCancel(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.injectIfNeededIn(this);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
        Lifecycle.State state = Lifecycle.State.CREATED;
        lifecycleRegistry.enforceMainThreadIfNeeded("markState");
        lifecycleRegistry.setCurrentState(state);
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.core.view.KeyEventDispatcher$Component
    public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }
}
