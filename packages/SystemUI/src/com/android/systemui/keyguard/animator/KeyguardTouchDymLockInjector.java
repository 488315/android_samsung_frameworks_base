package com.android.systemui.keyguard.animator;

import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardTouchDymLockInjector {
    public final PluginLockMediator mPluginLockMediator;
    public final AnonymousClass1 mPluginLockStateListener;
    public Direction mDirection = null;
    public int mNonSwipeMode = 0;
    public int mViewMode = 0;
    public boolean mIsDynamicLockEnabled = false;
    public boolean mLockStarEnabled = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        TAP,
        SWIPE
    }

    public KeyguardTouchDymLockInjector(PluginLockMediator pluginLockMediator) {
        PluginLockListener.State state = new PluginLockListener.State() { // from class: com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.1
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
        LogUtil.d("KeyguardTouchDymLockInjector", "KeyguardTouchDymLockInjector pluginLockMediator: " + pluginLockMediator, new Object[0]);
        this.mPluginLockMediator = pluginLockMediator;
        pluginLockMediator.registerStateCallback(state);
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDirection(int r15, float r16, float r17, android.view.MotionEvent r18) {
        /*
            r14 = this;
            r0 = r14
            r1 = r16
            r2 = r17
            int r3 = r0.mNonSwipeMode
            if (r3 != 0) goto La
            return
        La:
            float r4 = r18.getRawX()
            float r5 = r18.getRawY()
            float r6 = r4 - r1
            int r6 = (int) r6
            float r7 = r5 - r2
            int r7 = (int) r7
            double r8 = (double) r6
            r10 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r8 = java.lang.Math.pow(r8, r10)
            double r12 = (double) r7
            double r10 = java.lang.Math.pow(r12, r10)
            double r10 = r10 + r8
            double r8 = java.lang.Math.sqrt(r10)
            double r10 = (double) r1
            double r1 = (double) r2
            double r12 = (double) r4
            double r4 = (double) r5
            double r12 = r12 - r10
            double r4 = r4 - r1
            double r1 = java.lang.Math.atan2(r4, r12)
            r4 = 4633260481411531256(0x404ca5dc1a63c1f8, double:57.29577951308232)
            double r1 = r1 * r4
            r4 = r15
            double r4 = (double) r4
            int r4 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r4 <= 0) goto L8e
            r4 = r3 & 1
            if (r4 == 0) goto L52
            if (r6 >= 0) goto L52
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = r14.getDirection(r1)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.LEFT
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L52
            goto L97
        L52:
            r4 = r3 & 2
            if (r4 == 0) goto L65
            if (r6 <= 0) goto L65
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = r14.getDirection(r1)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.RIGHT
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L65
            goto L97
        L65:
            r4 = r3 & 4
            if (r4 == 0) goto L78
            if (r7 >= 0) goto L78
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r4 = r14.getDirection(r1)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.UP
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L78
            goto L97
        L78:
            r3 = r3 & 8
            if (r3 == 0) goto L8b
            if (r7 <= 0) goto L8b
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r1 = r14.getDirection(r1)
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.DOWN
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L8b
            goto L97
        L8b:
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.SWIPE
            goto L97
        L8e:
            r1 = r3 & 16
            if (r1 == 0) goto L95
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.TAP
            goto L97
        L95:
            com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector$Direction r5 = com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.Direction.SWIPE
        L97:
            r0.mDirection = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.animator.KeyguardTouchDymLockInjector.updateDirection(int, float, float, android.view.MotionEvent):void");
    }
}
