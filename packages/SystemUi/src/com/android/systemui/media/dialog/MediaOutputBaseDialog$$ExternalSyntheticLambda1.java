package com.android.systemui.media.dialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaOutputBaseDialog$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaOutputBaseDialog f$0;

    public /* synthetic */ MediaOutputBaseDialog$$ExternalSyntheticLambda1(MediaOutputBaseDialog mediaOutputBaseDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaOutputBaseDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.refresh();
                break;
            case 1:
                this.f$0.refresh(true);
                break;
            case 2:
                this.f$0.refresh();
                break;
            default:
                this.f$0.refresh();
                break;
        }
    }
}
