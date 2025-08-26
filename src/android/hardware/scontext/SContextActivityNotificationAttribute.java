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

    public SContextActivityNotificationAttribute(int[] iArr) {
        this.mActivityFilter = iArr;
        setAttribute();
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003b, code lost:
    
        android.util.Log.e(android.hardware.scontext.SContextActivityNotificationAttribute.TAG, "The activity status is wrong.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0040, code lost:
    
        return false;
     */
    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean checkAttribute() {
        if (this.mActivityFilter == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int[] iArr = this.mActivityFilter;
            if (i >= iArr.length) {
                return true;
            }
            int i2 = iArr[i];
            if (i2 < 0 || i2 > 5) {
                break;
            }
            arrayList.add(Integer.valueOf(i2));
            for (int i3 = 0; i3 < i; i3++) {
                if (arrayList.get(i) == arrayList.get(i3)) {
                    Log.e(TAG, "This activity status cannot have duplicated status.");
                    return false;
                }
            }
            i++;
        }
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putIntArray("activity_filter", this.mActivityFilter);
        super.setAttribute(27, bundle);
    }
}
