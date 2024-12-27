package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.AutoHideUiElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class AutoHideController {
    public final AccessibilityManager mAccessibilityManager;
    public final AutoHideController$$ExternalSyntheticLambda1 mAutoHide = new AutoHideController$$ExternalSyntheticLambda1(this, 0);
    public boolean mAutoHideSuspended;
    public final int mDisplayId;
    public AutoHideUiElement mFlexPanel;
    public boolean mGameToolsShown;
    public final Handler mHandler;
    public boolean mIsVisible;
    public AutoHideUiElement mNavigationBar;
    public final AutoHideUiElementObserver mObserver;
    public boolean mShouldHide;
    public AutoHideUiElement mStatusBar;
    public final IWindowManager mWindowManagerService;

    public final class AutoHideUiElementObserver extends SystemBarObserver {
        public final List mList;

        public /* synthetic */ AutoHideUiElementObserver(int i) {
            this();
        }

        public final void notify(Consumer consumer) {
            ((ArrayList) this.mList).forEach(new AutoHideController$$ExternalSyntheticLambda0(consumer, 5));
        }

        private AutoHideUiElementObserver() {
            this.mList = new ArrayList();
        }
    }

    public final class Factory {
        public final Handler mHandler;
        public final IWindowManager mIWindowManager;

        public Factory(Handler handler, IWindowManager iWindowManager) {
            this.mHandler = handler;
            this.mIWindowManager = iWindowManager;
        }
    }

    public AutoHideController(Context context, Handler handler, IWindowManager iWindowManager) {
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mHandler = handler;
        this.mWindowManagerService = iWindowManager;
        this.mDisplayId = context.getDisplayId();
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver = new AutoHideUiElementObserver(0);
        }
    }

    public final void checkUserAutoHide(MotionEvent motionEvent) {
        boolean z = isAnyTransientBarShown() && motionEvent.getAction() == 4 && motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f;
        AutoHideUiElement autoHideUiElement = this.mStatusBar;
        if (autoHideUiElement != null) {
            z &= autoHideUiElement.shouldHideOnTouch();
        }
        boolean z2 = BasicRune.NAVBAR_POLICY_VISIBILITY;
        AutoHideController$$ExternalSyntheticLambda1 autoHideController$$ExternalSyntheticLambda1 = this.mAutoHide;
        Handler handler = this.mHandler;
        if (z2) {
            this.mShouldHide = z;
            this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda0(this, 4));
            if (this.mShouldHide) {
                this.mAutoHideSuspended = false;
                handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda1);
                handler.postDelayed(autoHideController$$ExternalSyntheticLambda1, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
                return;
            }
            return;
        }
        AutoHideUiElement autoHideUiElement2 = this.mNavigationBar;
        if (autoHideUiElement2 != null) {
            z &= autoHideUiElement2.shouldHideOnTouch();
        }
        if (z) {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda1);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda1, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
        }
    }

    public final Runnable getCheckBarModesRunnable() {
        if (this.mStatusBar != null) {
            return new AutoHideController$$ExternalSyntheticLambda1(this, 1);
        }
        if (this.mNavigationBar != null) {
            return new AutoHideController$$ExternalSyntheticLambda1(this, 2);
        }
        return null;
    }

    public final boolean isAnyTransientBarShown() {
        AutoHideUiElement autoHideUiElement = this.mStatusBar;
        if (autoHideUiElement != null && autoHideUiElement.isVisible()) {
            return true;
        }
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mIsVisible = false;
            this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda0(this, 0));
            if (this.mIsVisible) {
                return true;
            }
        }
        AutoHideUiElement autoHideUiElement2 = this.mNavigationBar;
        return autoHideUiElement2 != null && autoHideUiElement2.isVisible();
    }

    public final void notifyRequestedGameToolsWin(boolean z) {
        this.mGameToolsShown = z;
        if (z) {
            suspendAutoHide();
            return;
        }
        boolean isAnyTransientBarShown = isAnyTransientBarShown();
        this.mAutoHideSuspended = isAnyTransientBarShown;
        if (isAnyTransientBarShown) {
            this.mAutoHideSuspended = false;
            Handler handler = this.mHandler;
            AutoHideController$$ExternalSyntheticLambda1 autoHideController$$ExternalSyntheticLambda1 = this.mAutoHide;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda1);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda1, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda0(this, 1));
                return;
            }
            Runnable checkBarModesRunnable = getCheckBarModesRunnable();
            if (checkBarModesRunnable != null) {
                handler.post(checkBarModesRunnable);
            }
        }
    }

    public final void resumeSuspendedAutoHide() {
        if (this.mAutoHideSuspended) {
            this.mAutoHideSuspended = false;
            Handler handler = this.mHandler;
            AutoHideController$$ExternalSyntheticLambda1 autoHideController$$ExternalSyntheticLambda1 = this.mAutoHide;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda1);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda1, this.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda0(this, 2));
                return;
            }
            Runnable checkBarModesRunnable = getCheckBarModesRunnable();
            if (checkBarModesRunnable != null) {
                handler.postDelayed(checkBarModesRunnable, 500L);
            }
        }
    }

    public final void suspendAutoHide() {
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mAutoHide);
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda0(this, 3));
            this.mAutoHideSuspended = isAnyTransientBarShown();
        } else {
            Runnable checkBarModesRunnable = getCheckBarModesRunnable();
            if (checkBarModesRunnable != null) {
                handler.removeCallbacks(checkBarModesRunnable);
            }
            this.mAutoHideSuspended = isAnyTransientBarShown();
        }
    }

    public final void touchAutoHide() {
        boolean isAnyTransientBarShown = isAnyTransientBarShown();
        AutoHideController$$ExternalSyntheticLambda1 autoHideController$$ExternalSyntheticLambda1 = this.mAutoHide;
        Handler handler = this.mHandler;
        if (!isAnyTransientBarShown) {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda1);
        } else {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda1);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda1, this.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
        }
    }

    public final Runnable getCheckBarModesRunnable(AutoHideUiElement autoHideUiElement) {
        if (this.mStatusBar != null) {
            return new AutoHideController$$ExternalSyntheticLambda1(this, 3);
        }
        if (this.mNavigationBar != null) {
            return new AutoHideController$$ExternalSyntheticLambda1(autoHideUiElement, 4);
        }
        return null;
    }
}
