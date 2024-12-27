package com.android.systemui.statusbar;

import com.android.systemui.Dependency;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
                keyguardSecAffordanceView.updateBgBlur(((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible(), false);
                keyguardSecAffordanceView.setBackgroundCircleColor();
                keyguardSecAffordanceView.setForegroundCircleColor();
                break;
            default:
                KeyguardSecAffordanceView.$r8$lambda$hPpMwnVD27esyCsYHNZU3Xvb_mE(keyguardSecAffordanceView);
                break;
        }
    }
}
