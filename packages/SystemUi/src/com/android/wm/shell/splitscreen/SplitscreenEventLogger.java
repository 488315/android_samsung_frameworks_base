package com.android.wm.shell.splitscreen;

import android.util.Slog;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitscreenEventLogger {
    public int mDragEnterPosition;
    public InstanceId mEnterSessionId;
    public InstanceId mLoggerSessionId;
    public int mLastMainStagePosition = -1;
    public int mLastMainStageUid = -1;
    public int mLastSideStagePosition = -1;
    public int mLastSideStageUid = -1;
    public float mLastSplitRatio = -1.0f;
    public int mEnterReason = 0;
    public final InstanceIdSequence mIdSequence = new InstanceIdSequence(Integer.MAX_VALUE);

    public static int getMainStagePositionFromSplitPosition(int i, boolean z) {
        if (i == -1) {
            return 0;
        }
        return z ? i == 0 ? 1 : 2 : i == 0 ? 3 : 4;
    }

    public static int getSideStagePositionFromSplitPosition(int i, boolean z) {
        if (i == -1) {
            return 0;
        }
        return z ? i == 0 ? 1 : 2 : i == 0 ? 3 : 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void logEnter(float f, int i, int i2, int i3, int i4, boolean z) {
        int i5;
        this.mLoggerSessionId = this.mIdSequence.newInstanceId();
        int i6 = this.mEnterReason;
        if (i6 != 1) {
            int i7 = 2;
            if (i6 == 2) {
                int i8 = this.mDragEnterPosition;
                if (!z) {
                    i7 = i8 != 0 ? 5 : 3;
                } else if (i8 != 0) {
                    i7 = 4;
                }
                i5 = i7;
            } else if (i6 != 3) {
                i5 = 0;
            } else {
                i5 = 6;
            }
            updateMainStageState(getMainStagePositionFromSplitPosition(i, z), i2);
            updateSideStageState(getSideStagePositionFromSplitPosition(i3, z), i4);
            if (Float.compare(this.mLastSplitRatio, f) != 0) {
                this.mLastSplitRatio = f;
            }
            int i9 = this.mLastMainStagePosition;
            int i10 = this.mLastMainStageUid;
            int i11 = this.mLastSideStagePosition;
            int i12 = this.mLastSideStageUid;
            InstanceId instanceId = this.mEnterSessionId;
            FrameworkStatsLog.write(388, 1, i5, 0, f, i9, i10, i11, i12, instanceId != null ? instanceId.getId() : 0, this.mLoggerSessionId.getId());
        }
        i5 = 7;
        updateMainStageState(getMainStagePositionFromSplitPosition(i, z), i2);
        updateSideStageState(getSideStagePositionFromSplitPosition(i3, z), i4);
        if (Float.compare(this.mLastSplitRatio, f) != 0) {
        }
        int i92 = this.mLastMainStagePosition;
        int i102 = this.mLastMainStageUid;
        int i112 = this.mLastSideStagePosition;
        int i122 = this.mLastSideStageUid;
        InstanceId instanceId2 = this.mEnterSessionId;
        FrameworkStatsLog.write(388, 1, i5, 0, f, i92, i102, i112, i122, instanceId2 != null ? instanceId2.getId() : 0, this.mLoggerSessionId.getId());
    }

    public final void logExit(int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6;
        int i7;
        if (this.mLoggerSessionId == null) {
            return;
        }
        if ((i2 != -1 && i4 != -1) || (i3 != 0 && i5 != 0)) {
            throw new IllegalArgumentException("Only main or side stage should be set");
        }
        switch (i) {
            case 1:
                i6 = 8;
                i7 = i6;
                break;
            case 2:
                i6 = 7;
                i7 = i6;
                break;
            case 3:
                i6 = 5;
                i7 = i6;
                break;
            case 4:
                i6 = 1;
                i7 = i6;
                break;
            case 5:
                i6 = 2;
                i7 = i6;
                break;
            case 6:
                i6 = 6;
                i7 = i6;
                break;
            case 7:
                i6 = 3;
                i7 = i6;
                break;
            case 8:
                i6 = 4;
                i7 = i6;
                break;
            case 9:
                i6 = 9;
                i7 = i6;
                break;
            case 10:
                i6 = 10;
                i7 = i6;
                break;
            case 11:
                i6 = 11;
                i7 = i6;
                break;
            default:
                Slog.e("SplitscreenEventLogger", "Unknown exit reason: " + i);
                i7 = 0;
                break;
        }
        FrameworkStatsLog.write(388, 2, 0, i7, 0.0f, getMainStagePositionFromSplitPosition(i2, z), i3, getSideStagePositionFromSplitPosition(i4, z), i5, 0, this.mLoggerSessionId.getId());
        this.mLoggerSessionId = null;
        this.mDragEnterPosition = -1;
        this.mEnterSessionId = null;
        this.mLastMainStagePosition = -1;
        this.mLastMainStageUid = -1;
        this.mLastSideStagePosition = -1;
        this.mLastSideStageUid = -1;
        this.mEnterReason = 0;
    }

    public final boolean updateMainStageState(int i, int i2) {
        if (!((this.mLastMainStagePosition == i && this.mLastMainStageUid == i2) ? false : true)) {
            return false;
        }
        this.mLastMainStagePosition = i;
        this.mLastMainStageUid = i2;
        return true;
    }

    public final boolean updateSideStageState(int i, int i2) {
        if (!((this.mLastSideStagePosition == i && this.mLastSideStageUid == i2) ? false : true)) {
            return false;
        }
        this.mLastSideStagePosition = i;
        this.mLastSideStageUid = i2;
        return true;
    }
}
