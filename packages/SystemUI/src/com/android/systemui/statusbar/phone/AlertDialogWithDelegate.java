package com.android.systemui.statusbar.phone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewRootImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AlertDialogWithDelegate extends AlertDialog implements ViewRootImpl.ConfigChangedCallback {
    public final DialogDelegate delegate;

    public AlertDialogWithDelegate(Context context, int i, DialogDelegate dialogDelegate) {
        super(context, i);
        this.delegate = dialogDelegate;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        this.delegate.onConfigurationChanged(this, configuration);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        this.delegate.beforeCreate(this);
        super.onCreate(bundle);
        this.delegate.onCreate(this, bundle);
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        ViewRootImpl.addConfigCallback(this);
        this.delegate.onStart(this);
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        ViewRootImpl.removeConfigCallback(this);
        this.delegate.onStop(this);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.delegate.onWindowFocusChanged(this, z);
    }
}
