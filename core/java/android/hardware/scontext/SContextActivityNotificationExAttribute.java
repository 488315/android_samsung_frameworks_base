package android.hardware.scontext;

import android.p009os.Bundle;
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
        this.mActivityFilter = new int[]{4};
        this.mDuration = 30;
        setAttribute();
    }

    public SContextActivityNotificationExAttribute(int[] activityFilter, int duration) {
        this.mActivityFilter = new int[]{4};
        this.mDuration = 30;
        this.mActivityFilter = activityFilter;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mActivityFilter == null) {
            return false;
        }
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        while (true) {
            int[] iArr = this.mActivityFilter;
            if (i < iArr.length) {
                int i2 = iArr[i];
                if ((i2 < 0 || i2 > 5) && i2 != 30) {
                    Log.m96e(TAG, "The activity status is wrong.");
                    return false;
                }
                list.add(Integer.valueOf(i2));
                for (int j = 0; j < i; j++) {
                    if (list.get(i) == list.get(j)) {
                        Log.m96e(TAG, "This activity status cannot have duplicated status.");
                        return false;
                    }
                }
                i++;
            } else {
                int i3 = this.mDuration;
                if (i3 < 0) {
                    Log.m96e(TAG, "The duration is wrong.");
                    return false;
                }
                return true;
            }
        }
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putIntArray("activity_filter", this.mActivityFilter);
        attribute.putInt("duration", this.mDuration);
        super.setAttribute(30, attribute);
    }
}
