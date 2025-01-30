package com.android.server.wm;

import android.util.ArrayMap;
import android.util.Slog;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class UnknownAppVisibilityController {
    public final DisplayContent mDisplayContent;
    public final WindowManagerService mService;
    public final ArrayMap mUnknownApps = new ArrayMap();

    public UnknownAppVisibilityController(WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
    }

    public boolean allResolved() {
        return this.mUnknownApps.isEmpty();
    }

    public boolean isVisibilityUnknown(ActivityRecord activityRecord) {
        return this.mUnknownApps.containsKey(activityRecord);
    }

    public void clear() {
        this.mUnknownApps.clear();
    }

    public String getDebugMessage() {
        StringBuilder sb = new StringBuilder();
        for (int size = this.mUnknownApps.size() - 1; size >= 0; size--) {
            sb.append("app=");
            sb.append(this.mUnknownApps.keyAt(size));
            sb.append(" state=");
            sb.append(this.mUnknownApps.valueAt(size));
            if (size != 0) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public void appRemovedOrHidden(ActivityRecord activityRecord) {
        this.mUnknownApps.remove(activityRecord);
    }

    public void notifyLaunched(ActivityRecord activityRecord) {
        if (!activityRecord.mLaunchTaskBehind) {
            this.mUnknownApps.put(activityRecord, 1);
        } else {
            this.mUnknownApps.put(activityRecord, 2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("App launched activity=");
        sb.append(activityRecord);
        sb.append(", state=");
        sb.append(activityRecord.mLaunchTaskBehind ? 2 : 1);
        Slog.d(StartingSurfaceController.TAG, sb.toString());
    }

    public void notifyAppResumedFinished(ActivityRecord activityRecord) {
        if (this.mUnknownApps.containsKey(activityRecord) && ((Integer) this.mUnknownApps.get(activityRecord)).intValue() == 1) {
            this.mUnknownApps.put(activityRecord, 2);
            return;
        }
        if (this.mUnknownApps.containsKey(activityRecord)) {
            Slog.d(StartingSurfaceController.TAG, "App relayouted appWindow=" + activityRecord + ", state=" + this.mUnknownApps.get(activityRecord));
        }
    }

    public void notifyRelayouted(ActivityRecord activityRecord, int i) {
        if (this.mUnknownApps.containsKey(activityRecord)) {
            int intValue = ((Integer) this.mUnknownApps.get(activityRecord)).intValue();
            if (intValue == 2 || activityRecord.mStartingWindow != null || (i != 3 && intValue == 1)) {
                this.mUnknownApps.put(activityRecord, 3);
                this.mDisplayContent.notifyKeyguardFlagsChanged();
                notifyVisibilitiesUpdated();
            } else {
                Slog.d(StartingSurfaceController.TAG, "App relayouted appWindow=" + activityRecord + ", state=" + intValue);
            }
        }
    }

    public final void notifyVisibilitiesUpdated() {
        boolean z = false;
        for (int size = this.mUnknownApps.size() - 1; size >= 0; size--) {
            if (((Integer) this.mUnknownApps.valueAt(size)).intValue() == 3) {
                this.mUnknownApps.removeAt(size);
                z = true;
            }
        }
        if (z) {
            this.mService.mWindowPlacerLocked.performSurfacePlacement();
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        if (this.mUnknownApps.isEmpty()) {
            return;
        }
        printWriter.println(str + "Unknown visibilities:");
        for (int size = this.mUnknownApps.size() + (-1); size >= 0; size += -1) {
            printWriter.println(str + "  app=" + this.mUnknownApps.keyAt(size) + " state=" + this.mUnknownApps.valueAt(size));
        }
    }
}
