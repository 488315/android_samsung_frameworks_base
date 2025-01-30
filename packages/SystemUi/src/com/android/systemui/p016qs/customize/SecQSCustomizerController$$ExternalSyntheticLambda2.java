package com.android.systemui.p016qs.customize;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSCustomizerController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecQSCustomizerController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((SecQSCustomizerController) this.f$0).mDoneCallBack.this$0.finish();
                break;
            case 1:
                ((SecQSCustomizerController) this.f$0).mIsReadyToClick = true;
                break;
            case 2:
                ((SecQSCustomizerController) this.f$0).mIsReadyToMove = true;
                break;
            default:
                SecQSCustomizerController secQSCustomizerController = SecQSCustomizerController.this;
                if (!((SecQSCustomizerBase) secQSCustomizerController.mView).mIsDragging) {
                    secQSCustomizerController.animationDrop(secQSCustomizerController.mLongClickedViewInfo);
                    break;
                }
                break;
        }
    }
}
