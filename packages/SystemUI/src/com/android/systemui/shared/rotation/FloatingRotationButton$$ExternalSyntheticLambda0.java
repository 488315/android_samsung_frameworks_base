package com.android.systemui.shared.rotation;

import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.statusbar.phone.AutoHideController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class FloatingRotationButton$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FloatingRotationButton f$0;

    public /* synthetic */ FloatingRotationButton$$ExternalSyntheticLambda0(FloatingRotationButton floatingRotationButton, int i) {
        this.$r8$classId = i;
        this.f$0 = floatingRotationButton;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NavigationBarView.AnonymousClass2 anonymousClass2;
        int i = this.$r8$classId;
        FloatingRotationButton floatingRotationButton = this.f$0;
        switch (i) {
            case 0:
                NavigationBarView.AnonymousClass2 anonymousClass22 = floatingRotationButton.mUpdatesCallback;
                if (anonymousClass22 != null && floatingRotationButton.mIsShowing) {
                    NavigationBarView.this.notifyActiveTouchRegions();
                    break;
                }
                break;
            default:
                if (floatingRotationButton.mIsShowing && (anonymousClass2 = floatingRotationButton.mUpdatesCallback) != null) {
                    NavigationBarView navigationBarView = NavigationBarView.this;
                    AutoHideController autoHideController = navigationBarView.mAutoHideController;
                    if (autoHideController != null) {
                        autoHideController.touchAutoHide();
                    }
                    navigationBarView.notifyActiveTouchRegions();
                    break;
                }
                break;
        }
    }
}
