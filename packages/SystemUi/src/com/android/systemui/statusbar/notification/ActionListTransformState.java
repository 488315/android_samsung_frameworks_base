package com.android.systemui.statusbar.notification;

import android.util.Pools;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActionListTransformState extends TransformState {
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void resetTransformedView() {
        float translationY = this.mTransformedView.getTranslationY();
        super.resetTransformedView();
        this.mTransformedView.setTranslationY(translationY);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean sameAs(TransformState transformState) {
        return transformState instanceof ActionListTransformState;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void transformViewFullyFrom(TransformState transformState, float f) {
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void transformViewFullyTo(TransformState transformState, float f) {
    }
}
