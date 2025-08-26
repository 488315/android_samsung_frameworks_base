package com.android.wm.shell.freeform;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.android.wm.shell.freeform.FreeformContainerFolderView;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 implements View.OnHoverListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FreeformContainerFolderView.FolderViewItemViewHolder f$0;

    public /* synthetic */ FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1(FreeformContainerFolderView.FolderViewItemViewHolder folderViewItemViewHolder, int i) {
        this.$r8$classId = i;
        this.f$0 = folderViewItemViewHolder;
    }

    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int action;
        FreeformContainerItem freeformContainerItem;
        int action2;
        FreeformThumbnailView freeformThumbnailView;
        int i = this.$r8$classId;
        FreeformContainerFolderView.FolderViewItemViewHolder folderViewItemViewHolder = this.f$0;
        switch (i) {
            case 0:
                FreeformContainerFolderView.MultiInstanceItemViewHolder multiInstanceItemViewHolder = (FreeformContainerFolderView.MultiInstanceItemViewHolder) folderViewItemViewHolder;
                if (FreeformContainerFolderView.this.mFreeformThumbnailView != null && (action = motionEvent.getAction()) != 7 && !multiInstanceItemViewHolder.mHandler.hasMessages(2)) {
                    FreeformContainerFolderView.this.mH.removeCallbacks(multiInstanceItemViewHolder.mDismissMultiInstancePreviewByHoverExit);
                    multiInstanceItemViewHolder.mHandler.removeMessages(1);
                    if (action != 9) {
                        FreeformContainerFolderView.this.mH.post(multiInstanceItemViewHolder.mDismissMultiInstancePreviewByHoverExit);
                        break;
                    } else if (!multiInstanceItemViewHolder.mHandler.hasMessages(1)) {
                        FreeformContainerFolderView.MultiInstancePreviewPopupWindow multiInstancePreviewPopupWindow = FreeformContainerFolderView.this.mPopupWindow;
                        if (multiInstancePreviewPopupWindow != null) {
                            FreeformContainerItem freeformContainerItem2 = multiInstanceItemViewHolder.mItem;
                            if (!multiInstancePreviewPopupWindow.mOpenedByHoverAction && (freeformContainerItem = multiInstancePreviewPopupWindow.mItem) != null && freeformContainerItem == freeformContainerItem2) {
                                Log.d("FreeformContainer", "[FolderView] skip create preview by hover, reason=click_mode");
                                break;
                            }
                        }
                        multiInstanceItemViewHolder.mHandler.sendMessageDelayed(multiInstanceItemViewHolder.mHandler.obtainMessage(1), 400L);
                        break;
                    }
                }
                break;
            default:
                FreeformContainerFolderView.SingleInstanceItemViewHolder singleInstanceItemViewHolder = (FreeformContainerFolderView.SingleInstanceItemViewHolder) folderViewItemViewHolder;
                if (FreeformContainerFolderView.this.mFreeformThumbnailView != null && (action2 = motionEvent.getAction()) != 7) {
                    FreeformContainerFolderView.this.mH.removeCallbacks(singleInstanceItemViewHolder.mDismissPreview);
                    FreeformContainerFolderView.this.mH.removeCallbacks(singleInstanceItemViewHolder.mShowPreview);
                    if (action2 != 9) {
                        FreeformContainerFolderView.this.mH.post(singleInstanceItemViewHolder.mDismissPreview);
                        break;
                    } else {
                        FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                        freeformContainerFolderView.getClass();
                        if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW && (freeformThumbnailView = freeformContainerFolderView.mFreeformThumbnailView) != null && !freeformThumbnailView.isAttachedToWindow()) {
                            WindowManager windowManager = freeformContainerFolderView.mWindowManager;
                            FreeformThumbnailView freeformThumbnailView2 = freeformContainerFolderView.mFreeformThumbnailView;
                            freeformThumbnailView2.getClass();
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2604, 16778040, -3);
                            layoutParams.setTitle("freeform-thumbnail");
                            layoutParams.privateFlags |= 536870992;
                            layoutParams.setFitInsetsTypes(0);
                            layoutParams.layoutInDisplayCutoutMode = 1;
                            layoutParams.gravity = 8388659;
                            layoutParams.x = 0;
                            layoutParams.y = 0;
                            windowManager.addView(freeformThumbnailView2, layoutParams);
                        }
                        FreeformContainerFolderView.this.mH.postDelayed(singleInstanceItemViewHolder.mShowPreview, 400L);
                        break;
                    }
                }
                break;
        }
        return false;
    }
}
