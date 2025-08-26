package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.util.LogUtil;

/* loaded from: classes2.dex */
public class KeyguardTouchDymLockInjector {
    public final PluginLockMediator mPluginLockMediator;
    public final AnonymousClass1 mPluginLockStateListener;
    public Direction mDirection = null;
    public int mNonSwipeMode = 0;
    public int mViewMode = 0;
    public boolean mIsDynamicLockEnabled = false;
    public boolean mLockStarEnabled = false;

    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        TAP,
        SWIPE
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$1, com.android.systemui.pluginlock.listener.PluginLockListener$State] */
    public KeyguardTouchDymLockInjector(PluginLockMediator pluginLockMediator) {
        ?? r1 = new PluginLockListener.State() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onLockStarEnabled(boolean z) {
                KeyguardTouchDymLockInjector.this.mLockStarEnabled = z;
            }

            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onViewModeChanged(int i) {
                KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = KeyguardTouchDymLockInjector.this;
                LogUtil.d("KeyguardTouchDymLockInjector", "onViewModeChanged mViewMode[%d], newMode[%d]", Integer.valueOf(keyguardTouchDymLockInjector.mViewMode), Integer.valueOf(i));
                if (keyguardTouchDymLockInjector.mViewMode == 1 && i == 0) {
                    keyguardTouchDymLockInjector.getClass();
                }
                keyguardTouchDymLockInjector.mViewMode = i;
            }
        };
        this.mPluginLockStateListener = r1;
        LogUtil.d("KeyguardTouchDymLockInjector", "KeyguardTouchDymLockInjector pluginLockMediator: " + pluginLockMediator, new Object[0]);
        this.mPluginLockMediator = pluginLockMediator;
        pluginLockMediator.registerStateCallback(r1);
    }

    public final Direction getDirection(double d) {
        double nonSwipeModeAngle = this.mPluginLockMediator.getPluginLockSwipe() != null ? r6.getPluginLockSwipe().getNonSwipeModeAngle() : 45.0d;
        return (d >= 180.0d - nonSwipeModeAngle || d <= (-180.0d) + nonSwipeModeAngle) ? Direction.LEFT : (d < (-nonSwipeModeAngle) || d > nonSwipeModeAngle) ? (d < (-90.0d) - nonSwipeModeAngle || d > (-90.0d) + nonSwipeModeAngle) ? (d < 90.0d - nonSwipeModeAngle || d > nonSwipeModeAngle + 90.0d) ? Direction.SWIPE : Direction.DOWN : Direction.UP : Direction.RIGHT;
    }

    public final void resetDynamicLock() {
        LogUtil.d("KeyguardTouchDymLockInjector", "resetDynamicLock mIsDynamicLockEnabled: " + this.mIsDynamicLockEnabled, new Object[0]);
        if (this.mIsDynamicLockEnabled) {
            this.mNonSwipeMode = 0;
            this.mDirection = null;
            this.mIsDynamicLockEnabled = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDirection(int i, float f, float f2, MotionEvent motionEvent) {
        Direction direction;
        int i2 = this.mNonSwipeMode;
        if (i2 == 0) {
            return;
        }
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        int i3 = (int) (rawX - f);
        int i4 = (int) (rawY - f2);
        double dSqrt = Math.sqrt(Math.pow(i4, 2.0d) + Math.pow(i3, 2.0d));
        double dAtan2 = Math.atan2(rawY - f2, rawX - f) * 57.29577951308232d;
        if (dSqrt <= i) {
            direction = (i2 & 16) != 0 ? Direction.TAP : Direction.SWIPE;
        } else if ((i2 & 1) != 0 && i3 < 0) {
            Direction direction2 = getDirection(dAtan2);
            direction = Direction.LEFT;
            if (!direction2.equals(direction)) {
            }
        } else if ((i2 & 2) != 0 && i3 > 0) {
            Direction direction3 = getDirection(dAtan2);
            direction = Direction.RIGHT;
            if (!direction3.equals(direction)) {
            }
        } else if ((i2 & 4) != 0 && i4 < 0) {
            Direction direction4 = getDirection(dAtan2);
            direction = Direction.UP;
            if (!direction4.equals(direction)) {
            }
        } else if ((i2 & 8) == 0 || i4 <= 0) {
            direction = Direction.SWIPE;
        } else {
            Direction direction5 = getDirection(dAtan2);
            direction = Direction.DOWN;
            if (!direction5.equals(direction)) {
            }
        }
        this.mDirection = direction;
    }
}
