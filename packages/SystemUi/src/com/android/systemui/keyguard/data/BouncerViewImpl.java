package com.android.systemui.keyguard.data;

import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerViewImpl implements BouncerView {
    public WeakReference _delegate = new WeakReference(null);

    public final BouncerViewDelegate getDelegate() {
        return (BouncerViewDelegate) this._delegate.get();
    }
}
