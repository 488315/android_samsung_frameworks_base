package com.android.systemui.shade;

import android.view.MotionEvent;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickSettingsController$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QuickSettingsController f$0;

    public /* synthetic */ QuickSettingsController$$ExternalSyntheticLambda12(QuickSettingsController quickSettingsController, int i) {
        this.$r8$classId = i;
        this.f$0 = quickSettingsController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QuickSettingsController quickSettingsController = this.f$0;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (quickSettingsController.isQsFragmentCreated()) {
                    if (booleanValue) {
                        quickSettingsController.mAnimateNextNotificationBounds = true;
                        quickSettingsController.mNotificationBoundsAnimationDuration = 360L;
                        quickSettingsController.mNotificationBoundsAnimationDelay = 0L;
                    }
                    quickSettingsController.setClippingBounds();
                    break;
                }
                break;
            case 1:
                QuickSettingsController quickSettingsController2 = this.f$0;
                quickSettingsController2.getClass();
                quickSettingsController2.mTouchAboveFalsingThreshold = ((Boolean) obj).booleanValue();
                break;
            case 2:
                this.f$0.trackMovement((MotionEvent) obj);
                break;
            default:
                QuickSettingsController quickSettingsController3 = this.f$0;
                ((Integer) obj).intValue();
                quickSettingsController3.updateExpansionEnabledAmbient();
                break;
        }
    }
}
