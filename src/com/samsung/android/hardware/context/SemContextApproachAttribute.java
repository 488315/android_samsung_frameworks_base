package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextApproachAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextApproachAttribute> CREATOR = new Parcelable.Creator<SemContextApproachAttribute>() { // from class: com.samsung.android.hardware.context.SemContextApproachAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute createFromParcel(Parcel parcel) {
            return new SemContextApproachAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute[] newArray(int i) {
            return new SemContextApproachAttribute[i];
        }
    };
    private int mUserID;

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        return true;
    }

    SemContextApproachAttribute() {
        this.mUserID = -1;
        setAttribute();
    }

    SemContextApproachAttribute(Parcel parcel) {
        super(parcel);
        this.mUserID = -1;
    }

    public SemContextApproachAttribute(int i) {
        this.mUserID = i;
        setAttribute();
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", this.mUserID);
        super.setAttribute(1, bundle);
    }
}
