package com.android.systemui.accessibility;

import com.android.systemui.R;
import com.android.systemui.accessibility.AccessibilityLogger;
import com.android.systemui.accessibility.MagnificationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationImpl$4$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationImpl.AnonymousClass4 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ MagnificationImpl$4$$ExternalSyntheticLambda1(MagnificationImpl.AnonymousClass4 anonymousClass4, int i, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass4;
        this.f$1 = i;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MagnificationImpl.AnonymousClass4 anonymousClass4 = this.f$0;
                int i = this.f$1;
                boolean z = this.f$2;
                MagnificationImpl magnificationImpl = MagnificationImpl.this;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationImpl.mWindowMagnificationControllerSupplier.get(i);
                if (windowMagnificationController != null) {
                    if (windowMagnificationController.isActivated()) {
                        windowMagnificationController.mDragView.setImageResource(z ? R.drawable.ic_move_setting_magnification_change : R.drawable.ic_move_setting_magnification);
                        if (z) {
                            windowMagnificationController.mDragView.performAccessibilityAction(128, null);
                        }
                    }
                    AccessibilityLogger accessibilityLogger = magnificationImpl.mA11yLogger;
                    if (!z) {
                        accessibilityLogger.uiEventLogger.log(AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_PANEL_CLOSED);
                        break;
                    } else {
                        accessibilityLogger.uiEventLogger.logWithPosition(AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_PANEL_OPENED, 0, (String) null, windowMagnificationController.isActivated() ? 2 : 1);
                        break;
                    }
                }
                break;
            default:
                MagnificationImpl.AnonymousClass4 anonymousClass42 = this.f$0;
                int i2 = this.f$1;
                boolean z2 = this.f$2;
                WindowMagnificationController windowMagnificationController2 = (WindowMagnificationController) MagnificationImpl.this.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController2 != null) {
                    windowMagnificationController2.mAllowDiagonalScrolling = z2;
                    break;
                }
                break;
        }
    }
}
