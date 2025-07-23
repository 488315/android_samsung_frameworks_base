package com.android.systemui.qs;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
                qSImpl.updateQsState$1();
                break;
        }
    }
}
