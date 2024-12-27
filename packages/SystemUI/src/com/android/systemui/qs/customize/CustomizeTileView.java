package com.android.systemui.qs.customize;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomizeTileView extends QSTileViewImpl {
    public boolean showAppLabel;
    public boolean showSideView;

    public CustomizeTileView(Context context) {
        super(context, false, null, 4, null);
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
        TextView textView = this.secondaryLabel;
        if (textView == null) {
            textView = null;
        }
        textView.setVisibility((!this.showAppLabel || TextUtils.isEmpty(state.secondaryLabel)) ? 8 : 0);
        if (this.showSideView) {
            return;
        }
        ViewGroup viewGroup = this.sideView;
        (viewGroup != null ? viewGroup : null).setVisibility(8);
    }

    @Override // android.view.View
    public final boolean isLongClickable() {
        return false;
    }
}
