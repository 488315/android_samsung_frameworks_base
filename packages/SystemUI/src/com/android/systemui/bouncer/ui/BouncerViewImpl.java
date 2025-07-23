package com.android.systemui.bouncer.ui;

import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerViewImpl implements BouncerView {
    public WeakReference _delegate = new WeakReference(null);

    public final KeyguardBouncerViewBinder$bind$delegate$1 getDelegate() {
        return (KeyguardBouncerViewBinder$bind$delegate$1) this._delegate.get();
    }
}
