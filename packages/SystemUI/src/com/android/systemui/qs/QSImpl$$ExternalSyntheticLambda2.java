package com.android.systemui.qs;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSImpl f$0;

    public /* synthetic */ QSImpl$$ExternalSyntheticLambda2(QSImpl qSImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = qSImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        QSImpl qSImpl = this.f$0;
        switch (i) {
            case 0:
                qSImpl.mContainer.requestLayout();
                break;
            case 1:
                qSImpl.mLastQSExpansion = -1.0f;
                break;
            default:
                qSImpl.updateQsState();
                break;
        }
    }
}
