package com.android.systemui.accessibility;

import com.android.systemui.accessibility.WindowMagnification;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnification$3$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowMagnification.C10003 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ WindowMagnification$3$$ExternalSyntheticLambda1(WindowMagnification.C10003 c10003, int i, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = c10003;
        this.f$1 = i;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WindowMagnification.C10003 c10003 = this.f$0;
                int i = this.f$1;
                boolean z = this.f$2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) WindowMagnification.this.mMagnificationControllerSupplier.get(i);
                if (windowMagnificationController != null && windowMagnificationController.isActivated()) {
                    windowMagnificationController.updateDragHandleResourcesIfNeeded(z);
                    break;
                }
                break;
            case 1:
                WindowMagnification.C10003 c100032 = this.f$0;
                int i2 = this.f$1;
                boolean z2 = this.f$2;
                WindowMagnificationController windowMagnificationController2 = (WindowMagnificationController) WindowMagnification.this.mMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController2 != null) {
                    windowMagnificationController2.mAllowDiagonalScrolling = z2;
                    break;
                }
                break;
            default:
                WindowMagnification.C10003 c100033 = this.f$0;
                int i3 = this.f$1;
                boolean z3 = this.f$2;
                WindowMagnificationController windowMagnificationController3 = (WindowMagnificationController) WindowMagnification.this.mMagnificationControllerSupplier.get(i3);
                if (windowMagnificationController3 != null && windowMagnificationController3.isActivated()) {
                    windowMagnificationController3.setEditMagnifierSizeMode(z3);
                    break;
                }
                break;
        }
    }
}
