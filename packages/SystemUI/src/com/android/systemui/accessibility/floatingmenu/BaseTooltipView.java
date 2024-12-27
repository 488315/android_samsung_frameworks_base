package com.android.systemui.accessibility.floatingmenu;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.systemui.recents.TriangleShape;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class BaseTooltipView extends FrameLayout {
    public final AccessibilityFloatingMenuView mAnchorView;
    public int mArrowCornerRadius;
    public int mArrowHeight;
    public int mArrowMargin;
    public int mArrowWidth;
    public final WindowManager.LayoutParams mCurrentLayoutParams;
    public int mFontSize;
    public boolean mIsShowing;
    public int mScreenWidth;
    public final TextView mTextView;
    public int mTextViewCornerRadius;
    public int mTextViewMargin;
    public int mTextViewPadding;
    public final WindowManager mWindowManager;

    public BaseTooltipView(Context context, AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        super(context);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mAnchorView = accessibilityFloatingMenuView;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 262152, -3);
        layoutParams.windowAnimations = R.style.Animation.Translucent;
        layoutParams.gravity = 8388659;
        layoutParams.setTitle("BaseTooltipView");
        this.mCurrentLayoutParams = layoutParams;
        View inflate = LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.sec_accessibility_floating_menu_tooltip, (ViewGroup) this, false);
        this.mTextView = (TextView) inflate.findViewById(com.android.systemui.R.id.text);
        addView(inflate);
    }

    public final int getTextWidthWith(Rect rect) {
        this.mTextView.measure(View.MeasureSpec.makeMeasureSpec((((this.mScreenWidth - rect.width()) - this.mArrowWidth) - this.mArrowMargin) - this.mTextViewMargin, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
        return this.mTextView.getMeasuredWidth();
    }

    public final int getWindowWidthWith(Rect rect) {
        return getResources().getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_space) + getTextWidthWith(rect) + this.mArrowWidth + this.mArrowMargin;
    }

    public void hide() {
        if (this.mIsShowing) {
            this.mIsShowing = false;
            this.mWindowManager.removeView(this);
        }
    }

    public final boolean isAnchorViewOnLeft(Rect rect) {
        return rect.centerX() < this.mScreenWidth / 2;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mAnchorView.onConfigurationChanged(configuration);
        updateTooltipView();
        this.mWindowManager.updateViewLayout(this, this.mCurrentLayoutParams);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 4) {
            hide();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i != AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS.getId()) {
            return super.performAccessibilityAction(i, bundle);
        }
        hide();
        return true;
    }

    public final void updateTooltipView() {
        int i;
        int i2;
        Resources resources = getResources();
        this.mScreenWidth = resources.getDisplayMetrics().widthPixels;
        this.mArrowWidth = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_arrow_width);
        this.mArrowHeight = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_arrow_height);
        this.mArrowMargin = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_arrow_margin);
        this.mArrowCornerRadius = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_arrow_corner_radius);
        this.mFontSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_font_size);
        this.mTextViewMargin = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_margin);
        this.mTextViewPadding = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_padding);
        this.mTextViewCornerRadius = resources.getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_tooltip_text_corner_radius);
        this.mTextView.setTextSize(0, this.mFontSize);
        TextView textView = this.mTextView;
        int i3 = this.mTextViewPadding;
        textView.setPadding(i3, i3, i3, i3);
        GradientDrawable gradientDrawable = (GradientDrawable) this.mTextView.getBackground();
        gradientDrawable.setCornerRadius(this.mTextViewCornerRadius);
        gradientDrawable.setColor(getResources().getColor(com.android.systemui.R.color.accessibility_floating_menu_background));
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.mAnchorView;
        if (accessibilityFloatingMenuView.mIsHideHandle) {
            WindowManager.LayoutParams layoutParams = accessibilityFloatingMenuView.mHideHandleLayoutParams;
            i = layoutParams.x;
            i2 = layoutParams.y;
        } else {
            WindowManager.LayoutParams layoutParams2 = accessibilityFloatingMenuView.mCurrentLayoutParams;
            i = layoutParams2.x;
            i2 = layoutParams2.y;
        }
        Rect rect = new Rect(i, i2, accessibilityFloatingMenuView.getWindowWidth() + i, accessibilityFloatingMenuView.getWindowHeight() + i2);
        boolean isAnchorViewOnLeft = isAnchorViewOnLeft(rect);
        View findViewById = findViewById(isAnchorViewOnLeft ? com.android.systemui.R.id.arrow_left : com.android.systemui.R.id.arrow_right);
        findViewById.setVisibility(0);
        ViewGroup.LayoutParams layoutParams3 = findViewById.getLayoutParams();
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.createHorizontal(layoutParams3.width, layoutParams3.height, isAnchorViewOnLeft));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(getResources().getColor(com.android.systemui.R.color.accessibility_floating_menu_background));
        float f = layoutParams3.width;
        float f2 = layoutParams3.height;
        int i4 = TriangleStrokeShape.$r8$clinit;
        Path path = new Path();
        if (isAnchorViewOnLeft) {
            path.moveTo(f, f2);
            path.lineTo(0.0f, f2 / 2.0f);
            path.lineTo(f, 0.0f);
        } else {
            path.moveTo(0.0f, f2);
            path.lineTo(f, f2 / 2.0f);
            path.lineTo(0.0f, 0.0f);
        }
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(new TriangleStrokeShape(path, f, f2));
        Paint paint2 = shapeDrawable2.getPaint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(getResources().getColor(com.android.systemui.R.color.accessibility_floating_menu_stroke));
        paint2.setStrokeWidth(getResources().getDimensionPixelSize(com.android.systemui.R.dimen.accessibility_floating_menu_stroke_width));
        paint.setPathEffect(new CornerPathEffect(this.mArrowCornerRadius));
        findViewById.setBackground(new InstantInsetLayerDrawable(new Drawable[]{shapeDrawable, shapeDrawable2}));
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
        layoutParams4.width = this.mArrowWidth;
        layoutParams4.height = this.mArrowHeight;
        layoutParams4.setMargins(isAnchorViewOnLeft ? 0 : this.mArrowMargin, 0, isAnchorViewOnLeft ? this.mArrowMargin : 0, 0);
        findViewById.setLayoutParams(layoutParams4);
        ViewGroup.LayoutParams layoutParams5 = this.mTextView.getLayoutParams();
        if (isAnchorViewOnLeft(rect)) {
            int i5 = this.mScreenWidth - rect.right;
            int windowWidthWith = getWindowWidthWith(rect);
            int i6 = this.mArrowWidth;
            if (i5 < windowWidthWith + i6) {
                layoutParams5.width = (this.mScreenWidth - rect.right) - i6;
            } else {
                layoutParams5.width = getTextWidthWith(rect);
            }
        } else {
            int i7 = rect.left;
            int windowWidthWith2 = getWindowWidthWith(rect);
            int i8 = this.mArrowWidth;
            if (i7 < windowWidthWith2 + i8) {
                layoutParams5.width = rect.left - i8;
            } else {
                layoutParams5.width = getTextWidthWith(rect);
            }
        }
        this.mTextView.setLayoutParams(layoutParams5);
        if (this.mAnchorView.mIsHideHandle) {
            this.mCurrentLayoutParams.x = isAnchorViewOnLeft(rect) ? this.mAnchorView.mHideHandleWidth : (this.mScreenWidth - getWindowWidthWith(rect)) - this.mAnchorView.mHideHandleWidth;
            this.mCurrentLayoutParams.y = rect.top;
        } else {
            this.mCurrentLayoutParams.x = isAnchorViewOnLeft(rect) ? rect.right : rect.left - getWindowWidthWith(rect);
            WindowManager.LayoutParams layoutParams6 = this.mCurrentLayoutParams;
            int centerY = rect.centerY();
            this.mTextView.measure(View.MeasureSpec.makeMeasureSpec((((this.mScreenWidth - rect.width()) - this.mArrowWidth) - this.mArrowMargin) - this.mTextViewMargin, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
            layoutParams6.y = centerY - (this.mTextView.getMeasuredHeight() / 2);
        }
        if (this.mAnchorView.offsetForLeftNaviBar()) {
            WindowManager.LayoutParams layoutParams7 = this.mCurrentLayoutParams;
            layoutParams7.x = this.mAnchorView.getNavigationBarHeight() + layoutParams7.x;
        }
    }
}
