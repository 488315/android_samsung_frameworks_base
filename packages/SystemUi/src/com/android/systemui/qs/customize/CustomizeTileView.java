package com.android.systemui.qs.customize;

import android.content.Context;
import android.text.TextUtils;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomizeTileView extends QSTileViewImpl {
    public boolean showAppLabel;
    public boolean showSideView;

    public CustomizeTileView(Context context, QSIconView qSIconView) {
        super(context, qSIconView, false);
        this.showSideView = true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileViewImpl
    public final boolean animationsEnabled() {
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileViewImpl
    public final void handleStateChanged(QSTile.State state) {
        super.handleStateChanged(state);
        this.showRippleEffect = false;
        getSecondaryLabel().setVisibility((!this.showAppLabel || TextUtils.isEmpty(state.secondaryLabel)) ? 8 : 0);
        if (this.showSideView) {
            return;
        }
        getSideView().setVisibility(8);
    }

    @Override // android.view.View
    public final boolean isLongClickable() {
        return false;
    }
}
