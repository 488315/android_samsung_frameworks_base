package com.android.systemui.media.dialog;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputBaseDialog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaOutputBaseDialog f$0;

    public /* synthetic */ MediaOutputBaseDialog$$ExternalSyntheticLambda0(MediaOutputBaseDialog mediaOutputBaseDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaOutputBaseDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MediaOutputBaseDialog mediaOutputBaseDialog = this.f$0;
        switch (i) {
            case 0:
                int i2 = MediaOutputBaseDialog.$r8$clinit;
                mediaOutputBaseDialog.refresh(true);
                break;
            case 1:
                int i3 = MediaOutputBaseDialog.$r8$clinit;
                mediaOutputBaseDialog.refresh();
                break;
            default:
                int i4 = MediaOutputBaseDialog.$r8$clinit;
                mediaOutputBaseDialog.refresh();
                break;
        }
    }
}
