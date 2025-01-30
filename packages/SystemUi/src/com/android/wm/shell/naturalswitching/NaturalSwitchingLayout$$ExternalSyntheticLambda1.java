package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.secutil.Slog;
import com.android.wm.shell.naturalswitching.NaturalSwitchingChanger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingLayout f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Rect f$4;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda1(NaturalSwitchingLayout naturalSwitchingLayout, int i, int i2, int i3, Rect rect, int i4) {
        this.$r8$classId = i4;
        this.f$0 = naturalSwitchingLayout;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
        this.f$4 = rect;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00b7  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        int i;
        NaturalSwitchingChanger naturalSwitchingChanger;
        switch (this.$r8$classId) {
            case 0:
                NaturalSwitchingLayout naturalSwitchingLayout = this.f$0;
                naturalSwitchingLayout.mHandler.post(new NaturalSwitchingLayout$$ExternalSyntheticLambda1(naturalSwitchingLayout, this.f$1, this.f$2, this.f$3, this.f$4, 1));
                break;
            default:
                NaturalSwitchingLayout naturalSwitchingLayout2 = this.f$0;
                int i2 = this.f$1;
                int i3 = this.f$2;
                int i4 = this.f$3;
                Rect rect = this.f$4;
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
                if (windowingMode == 6) {
                    if (i == 6) {
                        naturalSwitchingChanger = new NaturalSwitchingChanger.SplitToSplitChanger();
                    } else if (i == 5) {
                        naturalSwitchingChanger = new NaturalSwitchingChanger.SplitToFreeformChanger();
                    }
                    if (naturalSwitchingChanger != null) {
                        Slog.d("NaturalSwitchingLayout", "changeLayout: " + naturalSwitchingChanger);
                        boolean z = naturalSwitchingLayout2.mNaturalSwitchingAlgorithm.mNeedToReparentCell;
                        boolean isInSubDisplay = MultiWindowUtils.isInSubDisplay(naturalSwitchingLayout2.mContext) ^ true;
                        NaturalSwitchingLayout$$ExternalSyntheticLambda3 naturalSwitchingLayout$$ExternalSyntheticLambda3 = new NaturalSwitchingLayout$$ExternalSyntheticLambda3(naturalSwitchingLayout2, 1);
                        naturalSwitchingChanger.mSplitController = naturalSwitchingLayout2.mSplitScreenController;
                        naturalSwitchingChanger.mSyncQueue = naturalSwitchingLayout2.mSyncQueue;
                        naturalSwitchingChanger.mTask = runningTaskInfo;
                        naturalSwitchingChanger.mDropBounds.set(rect);
                        naturalSwitchingChanger.mIsMainDisplay = isInSubDisplay;
                        naturalSwitchingChanger.mToPosition = i3;
                        naturalSwitchingChanger.mNeedToReparentCell = z;
                        if (CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT && naturalSwitchingChanger.mSplitController.isMultiSplitScreenVisible()) {
                            naturalSwitchingChanger.mCurrentSplitMode = 2;
                        } else {
                            naturalSwitchingChanger.mCurrentSplitMode = naturalSwitchingChanger.mSplitController.isSplitScreenVisible() ? 1 : 0;
                        }
                        naturalSwitchingChanger.mHideLayoutCallback = naturalSwitchingLayout$$ExternalSyntheticLambda3;
                        naturalSwitchingChanger.mRequestedCreateMode = i4;
                        naturalSwitchingChanger.changeLayout();
                        naturalSwitchingLayout2.mLastChanger = naturalSwitchingChanger;
                        break;
                    } else {
                        Slog.w("NaturalSwitchingLayout", "changeLayout: invalid changer, from=" + windowingMode + ", to=" + i);
                        naturalSwitchingLayout2.hide(false);
                        break;
                    }
                }
                if (windowingMode == 5) {
                    if (i == 6) {
                        naturalSwitchingChanger = new NaturalSwitchingChanger.FreeformToSplitChanger();
                    } else if (i == 5) {
                        naturalSwitchingChanger = new NaturalSwitchingChanger.FreeformToFreeformChanger();
                    }
                    if (naturalSwitchingChanger != null) {
                    }
                }
                if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN && windowingMode == 1 && i == 5) {
                    naturalSwitchingChanger = new NaturalSwitchingChanger.FullToFreeformChanger();
                } else {
                    if (CoreRune.MW_NATURAL_SWITCHING_PIP && windowingMode == 2) {
                        if (i == 6) {
                            naturalSwitchingChanger = new NaturalSwitchingChanger.PipToSplitChanger();
                        } else if (i == 2) {
                            naturalSwitchingChanger = new NaturalSwitchingChanger.PipToPipChanger();
                        }
                    }
                    naturalSwitchingChanger = null;
                }
                if (naturalSwitchingChanger != null) {
                }
                break;
        }
    }
}
