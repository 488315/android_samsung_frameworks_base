package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SecShelfNotificationIconContainer extends NotificationIconContainer {
    public final ArrayList mBgViews;
    public int mDotWidth;
    public int mPaddingBetweenIcons;
    public int mPaddingForDot;

    public SecShelfNotificationIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBgViews = new ArrayList();
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00aa  */
    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void calculateIconXTranslations() {
        float f;
        int i;
        int i2;
        int i3;
        int i4;
        int childCount = getChildCount();
        this.mBgViews.clear();
        float f2 = 0.0f;
        int i5 = -1;
        int i6 = -1;
        float fM = 0.0f;
        int i7 = 0;
        while (true) {
            boolean z = true;
            if (i7 >= childCount) {
                break;
            }
            View childAt = getChildAt(i7);
            if (!(childAt instanceof TextView)) {
                if (childAt instanceof StatusBarIconView) {
                    ((StatusBarIconView) childAt).mBlockDotAnim = true;
                }
                NotificationIconContainer.IconState iconState = (NotificationIconContainer.IconState) this.mIconStates.get(childAt);
                if (iconState.clampedAppearAmount == 1.0f && iconState.iconAppearAmount == 1.0f) {
                    if (i6 == -1) {
                        int i8 = childCount - i7;
                        int width = getWidth();
                        int i9 = this.mIconSize;
                        if (i8 == 1) {
                            f = 1.0f;
                        } else if (i8 != 2) {
                            f = 1.0f;
                            if (i8 != 3) {
                                if (i8 != 4) {
                                    i2 = (i9 * 3) + this.mDotWidth;
                                    i4 = this.mPaddingBetweenIcons;
                                } else {
                                    i2 = i9 * 4;
                                    i4 = this.mPaddingBetweenIcons;
                                }
                                i3 = i4 * 3;
                            } else {
                                i2 = i9 * 3;
                                i3 = this.mPaddingBetweenIcons * 2;
                            }
                            i = width - (i3 + i2);
                            fM = i / 2.0f;
                            i6 = i7;
                        } else {
                            f = 1.0f;
                            i9 = (i9 * 2) + this.mPaddingBetweenIcons;
                        }
                        i = width - i9;
                        fM = i / 2.0f;
                        i6 = i7;
                    } else {
                        f = 1.0f;
                    }
                    this.mBgViews.add(childAt);
                    iconState.setXTranslation(fM);
                } else {
                    f = 1.0f;
                }
                iconState.visibleState = iconState.hidden ? 2 : 0;
                float f3 = iconState.clampedAppearAmount;
                if (f3 == f && iconState.iconAppearAmount == f) {
                    if ((i7 - (i6 > -1 ? i6 : 0)) + 1 <= 4) {
                    }
                    if (i5 == -1) {
                        i5 = i7 - 1;
                        f2 = (fM - this.mIconSize) - this.mPaddingForDot;
                    }
                    fM = DrawerArrowDrawable$$ExternalSyntheticOutline0.m(iconState.clampedAppearAmount, this.mPaddingBetweenIcons, f3 * childAt.getWidth(), fM);
                } else {
                    z = false;
                    if (i5 == -1 && z) {
                        i5 = i7 - 1;
                        f2 = (fM - this.mIconSize) - this.mPaddingForDot;
                    }
                    fM = DrawerArrowDrawable$$ExternalSyntheticOutline0.m(iconState.clampedAppearAmount, this.mPaddingBetweenIcons, f3 * childAt.getWidth(), fM);
                }
            }
            i7++;
        }
        this.mIsShowingOverflowDot = false;
        if (i5 != -1) {
            while (i5 < childCount) {
                View childAt2 = getChildAt(i5);
                NotificationIconContainer.IconState iconState2 = (NotificationIconContainer.IconState) this.mIconStates.get(childAt2);
                iconState2.setXTranslation(f2);
                if (this.mIsShowingOverflowDot) {
                    if (!(childAt2 instanceof TextView)) {
                        this.mBgViews.remove(childAt2);
                    }
                    iconState2.visibleState = 2;
                    childAt2.setImportantForAccessibility(2);
                } else {
                    if (iconState2.iconAppearAmount < 0.8f) {
                        iconState2.visibleState = 0;
                    } else {
                        iconState2.visibleState = 1;
                        this.mIsShowingOverflowDot = true;
                    }
                    childAt2.setImportantForAccessibility(1);
                }
                i5++;
            }
        }
        if (isLayoutRtl()) {
            for (int i10 = 0; i10 < childCount; i10++) {
                View childAt3 = getChildAt(i10);
                if (childAt3 instanceof TextView) {
                    childAt3.setTranslationX((getWidth() - childAt3.getTranslationX()) - childAt3.getWidth());
                } else {
                    NotificationIconContainer.IconState iconState3 = (NotificationIconContainer.IconState) this.mIconStates.get(childAt3);
                    if (iconState3 != null) {
                        iconState3.setXTranslation((getWidth() - iconState3.mXTranslation) - childAt3.getWidth());
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    public final void initResources() throws Resources.NotFoundException {
        super.initResources();
        this.mPaddingBetweenIcons = getResources().getDimensionPixelSize(R.dimen.padding_between_icons_in_shelf);
        this.mPaddingForDot = getResources().getDimensionPixelSize(R.dimen.padding_for_dot_in_shelf);
        this.mDotWidth = getResources().getDimensionPixelSize(R.dimen.overflow_dot_width);
        setIconSize(getResources().getDimensionPixelSize(R.dimen.icon_size_in_shelf));
        int i = this.mShelfIconColor;
        Log.d("SecShelfNotificationIconContainer", " onClockColorChanged - current : " + Integer.toHexString(this.mShelfIconColor) + " new : " + Integer.toHexString(i) + " F : true");
        this.mShelfIconColor = i;
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth;
        float height = getHeight() / 2.0f;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            int measuredHeight = this.mIconSize;
            if (childAt instanceof TextView) {
                measuredWidth = childAt.getMeasuredWidth();
                measuredHeight = childAt.getMeasuredHeight();
            } else {
                measuredWidth = measuredHeight;
            }
            int i6 = (int) (height - (measuredHeight / 2.0f));
            childAt.layout(0, i6, measuredWidth, measuredHeight + i6);
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer, android.view.View
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSize, Integer.MIN_VALUE);
        for (int i3 = 0; i3 < childCount; i3++) {
            measureChild(getChildAt(i3), iMakeMeasureSpec, i2);
        }
        setMeasuredDimension(ViewGroup.resolveSize(getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_width), i), View.MeasureSpec.getSize(i2));
    }
}
