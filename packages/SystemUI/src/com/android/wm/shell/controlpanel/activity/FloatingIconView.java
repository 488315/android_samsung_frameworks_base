package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class FloatingIconView extends FrameLayout {
    public final Region mTmpRegion;
    public final Region mTouchableRegion;

    public FloatingIconView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
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
