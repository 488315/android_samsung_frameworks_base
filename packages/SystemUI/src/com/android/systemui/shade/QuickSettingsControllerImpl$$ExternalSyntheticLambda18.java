package com.android.systemui.shade;

import android.view.MotionEvent;
import com.android.systemui.plugins.qs.QS;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class QuickSettingsControllerImpl$$ExternalSyntheticLambda18 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QuickSettingsControllerImpl f$0;

    public /* synthetic */ QuickSettingsControllerImpl$$ExternalSyntheticLambda18(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = quickSettingsControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
        switch (i) {
            case 0:
                quickSettingsControllerImpl.trackMovement((MotionEvent) obj);
                break;
            case 1:
                quickSettingsControllerImpl.getClass();
                quickSettingsControllerImpl.mTouchAboveFalsingThreshold = ((Boolean) obj).booleanValue();
                break;
            case 2:
                quickSettingsControllerImpl.mExpansionEnabledPolicy = ((Boolean) obj).booleanValue();
                QS qs = quickSettingsControllerImpl.mQs;
                if (qs != null) {
                    qs.setHeaderClickable(quickSettingsControllerImpl.isExpansionEnabled());
                    break;
                }
                break;
            case 3:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                QS qs2 = quickSettingsControllerImpl.mQs;
                if (qs2 != null) {
                    qs2.setShouldUpdateSquishinessOnMedia(booleanValue);
                    break;
                }
                break;
            case 4:
                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                if (quickSettingsControllerImpl.isQsFragmentCreated()) {
                    if (booleanValue2) {
                        quickSettingsControllerImpl.mAnimateNextNotificationBounds = true;
                        quickSettingsControllerImpl.mNotificationBoundsAnimationDuration = 360L;
                    }
                    quickSettingsControllerImpl.setClippingBounds();
                    break;
                }
                break;
            default:
                ((Integer) obj).intValue();
                quickSettingsControllerImpl.updateExpansionEnabledAmbient();
                break;
        }
    }
}
