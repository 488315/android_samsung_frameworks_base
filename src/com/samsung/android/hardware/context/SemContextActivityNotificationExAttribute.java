package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemContextActivityNotificationExAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityNotificationExAttribute> CREATOR = new Parcelable.Creator<SemContextActivityNotificationExAttribute>() { // from class: com.samsung.android.hardware.context.SemContextActivityNotificationExAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationExAttribute createFromParcel(Parcel parcel) {
            return new SemContextActivityNotificationExAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationExAttribute[] newArray(int i) {
            return new SemContextActivityNotificationExAttribute[i];
        }
    };
    private static final int STATUS_MAX = 5;
    private static final String TAG = "SemContextActivityNotificationExAttribute";
    private int[] mActivityFilter;
    private int mDuration;

    SemContextActivityNotificationExAttribute() {
        this.mDuration = 30;
        this.mActivityFilter = new int[]{4};
        setAttribute();
    }

    SemContextActivityNotificationExAttribute(Parcel parcel) {
        super(parcel);
        this.mActivityFilter = null;
        this.mDuration = 30;
    }

    public SemContextActivityNotificationExAttribute(int[] iArr, int i) {
        this.mActivityFilter = null;
        this.mDuration = 30;
        if (iArr != null) {
            int[] iArr2 = new int[iArr.length];
            this.mActivityFilter = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.mDuration = i;
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
