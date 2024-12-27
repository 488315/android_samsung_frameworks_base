package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/* loaded from: classes6.dex */
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
            this.mActivityFilter = new int[activityFilter.length];
            System.arraycopy(activityFilter, 0, this.mActivityFilter, 0, activityFilter.length);
            this.mDuration = duration;
            setAttribute();
            return;
        }
        Log.e(TAG, "The activityFilter is wrong.");
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
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
                if (list.get(i).equals(list.get(j))) {
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
