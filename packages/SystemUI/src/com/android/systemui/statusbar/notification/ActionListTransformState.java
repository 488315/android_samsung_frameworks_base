package com.android.systemui.statusbar.notification;

import android.util.Pools;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
