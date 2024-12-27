package com.android.systemui.bouncer.ui;

import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BouncerViewImpl implements BouncerView {
    public WeakReference _delegate = new WeakReference(null);

    public final KeyguardBouncerViewBinder$bind$delegate$1 getDelegate() {
        return (KeyguardBouncerViewBinder$bind$delegate$1) this._delegate.get();
    }
}
