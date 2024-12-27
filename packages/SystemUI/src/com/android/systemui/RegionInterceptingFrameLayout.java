package com.android.systemui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class RegionInterceptingFrameLayout extends FrameLayout {
    public final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface RegionInterceptableView {
    }

    /* renamed from: $r8$lambda$poBU2FDiEZ-WE5dImWPjK06PXco, reason: not valid java name */
    public static void m878$r8$lambda$poBU2FDiEZWE5dImWPjK06PXco(RegionInterceptingFrameLayout regionInterceptingFrameLayout, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        Region region;
        regionInterceptingFrameLayout.getClass();
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.setEmpty();
        for (int i = 0; i < regionInterceptingFrameLayout.getChildCount(); i++) {
            KeyEvent.Callback childAt = regionInterceptingFrameLayout.getChildAt(i);
            if (childAt instanceof RegionInterceptableView) {
                DisplayCutoutBaseView displayCutoutBaseView = (DisplayCutoutBaseView) ((RegionInterceptableView) childAt);
                if (displayCutoutBaseView.displayInfo.displayCutout != null && displayCutoutBaseView.getVisibility() == 0 && displayCutoutBaseView.shouldDrawCutout) {
                    DisplayCutout displayCutout = displayCutoutBaseView.displayInfo.displayCutout;
                    if (displayCutout == null) {
                        region = null;
                    } else {
                        List<Rect> boundingRects = displayCutout.getBoundingRects();
                        Region obtain = Region.obtain();
                        if (boundingRects != null) {
                            for (Rect rect : boundingRects) {
                                if (rect != null && !rect.isEmpty()) {
                                    obtain.op(rect, Region.Op.UNION);
                                }
                            }
                        }
                        displayCutoutBaseView.getRootView().getLocationOnScreen(displayCutoutBaseView.location);
                        int[] iArr = displayCutoutBaseView.location;
                        obtain.translate(-iArr[0], -iArr[1]);
                        obtain.op(displayCutoutBaseView.getRootView().getLeft(), displayCutoutBaseView.getRootView().getTop(), displayCutoutBaseView.getRootView().getRight(), displayCutoutBaseView.getRootView().getBottom(), Region.Op.INTERSECT);
                        region = obtain;
                    }
                    if (region != null) {
                        internalInsetsInfo.touchableRegion.op(region, Region.Op.UNION);
                    }
                }
            }
        }
    }

    public RegionInterceptingFrameLayout(Context context) {
        super(context);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.m878$r8$lambda$poBU2FDiEZWE5dImWPjK06PXco(RegionInterceptingFrameLayout.this, internalInsetsInfo);
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsListener);
    }

    public RegionInterceptingFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.m878$r8$lambda$poBU2FDiEZWE5dImWPjK06PXco(RegionInterceptingFrameLayout.this, internalInsetsInfo);
            }
        };
    }

    public RegionInterceptingFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.m878$r8$lambda$poBU2FDiEZWE5dImWPjK06PXco(RegionInterceptingFrameLayout.this, internalInsetsInfo);
            }
        };
    }

    public RegionInterceptingFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.RegionInterceptingFrameLayout$$ExternalSyntheticLambda0
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                RegionInterceptingFrameLayout.m878$r8$lambda$poBU2FDiEZWE5dImWPjK06PXco(RegionInterceptingFrameLayout.this, internalInsetsInfo);
            }
        };
    }
}
