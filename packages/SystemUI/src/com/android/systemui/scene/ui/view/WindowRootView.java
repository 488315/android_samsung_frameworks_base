package com.android.systemui.scene.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.android.systemui.LsRune;
import com.android.systemui.compose.ComposeInitializer;
import com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler;
import com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.res.R$styleable;
import com.android.systemui.statusbar.NotificationInsetsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class WindowRootView extends FrameLayout {
    public NotificationInsetsController layoutInsetsController;
    public int leftInset;
    public boolean mBouncerShowing;
    public WindowInsets previousInsets;
    public int rightInset;
    public WindowRootViewKeyEventHandler windowRootViewKeyEventHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LayoutParams extends FrameLayout.LayoutParams {
        public final boolean ignoreRightInset;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StatusBarWindowView_Layout);
            this.ignoreRightInset = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }
    }

    public WindowRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void applyBouncerMargins() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getLayoutParams() instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreRightInset && (((FrameLayout.LayoutParams) layoutParams).rightMargin != 0 || ((FrameLayout.LayoutParams) layoutParams).leftMargin != 0)) {
                    layoutParams.setMargins(0, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, 0, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                    childAt.requestLayout();
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        WindowRootViewKeyEventHandler windowRootViewKeyEventHandler = this.windowRootViewKeyEventHandler;
        if (windowRootViewKeyEventHandler == null) {
            windowRootViewKeyEventHandler = null;
        }
        windowRootViewKeyEventHandler.falsingCollector.onKeyEvent(keyEvent);
        WindowRootViewKeyEventHandler windowRootViewKeyEventHandler2 = this.windowRootViewKeyEventHandler;
        if (windowRootViewKeyEventHandler2 == null) {
            windowRootViewKeyEventHandler2 = null;
        }
        if (((SysUIKeyEventHandler) windowRootViewKeyEventHandler2.sysUIKeyEventHandlerLazy.get()).interceptMediaKey(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        WindowRootViewKeyEventHandler windowRootViewKeyEventHandler3 = this.windowRootViewKeyEventHandler;
        return ((SysUIKeyEventHandler) (windowRootViewKeyEventHandler3 != null ? windowRootViewKeyEventHandler3 : null).sysUIKeyEventHandlerLazy.get()).dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        InputDevice device;
        WindowRootViewKeyEventHandler windowRootViewKeyEventHandler = this.windowRootViewKeyEventHandler;
        if (windowRootViewKeyEventHandler == null) {
            windowRootViewKeyEventHandler = null;
        }
        KeyguardKeyEventInteractor keyguardKeyEventInteractor = ((SysUIKeyEventHandler) windowRootViewKeyEventHandler.sysUIKeyEventHandlerLazy.get()).keyguardKeyEventInteractor;
        keyguardKeyEventInteractor.getClass();
        if (keyEvent.getAction() == 0 && (device = keyEvent.getDevice()) != null && device.isFullKeyboard() && device.isExternal()) {
            PowerInteractor.onUserTouch$default(keyguardKeyEventInteractor.powerInteractor);
        }
        if (keyEvent.getKeyCode() != 4) {
            return false;
        }
        boolean z = LsRune.SECURITY_CAPTURED_BLUR;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardKeyEventInteractor.statusBarKeyguardViewManager;
        if (!(z && statusBarKeyguardViewManager.getLastPrimaryBouncerShowing()) && keyguardKeyEventInteractor.statusBarStateController.getState() == 1 && statusBarKeyguardViewManager.dispatchBackKeyEventPreIme()) {
            return keyguardKeyEventInteractor.backActionInteractor.onBackRequested();
        }
        return false;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (Intrinsics.areEqual(windowInsets, this.previousInsets)) {
            return windowInsets;
        }
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        if (getFitsSystemWindows()) {
            if (insetsIgnoringVisibility.top != getPaddingTop() || insetsIgnoringVisibility.bottom != getPaddingBottom()) {
                setPadding(0, 0, 0, 0);
            }
        } else if (getPaddingLeft() != 0 || getPaddingRight() != 0 || getPaddingTop() != 0 || getPaddingBottom() != 0) {
            setPadding(0, 0, 0, 0);
        }
        this.leftInset = 0;
        this.rightInset = 0;
        DisplayCutout displayCutout = getRootWindowInsets().getDisplayCutout();
        NotificationInsetsController notificationInsetsController = this.layoutInsetsController;
        if (notificationInsetsController == null) {
            notificationInsetsController = null;
        }
        Pair pair = notificationInsetsController.getinsets(windowInsets, displayCutout);
        this.leftInset = ((Number) pair.first).intValue();
        this.rightInset = ((Number) pair.second).intValue();
        if (LsRune.SECURITY_CAPTURED_BLUR && this.mBouncerShowing) {
            applyBouncerMargins();
        } else {
            int childCount = getChildCount();
            boolean z = false;
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.getLayoutParams() instanceof LayoutParams) {
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (!layoutParams.ignoreRightInset) {
                        int i2 = ((FrameLayout.LayoutParams) layoutParams).rightMargin;
                        int i3 = this.rightInset;
                        if (i2 != i3 || ((FrameLayout.LayoutParams) layoutParams).leftMargin != this.leftInset) {
                            layoutParams.setMargins(this.leftInset, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, i3, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                            z = true;
                        }
                    }
                }
            }
            if (z) {
                requestLayout();
            }
        }
        this.previousInsets = new WindowInsets(windowInsets);
        return windowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object parent = getParent();
        if (!(parent instanceof View) || ((View) parent).getId() == 16908290) {
            ComposeInitializer.INSTANCE.getClass();
            ComposeInitializer.onAttachedToWindow(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object parent = getParent();
        if (!(parent instanceof View) || ((View) parent).getId() == 16908290) {
            ComposeInitializer.INSTANCE.getClass();
            ComposeInitializer.onDetachedFromWindow(this);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
