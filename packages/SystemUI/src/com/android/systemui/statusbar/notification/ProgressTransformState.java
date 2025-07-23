package com.android.systemui.statusbar.notification;

import android.util.Pools;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ProgressTransformState extends TransformState {
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean sameAs(TransformState transformState) {
        if (transformState instanceof ProgressTransformState) {
            return true;
        }
        return this.mSameAsAny;
    }
}
