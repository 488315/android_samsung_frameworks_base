package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FloatingIconView extends FrameLayout {
    public final Region mTmpRegion;
    public final Region mTouchableRegion;

    public FloatingIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new Rect();
        this.mTmpRegion = new Region();
        this.mTouchableRegion = new Region();
        context.getResources().getDimensionPixelSize(R.dimen.flex_panel_floating_icon_size);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        super.gatherTransparentRegion(region);
        this.mTmpRegion.set(this.mTouchableRegion);
        throw null;
    }
}
