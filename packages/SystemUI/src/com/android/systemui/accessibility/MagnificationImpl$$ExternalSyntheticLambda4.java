package com.android.systemui.accessibility;

import com.android.systemui.accessibility.MagnificationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationImpl$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MagnificationImpl$$ExternalSyntheticLambda4(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MagnificationImpl magnificationImpl = (MagnificationImpl) this.f$0;
                int i = this.f$1;
                MagnificationImpl.AnonymousClass4 anonymousClass4 = (MagnificationImpl.AnonymousClass4) magnificationImpl.mMagnificationSettingsControllerCallback;
                MagnificationImpl.this.mHandler.post(new MagnificationImpl$4$$ExternalSyntheticLambda0(anonymousClass4, i, 2, 0));
                break;
            default:
                MagnificationImpl.AnonymousClass4 anonymousClass42 = (MagnificationImpl.AnonymousClass4) this.f$0;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) MagnificationImpl.this.mWindowMagnificationControllerSupplier.get(this.f$1);
                if (windowMagnificationController != null && windowMagnificationController.isActivated()) {
                    windowMagnificationController.setEditMagnifierSizeMode(true);
                    break;
                }
                break;
        }
    }
}
