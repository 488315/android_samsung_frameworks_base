package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityNotificationExAttribute extends SContextAttribute {
    private static final int ACTIVITY_STATUS_MAX = 5;
    private static final String TAG = "SContextActivityNotificationExAttribute";
    private int[] mActivityFilter;
    private int mDuration;

    SContextActivityNotificationExAttribute() {
        this.mActivityFilter = new int[] {4};
        this.mDuration = 30;
        setAttribute();
    }

    public SContextActivityNotificationExAttribute(int[] activityFilter, int duration) {
        this.mActivityFilter = new int[] {4};
        this.mDuration = 30;
        this.mActivityFilter = activityFilter;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mActivityFilter == null) {
            return false;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < this.mActivityFilter.length; i++) {
            if ((this.mActivityFilter[i] < 0 || this.mActivityFilter[i] > 5)
                    && this.mActivityFilter[i] != 30) {
                Log.e(TAG, "The activity status is wrong.");
                return false;
            }
            list.add(Integer.valueOf(this.mActivityFilter[i]));
            for (int j = 0; j < i; j++) {
                if (list.get(i) == list.get(j)) {
                    Log.e(TAG, "This activity status cannot have duplicated status.");
                    return false;
                }
            }
        }
        int i2 = this.mDuration;
        if (i2 < 0) {
            Log.e(TAG, "The duration is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putIntArray("activity_filter", this.mActivityFilter);
        attribute.putInt("duration", this.mDuration);
        super.setAttribute(30, attribute);
    }
}
