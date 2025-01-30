package com.android.systemui.biometrics.ui.binder;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.biometrics.AuthIconController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Spaghetti$attach$1 implements DefaultLifecycleObserver {
    public final /* synthetic */ AuthIconController $iconController;
    public final /* synthetic */ Spaghetti this$0;

    public Spaghetti$attach$1(Spaghetti spaghetti, AuthIconController authIconController) {
        this.this$0 = spaghetti;
        this.$iconController = authIconController;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public final void onCreate(LifecycleOwner lifecycleOwner) {
        LifecycleOwnerKt.getLifecycleScope(lifecycleOwner);
        this.this$0.getClass();
        this.$iconController.deactivated = false;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public final void onDestroy$1() {
        this.this$0.getClass();
        this.$iconController.deactivated = true;
    }
}
