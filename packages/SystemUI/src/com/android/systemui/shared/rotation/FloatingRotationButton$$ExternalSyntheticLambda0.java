package com.android.systemui.shared.rotation;

import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.statusbar.phone.AutoHideControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
                    AutoHideControllerImpl autoHideControllerImpl = navigationBarView.mAutoHideController;
                    if (autoHideControllerImpl != null) {
                        autoHideControllerImpl.touchAutoHide();
                    }
                    navigationBarView.notifyActiveTouchRegions();
                    break;
                }
                break;
        }
    }
}
