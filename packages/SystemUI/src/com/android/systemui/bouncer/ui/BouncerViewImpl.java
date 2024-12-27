package com.android.systemui.bouncer.ui;

import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import java.lang.ref.WeakReference;

public final class BouncerViewImpl implements BouncerView {
    public WeakReference _delegate = new WeakReference(null);

    public final KeyguardBouncerViewBinder$bind$delegate$1 getDelegate() {
        return (KeyguardBouncerViewBinder$bind$delegate$1) this._delegate.get();
    }
}
