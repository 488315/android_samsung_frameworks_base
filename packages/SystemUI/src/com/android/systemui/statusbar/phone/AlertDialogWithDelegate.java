package com.android.systemui.statusbar.phone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewRootImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
