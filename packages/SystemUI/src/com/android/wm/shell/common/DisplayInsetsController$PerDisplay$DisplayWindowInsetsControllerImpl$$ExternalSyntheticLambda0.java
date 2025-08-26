package com.android.wm.shell.common;

import android.content.ComponentName;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.InsetsState;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public final /* synthetic */ class DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0(DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl, ComponentName componentName, int i) {
        this.f$0 = displayWindowInsetsControllerImpl;
        this.f$1 = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        switch (this.$r8$classId) {
            case 0:
                DisplayInsetsController.PerDisplay perDisplay = DisplayInsetsController.PerDisplay.this;
                CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) DisplayInsetsController.this.mListeners.get(perDisplay.mDisplayId);
                if (copyOnWriteArrayList != null) {
                    Iterator it = copyOnWriteArrayList.iterator();
                    while (it.hasNext()) {
                        ((DisplayInsetsController.OnInsetsChangedListener) it.next()).getClass();
                    }
                    break;
                }
                break;
            default:
                DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl = this.f$0;
                InsetsState insetsState = (InsetsState) this.f$1;
                DisplayInsetsController.PerDisplay perDisplay2 = DisplayInsetsController.PerDisplay.this;
                DisplayInsetsController displayInsetsController = DisplayInsetsController.this;
                SparseArray sparseArray = displayInsetsController.mListeners;
                int i = perDisplay2.mDisplayId;
                CopyOnWriteArrayList copyOnWriteArrayList2 = (CopyOnWriteArrayList) sparseArray.get(i);
                if (copyOnWriteArrayList2 != null || !displayInsetsController.mGlobalListeners.isEmpty()) {
                    DisplayController.DisplayRecord displayRecord = (DisplayController.DisplayRecord) displayInsetsController.mDisplayController.mDisplays.get(i);
                    if (displayRecord != null) {
                        displayRecord.mInsetsState = insetsState;
                        DisplayLayout displayLayout = displayRecord.mDisplayLayout;
                        Resources resources = displayRecord.mContext.getResources();
                        displayLayout.mInsetsState = insetsState;
                        displayLayout.recalcInsets(resources);
                    }
                    Iterator it2 = displayInsetsController.mGlobalListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayInsetsController.OnInsetsChangedListener) it2.next()).insetsChanged(i, insetsState);
                    }
                    if (copyOnWriteArrayList2 != null) {
                        Iterator it3 = copyOnWriteArrayList2.iterator();
                        while (it3.hasNext()) {
                            ((DisplayInsetsController.OnInsetsChangedListener) it3.next()).insetsChanged(i, insetsState);
                        }
                        break;
                    }
                }
                break;
        }
    }

    public /* synthetic */ DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0(DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl, InsetsState insetsState) {
        this.f$0 = displayWindowInsetsControllerImpl;
        this.f$1 = insetsState;
    }
}
