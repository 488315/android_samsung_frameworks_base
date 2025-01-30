package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FloatingIconView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mIconArea;
    public final int mIconSize;
    public final FloatingIconView$$ExternalSyntheticLambda0 mInsetsComputer;
    public final Rect mTmpBounds;
    public final Region mTmpRegion;
    public final Region mTouchableRegion;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.wm.shell.controlpanel.activity.FloatingIconView$$ExternalSyntheticLambda0] */
    public FloatingIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpBounds = new Rect();
        this.mTmpRegion = new Region();
        this.mTouchableRegion = new Region();
        this.mInsetsComputer = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.wm.shell.controlpanel.activity.FloatingIconView$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                FloatingIconView floatingIconView = FloatingIconView.this;
                int i = FloatingIconView.$r8$clinit;
                floatingIconView.getClass();
                internalInsetsInfo.contentInsets.setEmpty();
                internalInsetsInfo.visibleInsets.setEmpty();
                internalInsetsInfo.touchableRegion.set(floatingIconView.mTouchableRegion);
                internalInsetsInfo.setTouchableInsets(3);
            }
        };
        this.mIconSize = context.getResources().getDimensionPixelSize(R.dimen.flex_panel_floating_icon_size);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        boolean gatherTransparentRegion = super.gatherTransparentRegion(region);
        this.mTmpRegion.set(this.mTouchableRegion);
        Rect rect = this.mTmpBounds;
        int x = (int) this.mIconArea.getX();
        int y = (int) this.mIconArea.getY();
        int i = this.mIconSize;
        rect.set(x, y, x + i, i + y);
        this.mTouchableRegion.set(this.mTmpBounds);
        if (!this.mTmpRegion.equals(this.mTouchableRegion)) {
            forceLayout();
            requestLayout();
        }
        Region region2 = new Region(0, 0, getWidth(), getHeight());
        region2.op(this.mTouchableRegion, Region.Op.XOR);
        region.set(region2);
        return gatherTransparentRegion;
    }
}
