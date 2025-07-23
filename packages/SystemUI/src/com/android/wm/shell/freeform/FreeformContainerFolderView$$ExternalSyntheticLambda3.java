package com.android.wm.shell.freeform;

import android.view.View;
import com.android.wm.shell.freeform.FreeformContainerFolderView;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerFolderView$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FreeformContainerFolderView$$ExternalSyntheticLambda3(FreeformContainerFolderView.MultiInstancePreviewAdapter multiInstancePreviewAdapter, int i) {
        this.$r8$classId = 2;
        this.f$0 = multiInstancePreviewAdapter;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((FreeformContainerFolderView) obj).mViewController.mItemController.throwAwayAllItems();
                break;
            case 1:
                FreeformContainerViewController freeformContainerViewController = ((FreeformContainerFolderView) obj).mViewController;
                freeformContainerViewController.mFolderView.collapse(false);
                FreeformContainerItemController freeformContainerItemController = freeformContainerViewController.mItemController;
                freeformContainerItemController.getClass();
                ArrayList arrayList = new ArrayList(freeformContainerItemController.mItemList);
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    FreeformContainerItem freeformContainerItem = (FreeformContainerItem) arrayList.get(size);
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                        freeformContainerItem.getClass();
                        if (freeformContainerItem instanceof MultiInstanceItem) {
                            ArrayList arrayList2 = new ArrayList(freeformContainerItem.getItemList());
                            int size2 = arrayList2.size();
                            int i2 = 0;
                            while (i2 < size2) {
                                Object obj2 = arrayList2.get(i2);
                                i2++;
                                FreeformContainerItem freeformContainerItem2 = (FreeformContainerItem) obj2;
                                freeformContainerItemController.removeItem(freeformContainerItem2);
                                freeformContainerItem2.launch();
                            }
                        }
                    }
                    freeformContainerItemController.removeItem(freeformContainerItem);
                    freeformContainerItem.launch();
                }
                break;
            default:
                int i3 = FreeformContainerFolderView.MultiInstancePreviewAdapter.$r8$clinit;
                ((FreeformContainerFolderView.MultiInstancePreviewAdapter) obj).getClass();
                break;
        }
    }

    public /* synthetic */ FreeformContainerFolderView$$ExternalSyntheticLambda3(FreeformContainerFolderView freeformContainerFolderView, int i) {
        this.$r8$classId = i;
        this.f$0 = freeformContainerFolderView;
    }
}
