package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import android.view.View;
import com.android.systemui.statusbar.notification.stack.MediaContainerView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaContainerController implements NodeController {
    public final LayoutInflater layoutInflater;
    public MediaContainerView mediaContainerView;

    public MediaContainerController(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        return "MediaContainer";
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getView() {
        MediaContainerView mediaContainerView = this.mediaContainerView;
        mediaContainerView.getClass();
        return mediaContainerView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void resetKeepInParentForAnimation() {
    }
}
