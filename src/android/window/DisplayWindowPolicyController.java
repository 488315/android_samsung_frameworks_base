package android.window;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.util.ArraySet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public abstract class DisplayWindowPolicyController {
    private final Set<Integer> mSupportedWindowingModes;
    private int mSystemWindowFlags;
    private int mWindowFlags;

    public abstract boolean canActivityBeLaunched(ActivityInfo activityInfo, Intent intent, int i, int i2, boolean z, boolean z2, Supplier<IntentSender> supplier);

    protected abstract boolean canContainActivity(ActivityInfo activityInfo, int i, int i2, boolean z);

    public abstract boolean canShowTasksInHostDeviceRecents();

    public abstract ComponentName getCustomHomeComponent();

    public abstract boolean keepActivityOnWindowFlagsChanged(ActivityInfo activityInfo, int i, int i2);

    public void onRunningAppsChanged(ArraySet<Integer> arraySet) {
    }

    public void onTopActivityChanged(ComponentName componentName, int i, int i2) {
    }

    public DisplayWindowPolicyController() {
        ArraySet arraySet = new ArraySet();
        this.mSupportedWindowingModes = arraySet;
        synchronized (arraySet) {
            arraySet.add(1);
            arraySet.add(6);
        }
    }

    public final boolean isInterestedWindowFlags(int i, int i2) {
        return ((i & this.mWindowFlags) == 0 && (this.mSystemWindowFlags & i2) == 0) ? false : true;
    }

    public final void setInterestedWindowFlags(int i, int i2) {
        this.mWindowFlags = i;
        this.mSystemWindowFlags = i2;
    }

    public final boolean isWindowingModeSupported(int i) {
        boolean zContains;
        synchronized (this.mSupportedWindowingModes) {
            zContains = this.mSupportedWindowingModes.contains(Integer.valueOf(i));
        }
        return zContains;
    }

    public final void setSupportedWindowingModes(Set<Integer> set) {
        synchronized (this.mSupportedWindowingModes) {
            this.mSupportedWindowingModes.clear();
            this.mSupportedWindowingModes.addAll(set);
        }
    }

    public boolean canContainActivities(List<ActivityInfo> list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (!canContainActivity(list.get(i2), i, -1, false)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEnteringPipAllowed(int i) {
        return isWindowingModeSupported(2);
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println(str + "DisplayWindowPolicyController{" + super.toString() + "}");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  mWindowFlags=");
        sb.append(this.mWindowFlags);
        printWriter.println(sb.toString());
        printWriter.println(str + "  mSystemWindowFlags=" + this.mSystemWindowFlags);
    }

    public final void addSupportedWindowingMode(int i) {
        synchronized (this.mSupportedWindowingModes) {
            this.mSupportedWindowingModes.add(Integer.valueOf(i));
        }
    }
}
