package com.android.systemui.statusbar.phone;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.util.Assert;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda5(KeyguardStatusBarViewController keyguardStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        switch (i) {
            case 0:
                TintedIconManager tintedIconManager = keyguardStatusBarViewController.mTintedIconManager;
                if (tintedIconManager != null) {
                    List<String> blockedIcons = keyguardStatusBarViewController.getBlockedIcons();
                    Assert.isMainThread();
                    tintedIconManager.mBlockList.clear();
                    tintedIconManager.mBlockList.addAll(blockedIcons);
                    StatusBarIconController statusBarIconController = tintedIconManager.mController;
                    if (statusBarIconController != null) {
                        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                        statusBarIconControllerImpl.removeIconGroup(tintedIconManager);
                        statusBarIconControllerImpl.addIconGroup(tintedIconManager);
                        break;
                    }
                }
                break;
            case 1:
                keyguardStatusBarViewController.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda16(keyguardStatusBarViewController, keyguardStatusBarViewController.mUserManager.isUserSwitcherEnabled(keyguardStatusBarViewController.getResources().getBoolean(R.bool.qs_show_user_switcher_for_single_user)), 1));
                break;
            default:
                ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).updateIconsAndTextColors(keyguardStatusBarViewController.mTintedIconManager);
                break;
        }
    }
}
