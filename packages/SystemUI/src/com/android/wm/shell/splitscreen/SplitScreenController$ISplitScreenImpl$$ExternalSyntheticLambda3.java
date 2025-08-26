package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController.ISplitScreenImpl f$0;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(SplitScreenController.ISplitScreenImpl iSplitScreenImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = iSplitScreenImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SplitScreenController.ISplitScreenImpl iSplitScreenImpl = this.f$0;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        switch (i) {
            case 0:
                splitScreenController.mStageCoordinator.registerSplitScreenListener(iSplitScreenImpl.mSplitScreenListener);
                break;
            case 1:
                ((ArrayList) splitScreenController.mStageCoordinator.mListeners).remove(iSplitScreenImpl.mSplitScreenListener);
                break;
            case 2:
                splitScreenController.mStageCoordinator.mSelectListeners.add(iSplitScreenImpl.mSplitSelectListener);
                break;
            case 3:
                splitScreenController.mStageCoordinator.mSelectListeners.remove(iSplitScreenImpl.mSplitSelectListener);
                break;
            case 4:
                iSplitScreenImpl.mListener.unregister();
                break;
            default:
                iSplitScreenImpl.mSelectListener.unregister();
                break;
        }
    }
}
