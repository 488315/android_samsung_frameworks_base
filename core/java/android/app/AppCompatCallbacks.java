package android.app;

import android.compat.Compatibility;
import android.os.Process;
import com.android.internal.compat.ChangeReporter;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class AppCompatCallbacks implements Compatibility.BehaviorChangeDelegate {
    private final ChangeReporter mChangeReporter;
    private final long[] mDisabledChanges;
    private final long[] mLoggableChanges;

    public static void install(long[] disabledChanges, long[] loggableChanges) {
        Compatibility.setBehaviorChangeDelegate(new AppCompatCallbacks(disabledChanges, loggableChanges));
    }

    private AppCompatCallbacks(long[] disabledChanges, long[] loggableChanges) {
        this.mDisabledChanges = Arrays.copyOf(disabledChanges, disabledChanges.length);
        this.mLoggableChanges = Arrays.copyOf(loggableChanges, loggableChanges.length);
        Arrays.sort(this.mDisabledChanges);
        Arrays.sort(this.mLoggableChanges);
        this.mChangeReporter = new ChangeReporter(1);
    }

    private boolean changeIdInChangeList(long[] list, long changeId) {
        return Arrays.binarySearch(list, changeId) >= 0;
    }

    public void onChangeReported(long changeId) {
        boolean isLoggable = changeIdInChangeList(this.mLoggableChanges, changeId);
        reportChange(changeId, 3, isLoggable);
    }

    public boolean isChangeEnabled(long changeId) {
        boolean isEnabled = !changeIdInChangeList(this.mDisabledChanges, changeId);
        boolean isLoggable = changeIdInChangeList(this.mLoggableChanges, changeId);
        if (isEnabled) {
            reportChange(changeId, 1, isLoggable);
            return true;
        }
        reportChange(changeId, 2, isLoggable);
        return false;
    }

    private void reportChange(long changeId, int state, boolean isLoggable) {
        int uid = Process.myUid();
        this.mChangeReporter.reportChange(uid, changeId, state, isLoggable);
    }
}
