package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemContextActivityNotificationForLocationAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityNotificationForLocationAttribute> CREATOR = new Parcelable.Creator<SemContextActivityNotificationForLocationAttribute>() { // from class: com.samsung.android.hardware.context.SemContextActivityNotificationForLocationAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationForLocationAttribute createFromParcel(Parcel parcel) {
            return new SemContextActivityNotificationForLocationAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationForLocationAttribute[] newArray(int i) {
            return new SemContextActivityNotificationForLocationAttribute[i];
        }
    };
    private static final int STATUS_MAX = 5;
    private static final String TAG = "SemContextActivityNotificationForLocationAttribute";
    private int[] mActivityFilter;
    private int mDuration;

    SemContextActivityNotificationForLocationAttribute() {
        this.mActivityFilter = new int[]{4};
        this.mDuration = 30;
        setAttribute();
    }

    SemContextActivityNotificationForLocationAttribute(Parcel parcel) {
        super(parcel);
        this.mActivityFilter = new int[]{4};
        this.mDuration = 30;
    }

    public SemContextActivityNotificationForLocationAttribute(int[] iArr, int i) {
        this.mActivityFilter = new int[]{4};
        this.mDuration = 30;
        int[] iArr2 = new int[iArr.length];
        this.mActivityFilter = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        this.mDuration = i;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
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
                    if (((Integer) arrayList.get(i)).equals(arrayList.get(i3))) {
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
