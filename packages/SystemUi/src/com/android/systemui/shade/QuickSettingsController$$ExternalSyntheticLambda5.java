package com.android.systemui.shade;

import android.view.VelocityTracker;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.shade.QuickSettingsController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickSettingsController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QuickSettingsController$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                QuickSettingsController quickSettingsController = (QuickSettingsController) this.f$0;
                if (!quickSettingsController.mSplitShadeEnabled) {
                    quickSettingsController.onExpansionStarted();
                    if (!quickSettingsController.mExpanded) {
                        if (quickSettingsController.isExpansionEnabled()) {
                            quickSettingsController.mLockscreenGestureLogger.write(195, 0, 0);
                            quickSettingsController.flingQs(0.0f, 0, null, true);
                            break;
                        }
                    } else {
                        quickSettingsController.flingQs(0.0f, 1, null, true);
                        break;
                    }
                }
                break;
            case 1:
                QuickSettingsController quickSettingsController2 = (QuickSettingsController) this.f$0;
                VelocityTracker velocityTracker = quickSettingsController2.mQsVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                }
                quickSettingsController2.mQsVelocityTracker = VelocityTracker.obtain();
                break;
            case 2:
                QuickSettingsController quickSettingsController3 = (QuickSettingsController) this.f$0;
                quickSettingsController3.setTracking(true);
                quickSettingsController3.traceQsJank(true, false);
                quickSettingsController3.onExpansionStarted();
                break;
            case 3:
                QuickSettingsController quickSettingsController4 = (QuickSettingsController) this.f$0;
                quickSettingsController4.mInitialHeightOnTouch = quickSettingsController4.mExpansionHeight;
                break;
            case 4:
                InterfaceC1922QS interfaceC1922QS = ((QuickSettingsController) this.f$0).mQs;
                if (interfaceC1922QS != null) {
                    interfaceC1922QS.animateHeaderSlidingOut();
                    break;
                }
                break;
            case 5:
                QuickSettingsController quickSettingsController5 = (QuickSettingsController) this.f$0;
                VelocityTracker velocityTracker2 = quickSettingsController5.mQsVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    quickSettingsController5.mQsVelocityTracker = null;
                    break;
                }
                break;
            default:
                QuickSettingsController quickSettingsController6 = ((QuickSettingsController.NsslOverscrollTopChangedListener) this.f$0).this$0;
                quickSettingsController6.mStackScrollerOverscrolling = false;
                InterfaceC1922QS interfaceC1922QS2 = quickSettingsController6.mQs;
                if (interfaceC1922QS2 != null) {
                    interfaceC1922QS2.setOverscrolling(false);
                }
                quickSettingsController6.updateQsState();
                break;
        }
    }
}
