package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class SemContextActivityNotificationExAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextActivityNotificationExAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextActivityNotificationExAttribute>() { // from class:
                                                         // com.samsung.android.hardware.context.SemContextActivityNotificationExAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationExAttribute createFromParcel(Parcel in) {
          return new SemContextActivityNotificationExAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationExAttribute[] newArray(int size) {
          return new SemContextActivityNotificationExAttribute[size];
        }
      };
  private static final int STATUS_MAX = 5;
  private static final String TAG = "SemContextActivityNotificationExAttribute";
  private int[] mActivityFilter;
  private int mDuration;

  SemContextActivityNotificationExAttribute() {
    this.mActivityFilter = null;
    this.mDuration = 30;
    this.mActivityFilter = new int[] {4};
    setAttribute();
  }

  SemContextActivityNotificationExAttribute(Parcel src) {
    super(src);
    this.mActivityFilter = null;
    this.mDuration = 30;
  }

  public SemContextActivityNotificationExAttribute(int[] activityFilter, int duration) {
    this.mActivityFilter = null;
    this.mDuration = 30;
    if (activityFilter != null) {
      int[] iArr = new int[activityFilter.length];
      this.mActivityFilter = iArr;
      System.arraycopy(activityFilter, 0, iArr, 0, activityFilter.length);
      this.mDuration = duration;
      setAttribute();
      return;
    }
    Log.m96e(TAG, "The activityFilter is wrong.");
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
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
          if (list.get(i).equals(list.get(j))) {
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
