package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemContextActivityNotificationAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextActivityNotificationAttribute> CREATOR = new Parcelable.Creator<SemContextActivityNotificationAttribute>() { // from class: com.samsung.android.hardware.context.SemContextActivityNotificationAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationAttribute createFromParcel(Parcel parcel) {
            return new SemContextActivityNotificationAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationAttribute[] newArray(int i) {
            return new SemContextActivityNotificationAttribute[i];
        }
    };
    private static final int STATUS_MAX = 5;
    private static final String TAG = "SemContextActivityNotificationAttribute";
    private int[] mActivityFilter;

    SemContextActivityNotificationAttribute() {
        this.mActivityFilter = new int[]{4};
        setAttribute();
    }

    SemContextActivityNotificationAttribute(Parcel parcel) {
        super(parcel);
        this.mActivityFilter = null;
    }

    public SemContextActivityNotificationAttribute(int[] iArr) {
        this.mActivityFilter = null;
        if (iArr != null) {
            int[] iArr2 = new int[iArr.length];
            this.mActivityFilter = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
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
            if (i >= iArr.length) {
                return true;
            }
            int i2 = iArr[i];
            if (i2 < 0 || i2 > 5) {
                break;
            }
            arrayList.add(Integer.valueOf(i2));
            for (int i3 = 0; i3 < i; i3++) {
                if (((Integer) arrayList.get(i)).equals(arrayList.get(i3))) {
                    Log.e(TAG, "This activity status cannot have duplicated status.");
                    return false;
                }
            }
            i++;
        }
        Log.e(TAG, "The activity status is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putIntArray("activity_filter", this.mActivityFilter);
        super.setAttribute(27, bundle);
    }
}
