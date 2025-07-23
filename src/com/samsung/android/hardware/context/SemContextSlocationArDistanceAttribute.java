package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextSlocationArDistanceAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextSlocationArDistanceAttribute> CREATOR = new Parcelable.Creator<SemContextSlocationArDistanceAttribute>() { // from class: com.samsung.android.hardware.context.SemContextSlocationArDistanceAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSlocationArDistanceAttribute createFromParcel(Parcel parcel) {
            return new SemContextSlocationArDistanceAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSlocationArDistanceAttribute[] newArray(int i) {
            return new SemContextSlocationArDistanceAttribute[i];
        }
    };
    private static final String TAG = "SemContextSlocationArDistanceAttribute";
    private int[] mDistanceCmd;
    private int mMode;

    SemContextSlocationArDistanceAttribute() {
        this.mMode = -1;
        this.mDistanceCmd = null;
        setAttribute();
    }

    private SemContextSlocationArDistanceAttribute(Parcel parcel) {
        super(parcel);
        this.mMode = -1;
        this.mDistanceCmd = null;
    }

    public SemContextSlocationArDistanceAttribute(int i, int[] iArr) {
        this.mDistanceCmd = null;
        this.mMode = i;
        int[] iArr2 = new int[iArr.length];
        this.mDistanceCmd = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mMode;
        if (i >= -1 && i <= 1) {
            return true;
        }
        Log.d(TAG, "Mode value is wrong!!");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        if (this.mMode == 1) {
            int[] iArr = this.mDistanceCmd;
            int[] iArr2 = new int[iArr.length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            bundle.putIntArray("distance_cmd_array", iArr2);
        }
        bundle.putInt("mode", this.mMode);
        Log.d(TAG, "setAttribute() mode : " + bundle.getInt("mode"));
        super.setAttribute(56, bundle);
    }
}
