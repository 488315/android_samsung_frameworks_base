package com.android.p038wm.shell.splitscreen;

import android.util.ArrayMap;
import com.android.p038wm.shell.splitscreen.SplitScreen;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3(SplitScreenController.SplitScreenImpl splitScreenImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
                SplitScreen.SplitScreenListener splitScreenListener = (SplitScreen.SplitScreenListener) this.f$1;
                ArrayMap arrayMap = splitScreenImpl.mExecutors;
                arrayMap.remove(splitScreenListener);
                if (arrayMap.size() == 0) {
                    ((ArrayList) SplitScreenController.this.mStageCoordinator.mListeners).remove(splitScreenImpl.mListener);
                    break;
                }
                break;
            case 1:
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = this.f$0;
                SplitScreenController.this.mStageCoordinator.sendStatusToListener((SplitScreen.SplitScreenListener) this.f$1);
                break;
            default:
                SplitScreenController.this.mIsKeyguardOccludedAndShowingSupplier = (BooleanSupplier) this.f$1;
                break;
        }
    }
}
