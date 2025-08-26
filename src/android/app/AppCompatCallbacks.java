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

    public static void install(long[] jArr, long[] jArr2) {
        Compatibility.setBehaviorChangeDelegate(new AppCompatCallbacks(jArr, jArr2));
    }

    private AppCompatCallbacks(long[] jArr, long[] jArr2) {
        long[] jArrCopyOf = Arrays.copyOf(jArr, jArr.length);
        this.mDisabledChanges = jArrCopyOf;
        long[] jArrCopyOf2 = Arrays.copyOf(jArr2, jArr2.length);
        this.mLoggableChanges = jArrCopyOf2;
        Arrays.sort(jArrCopyOf);
        Arrays.sort(jArrCopyOf2);
        this.mChangeReporter = new ChangeReporter(1);
    }

    private boolean changeIdInChangeList(long[] jArr, long j) {
        return Arrays.binarySearch(jArr, j) >= 0;
    }

    public void onChangeReported(long j) {
        reportChange(j, 3, changeIdInChangeList(this.mLoggableChanges, j));
    }

    public boolean isChangeEnabled(long j) {
        boolean zChangeIdInChangeList = changeIdInChangeList(this.mDisabledChanges, j);
        boolean zChangeIdInChangeList2 = changeIdInChangeList(this.mLoggableChanges, j);
        if (!zChangeIdInChangeList) {
            reportChange(j, 1, zChangeIdInChangeList2);
            return true;
        }
        reportChange(j, 2, zChangeIdInChangeList2);
        return false;
    }

    private void reportChange(long j, int i, boolean z) {
        this.mChangeReporter.reportChange(Process.myUid(), j, i, false, z);
    }
}
