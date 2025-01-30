package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class SemContextActivityNotificationForLocationAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextActivityNotificationForLocationAttribute>
      CREATOR =
          new Parcelable.Creator<
              SemContextActivityNotificationForLocationAttribute>() { // from class:
                                                                      // com.samsung.android.hardware.context.SemContextActivityNotificationForLocationAttribute.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SemContextActivityNotificationForLocationAttribute createFromParcel(Parcel in) {
              return new SemContextActivityNotificationForLocationAttribute(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SemContextActivityNotificationForLocationAttribute[] newArray(int size) {
              return new SemContextActivityNotificationForLocationAttribute[size];
            }
          };
  private static final int STATUS_MAX = 5;
  private static final String TAG = "SemContextActivityNotificationForLocationAttribute";
  private int[] mActivityFilter;
  private int mDuration;

  SemContextActivityNotificationForLocationAttribute() {
    this.mActivityFilter = new int[] {4};
    this.mDuration = 30;
    setAttribute();
  }

  SemContextActivityNotificationForLocationAttribute(Parcel src) {
    super(src);
    this.mActivityFilter = new int[] {4};
    this.mDuration = 30;
  }

  public SemContextActivityNotificationForLocationAttribute(int[] activityFilter, int duration) {
    this.mActivityFilter = new int[] {4};
    this.mDuration = 30;
    int[] iArr = new int[activityFilter.length];
    this.mActivityFilter = iArr;
    System.arraycopy(activityFilter, 0, iArr, 0, activityFilter.length);
    this.mDuration = duration;
    setAttribute();
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
