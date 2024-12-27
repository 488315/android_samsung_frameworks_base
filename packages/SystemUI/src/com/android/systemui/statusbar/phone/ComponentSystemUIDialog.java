package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ComponentSystemUIDialog extends SystemUIDialog implements LifecycleOwner, SavedStateRegistryOwner, OnBackPressedDispatcherOwner {
    public LifecycleRegistry _lifecycleRegistry;
    public final OnBackPressedDispatcher onBackPressedDispatcher;
    public final SavedStateRegistryController savedStateRegistryController;

    public ComponentSystemUIDialog(Context context, int i, boolean z, SystemUIDialogManager systemUIDialogManager, SysUiState sysUiState, BroadcastDispatcher broadcastDispatcher, DialogTransitionAnimator dialogTransitionAnimator, DialogDelegate dialogDelegate) {
        super(context, i, z, systemUIDialogManager, sysUiState, broadcastDispatcher, dialogTransitionAnimator, dialogDelegate);
        SavedStateRegistryController.Companion.getClass();
        this.savedStateRegistryController = SavedStateRegistryController.Companion.create(this);
        this.onBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable() { // from class: com.android.systemui.statusbar.phone.ComponentSystemUIDialog$onBackPressedDispatcher$1
            @Override // java.lang.Runnable
            public final void run() {
                super/*android.app.AlertDialog*/.onBackPressed();
            }
        });
    }

    @Override // android.app.Dialog
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initializeViewTreeOwners();
        super.addContentView(view, layoutParams);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return getLifecycleRegistry$1();
    }

    public final LifecycleRegistry getLifecycleRegistry$1() {
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
        Intrinsics.checkNotNull(window);
        ViewTreeLifecycleOwner.set(window.getDecorView(), this);
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        ViewTreeOnBackPressedDispatcherOwner.set(window2.getDecorView(), this);
        Window window3 = getWindow();
        Intrinsics.checkNotNull(window3);
        ViewTreeSavedStateRegistryOwner.set(window3.getDecorView(), this);
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        this.onBackPressedDispatcher.onBackPressed();
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.onBackPressedDispatcher.setOnBackInvokedDispatcher(getOnBackInvokedDispatcher());
        this.savedStateRegistryController.performRestore(bundle);
        getLifecycleRegistry$1().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override // android.app.Dialog
    public final Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        this.savedStateRegistryController.performSave(onSaveInstanceState);
        return onSaveInstanceState;
    }

    @Override // android.app.Dialog
    public final void setContentView(int i) {
        initializeViewTreeOwners();
        super.setContentView(i);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        getLifecycleRegistry$1().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        getLifecycleRegistry$1().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        this._lifecycleRegistry = null;
    }

    @Override // android.app.Dialog
    public final void setContentView(View view) {
        initializeViewTreeOwners();
        super.setContentView(view);
    }

    @Override // android.app.Dialog
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initializeViewTreeOwners();
        super.setContentView(view, layoutParams);
    }
}
