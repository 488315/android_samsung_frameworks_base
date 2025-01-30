package com.android.wm.shell.splitscreen;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenTransitions f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda4(SplitScreenTransitions splitScreenTransitions, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenTransitions;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onFinish(null, null);
                break;
            case 1:
                this.f$0.onFinish(null, null);
                break;
            default:
                this.f$0.onFinish(null, null);
                break;
        }
    }
}
