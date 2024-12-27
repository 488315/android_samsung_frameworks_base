package com.android.server.wm;

import android.util.Slog;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (!activityRecord.isVisibleRequested()) {
                    DisplayContent displayContent = activityRecord.mDisplayContent;
                    if (displayContent != null && !displayContent.mUnknownAppVisibilityController.mUnknownApps.isEmpty()) {
                        Slog.i("BLASTSyncEngine", "  UnknownAppVisibility: " + activityRecord.mDisplayContent.mUnknownAppVisibilityController.getDebugMessage());
                        break;
                    }
                } else {
                    if (activityRecord.isRelaunching()) {
                        Slog.i("BLASTSyncEngine", "  " + activityRecord + " is relaunching");
                    }
                    activityRecord.forAllWindows((Consumer) new BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda3(1), true);
                    break;
                }
                break;
            default:
                WindowState windowState = (WindowState) obj;
                Slog.i("BLASTSyncEngine", "  " + windowState + " " + windowState.mWinAnimator.drawStateToString());
                break;
        }
    }
}
