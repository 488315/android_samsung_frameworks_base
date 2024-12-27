package com.android.systemui.animation;

import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.animation.ViewHierarchyAnimator;

public final class ViewHierarchyAnimator$Companion$createListener$1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ long $duration;
    public final /* synthetic */ boolean $ephemeral;
    public final /* synthetic */ boolean $ignorePreviousValues;
    public final /* synthetic */ Interpolator $interpolator;
    public final /* synthetic */ Runnable $onAnimationEnd;
    public final /* synthetic */ ViewHierarchyAnimator.Hotspot $origin;

    public ViewHierarchyAnimator$Companion$createListener$1(ViewHierarchyAnimator.Hotspot hotspot, boolean z, Interpolator interpolator, long j, boolean z2, Runnable runnable) {
        this.$origin = hotspot;
        this.$ignorePreviousValues = z;
        this.$interpolator = interpolator;
        this.$duration = j;
        this.$ephemeral = z2;
        this.$onAnimationEnd = runnable;
    }

    @Override // android.view.View.OnLayoutChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLayoutChange(android.view.View r20, int r21, int r22, int r23, int r24, int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instructions count: 550
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void");
    }
}
