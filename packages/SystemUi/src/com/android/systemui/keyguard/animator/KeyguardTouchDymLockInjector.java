package com.android.systemui.keyguard.animator;

import android.view.MotionEvent;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTouchDymLockInjector {
    public final PluginLockMediator mPluginLockMediator;
    public final C15291 mPluginLockStateListener;
    public Direction mDirection = null;
    public int mNonSwipeMode = 0;
    public int mViewMode = 0;
    public boolean mIsDynamicLockEnabled = false;
    public boolean mLockStarEnabled = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        ?? r1 = new PluginLockListener$State() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
            public final void onLockStarEnabled(boolean z) {
                KeyguardTouchDymLockInjector.this.mLockStarEnabled = z;
            }

            @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
            public final void onViewModeChanged(int i) {
                KeyguardTouchDymLockInjector keyguardTouchDymLockInjector = KeyguardTouchDymLockInjector.this;
                LogUtil.m223d("KeyguardTouchDymLockInjector", "onViewModeChanged mViewMode[%d], newMode[%d]", Integer.valueOf(keyguardTouchDymLockInjector.mViewMode), Integer.valueOf(i));
                int i2 = keyguardTouchDymLockInjector.mViewMode;
                keyguardTouchDymLockInjector.mViewMode = i;
            }
        };
        this.mPluginLockStateListener = r1;
        LogUtil.m223d("KeyguardTouchDymLockInjector", "KeyguardTouchDymLockInjector pluginLockMediator: " + pluginLockMediator, new Object[0]);
        this.mPluginLockMediator = pluginLockMediator;
        ((PluginLockMediatorImpl) pluginLockMediator).registerStateCallback(r1);
    }

    public final Direction getDirection(double d) {
        double d2 = ((PluginLockMediatorImpl) this.mPluginLockMediator).mSwipe != null ? ((PluginLockMediatorImpl) r6).mSwipe.mNonSwipeModeAngle : 45.0d;
        return (d >= 180.0d - d2 || d <= (-180.0d) + d2) ? Direction.LEFT : (d < (-d2) || d > d2) ? (d < (-90.0d) - d2 || d > (-90.0d) + d2) ? (d < 90.0d - d2 || d > d2 + 90.0d) ? Direction.SWIPE : Direction.DOWN : Direction.UP : Direction.RIGHT;
    }

    public final void resetDynamicLock() {
        LogUtil.m223d("KeyguardTouchDymLockInjector", "resetDynamicLock mIsDynamicLockEnabled: " + this.mIsDynamicLockEnabled, new Object[0]);
        if (this.mIsDynamicLockEnabled) {
            this.mNonSwipeMode = 0;
            this.mDirection = null;
            this.mIsDynamicLockEnabled = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004f, code lost:
    
        if (r4.equals(r5) != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0062, code lost:
    
        if (r4.equals(r5) != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0075, code lost:
    
        if (r4.equals(r5) != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0088, code lost:
    
        if (r1.equals(r5) != false) goto L36;
     */
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
        double sqrt = Math.sqrt(Math.pow(i4, 2.0d) + Math.pow(i3, 2.0d));
        double atan2 = Math.atan2(rawY - f2, rawX - f) * 57.29577951308232d;
        if (sqrt > i) {
            if ((i2 & 1) != 0 && i3 < 0) {
                Direction direction2 = getDirection(atan2);
                direction = Direction.LEFT;
            }
            if ((i2 & 2) != 0 && i3 > 0) {
                Direction direction3 = getDirection(atan2);
                direction = Direction.RIGHT;
            }
            if ((i2 & 4) != 0 && i4 < 0) {
                Direction direction4 = getDirection(atan2);
                direction = Direction.UP;
            }
            if ((i2 & 8) != 0 && i4 > 0) {
                Direction direction5 = getDirection(atan2);
                direction = Direction.DOWN;
            }
            direction = Direction.SWIPE;
        } else {
            direction = (i2 & 16) != 0 ? Direction.TAP : Direction.SWIPE;
        }
        this.mDirection = direction;
    }
}
