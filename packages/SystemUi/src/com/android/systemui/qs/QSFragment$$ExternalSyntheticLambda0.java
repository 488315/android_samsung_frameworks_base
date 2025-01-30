package com.android.systemui.qs;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragment$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSFragment f$0;

    public /* synthetic */ QSFragment$$ExternalSyntheticLambda0(QSFragment qSFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = qSFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateQsState();
                break;
            case 1:
                this.f$0.mContainer.requestLayout();
                break;
            default:
                this.f$0.mLastQSExpansion = -1.0f;
                break;
        }
    }
}
