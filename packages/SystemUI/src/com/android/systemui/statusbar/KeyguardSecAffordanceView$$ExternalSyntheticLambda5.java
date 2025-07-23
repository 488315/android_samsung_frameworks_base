package com.android.systemui.statusbar;

import android.graphics.Rect;
import android.view.animation.Interpolator;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardSecAffordanceView$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecAffordanceView f$0;

    public /* synthetic */ KeyguardSecAffordanceView$$ExternalSyntheticLambda5(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecAffordanceView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
        switch (i) {
            case 0:
                keyguardSecAffordanceView.mIsTaskTypeShortcutEnabled = keyguardSecAffordanceView.mIsTaskTypeShortcut && keyguardSecAffordanceView.mShortcutManager.isTaskTypeEnabled(keyguardSecAffordanceView.mRight ? 1 : 0);
                if (!keyguardSecAffordanceView.mIsNowBarExpanded) {
                    keyguardSecAffordanceView.updateBgBlur(((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible());
                    keyguardSecAffordanceView.setBackgroundCircleColor();
                    keyguardSecAffordanceView.setForegroundCircleColor();
                    break;
                }
                break;
            default:
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                Rect rect = new Rect(0, 0, 0, 0);
                if (keyguardSecAffordanceView.mKeyguardStateController.isVisible()) {
                    int[] iArr = new int[2];
                    keyguardSecAffordanceView.getLocationOnScreen(iArr);
                    int i2 = iArr[0];
                    rect.left = i2;
                    rect.top = iArr[1];
                    rect.right = keyguardSecAffordanceView.getWidth() + i2;
                    rect.bottom = keyguardSecAffordanceView.getHeight() + iArr[1];
                }
                keyguardSecAffordanceView.sendBlockingTouchAreaToWallpaper(rect);
                break;
        }
    }
}
