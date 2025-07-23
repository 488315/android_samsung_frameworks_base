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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void hide() {
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
        int i = this.mTextViewPadding;
        textView.setPadding(i, i, i, i);
        GradientDrawable gradientDrawable = (GradientDrawable) this.mTextView.getBackground();
        gradientDrawable.setCornerRadius(this.mTextViewCornerRadius);
        gradientDrawable.setColor(getResources().getColor(com.android.systemui.R.color.accessibility_floating_menu_background));
        Rect windowLocationOnScreen = this.mAnchorView.getWindowLocationOnScreen();
        boolean isAnchorViewOnLeft = isAnchorViewOnLeft(windowLocationOnScreen);
        View findViewById = findViewById(isAnchorViewOnLeft ? com.android.systemui.R.id.arrow_left : com.android.systemui.R.id.arrow_right);
        findViewById.setVisibility(0);
        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.createHorizontal(layoutParams.width, layoutParams.height, isAnchorViewOnLeft));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(getResources().getColor(com.android.systemui.R.color.accessibility_floating_menu_background));
        float f = layoutParams.width;
        float f2 = layoutParams.height;
        int i2 = TriangleStrokeShape.$r8$clinit;
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
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
        layoutParams2.width = this.mArrowWidth;
        layoutParams2.height = this.mArrowHeight;
        layoutParams2.setMargins(isAnchorViewOnLeft ? 0 : this.mArrowMargin, 0, isAnchorViewOnLeft ? this.mArrowMargin : 0, 0);
        findViewById.setLayoutParams(layoutParams2);
        ViewGroup.LayoutParams layoutParams3 = this.mTextView.getLayoutParams();
        if (isAnchorViewOnLeft(windowLocationOnScreen)) {
            int i3 = this.mScreenWidth - windowLocationOnScreen.right;
            int windowWidthWith = getWindowWidthWith(windowLocationOnScreen);
            int i4 = this.mArrowWidth;
            if (i3 < windowWidthWith + i4) {
                layoutParams3.width = (this.mScreenWidth - windowLocationOnScreen.right) - i4;
            } else {
                layoutParams3.width = getTextWidthWith(windowLocationOnScreen);
            }
        } else {
            int i5 = windowLocationOnScreen.left;
            int windowWidthWith2 = getWindowWidthWith(windowLocationOnScreen);
            int i6 = this.mArrowWidth;
            if (i5 < windowWidthWith2 + i6) {
                layoutParams3.width = windowLocationOnScreen.left - i6;
            } else {
                layoutParams3.width = getTextWidthWith(windowLocationOnScreen);
            }
        }
        this.mTextView.setLayoutParams(layoutParams3);
        if (this.mAnchorView.mIsHideHandle) {
            this.mCurrentLayoutParams.x = isAnchorViewOnLeft(windowLocationOnScreen) ? this.mAnchorView.mHideHandleWidth : (this.mScreenWidth - getWindowWidthWith(windowLocationOnScreen)) - this.mAnchorView.mHideHandleWidth;
            this.mCurrentLayoutParams.y = windowLocationOnScreen.top;
        } else {
            this.mCurrentLayoutParams.x = isAnchorViewOnLeft(windowLocationOnScreen) ? windowLocationOnScreen.right : windowLocationOnScreen.left - getWindowWidthWith(windowLocationOnScreen);
            WindowManager.LayoutParams layoutParams4 = this.mCurrentLayoutParams;
            int centerY = windowLocationOnScreen.centerY();
            this.mTextView.measure(View.MeasureSpec.makeMeasureSpec((((this.mScreenWidth - windowLocationOnScreen.width()) - this.mArrowWidth) - this.mArrowMargin) - this.mTextViewMargin, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
            layoutParams4.y = centerY - (this.mTextView.getMeasuredHeight() / 2);
        }
        if (this.mAnchorView.offsetForLeftNaviBar()) {
            WindowManager.LayoutParams layoutParams5 = this.mCurrentLayoutParams;
            layoutParams5.x = this.mAnchorView.getNavigationBarHeight() + layoutParams5.x;
        }
    }
}
