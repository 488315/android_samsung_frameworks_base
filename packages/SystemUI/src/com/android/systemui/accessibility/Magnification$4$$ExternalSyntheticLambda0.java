package com.android.systemui.accessibility;

import com.android.systemui.R;
import com.android.systemui.accessibility.AccessibilityLogger;
import com.android.systemui.accessibility.Magnification;

public final /* synthetic */ class Magnification$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Magnification.AnonymousClass4 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ Magnification$4$$ExternalSyntheticLambda0(Magnification.AnonymousClass4 anonymousClass4, int i) {
        this.$r8$classId = 2;
        this.f$0 = anonymousClass4;
        this.f$1 = i;
        this.f$2 = true;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Magnification.AnonymousClass4 anonymousClass4 = this.f$0;
                int i = this.f$1;
                boolean z = this.f$2;
                Magnification magnification = Magnification.this;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnification.mWindowMagnificationControllerSupplier.get(i);
                if (windowMagnificationController != null) {
                    boolean isActivated = windowMagnificationController.isActivated();
                    if (isActivated && windowMagnificationController.isActivated()) {
                        windowMagnificationController.mDragView.setImageResource(z ? R.drawable.ic_move_setting_magnification_change : R.drawable.ic_move_setting_magnification);
                        if (z) {
                            windowMagnificationController.mDragView.performAccessibilityAction(128, null);
                        }
                    }
                    AccessibilityLogger accessibilityLogger = magnification.mA11yLogger;
                    if (!z) {
                        accessibilityLogger.uiEventLogger.log(AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_PANEL_CLOSED);
                        break;
                    } else {
                        accessibilityLogger.uiEventLogger.logWithPosition(AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_PANEL_OPENED, 0, (String) null, isActivated ? 2 : 1);
                        break;
                    }
                }
                break;
            case 1:
                Magnification.AnonymousClass4 anonymousClass42 = this.f$0;
                int i2 = this.f$1;
                boolean z2 = this.f$2;
                WindowMagnificationController windowMagnificationController2 = (WindowMagnificationController) Magnification.this.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController2 != null) {
                    windowMagnificationController2.mAllowDiagonalScrolling = z2;
                    break;
                }
                break;
            default:
                Magnification.AnonymousClass4 anonymousClass43 = this.f$0;
                int i3 = this.f$1;
                boolean z3 = this.f$2;
                WindowMagnificationController windowMagnificationController3 = (WindowMagnificationController) Magnification.this.mWindowMagnificationControllerSupplier.get(i3);
                if (windowMagnificationController3 != null && windowMagnificationController3.isActivated()) {
                    windowMagnificationController3.setEditMagnifierSizeMode(z3);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ Magnification$4$$ExternalSyntheticLambda0(Magnification.AnonymousClass4 anonymousClass4, int i, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass4;
        this.f$1 = i;
        this.f$2 = z;
    }
}
