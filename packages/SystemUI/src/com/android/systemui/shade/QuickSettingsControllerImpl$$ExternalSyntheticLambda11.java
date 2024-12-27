package com.android.systemui.shade;

import android.view.VelocityTracker;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.shade.QuickSettingsControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickSettingsControllerImpl$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QuickSettingsControllerImpl$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                QuickSettingsControllerImpl quickSettingsControllerImpl = (QuickSettingsControllerImpl) obj;
                VelocityTracker velocityTracker = quickSettingsControllerImpl.mQsVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    quickSettingsControllerImpl.mQsVelocityTracker = null;
                    break;
                }
                break;
            case 1:
                QuickSettingsControllerImpl quickSettingsControllerImpl2 = (QuickSettingsControllerImpl) obj;
                quickSettingsControllerImpl2.onExpansionStarted$1();
                if (!quickSettingsControllerImpl2.getExpanded$1()) {
                    if (quickSettingsControllerImpl2.isExpansionEnabled()) {
                        quickSettingsControllerImpl2.mLockscreenGestureLogger.write(195, 0, 0);
                        quickSettingsControllerImpl2.flingQs(0.0f, 0, null, true);
                        break;
                    }
                } else {
                    quickSettingsControllerImpl2.flingQs(0.0f, 1, null, true);
                    break;
                }
                break;
            case 2:
                QuickSettingsControllerImpl quickSettingsControllerImpl3 = (QuickSettingsControllerImpl) obj;
                VelocityTracker velocityTracker2 = quickSettingsControllerImpl3.mQsVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                }
                quickSettingsControllerImpl3.mQsVelocityTracker = VelocityTracker.obtain();
                break;
            case 3:
                QS qs = ((QuickSettingsControllerImpl) obj).mQs;
                if (qs != null) {
                    qs.animateHeaderSlidingOut();
                    break;
                }
                break;
            default:
                QuickSettingsControllerImpl quickSettingsControllerImpl4 = ((QuickSettingsControllerImpl.NsslOverscrollTopChangedListener) obj).this$0;
                quickSettingsControllerImpl4.mStackScrollerOverscrolling = false;
                QS qs2 = quickSettingsControllerImpl4.mQs;
                if (qs2 != null) {
                    qs2.setOverscrolling(false);
                }
                quickSettingsControllerImpl4.updateQsState$1$1();
                break;
        }
    }
}
