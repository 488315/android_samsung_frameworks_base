package android.hardware.scontext;

import android.p009os.Bundle;
import android.util.Log;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityNotificationAttribute extends SContextAttribute {
  private static final int ACTIVITY_STATUS_MAX = 5;
  private static final String TAG = "SContextActivityNotificationAttribute";
  private int[] mActivityFilter;

  SContextActivityNotificationAttribute() {
    this.mActivityFilter = new int[] {4};
    setAttribute();
  }

  public SContextActivityNotificationAttribute(int[] activityFilter) {
    this.mActivityFilter = new int[] {4};
    this.mActivityFilter = activityFilter;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
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
        if (i2 < 0 || i2 > 5) {
          break;
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
        return true;
      }
    }
    Log.m96e(TAG, "The activity status is wrong.");
    return false;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putIntArray("activity_filter", this.mActivityFilter);
    super.setAttribute(27, attribute);
  }
}
