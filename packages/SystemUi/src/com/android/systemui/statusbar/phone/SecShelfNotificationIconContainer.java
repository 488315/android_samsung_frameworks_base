package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.MotionPaths$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecShelfNotificationIconContainer extends NotificationIconContainer {
    public int mCount;
    public TextView mMoreView;
    public Drawable mMoreViewBackground;
    public int mMoreViewRange;
    public int mPaddingBetweenIcons;
    public int mPaddingForMoreView;

    public SecShelfNotificationIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0080, code lost:
    
        if (((r6 - (r8 > -1 ? r8 : 0)) + 1) > 4) goto L40;
     */
    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void calculateIconXTranslations() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int childCount = getChildCount();
        float f = 0.0f;
        int i6 = -1;
        int i7 = -1;
        float f2 = 0.0f;
        int i8 = 0;
        while (true) {
            boolean z = true;
            if (i8 >= childCount) {
                break;
            }
            View childAt = getChildAt(i8);
            if (!(childAt instanceof TextView)) {
                if (childAt instanceof StatusBarIconView) {
                    ((StatusBarIconView) childAt).mBlockDotAnim = true;
                }
                NotificationIconContainer.IconState iconState = (NotificationIconContainer.IconState) this.mIconStates.get(childAt);
                if (iconState.clampedAppearAmount == 1.0f) {
                    if (i7 == -1) {
                        int i9 = childCount - i8;
                        int width = getWidth();
                        int i10 = this.mIconSize;
                        if (i9 != 1) {
                            if (i9 == 2) {
                                i4 = (width / 2) - i10;
                                i5 = this.mPaddingBetweenIcons / 2;
                            } else if (i9 != 3) {
                                int i11 = this.mPaddingBetweenIcons;
                                i4 = (((width / 2) - i10) - i11) - i10;
                                i5 = i11 / 2;
                            } else {
                                i = ((width / 2) - i10) - this.mPaddingBetweenIcons;
                                i2 = i10 / 2;
                            }
                            i3 = i4 - i5;
                            f2 = i3;
                            i7 = i8;
                        } else {
                            i = width / 2;
                            i2 = i10 / 2;
                        }
                        i3 = i - i2;
                        f2 = i3;
                        i7 = i8;
                    }
                    iconState.setXTranslation(f2);
                }
                iconState.visibleState = iconState.hidden ? 2 : 0;
                float f3 = iconState.clampedAppearAmount;
                if (f3 == 1.0f) {
                }
                z = false;
                if (i6 == -1 && z) {
                    i6 = i8 - 1;
                    f = (f2 - this.mIconSize) - this.mPaddingForMoreView;
                }
                f2 = MotionPaths$$ExternalSyntheticOutline0.m24m(iconState.clampedAppearAmount, this.mPaddingBetweenIcons, f3 * childAt.getWidth(), f2);
            }
            i8++;
        }
        this.mIsShowingOverflowDot = false;
        if (i6 != -1) {
            for (int i12 = i6; i12 < childCount; i12++) {
                View childAt2 = getChildAt(i12);
                NotificationIconContainer.IconState iconState2 = (NotificationIconContainer.IconState) this.mIconStates.get(childAt2);
                iconState2.setXTranslation(f);
                if (this.mIsShowingOverflowDot) {
                    iconState2.visibleState = 2;
                    childAt2.setImportantForAccessibility(2);
                } else {
                    if (iconState2.iconAppearAmount < 0.8f) {
                        iconState2.visibleState = 0;
                    } else {
                        iconState2.visibleState = 2;
                        updateMoreView((childCount - i6) - 1);
                        this.mMoreView.setTranslationX(f);
                        this.mIsShowingOverflowDot = true;
                    }
                    childAt2.setImportantForAccessibility(1);
                }
            }
        } else {
            updateMoreView(0);
        }
        if (isLayoutRtl()) {
            for (int i13 = 0; i13 < childCount; i13++) {
                View childAt3 = getChildAt(i13);
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
    public final void initResources() {
        super.initResources();
        updateResource();
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    public final void onClockColorChanged(int i) {
        onClockColorChanged(i, false);
    }

    public final void onDensityOrFontScaleChanged() {
        updateResource();
        this.mMoreViewRange = -1;
        updateMoreView(this.mCount);
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        float height = getHeight() / 2.0f;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            View childAt = getChildAt(i6);
            int i7 = this.mIconSize;
            if (childAt instanceof TextView) {
                i5 = childAt.getMeasuredWidth();
                i7 = childAt.getMeasuredHeight();
            } else {
                i5 = i7;
            }
            int i8 = (int) (height - (i7 / 2.0f));
            childAt.layout(0, i8, i5, i7 + i8);
        }
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer, android.view.View
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSize, VideoPlayer.MEDIA_ERROR_SYSTEM);
        for (int i3 = 0; i3 < childCount; i3++) {
            measureChild(getChildAt(i3), makeMeasureSpec, i2);
        }
        setMeasuredDimension(ViewGroup.resolveSize(getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_width), i), View.MeasureSpec.getSize(i2));
    }

    public final void updateMoreView(int i) {
        if (i > 0) {
            int i2 = i >= 100 ? 3 : i >= 10 ? 2 : 1;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(i2 == 3 ? getResources().getDimensionPixelSize(R.dimen.more_view_width_in_shelf_3rd) : i2 == 2 ? getResources().getDimensionPixelSize(R.dimen.more_view_width_in_shelf_2nd) : getResources().getDimensionPixelSize(R.dimen.more_view_width_in_shelf_1st), getResources().getDimensionPixelSize(R.dimen.more_view_height_in_shelf));
            if (this.mMoreView.getParent() == null) {
                addView(this.mMoreView, layoutParams);
            } else if (this.mMoreViewRange != i2) {
                this.mMoreView.setLayoutParams(layoutParams);
                this.mMoreViewRange = i2;
            }
            this.mMoreView.setText("+" + i);
            this.mMoreView.setVisibility(0);
        } else {
            this.mMoreView.setVisibility(8);
            removeView(this.mMoreView);
        }
        this.mCount = i;
    }

    public final void updateResource() {
        this.mPaddingBetweenIcons = getResources().getDimensionPixelSize(R.dimen.padding_between_icons_in_shelf);
        this.mPaddingForMoreView = getResources().getDimensionPixelSize(R.dimen.padding_for_more_view_in_shelf);
        setIconSize(getResources().getDimensionPixelSize(R.dimen.icon_size_in_shelf));
        if (this.mMoreView == null) {
            this.mMoreView = new TextView(getContext());
        }
        this.mMoreView.setTextAppearance(2132018282);
        this.mMoreView.setIncludeFontPadding(false);
        this.mMoreView.setGravity(17);
        this.mMoreViewBackground = getResources().getDrawable(R.drawable.shelf_more_view_background);
        onClockColorChanged(this.mShelfIconColor, true);
    }

    public final void onClockColorChanged(int i, boolean z) {
        if (z || !(this.mMoreView == null || i == 0 || this.mShelfIconColor == i)) {
            Log.d("SecShelfNotificationIconContainer", " onClockColorChanged - current : " + Integer.toHexString(this.mShelfIconColor) + " new : " + Integer.toHexString(i) + " F : " + z);
            this.mMoreViewBackground.setColorFilter(i, PorterDuff.Mode.SRC);
            this.mMoreView.setBackground(this.mMoreViewBackground);
            this.mMoreView.setTextColor(((Color.red(i) + Color.green(i)) + Color.blue(i)) / 3 >= 128 ? EmergencyPhoneWidget.BG_COLOR : -1);
            this.mShelfIconColor = i;
        }
    }
}
