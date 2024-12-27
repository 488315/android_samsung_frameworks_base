package android.window;

import android.companion.virtualdevice.flags.Flags;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.ArraySet;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public abstract class DisplayWindowPolicyController {
    private final Set<Integer> mSupportedWindowingModes = new ArraySet();
    private int mSystemWindowFlags;
    private int mWindowFlags;

    public abstract boolean canActivityBeLaunched(
            ActivityInfo activityInfo, Intent intent, int i, int i2, boolean z);

    protected abstract boolean canContainActivity(
            ActivityInfo activityInfo, int i, int i2, boolean z);

    public abstract boolean canShowTasksInHostDeviceRecents();

    public abstract ComponentName getCustomHomeComponent();

    public abstract boolean keepActivityOnWindowFlagsChanged(
            ActivityInfo activityInfo, int i, int i2);

    public DisplayWindowPolicyController() {
        synchronized (this.mSupportedWindowingModes) {
            this.mSupportedWindowingModes.add(1);
            if (Flags.virtualDisplayMultiWindowModeSupport()) {
                this.mSupportedWindowingModes.add(6);
            }
        }
    }

    public final boolean isInterestedWindowFlags(int windowFlags, int systemWindowFlags) {
        return ((this.mWindowFlags & windowFlags) == 0
                        && (this.mSystemWindowFlags & systemWindowFlags) == 0)
                ? false
                : true;
    }

    public final void setInterestedWindowFlags(int windowFlags, int systemWindowFlags) {
        this.mWindowFlags = windowFlags;
        this.mSystemWindowFlags = systemWindowFlags;
    }

    public final boolean isWindowingModeSupported(int windowingMode) {
        boolean contains;
        synchronized (this.mSupportedWindowingModes) {
            contains = this.mSupportedWindowingModes.contains(Integer.valueOf(windowingMode));
        }
        return contains;
    }

    public final void setSupportedWindowingModes(Set<Integer> supportedWindowingModes) {
        synchronized (this.mSupportedWindowingModes) {
            this.mSupportedWindowingModes.clear();
            this.mSupportedWindowingModes.addAll(supportedWindowingModes);
        }
    }

    public boolean canContainActivities(List<ActivityInfo> activities, int windowingMode) {
        for (int i = 0; i < activities.size(); i++) {
            if (!canContainActivity(activities.get(i), windowingMode, -1, false)) {
                return false;
            }
        }
        return true;
    }

    public void onTopActivityChanged(ComponentName topActivity, int uid, int userId) {}

    public void onRunningAppsChanged(ArraySet<Integer> runningUids) {}

    public boolean isEnteringPipAllowed(int uid) {
        return isWindowingModeSupported(2);
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.println(prefix + "DisplayWindowPolicyController{" + super.toString() + "}");
        pw.println(prefix + "  mWindowFlags=" + this.mWindowFlags);
        pw.println(prefix + "  mSystemWindowFlags=" + this.mSystemWindowFlags);
    }
}
