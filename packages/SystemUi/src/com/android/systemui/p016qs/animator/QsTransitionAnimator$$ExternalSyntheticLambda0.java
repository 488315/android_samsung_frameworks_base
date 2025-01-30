package com.android.systemui.p016qs.animator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QsTransitionAnimator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QsTransitionAnimator f$0;

    public /* synthetic */ QsTransitionAnimator$$ExternalSyntheticLambda0(QsTransitionAnimator qsTransitionAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = qsTransitionAnimator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mContext.getResources();
                break;
            default:
                this.f$0.updateAnimators();
                break;
        }
    }
}
