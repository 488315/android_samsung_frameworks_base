package android.util;

import android.os.SystemClock;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes4.dex */
public class TimingLogger {
    private boolean mDisabled;
    private String mLabel;
    ArrayList<String> mSplitLabels;
    ArrayList<Long> mSplits;
    private String mTag;

    public TimingLogger(String str, String str2) {
        reset(str, str2);
    }

    public void reset(String str, String str2) {
        this.mTag = str;
        this.mLabel = str2;
        reset();
    }

    public void reset() {
        boolean isLoggable = Log.isLoggable(this.mTag, 2);
        this.mDisabled = !isLoggable;
        if (isLoggable) {
            ArrayList<Long> arrayList = this.mSplits;
            if (arrayList == null) {
                this.mSplits = new ArrayList<>();
                this.mSplitLabels = new ArrayList<>();
            } else {
                arrayList.clear();
                this.mSplitLabels.clear();
            }
            addSplit(null);
        }
    }

    public void addSplit(String str) {
        if (this.mDisabled) {
            return;
        }
        this.mSplits.add(Long.valueOf(SystemClock.elapsedRealtime()));
        this.mSplitLabels.add(str);
    }

    public void dumpToLog() {
        if (this.mDisabled) {
            return;
        }
        Log.d(this.mTag, this.mLabel + ": begin");
        long longValue = this.mSplits.get(0).longValue();
        long j = longValue;
        for (int i = 1; i < this.mSplits.size(); i++) {
            j = this.mSplits.get(i).longValue();
            String str = this.mSplitLabels.get(i);
            long longValue2 = this.mSplits.get(i - 1).longValue();
            Log.d(this.mTag, this.mLabel + ":      " + (j - longValue2) + " ms, " + str);
        }
        Log.d(this.mTag, this.mLabel + ": end, " + (j - longValue) + " ms");
    }
}
