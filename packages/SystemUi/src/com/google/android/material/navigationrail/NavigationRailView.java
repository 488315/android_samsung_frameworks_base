package com.google.android.material.navigationrail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NavigationRailView extends NavigationBarView {
    public View headerView;
    public final Boolean paddingBottomSystemWindowInsets;
    public final Boolean paddingTopSystemWindowInsets;
    public final int topMargin;

    public NavigationRailView(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    public final NavigationBarMenuView createNavigationBarMenuView(Context context) {
        return new NavigationRailMenuView(context);
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    public final int getMaxItemCount() {
        return 7;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        NavigationRailMenuView navigationRailMenuView = (NavigationRailMenuView) this.menuView;
        View view = this.headerView;
        int i5 = 0;
        if ((view == null || view.getVisibility() == 8) ? false : true) {
            int bottom = this.headerView.getBottom() + this.topMargin;
            int top = navigationRailMenuView.getTop();
            if (top < bottom) {
                i5 = bottom - top;
            }
        } else {
            if ((navigationRailMenuView.layoutParams.gravity & 112) == 48) {
                i5 = this.topMargin;
            }
        }
        if (i5 > 0) {
            navigationRailMenuView.layout(navigationRailMenuView.getLeft(), navigationRailMenuView.getTop() + i5, navigationRailMenuView.getRight(), navigationRailMenuView.getBottom() + i5);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        if (View.MeasureSpec.getMode(i) != 1073741824 && suggestedMinimumWidth > 0) {
            i = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i), getPaddingRight() + getPaddingLeft() + suggestedMinimumWidth), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        super.onMeasure(i, i2);
        View view = this.headerView;
        if ((view == null || view.getVisibility() == 8) ? false : true) {
            measureChild((NavigationRailMenuView) this.menuView, i, View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - this.headerView.getMeasuredHeight()) - this.topMargin, VideoPlayer.MEDIA_ERROR_SYSTEM));
        }
    }

    public NavigationRailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.navigationRailStyle);
    }

    public NavigationRailView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 2132019141);
    }

    public NavigationRailView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.paddingTopSystemWindowInsets = null;
        this.paddingBottomSystemWindowInsets = null;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_rail_margin);
        this.topMargin = dimensionPixelSize;
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(getContext(), attributeSet, R$styleable.NavigationRailView, i, i2, new int[0]);
        int resourceId = obtainTintedStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            View inflate = LayoutInflater.from(getContext()).inflate(resourceId, (ViewGroup) this, false);
            View view = this.headerView;
            if (view != null) {
                removeView(view);
                this.headerView = null;
            }
            this.headerView = inflate;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 49;
            layoutParams.topMargin = dimensionPixelSize;
            addView(inflate, 0, layoutParams);
        }
        int i3 = obtainTintedStyledAttributes.getInt(2, 49);
        NavigationRailMenuView navigationRailMenuView = (NavigationRailMenuView) this.menuView;
        FrameLayout.LayoutParams layoutParams2 = navigationRailMenuView.layoutParams;
        if (layoutParams2 != null) {
            if (layoutParams2.gravity != i3) {
                layoutParams2.gravity = i3;
            }
            navigationRailMenuView.setLayoutParams(layoutParams2);
        }
        if (obtainTintedStyledAttributes.hasValue(1)) {
            int dimensionPixelSize2 = obtainTintedStyledAttributes.getDimensionPixelSize(1, -1);
            NavigationRailMenuView navigationRailMenuView2 = (NavigationRailMenuView) this.menuView;
            if (navigationRailMenuView2.itemMinimumHeight != dimensionPixelSize2) {
                navigationRailMenuView2.itemMinimumHeight = dimensionPixelSize2;
                navigationRailMenuView2.requestLayout();
            }
        }
        if (obtainTintedStyledAttributes.hasValue(4)) {
            this.paddingTopSystemWindowInsets = Boolean.valueOf(obtainTintedStyledAttributes.getBoolean(4, false));
        }
        if (obtainTintedStyledAttributes.hasValue(3)) {
            this.paddingBottomSystemWindowInsets = Boolean.valueOf(obtainTintedStyledAttributes.getBoolean(3, false));
        }
        obtainTintedStyledAttributes.recycle();
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.navigationrail.NavigationRailView.1
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                boolean fitsSystemWindows;
                boolean fitsSystemWindows2;
                NavigationRailView navigationRailView = NavigationRailView.this;
                Boolean bool = navigationRailView.paddingTopSystemWindowInsets;
                if (bool != null) {
                    fitsSystemWindows = bool.booleanValue();
                } else {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    fitsSystemWindows = ViewCompat.Api16Impl.getFitsSystemWindows(navigationRailView);
                }
                if (fitsSystemWindows) {
                    relativePadding.top += windowInsetsCompat.getInsets(7).top;
                }
                Boolean bool2 = navigationRailView.paddingBottomSystemWindowInsets;
                if (bool2 != null) {
                    fitsSystemWindows2 = bool2.booleanValue();
                } else {
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    fitsSystemWindows2 = ViewCompat.Api16Impl.getFitsSystemWindows(navigationRailView);
                }
                if (fitsSystemWindows2) {
                    relativePadding.bottom += windowInsetsCompat.getInsets(7).bottom;
                }
                WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                boolean z = ViewCompat.Api17Impl.getLayoutDirection(view2) == 1;
                int systemWindowInsetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                int systemWindowInsetRight = windowInsetsCompat.getSystemWindowInsetRight();
                int i4 = relativePadding.start;
                if (z) {
                    systemWindowInsetLeft = systemWindowInsetRight;
                }
                int i5 = i4 + systemWindowInsetLeft;
                relativePadding.start = i5;
                ViewCompat.Api17Impl.setPaddingRelative(view2, i5, relativePadding.top, relativePadding.end, relativePadding.bottom);
                return windowInsetsCompat;
            }
        });
    }
}
