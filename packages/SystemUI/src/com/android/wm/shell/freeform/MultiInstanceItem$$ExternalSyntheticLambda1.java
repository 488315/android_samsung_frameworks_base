package com.android.wm.shell.freeform;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MultiInstanceItem$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MultiInstanceItem$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Bitmap taskSnapshot;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                FreeformContainerViewController freeformContainerViewController = (FreeformContainerViewController) obj2;
                MultiInstanceItem multiInstanceItem = (MultiInstanceItem) obj;
                if (!multiInstanceItem.isParentMultiInstanceItem() && multiInstanceItem.mSnapshotBitmap == null && multiInstanceItem.mSnapshotBitmap != (taskSnapshot = freeformContainerViewController.mFolderView.getTaskSnapshot(multiInstanceItem.mTaskId))) {
                    multiInstanceItem.mSnapshotBitmap = taskSnapshot;
                    Log.d("FreeformContainer", "[MultiInstanceItem] setSnapshotBitmap: " + multiInstanceItem);
                    break;
                }
                break;
            default:
                ((MultiInstanceItem) obj).setIconView((ImageView) obj2);
                break;
        }
    }
}
