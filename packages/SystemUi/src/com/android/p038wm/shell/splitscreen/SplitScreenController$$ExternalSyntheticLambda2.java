package com.android.p038wm.shell.splitscreen;

import com.android.p038wm.shell.draganddrop.DragAndDropController;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController splitScreenController = (SplitScreenController) this.f$0;
                splitScreenController.getClass();
                ((DragAndDropController) obj).mSplitScreen = splitScreenController;
                break;
            case 1:
                SplitScreenController.ISplitScreenImpl.C41101 c41101 = ((SplitScreenController.ISplitScreenImpl) this.f$0).mSplitScreenListener;
                StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                ArrayList arrayList = (ArrayList) stageCoordinator.mListeners;
                if (!arrayList.contains(c41101)) {
                    arrayList.add(c41101);
                    stageCoordinator.sendStatusToListener(c41101);
                    break;
                }
                break;
            case 2:
                ((ArrayList) ((SplitScreenController) obj).mStageCoordinator.mListeners).remove(((SplitScreenController.ISplitScreenImpl) this.f$0).mSplitScreenListener);
                break;
            default:
                ((SplitScreenController.ISplitScreenImpl) this.f$0).mListener.unregister();
                break;
        }
    }
}
