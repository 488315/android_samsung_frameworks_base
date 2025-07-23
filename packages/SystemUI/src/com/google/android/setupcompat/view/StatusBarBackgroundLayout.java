package com.google.android.setupcompat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class StatusBarBackgroundLayout extends FrameLayout {
    public static final Logger LOG = new Logger("StatusBarBgLayout");
    public Object lastInsets;
    public Drawable statusBarBackground;

    public StatusBarBackgroundLayout(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext()) && windowInsets.getSystemWindowInsetBottom() > 0) {
            LOG.atDebug("NavigationBarHeight: " + windowInsets.getSystemWindowInsetBottom());
            windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), 0);
        }
        this.lastInsets = windowInsets;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.lastInsets == null) {
            requestApplyInsets();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int systemWindowInsetTop;
        super.onDraw(canvas);
        Object obj = this.lastInsets;
        if (obj == null || (systemWindowInsetTop = ((WindowInsets) obj).getSystemWindowInsetTop()) <= 0) {
            return;
        }
        this.statusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop);
        this.statusBarBackground.draw(canvas);
    }

    public StatusBarBackgroundLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StatusBarBackgroundLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
