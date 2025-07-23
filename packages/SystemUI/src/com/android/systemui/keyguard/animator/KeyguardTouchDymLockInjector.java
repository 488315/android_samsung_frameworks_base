package com.android.systemui.keyguard.animator;

import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class KeyguardTouchDymLockInjector {
    public final PluginLockMediator mPluginLockMediator;
    public final AnonymousClass1 mPluginLockStateListener;
    public Direction mDirection = null;
    public int mNonSwipeMode = 0;
    public int mViewMode = 0;
    public boolean mIsDynamicLockEnabled = false;
    public boolean mLockStarEnabled = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004c, code lost:
    
        if (r3.equals(r4) != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005f, code lost:
    
        if (r3.equals(r4) != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0072, code lost:
    
        if (r3.equals(r4) != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0085, code lost:
    
        if (r0.equals(r4) != false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDirection(int r14, float r15, float r16, android.view.MotionEvent r17) {
        /*
            r13 = this;
            r0 = r15
            r1 = r16
            int r2 = r13.mNonSwipeMode
            if (r2 != 0) goto L8
            return
        L8:
            float r3 = r17.getRawX()
            float r4 = r17.getRawY()
            float r5 = r3 - r0
            int r5 = (int) r5
            float r6 = r4 - r1
            int r6 = (int) r6
            double r7 = (double) r5
            r9 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r7 = java.lang.Math.pow(r7, r9)
            double r11 = (double) r6
            double r9 = java.lang.Math.pow(r11, r9)
            double r9 = r9 + r7
            double r7 = java.lang.Math.sqrt(r9)
            double r9 = (double) r0
            double r0 = (double) r1
            double r11 = (double) r3
            double r3 = (double) r4
            double r11 = r11 - r9
            double r3 = r3 - r0
            double r0 = java.lang.Math.atan2(r3, r11)
            r3 = 4633260481411531256(0x404ca5dc1a63c1f8, double:57.29577951308232)
            double r0 = r0 * r3
            double r3 = (double) r14
            int r3 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r3 <= 0) goto L8b
            r3 = r2 & 1
            if (r3 == 0) goto L4f
            if (r5 >= 0) goto L4f
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r3 = r13.getDirection(r0)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.LEFT
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L4f
            goto L94
        L4f:
            r3 = r2 & 2
            if (r3 == 0) goto L62
            if (r5 <= 0) goto L62
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r3 = r13.getDirection(r0)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.RIGHT
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L62
            goto L94
        L62:
            r3 = r2 & 4
            if (r3 == 0) goto L75
            if (r6 >= 0) goto L75
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r3 = r13.getDirection(r0)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.UP
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L75
            goto L94
        L75:
            r2 = r2 & 8
            if (r2 == 0) goto L88
            if (r6 <= 0) goto L88
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r0 = r13.getDirection(r0)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.DOWN
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L88
            goto L94
        L88:
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.SWIPE
            goto L94
        L8b:
            r0 = r2 & 16
            if (r0 == 0) goto L92
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.TAP
            goto L94
        L92:
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.SWIPE
        L94:
            r13.mDirection = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.updateDirection(int, float, float, android.view.MotionEvent):void");
    }
}
