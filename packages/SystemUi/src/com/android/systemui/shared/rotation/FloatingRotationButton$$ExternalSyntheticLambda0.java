package com.android.systemui.shared.rotation;

import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.statusbar.phone.AutoHideController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FloatingRotationButton$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FloatingRotationButton f$0;

    public /* synthetic */ FloatingRotationButton$$ExternalSyntheticLambda0(FloatingRotationButton floatingRotationButton, int i) {
        this.$r8$classId = i;
        this.f$0 = floatingRotationButton;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NavigationBarView.C18592 c18592;
        switch (this.$r8$classId) {
            case 0:
                FloatingRotationButton floatingRotationButton = this.f$0;
                if (floatingRotationButton.mIsShowing && (c18592 = floatingRotationButton.mUpdatesCallback) != null) {
                    NavigationBarView navigationBarView = NavigationBarView.this;
                    AutoHideController autoHideController = navigationBarView.mAutoHideController;
                    if (autoHideController != null) {
                        autoHideController.touchAutoHide();
                    }
                    navigationBarView.notifyActiveTouchRegions();
                    break;
                }
                break;
            default:
                FloatingRotationButton floatingRotationButton2 = this.f$0;
                NavigationBarView.C18592 c185922 = floatingRotationButton2.mUpdatesCallback;
                if (c185922 != null && floatingRotationButton2.mIsShowing) {
                    NavigationBarView.this.notifyActiveTouchRegions();
                    break;
                }
                break;
        }
    }
}
