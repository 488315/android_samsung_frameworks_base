package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.ComponentDialog;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatDialog extends ComponentDialog implements AppCompatCallback {
    public AppCompatDelegateImpl mDelegate;
    public final KeyEventDispatcher$Component mKeyDispatcher;

    public AppCompatDialog(Context context) {
        this(context, 0);
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners$1();
        getDelegate().addContentView(view, layoutParams);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        getDelegate().onDestroy();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        getWindow().getDecorView();
        KeyEventDispatcher$Component keyEventDispatcher$Component = this.mKeyDispatcher;
        if (keyEventDispatcher$Component == null) {
            return false;
        }
        return keyEventDispatcher$Component.superDispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Dialog
    public final View findViewById(int i) {
        return getDelegate().findViewById(i);
    }

    public final AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            AppCompatDelegate.SerialExecutor serialExecutor = AppCompatDelegate.sSerialExecutorForLocalesStorage;
            this.mDelegate = new AppCompatDelegateImpl(this, this);
        }
        return this.mDelegate;
    }

    public final void initViewTreeOwners$1() {
        ViewTreeLifecycleOwner.set(this, getWindow().getDecorView());
        ViewTreeSavedStateRegistryOwner.set(getWindow().getDecorView(), this);
        ViewTreeOnBackPressedDispatcherOwner.set(getWindow().getDecorView(), this);
    }

    @Override // android.app.Dialog
    public final void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        getDelegate().installViewFactory();
        super.onCreate(bundle);
        getDelegate().onCreate();
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void setContentView(int i) {
        initViewTreeOwners$1();
        getDelegate().setContentView(i);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        getDelegate().setTitle(charSequence);
    }

    public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public AppCompatDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context);
        this.mKeyDispatcher = new KeyEventDispatcher$Component() { // from class: androidx.appcompat.app.AppCompatDialog$$ExternalSyntheticLambda0
            @Override // androidx.core.view.KeyEventDispatcher$Component
            public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
                return AppCompatDialog.this.superDispatchKeyEvent(keyEvent);
            }
        };
        setCancelable(z);
        setOnCancelListener(onCancelListener);
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void setContentView(View view) {
        initViewTreeOwners$1();
        getDelegate().setContentView(view);
    }

    @Override // android.app.Dialog
    public final void setTitle(int i) {
        super.setTitle(i);
        getDelegate().setTitle(getContext().getString(i));
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners$1();
        getDelegate().setContentView(view, layoutParams);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AppCompatDialog(android.content.Context r5, int r6) {
        /*
            r4 = this;
            r0 = 1
            r1 = 2130969075(0x7f0401f3, float:1.7546822E38)
            if (r6 != 0) goto L15
            android.util.TypedValue r2 = new android.util.TypedValue
            r2.<init>()
            android.content.res.Resources$Theme r3 = r5.getTheme()
            r3.resolveAttribute(r1, r2, r0)
            int r2 = r2.resourceId
            goto L16
        L15:
            r2 = r6
        L16:
            r4.<init>(r5, r2)
            androidx.appcompat.app.AppCompatDialog$$ExternalSyntheticLambda0 r2 = new androidx.appcompat.app.AppCompatDialog$$ExternalSyntheticLambda0
            r2.<init>()
            r4.mKeyDispatcher = r2
            androidx.appcompat.app.AppCompatDelegate r4 = r4.getDelegate()
            if (r6 != 0) goto L34
            android.util.TypedValue r6 = new android.util.TypedValue
            r6.<init>()
            android.content.res.Resources$Theme r5 = r5.getTheme()
            r5.resolveAttribute(r1, r6, r0)
            int r6 = r6.resourceId
        L34:
            r5 = r4
            androidx.appcompat.app.AppCompatDelegateImpl r5 = (androidx.appcompat.app.AppCompatDelegateImpl) r5
            r5.mThemeResId = r6
            r4.onCreate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDialog.<init>(android.content.Context, int):void");
    }
}
