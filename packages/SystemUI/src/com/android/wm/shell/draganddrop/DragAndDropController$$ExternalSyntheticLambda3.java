package com.android.wm.shell.draganddrop;

import android.content.res.Configuration;
import com.android.wm.shell.draganddrop.DragAndDropController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DragAndDropController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DragAndDropController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DragAndDropController$$ExternalSyntheticLambda3(DragAndDropController dragAndDropController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = dragAndDropController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DragAndDropController.PerDisplay perDisplay = (DragAndDropController.PerDisplay) this.f$1;
                int i = DragAndDropController.$r8$clinit;
                if (perDisplay.activeDragCount == 0) {
                    DragAndDropController.setDropTargetWindowVisibility(perDisplay, 4);
                    break;
                }
                break;
            case 1:
                DragAndDropController dragAndDropController = this.f$0;
                DragAndDropController.PerDisplay perDisplay2 = (DragAndDropController.PerDisplay) this.f$1;
                int i2 = DragAndDropController.$r8$clinit;
                dragAndDropController.getClass();
                DragAndDropController.setDropTargetWindowVisibility(perDisplay2, 4);
                break;
            case 2:
                DragAndDropController dragAndDropController2 = this.f$0;
                DragAndDropController.PerDisplay perDisplay3 = (DragAndDropController.PerDisplay) this.f$1;
                int i3 = DragAndDropController.$r8$clinit;
                dragAndDropController2.getClass();
                if (perDisplay3.activeDragCount == 0) {
                    DragAndDropController.setDropTargetWindowVisibility(perDisplay3, 4);
                    break;
                }
                break;
            default:
                DragAndDropController dragAndDropController3 = this.f$0;
                Configuration configuration = (Configuration) this.f$1;
                for (int i4 = 0; i4 < dragAndDropController3.mDisplayDropTargets.size(); i4++) {
                    ((DropTargetLayout) ((DragAndDropController.PerDisplay) dragAndDropController3.mDisplayDropTargets.get(i4)).dragLayout).onConfigChanged(configuration);
                }
                break;
        }
    }
}
