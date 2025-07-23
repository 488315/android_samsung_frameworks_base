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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AutoHideControllerImpl implements AutoHideController {
    public final AccessibilityManager mAccessibilityManager;
    public final AutoHideControllerImpl$$ExternalSyntheticLambda0 mAutoHide = new AutoHideControllerImpl$$ExternalSyntheticLambda0(this, 0);
    public boolean mAutoHideSuspended;
    public final int mDisplayId;
    public boolean mGameToolsShown;
    public final Handler mHandler;
    public boolean mIsVisible;
    public AutoHideUiElement mNavigationBar;
    public final AutoHideUiElementObserver mObserver;
    public boolean mShouldHide;
    public AutoHideUiElement mStatusBar;
    public final IWindowManager mWindowManagerService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AutoHideUiElementObserver extends SystemBarObserver {
        public final List mList;

        public /* synthetic */ AutoHideUiElementObserver(int i) {
            this();
        }

        public final void notify(Consumer consumer) {
            ((ArrayList) this.mList).forEach(new AutoHideControllerImpl$$ExternalSyntheticLambda1(consumer, 5));
        }

        private AutoHideUiElementObserver() {
            this.mList = new ArrayList();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Factory {
        public final Handler mHandler;
        public final IWindowManager mIWindowManager;

        public Factory(Handler handler, IWindowManager iWindowManager) {
            this.mHandler = handler;
            this.mIWindowManager = iWindowManager;
        }
    }

    public AutoHideControllerImpl(Context context, Handler handler, IWindowManager iWindowManager) {
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
        Handler handler = this.mHandler;
        AutoHideControllerImpl$$ExternalSyntheticLambda0 autoHideControllerImpl$$ExternalSyntheticLambda0 = this.mAutoHide;
        if (z2) {
            this.mShouldHide = z;
            this.mObserver.notify(new AutoHideControllerImpl$$ExternalSyntheticLambda1(this, 4));
            if (this.mShouldHide) {
                this.mAutoHideSuspended = false;
                handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
                handler.postDelayed(autoHideControllerImpl$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
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
            handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideControllerImpl$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
        }
    }

    public final Runnable getCheckBarModesRunnable() {
        if (this.mStatusBar != null) {
            return new AutoHideControllerImpl$$ExternalSyntheticLambda0(this, 1);
        }
        if (this.mNavigationBar != null) {
            return new AutoHideControllerImpl$$ExternalSyntheticLambda0(this, 2);
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
            this.mObserver.notify(new AutoHideControllerImpl$$ExternalSyntheticLambda1(this, 0));
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
            AutoHideControllerImpl$$ExternalSyntheticLambda0 autoHideControllerImpl$$ExternalSyntheticLambda0 = this.mAutoHide;
            Handler handler = this.mHandler;
            handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideControllerImpl$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                this.mObserver.notify(new AutoHideControllerImpl$$ExternalSyntheticLambda1(this, 1));
                return;
            }
            Runnable checkBarModesRunnable = getCheckBarModesRunnable();
            if (checkBarModesRunnable != null) {
                handler.post(checkBarModesRunnable);
            }
        }
    }

    public final void registerElementToObserver(AutoHideUiElement autoHideUiElement) {
        AutoHideUiElementObserver autoHideUiElementObserver = this.mObserver;
        autoHideUiElementObserver.getClass();
        ((ArrayList) autoHideUiElementObserver.mList).remove(autoHideUiElement);
        if (autoHideUiElement != null) {
            ((ArrayList) autoHideUiElementObserver.mList).add(autoHideUiElement);
        }
    }

    public final void resumeSuspendedAutoHide() {
        if (this.mAutoHideSuspended) {
            this.mAutoHideSuspended = false;
            AutoHideControllerImpl$$ExternalSyntheticLambda0 autoHideControllerImpl$$ExternalSyntheticLambda0 = this.mAutoHide;
            Handler handler = this.mHandler;
            handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideControllerImpl$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                this.mObserver.notify(new AutoHideControllerImpl$$ExternalSyntheticLambda1(this, 2));
                return;
            }
            Runnable checkBarModesRunnable = getCheckBarModesRunnable();
            if (checkBarModesRunnable != null) {
                handler.postDelayed(checkBarModesRunnable, 500L);
            }
        }
    }

    public final void suspendAutoHide() {
        AutoHideControllerImpl$$ExternalSyntheticLambda0 autoHideControllerImpl$$ExternalSyntheticLambda0 = this.mAutoHide;
        Handler handler = this.mHandler;
        handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            this.mObserver.notify(new AutoHideControllerImpl$$ExternalSyntheticLambda1(this, 3));
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
        Handler handler = this.mHandler;
        AutoHideControllerImpl$$ExternalSyntheticLambda0 autoHideControllerImpl$$ExternalSyntheticLambda0 = this.mAutoHide;
        if (!isAnyTransientBarShown) {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
        } else {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideControllerImpl$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideControllerImpl$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
        }
    }

    public final Runnable getCheckBarModesRunnable(AutoHideUiElement autoHideUiElement) {
        if (this.mStatusBar != null) {
            return new AutoHideControllerImpl$$ExternalSyntheticLambda0(this, 3);
        }
        if (this.mNavigationBar != null) {
            return new AutoHideControllerImpl$$ExternalSyntheticLambda0(autoHideUiElement, 4);
        }
        return null;
    }
}
