package com.android.server.wm;


public final /* synthetic */ class DisplayRotation$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayRotation$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((DisplayRotation) obj).updateRotationAndSendNewConfigIfChanged();
                return;
            case 1:
                DisplayRotation.FoldController foldController =
                        (DisplayRotation.FoldController) obj;
                synchronized (foldController.this$0.mLock) {
                    foldController.updateSensorRotationBlockIfNeeded();
                }
                return;
            default:
                DisplayRotation.FoldController foldController2 =
                        (DisplayRotation.FoldController) obj;
                synchronized (foldController2.this$0.mLock) {
                    foldController2.updateSensorRotationBlockIfNeeded();
                }
                return;
        }
    }
}
