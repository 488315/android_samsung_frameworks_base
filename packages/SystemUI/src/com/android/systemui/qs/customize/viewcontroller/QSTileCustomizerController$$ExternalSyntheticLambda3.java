package com.android.systemui.qs.customize.viewcontroller;

import android.view.View;
import com.android.systemui.qs.customize.view.QSTileCustomizerBase;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSTileCustomizerController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSTileCustomizerController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        View view;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((QSTileCustomizerController) obj).mIsReadyToClick = true;
                break;
            default:
                QSTileCustomizerController.AnonymousClass6 anonymousClass6 = (QSTileCustomizerController.AnonymousClass6) obj;
                view = ((ViewController) QSTileCustomizerController.this).mView;
                if (!((QSTileCustomizerBase) view).mIsDragging) {
                    QSTileCustomizerController qSTileCustomizerController = QSTileCustomizerController.this;
                    qSTileCustomizerController.animationDrop(qSTileCustomizerController.mLongClickedViewInfo);
                    break;
                }
                break;
        }
    }
}
