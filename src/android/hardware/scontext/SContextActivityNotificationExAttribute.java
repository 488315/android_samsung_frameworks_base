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
        this.mActivityFilter = new int[]{4};
        this.mDuration = 30;
        setAttribute();
    }

    public SContextActivityNotificationExAttribute(int[] iArr, int i) {
        this.mActivityFilter = iArr;
        this.mDuration = i;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mActivityFilter == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int[] iArr = this.mActivityFilter;
            if (i < iArr.length) {
                int i2 = iArr[i];
                if ((i2 < 0 || i2 > 5) && i2 != 30) {
                    Log.e(TAG, "The activity status is wrong.");
                    return false;
                }
                arrayList.add(Integer.valueOf(i2));
                for (int i3 = 0; i3 < i; i3++) {
                    if (arrayList.get(i) == arrayList.get(i3)) {
                        Log.e(TAG, "This activity status cannot have duplicated status.");
                        return false;
                    }
                }
                i++;
            } else {
                if (this.mDuration >= 0) {
                    return true;
                }
                Log.e(TAG, "The duration is wrong.");
                return false;
            }
        }
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putIntArray("activity_filter", this.mActivityFilter);
        bundle.putInt("duration", this.mDuration);
        super.setAttribute(30, bundle);
    }
}
