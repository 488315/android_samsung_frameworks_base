package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    @Override // com.google.android.material.tabs.TabIndicatorInterpolator
    public final void updateIndicatorForOffset(TabLayout tabLayout, View view, View view2, float f, Drawable drawable) {
        float sin;
        float cos;
        RectF calculateIndicatorWidthForTab = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view);
        RectF calculateIndicatorWidthForTab2 = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view2);
        if (calculateIndicatorWidthForTab.left < calculateIndicatorWidthForTab2.left) {
            double d = (f * 3.141592653589793d) / 2.0d;
            sin = (float) (1.0d - Math.cos(d));
            cos = (float) Math.sin(d);
        } else {
            double d2 = (f * 3.141592653589793d) / 2.0d;
            sin = (float) Math.sin(d2);
            cos = (float) (1.0d - Math.cos(d2));
        }
        drawable.setBounds(AnimationUtils.lerp(sin, (int) calculateIndicatorWidthForTab.left, (int) calculateIndicatorWidthForTab2.left), drawable.getBounds().top, AnimationUtils.lerp(cos, (int) calculateIndicatorWidthForTab.right, (int) calculateIndicatorWidthForTab2.right), drawable.getBounds().bottom);
    }
}
