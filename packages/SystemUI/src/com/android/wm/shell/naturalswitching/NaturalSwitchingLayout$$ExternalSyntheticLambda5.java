package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.secutil.Slog;
import com.android.wm.shell.naturalswitching.NaturalSwitchingChanger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingLayout f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Rect f$4;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda5(NaturalSwitchingLayout naturalSwitchingLayout, int i, int i2, int i3, Rect rect, int i4) {
        this.$r8$classId = i4;
        this.f$0 = naturalSwitchingLayout;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = rect;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0081  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        int i;
        NaturalSwitchingChanger pipToPipChanger;
        switch (this.$r8$classId) {
            case 0:
                NaturalSwitchingLayout naturalSwitchingLayout = this.f$0;
                naturalSwitchingLayout.mHandler.post(new NaturalSwitchingLayout$$ExternalSyntheticLambda5(naturalSwitchingLayout, this.f$1, this.f$2, this.f$3, this.f$4, 1));
                break;
            default:
                NaturalSwitchingLayout naturalSwitchingLayout2 = this.f$0;
                int i2 = this.f$1;
                int i3 = this.f$2;
                int i4 = this.f$3;
                Rect rect = this.f$4;
                boolean z = NaturalSwitchingLayout.DEBUG_DEV;
                naturalSwitchingLayout2.getClass();
                switch (i2) {
                    case 1:
                        i = 1;
                        break;
                    case 2:
                        i = 2;
                        break;
                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 12:
                    case 13:
                        i = 6;
                        break;
                    case 5:
                        i = 5;
                        break;
                    case 10:
                    case 11:
                    default:
                        i = 0;
                        break;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = naturalSwitchingLayout2.mShellTaskOrganizer.getRunningTaskInfo(naturalSwitchingLayout2.mTaskInfo.taskId);
                int windowingMode = runningTaskInfo != null ? runningTaskInfo.getWindowingMode() : 0;
                if (windowingMode != 6) {
                    if (windowingMode != 5) {
                        if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN && windowingMode == 1 && i == 5) {
                            pipToPipChanger = new NaturalSwitchingChanger.FullToFreeformChanger();
                        } else if (!CoreRune.MW_NATURAL_SWITCHING_PIP || windowingMode != 2) {
                            pipToPipChanger = null;
                        } else if (i == 6) {
                            pipToPipChanger = new NaturalSwitchingChanger.PipToSplitChanger();
                        } else if (i == 2) {
                            pipToPipChanger = new NaturalSwitchingChanger.PipToPipChanger();
                        }
                    } else if (i == 6) {
                        pipToPipChanger = new NaturalSwitchingChanger.FreeformToSplitChanger();
                    } else if (i == 5) {
                        pipToPipChanger = new NaturalSwitchingChanger.FreeformToFreeformChanger();
                    }
                } else if (i == 6) {
                    pipToPipChanger = new NaturalSwitchingChanger.SplitToSplitChanger();
                } else if (i == 5) {
                    pipToPipChanger = new NaturalSwitchingChanger.SplitToFreeformChanger();
                }
                if (pipToPipChanger != null) {
                    Slog.d("NaturalSwitchingLayout", "changeLayout: " + pipToPipChanger);
                    boolean z2 = naturalSwitchingLayout2.mNaturalSwitchingAlgorithm.mNeedToReparentCell;
                    boolean zIsInSubDisplay = MultiWindowUtils.isInSubDisplay(naturalSwitchingLayout2.mContext) ^ true;
                    NaturalSwitchingLayout$$ExternalSyntheticLambda2 naturalSwitchingLayout$$ExternalSyntheticLambda2 = new NaturalSwitchingLayout$$ExternalSyntheticLambda2(naturalSwitchingLayout2, 1);
                    pipToPipChanger.mSplitController = naturalSwitchingLayout2.mSplitScreenController;
                    pipToPipChanger.mSyncQueue = naturalSwitchingLayout2.mSyncQueue;
                    pipToPipChanger.mTask = runningTaskInfo;
                    pipToPipChanger.mDropBounds.set(rect);
                    pipToPipChanger.mIsMainDisplay = zIsInSubDisplay;
                    pipToPipChanger.mToPosition = i3;
                    pipToPipChanger.mNeedToReparentCell = z2;
                    if (CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT && pipToPipChanger.mSplitController.isMultiSplitScreenVisible()) {
                        pipToPipChanger.mCurrentSplitMode = 2;
                    } else {
                        pipToPipChanger.mCurrentSplitMode = pipToPipChanger.mSplitController.isSplitScreenVisible() ? 1 : 0;
                    }
                    pipToPipChanger.mHideLayoutCallback = naturalSwitchingLayout$$ExternalSyntheticLambda2;
                    pipToPipChanger.mRequestedCreateMode = i4;
                    pipToPipChanger.changeLayout();
                    naturalSwitchingLayout2.mLastChanger = pipToPipChanger;
                    break;
                } else {
                    Slog.w("NaturalSwitchingLayout", "changeLayout: invalid changer, from=" + windowingMode + ", to=" + i);
                    naturalSwitchingLayout2.hide(false);
                    break;
                }
                break;
        }
    }
}
