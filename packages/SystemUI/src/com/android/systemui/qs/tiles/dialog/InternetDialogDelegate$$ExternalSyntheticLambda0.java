package com.android.systemui.qs.tiles.dialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class InternetDialogDelegate$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InternetDialogDelegate f$0;

    public /* synthetic */ InternetDialogDelegate$$ExternalSyntheticLambda0(InternetDialogDelegate internetDialogDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = internetDialogDelegate;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        InternetDialogDelegate internetDialogDelegate = this.f$0;
        switch (i) {
            case 0:
                internetDialogDelegate.updateDialog(true);
                break;
            case 1:
                internetDialogDelegate.updateDialog(true);
                break;
            case 2:
                internetDialogDelegate.updateDialog(true);
                break;
            case 3:
                internetDialogDelegate.updateDialog(true);
                break;
            case 4:
                internetDialogDelegate.updateDialog(true);
                break;
            case 5:
                internetDialogDelegate.mHandler.post(new InternetDialogDelegate$$ExternalSyntheticLambda27(internetDialogDelegate, internetDialogDelegate.getSignalStrengthDrawable(internetDialogDelegate.mDefaultDataSubId), 0));
                break;
            case 6:
                internetDialogDelegate.updateDialog(true);
                break;
            case 7:
                internetDialogDelegate.updateDialog(true);
                break;
            case 8:
                internetDialogDelegate.updateDialog(true);
                break;
            case 9:
                internetDialogDelegate.updateDialog(true);
                break;
            case 10:
                internetDialogDelegate.updateDialog(true);
                break;
            default:
                internetDialogDelegate.updateDialog(true);
                break;
        }
    }
}
