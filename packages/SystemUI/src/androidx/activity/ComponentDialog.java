package androidx.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ComponentDialog extends Dialog implements LifecycleOwner, OnBackPressedDispatcherOwner, SavedStateRegistryOwner {
    public LifecycleRegistry _lifecycleRegistry;
    public final OnBackPressedDispatcher onBackPressedDispatcher;
    public final SavedStateRegistryController savedStateRegistryController;

    public ComponentDialog(Context context) {
        this(context, 0, 2, null);
    }

    @Override // android.app.Dialog
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initializeViewTreeOwners();
        super.addContentView(view, layoutParams);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        LifecycleRegistry lifecycleRegistry = this._lifecycleRegistry;
        if (lifecycleRegistry != null) {
            return lifecycleRegistry;
        }
        LifecycleRegistry lifecycleRegistry2 = new LifecycleRegistry(this);
        this._lifecycleRegistry = lifecycleRegistry2;
        return lifecycleRegistry2;
    }

    @Override // androidx.activity.OnBackPressedDispatcherOwner
    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        return this.onBackPressedDispatcher;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.savedStateRegistryController.savedStateRegistry;
    }

    public final void initializeViewTreeOwners() {
        Window window = getWindow();
        window.getClass();
        ViewTreeLifecycleOwner.set(this, window.getDecorView());
        Window window2 = getWindow();
        window2.getClass();
        ViewTreeOnBackPressedDispatcherOwner.set(window2.getDecorView(), this);
        Window window3 = getWindow();
        window3.getClass();
        ViewTreeSavedStateRegistryOwner.set(window3.getDecorView(), this);
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        this.onBackPressedDispatcher.onBackPressed();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.onBackPressedDispatcher.setOnBackInvokedDispatcher(getOnBackInvokedDispatcher());
        this.savedStateRegistryController.performRestore(bundle);
        LifecycleRegistry lifecycleRegistry = this._lifecycleRegistry;
        if (lifecycleRegistry == null) {
            lifecycleRegistry = new LifecycleRegistry(this);
            this._lifecycleRegistry = lifecycleRegistry;
        }
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override // android.app.Dialog
    public final Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        this.savedStateRegistryController.performSave(onSaveInstanceState);
        return onSaveInstanceState;
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        LifecycleRegistry lifecycleRegistry = this._lifecycleRegistry;
        if (lifecycleRegistry == null) {
            lifecycleRegistry = new LifecycleRegistry(this);
            this._lifecycleRegistry = lifecycleRegistry;
        }
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override // android.app.Dialog
    public void onStop() {
        LifecycleRegistry lifecycleRegistry = this._lifecycleRegistry;
        if (lifecycleRegistry == null) {
            lifecycleRegistry = new LifecycleRegistry(this);
            this._lifecycleRegistry = lifecycleRegistry;
        }
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        this._lifecycleRegistry = null;
        super.onStop();
    }

    @Override // android.app.Dialog
    public void setContentView(int i) {
        initializeViewTreeOwners();
        super.setContentView(i);
    }

    public /* synthetic */ ComponentDialog(Context context, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? 0 : i);
    }

    public ComponentDialog(Context context, int i) {
        super(context, i);
        SavedStateRegistryController.Companion.getClass();
        this.savedStateRegistryController = SavedStateRegistryController.Companion.create(this);
        this.onBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable() { // from class: androidx.activity.ComponentDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                super/*android.app.Dialog*/.onBackPressed();
            }
        });
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        initializeViewTreeOwners();
        super.setContentView(view);
    }

    @Override // android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initializeViewTreeOwners();
        super.setContentView(view, layoutParams);
    }
}
