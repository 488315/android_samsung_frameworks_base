package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.SeslTouchTargetDelegate;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BottomNavigationView extends NavigationBarView {
    public ViewTreeObserverOnGlobalLayoutListenerC42302 mOnGlobalLayoutListenerForTD;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    public final NavigationBarMenuView createNavigationBarMenuView(Context context) {
        return new BottomNavigationMenuView(context);
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    public final int getMaxItemCount() {
        return 5;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, com.google.android.material.bottomnavigation.BottomNavigationView$2] */
    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            if (this.mOnGlobalLayoutListenerForTD != null) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListenerForTD);
                this.mOnGlobalLayoutListenerForTD = null;
                return;
            }
            return;
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver == 0 || this.mOnGlobalLayoutListenerForTD != null) {
            return;
        }
        ?? r0 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.bottomnavigation.BottomNavigationView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                final BottomNavigationView bottomNavigationView = BottomNavigationView.this;
                if (bottomNavigationView != null) {
                    bottomNavigationView.post(new Runnable(this) { // from class: com.google.android.material.bottomnavigation.BottomNavigationView.2.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            View view;
                            SeslTouchTargetDelegate seslTouchTargetDelegate = new SeslTouchTargetDelegate(bottomNavigationView);
                            int childCount = bottomNavigationView.getChildCount();
                            boolean z = false;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= childCount) {
                                    view = null;
                                    break;
                                }
                                view = bottomNavigationView.getChildAt(i2);
                                if (view instanceof BottomNavigationMenuView) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                            if (view != null && view.getVisibility() == 0) {
                                ViewGroup viewGroup = (ViewGroup) view;
                                int childCount2 = viewGroup.getChildCount();
                                int i3 = 0;
                                boolean z2 = false;
                                while (i3 < childCount2) {
                                    View childAt = viewGroup.getChildAt(i3);
                                    if (childAt.getVisibility() == 0) {
                                        int measuredHeight = childAt.getMeasuredHeight() / 2;
                                        seslTouchTargetDelegate.addTouchDelegate(childAt, SeslTouchTargetDelegate.ExtraInsets.m33of(i3 == 0 ? measuredHeight : 0, measuredHeight, i3 == childCount2 + (-1) ? measuredHeight : 0, measuredHeight));
                                        z2 = true;
                                    }
                                    i3++;
                                }
                                z = z2;
                            }
                            if (z) {
                                bottomNavigationView.setTouchDelegate(seslTouchTargetDelegate);
                            }
                        }
                    });
                }
            }
        };
        this.mOnGlobalLayoutListenerForTD = r0;
        viewTreeObserver.addOnGlobalLayoutListener(r0);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomNavigationStyle);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 2132018813);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int dimensionPixelSize;
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(getContext(), attributeSet, R$styleable.BottomNavigationView, i, i2, new int[0]);
        boolean z = obtainTintedStyledAttributes.getBoolean(4, true);
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) this.menuView;
        if (bottomNavigationMenuView.itemHorizontalTranslationEnabled != z) {
            bottomNavigationMenuView.itemHorizontalTranslationEnabled = z;
            this.presenter.updateMenuView(false);
        }
        obtainTintedStyledAttributes.recycle();
        NavigationBarMenuView navigationBarMenuView = this.menuView;
        if (navigationBarMenuView instanceof NavigationBarMenuView) {
            if (navigationBarMenuView.mViewType != 3) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_height);
            } else {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_text_mode_height);
                int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_text_mode_padding_horizontal);
                setPadding(dimensionPixelSize2, getPaddingTop(), dimensionPixelSize2, getPaddingBottom());
            }
            navigationBarMenuView.setMinimumHeight(dimensionPixelSize);
            setMinimumHeight(dimensionPixelSize);
        }
    }
}
