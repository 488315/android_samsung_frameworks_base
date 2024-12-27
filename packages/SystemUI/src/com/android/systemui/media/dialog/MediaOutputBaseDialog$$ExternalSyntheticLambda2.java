package com.android.systemui.media.dialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MediaOutputBaseDialog$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaOutputBaseDialog f$0;

    public /* synthetic */ MediaOutputBaseDialog$$ExternalSyntheticLambda2(MediaOutputBaseDialog mediaOutputBaseDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaOutputBaseDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MediaOutputBaseDialog mediaOutputBaseDialog = this.f$0;
        switch (i) {
            case 0:
                mediaOutputBaseDialog.refresh();
                break;
            case 1:
                mediaOutputBaseDialog.refresh();
                break;
            default:
                mediaOutputBaseDialog.refresh(true);
                break;
        }
    }
}
