package com.android.systemui.statusbar.ui;

import android.content.Context;
import android.view.DisplayCutout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
