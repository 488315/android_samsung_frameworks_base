package com.android.systemui.qs.tiles.dialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda0(InternetDialog internetDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = internetDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateDialog(true);
                break;
            case 1:
                this.f$0.updateDialog(true);
                break;
            case 2:
                this.f$0.setProgressBarVisible(false);
                break;
            case 3:
                InternetDialog internetDialog = this.f$0;
                internetDialog.mIsSearchingHidden = true;
                internetDialog.mInternetDialogSubTitle.setText(internetDialog.getSubtitleText());
                break;
            case 4:
                this.f$0.updateDialog(true);
                break;
            case 5:
                this.f$0.updateDialog(true);
                break;
            case 6:
                this.f$0.updateDialog(true);
                break;
            case 7:
                InternetDialog internetDialog2 = this.f$0;
                internetDialog2.mHandler.post(new InternetDialog$$ExternalSyntheticLambda9(internetDialog2, internetDialog2.getSignalStrengthDrawable(internetDialog2.mDefaultDataSubId), 1));
                break;
            case 8:
                this.f$0.updateDialog(true);
                break;
            case 9:
                this.f$0.updateDialog(true);
                break;
            case 10:
                this.f$0.updateDialog(true);
                break;
            case 11:
                this.f$0.updateDialog(true);
                break;
            case 12:
                this.f$0.updateDialog(true);
                break;
            default:
                this.f$0.updateDialog(true);
                break;
        }
    }
}
