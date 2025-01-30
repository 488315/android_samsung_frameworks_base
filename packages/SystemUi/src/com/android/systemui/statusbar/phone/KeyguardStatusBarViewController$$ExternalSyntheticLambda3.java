package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda3(KeyguardStatusBarViewController keyguardStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                StatusBarIconController.TintedIconManager tintedIconManager = keyguardStatusBarViewController.mTintedIconManager;
                if (tintedIconManager != null) {
                    List<String> blockedIcons = keyguardStatusBarViewController.getBlockedIcons();
                    Assert.isMainThread();
                    ArrayList arrayList = tintedIconManager.mBlockList;
                    arrayList.clear();
                    arrayList.addAll(blockedIcons);
                    StatusBarIconController statusBarIconController = tintedIconManager.mController;
                    if (statusBarIconController != null) {
                        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                        tintedIconManager.destroy();
                        statusBarIconControllerImpl.mIconGroups.remove(tintedIconManager);
                        statusBarIconControllerImpl.addIconGroup(tintedIconManager);
                        break;
                    }
                }
                break;
            default:
                KeyguardStatusBarViewController keyguardStatusBarViewController2 = this.f$0;
                ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).updateIconsAndTextColors(keyguardStatusBarViewController2.mTintedIconManager);
                break;
        }
    }
}
