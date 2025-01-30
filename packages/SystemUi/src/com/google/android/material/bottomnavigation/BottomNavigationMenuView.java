package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BottomNavigationMenuView extends NavigationBarMenuView {
    public int activeItemMaxWidth;
    public final int activeItemMinWidth;
    public final int inactiveItemMaxWidth;
    public final int inactiveItemMinWidth;
    public boolean itemHorizontalTranslationEnabled;
    public boolean mHasIcon;
    public float mWidthPercent;
    public int[] tempChildWidths;

    public BottomNavigationMenuView(Context context) {
        super(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        setLayoutParams(layoutParams);
        Resources resources = getResources();
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.sesl_bottom_navigation_width_proportion, typedValue, true);
        this.mWidthPercent = typedValue.getFloat();
        this.inactiveItemMaxWidth = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_item_min_width);
        this.activeItemMaxWidth = (int) (getResources().getDisplayMetrics().widthPixels * this.mWidthPercent);
        this.activeItemMinWidth = resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_active_item_min_width);
        resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_height);
        this.tempChildWidths = new int[5];
        this.mUseItemPool = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int dimensionPixelSize = this.mHasIcon ? this.mViewVisibleItemCount == 5 ? getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_min_padding_horizontal) : getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_padding_horizontal) : 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
                    int i9 = i5 - i7;
                    childAt.layout((i9 - childAt.getMeasuredWidth()) + dimensionPixelSize, 0, i9 - dimensionPixelSize, i6);
                } else {
                    childAt.layout(i7 + dimensionPixelSize, 0, (childAt.getMeasuredWidth() + i7) - dimensionPixelSize, i6);
                }
                i7 += childAt.getMeasuredWidth();
            }
        }
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    return;
                }
                updateBadge(navigationBarItemView);
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getSize(i) / getResources().getDisplayMetrics().density < 590.0f) {
            this.mWidthPercent = 1.0f;
        } else {
            this.mWidthPercent = 0.75f;
        }
        this.activeItemMaxWidth = (int) (getResources().getDisplayMetrics().widthPixels * this.mWidthPercent);
        int size = (int) (View.MeasureSpec.getSize(i) * this.mWidthPercent);
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            if (getChildAt(i4).getVisibility() == 0) {
                i3++;
            }
        }
        int childCount = getChildCount();
        this.tempChildWidths = new int[childCount];
        this.mHasIcon = this.mViewType != 3;
        getResources().getDimensionPixelSize(this.mHasIcon ? R.dimen.sesl_bottom_navigation_icon_mode_height : R.dimen.sesl_bottom_navigation_text_mode_height);
        int size2 = View.MeasureSpec.getSize(i2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        if ((this.labelVisibilityMode == 0) && this.itemHorizontalTranslationEnabled) {
            View childAt = getChildAt(this.selectedItemPosition);
            int i5 = this.activeItemMinWidth;
            if (childAt.getVisibility() != 8) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(this.activeItemMaxWidth, VideoPlayer.MEDIA_ERROR_SYSTEM), makeMeasureSpec);
                i5 = Math.max(i5, childAt.getMeasuredWidth());
            }
            int i6 = childCount - (childAt.getVisibility() != 8 ? 1 : 0);
            int min = Math.min(size - (this.inactiveItemMinWidth * i6), Math.min(i5, this.activeItemMaxWidth));
            int i7 = size - min;
            int min2 = Math.min(i7 / (i6 != 0 ? i6 : 1), this.inactiveItemMaxWidth);
            int i8 = i7 - (i6 * min2);
            int i9 = 0;
            while (i9 < childCount) {
                if (getChildAt(i9).getVisibility() != 8) {
                    int[] iArr = this.tempChildWidths;
                    int i10 = i9 == this.selectedItemPosition ? min : min2;
                    iArr[i9] = i10;
                    if (i8 > 0) {
                        iArr[i9] = i10 + 1;
                        i8--;
                    }
                } else {
                    this.tempChildWidths[i9] = 0;
                }
                i9++;
            }
        } else {
            int i11 = size / (i3 != 0 ? i3 : 1);
            if (i3 != 2) {
                i11 = Math.min(i11, this.activeItemMaxWidth);
            }
            int i12 = size - (i3 * i11);
            for (int i13 = 0; i13 < childCount; i13++) {
                if (getChildAt(i13).getVisibility() != 8) {
                    int[] iArr2 = this.tempChildWidths;
                    iArr2[i13] = i11;
                    if (i12 > 0) {
                        iArr2[i13] = i11 + 1;
                        i12--;
                    }
                } else {
                    this.tempChildWidths[i13] = 0;
                }
            }
        }
        int i14 = 0;
        for (int i15 = 0; i15 < childCount; i15++) {
            View childAt2 = getChildAt(i15);
            if (childAt2 != null && childAt2.getVisibility() != 8) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(this.tempChildWidths[i15], VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), makeMeasureSpec);
                childAt2.getLayoutParams().width = childAt2.getMeasuredWidth();
                i14 += childAt2.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(i14, View.MeasureSpec.makeMeasureSpec(i14, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), 0), View.resolveSizeAndState(size2, i2, 0));
    }
}
