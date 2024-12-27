package com.android.systemui.statusbar.ui;

import android.content.Context;
import android.view.DisplayCutout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SystemBarUtilsProxyImpl implements SystemBarUtilsProxy {
    public final Context context;

    public SystemBarUtilsProxyImpl(Context context) {
        this.context = context;
    }

    public final int getStatusBarHeaderHeightKeyguard() {
        DisplayCutout cutout = this.context.getDisplay().getCutout();
        return Math.max(SystemBarUtils.getStatusBarHeight(this.context), this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard) + (cutout == null ? 0 : cutout.getWaterfallInsets().top));
    }
}
