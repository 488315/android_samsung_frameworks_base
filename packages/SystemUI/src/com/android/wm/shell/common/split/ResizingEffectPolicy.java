package com.android.wm.shell.common.split;

import android.graphics.Point;
import android.graphics.Rect;

/* loaded from: classes3.dex */
public class ResizingEffectPolicy {
    public final ParallaxSpec mParallaxSpec;
    public final int mParallaxType;
    public final SplitLayout mSplitLayout;
    public final Rect mTempRect;
    public final Rect mTempRect2;
    public final int mShrinkSide = -1;
    public int mDimmingSide = -1;
    public final Point mRetreatingSideParallax = new Point();
    public final Point mAdvancingSideParallax = new Point();
    public float mDimValue = 0.0f;

    public ResizingEffectPolicy(int i, SplitLayout splitLayout) {
        new Rect();
        new Rect();
        new Rect();
        new Rect();
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mParallaxType = i;
        this.mSplitLayout = splitLayout;
        if (i == 1) {
            this.mParallaxSpec = new DismissingParallaxSpec();
            return;
        }
        if (i == 2) {
            this.mParallaxSpec = new CenterParallaxSpec();
        } else if (i != 3) {
            this.mParallaxSpec = new NoParallaxSpec();
        } else {
            this.mParallaxSpec = new FlexParallaxSpec();
        }
    }
}
