package com.android.wm.shell.splitscreen;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitBackgroundController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitBackgroundController f$0;

    public /* synthetic */ SplitBackgroundController$$ExternalSyntheticLambda0(SplitBackgroundController splitBackgroundController, int i) {
        this.$r8$classId = i;
        this.f$0 = splitBackgroundController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitBackgroundController splitBackgroundController = this.f$0;
                if (splitBackgroundController.canShow()) {
                    splitBackgroundController.updateBackgroundVisibility(true, true);
                    break;
                }
                break;
            case 1:
                this.f$0.updateBackgroundLayerColor(true);
                break;
            default:
                SplitBackgroundController splitBackgroundController2 = this.f$0;
                splitBackgroundController2.updateBackgroundLayer(splitBackgroundController2.mVisible);
                break;
        }
    }
}
