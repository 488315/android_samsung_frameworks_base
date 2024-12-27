package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemContextActivityNotificationAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityNotificationAttribute> CREATOR =
            new Parcelable.Creator<
                    SemContextActivityNotificationAttribute>() { // from class:
                                                                 // com.samsung.android.hardware.context.SemContextActivityNotificationAttribute.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextActivityNotificationAttribute createFromParcel(Parcel in) {
                    return new SemContextActivityNotificationAttribute(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextActivityNotificationAttribute[] newArray(int size) {
                    return new SemContextActivityNotificationAttribute[size];
                }
            };
    private static final int STATUS_MAX = 5;
    private static final String TAG = "SemContextActivityNotificationAttribute";
    private int[] mActivityFilter;

    SemContextActivityNotificationAttribute() {
        this.mActivityFilter = null;
        this.mActivityFilter = new int[] {4};
        setAttribute();
    }

    SemContextActivityNotificationAttribute(Parcel src) {
        super(src);
        this.mActivityFilter = null;
    }

    public SemContextActivityNotificationAttribute(int[] activityFilter) {
        this.mActivityFilter = null;
        if (activityFilter != null) {
            this.mActivityFilter = new int[activityFilter.length];
            System.arraycopy(activityFilter, 0, this.mActivityFilter, 0, activityFilter.length);
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
            if (this.mActivityFilter[i] < 0 || this.mActivityFilter[i] > 5) {
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
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putIntArray("activity_filter", this.mActivityFilter);
        super.setAttribute(27, attribute);
    }
}
