package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityNotificationAttribute extends SContextAttribute {
    private static final int ACTIVITY_STATUS_MAX = 5;
    private static final String TAG = "SContextActivityNotificationAttribute";
    private int[] mActivityFilter;

    SContextActivityNotificationAttribute() {
        this.mActivityFilter = new int[]{4};
        setAttribute();
    }

    public SContextActivityNotificationAttribute(int[] activityFilter) {
        this.mActivityFilter = new int[]{4};
        this.mActivityFilter = activityFilter;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mActivityFilter == null) {
            return false;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < this.mActivityFilter.length; i++) {
            if (this.mActivityFilter[i] < 0 || this.mActivityFilter[i] > 5) {
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
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putIntArray("activity_filter", this.mActivityFilter);
        super.setAttribute(27, attribute);
    }
}
