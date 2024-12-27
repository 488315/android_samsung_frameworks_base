package com.android.server.wm;

import android.view.SurfaceControl;
import com.android.server.wm.AppCompatLetterboxPolicy;
import com.android.server.wm.Letterbox;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class CapturedLetterbox$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ SurfaceControl.Transaction f$0;

    public /* synthetic */ CapturedLetterbox$$ExternalSyntheticLambda1(SurfaceControl.Transaction transaction) {
        this.f$0 = transaction;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowState findMainWindow;
        SurfaceControl.Transaction transaction = this.f$0;
        AppCompatLetterboxPolicy appCompatLetterboxPolicy = ((ActivityRecord) obj).mAppCompatController.mAppCompatLetterboxPolicy;
        AppCompatLetterboxPolicy.LetterboxPolicyState letterboxPolicyState = appCompatLetterboxPolicy.mLetterboxPolicyState;
        if (letterboxPolicyState.isRunning()) {
            Letterbox letterbox = letterboxPolicyState.mLetterbox;
            if (letterbox.useFullWindowSurface()) {
                letterbox.mFullWindowSurface.mSurfaceFrameRelative.setEmpty();
            } else {
                for (Letterbox.LetterboxSurface letterboxSurface : letterbox.mSurfaces) {
                    letterboxSurface.mSurfaceFrameRelative.setEmpty();
                }
            }
        }
        ActivityRecord activityRecord = appCompatLetterboxPolicy.mActivityRecord;
        if (!activityRecord.isVisibleRequested() || (findMainWindow = activityRecord.findMainWindow(true)) == null) {
            return;
        }
        appCompatLetterboxPolicy.mLetterboxPolicyState.updateLetterboxSurfaceIfNeeded(findMainWindow, transaction, activityRecord.getPendingTransaction());
    }
}
