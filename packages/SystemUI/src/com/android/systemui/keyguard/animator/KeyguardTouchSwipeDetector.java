package com.android.systemui.keyguard.animator;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardTouchSwipeDetector extends KeyguardTouchBase {
    public final KeyguardTouchDymLockInjector mDynamicLockInjector;
    public final KeyguardTouchSecurityInjector mSecurityInjector;
    public KeyguardTouchSwipeCallback mUnlockCallback;

    public KeyguardTouchSwipeDetector(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTouchDymLockInjector keyguardTouchDymLockInjector, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector) {
        super(context, keyguardUpdateMonitor);
        this.mDynamicLockInjector = keyguardTouchDymLockInjector;
        this.mSecurityInjector = keyguardTouchSecurityInjector;
        initDimens$5();
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.isUnlockExecuted) {
            if (action == 0 || action == 5) {
                Log.i("KeyguardTouchSwipeDetector", "onTouchEvent(): Unlock is started already");
            }
            return false;
        }
        if (!this.intercepting) {
            if (action == 0 || action == 5) {
                Log.i("KeyguardTouchSwipeDetector", "onTouchEvent(): mIntercepting is false");
            }
            return false;
        }
        KeyguardTouchSecurityInjector keyguardTouchSecurityInjector = this.mSecurityInjector;
        if (keyguardTouchSecurityInjector != null && CscRune.SECURITY_SIM_PERM_DISABLED && keyguardTouchSecurityInjector.mKeyguardUpdateMonitor.isIccBlockedPermanently()) {
            if (action == 0 || action == 5) {
                Log.d("KeyguardTouchSwipeDetector", "isSupportSimPermDisabled!!");
            }
            return true;
        }
        double d = this.distance;
        KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = this.mDynamicLockInjector;
        KeyguardTouchDymLockInjector.Direction direction = null;
        if (action == 0) {
            this.isMultiTouch = false;
            setTouch(true);
            PointF pointF = this.touchDownPos;
            pointF.x = motionEvent.getRawX();
            pointF.y = motionEvent.getRawY();
            this.distance = 0.0f;
            this.updateDistanceCount = 0;
            if (keyguardTouchDymLockInjector != null) {
                PluginLockMediator pluginLockMediator = keyguardTouchDymLockInjector.mPluginLockMediator;
                keyguardTouchDymLockInjector.mIsDynamicLockEnabled = pluginLockMediator.isDynamicLockEnabled();
                LogUtil.d("KeyguardTouchDymLockInjector", "initDynamicLock mIsDynamicLockEnabled: " + keyguardTouchDymLockInjector.mIsDynamicLockEnabled, new Object[0]);
                if (keyguardTouchDymLockInjector.mIsDynamicLockEnabled) {
                    keyguardTouchDymLockInjector.mNonSwipeMode = 0;
                    if (pluginLockMediator.getPluginLockSwipe() != null) {
                        keyguardTouchDymLockInjector.mNonSwipeMode = pluginLockMediator.getPluginLockSwipe().getNonSwipeMode();
                    }
                    keyguardTouchDymLockInjector.mDirection = null;
                    LogUtil.d("KeyguardTouchDymLockInjector", String.format("mNonSwipeMode: 0x%08x", Integer.valueOf(keyguardTouchDymLockInjector.mNonSwipeMode)), new Object[0]);
                }
            }
        } else if (action == 1) {
            LogUtil.d("KeyguardTouchSwipeDetector", "onTouchEvent(): ACTION_UP (T/D/R)=%d/%f/%d", Integer.valueOf(this.touchSlop), Double.valueOf(d), Integer.valueOf(this.swipeUnlockRadius));
            setIntercept(false);
            KeyguardTouchSwipeCallback keyguardTouchSwipeCallback = this.mUnlockCallback;
            if (keyguardTouchSwipeCallback != null) {
                keyguardTouchSwipeCallback.callUserActivity();
            }
            if (keyguardTouchDymLockInjector != null && keyguardTouchDymLockInjector.mIsDynamicLockEnabled && !((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).isShownSwipeBouncer()) {
                direction = keyguardTouchDymLockInjector.mDirection;
            }
            if (keyguardTouchDymLockInjector == null || direction == null || direction == KeyguardTouchDymLockInjector.Direction.SWIPE) {
                if (d >= this.touchSlop && this.swipeUnlockRadius < d && getCanBeUnlock() && this.mUnlockCallback != null) {
                    com.android.systemui.keyguard.Log.i("KeyguardTouchBase", "unlockExecute()");
                    this.isUnlockExecuted = true;
                    this.mUnlockCallback.onUnlockExecuted();
                }
                this.isUnlockExecuted = false;
                this.distance = 0.0f;
                this.updateDistanceCount = 0;
                PointF pointF2 = this.touchDownPos;
                pointF2.x = -1.0f;
                pointF2.y = -1.0f;
                if (keyguardTouchDymLockInjector != null) {
                    keyguardTouchDymLockInjector.resetDynamicLock();
                }
            }
            setTouch(false);
            this.isMultiTouch = motionEvent.getPointerCount() >= 2;
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onTouchEvent(): ACTION_UP mIsMultiTouch="), this.isMultiTouch, "KeyguardTouchSwipeDetector");
        } else if (action != 2) {
            if (action == 3) {
                Log.d("KeyguardTouchSwipeDetector", "onTouchEvent(): ACTION_CANCEL mDistance=" + d);
                if (motionEvent.getPointerCount() <= 1) {
                    this.isMultiTouch = false;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onTouchEvent(): ACTION_CANCEL mIsMultiTouch="), this.isMultiTouch, "KeyguardTouchSwipeDetector");
                }
                setTouch(false);
            } else if (action == 5) {
                this.isMultiTouch = motionEvent.getPointerCount() >= 2;
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onTouchEvent(): ACTION_POINTER_DOWN mIsMultiTouch="), this.isMultiTouch, "KeyguardTouchSwipeDetector");
            }
        } else {
            if (!this.isTouching) {
                return false;
            }
            userActivityForMove(new Runnable() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchSwipeDetector$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardTouchSwipeCallback keyguardTouchSwipeCallback2 = KeyguardTouchSwipeDetector.this.mUnlockCallback;
                    if (keyguardTouchSwipeCallback2 != null) {
                        keyguardTouchSwipeCallback2.callUserActivity();
                    }
                }
            });
            updateDistance(motionEvent, false);
            if (keyguardTouchDymLockInjector != null && keyguardTouchDymLockInjector.mIsDynamicLockEnabled) {
                PointF pointF3 = this.touchDownPos;
                keyguardTouchDymLockInjector.updateDirection(this.swipeUnlockRadius, pointF3.x, pointF3.y, motionEvent);
            }
        }
        return true;
    }
}
