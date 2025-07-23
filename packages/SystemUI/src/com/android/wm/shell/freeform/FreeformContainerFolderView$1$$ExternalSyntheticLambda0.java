package com.android.wm.shell.freeform;

import android.graphics.Point;
import com.android.internal.widget.RecyclerView;
import com.android.wm.shell.freeform.FreeformContainerFolderView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerFolderView$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FreeformContainerFolderView$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FreeformContainerFolderView.AnonymousClass1 anonymousClass1 = (FreeformContainerFolderView.AnonymousClass1) this.f$0;
                ((RecyclerView.ViewHolder) this.f$1).itemView.setVisibility(0);
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                if (freeformContainerFolderView.mBlockDataUpdate) {
                    freeformContainerFolderView.mBlockDataUpdate = false;
                    if (freeformContainerFolderView.mItemAddedWhileAnimating) {
                        freeformContainerFolderView.mH.postDelayed(new FreeformContainerFolderView$1$$ExternalSyntheticLambda1(anonymousClass1, 0), 100L);
                        break;
                    }
                }
                break;
            default:
                FreeformContainerFolderView.MultiInstanceItemViewHolder multiInstanceItemViewHolder = (FreeformContainerFolderView.MultiInstanceItemViewHolder) this.f$0;
                Point point = (Point) this.f$1;
                FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                FreeformContainerFolderView.MultiInstancePreviewPopupWindow multiInstancePreviewPopupWindow = freeformContainerFolderView2.mPopupWindow;
                if (multiInstancePreviewPopupWindow != null) {
                    multiInstancePreviewPopupWindow.showAtLocation(freeformContainerFolderView2.mViewController.mContainerView, 0, point.x, point.y);
                    break;
                }
                break;
        }
    }
}
