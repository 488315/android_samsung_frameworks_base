package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextFlipCoverAction extends SemContextEventContext {
    public static final int CLOSE = 1;
    public static final Parcelable.Creator<SemContextFlipCoverAction> CREATOR = new Parcelable.Creator<SemContextFlipCoverAction>() { // from class: com.samsung.android.hardware.context.SemContextFlipCoverAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlipCoverAction createFromParcel(Parcel parcel) {
            return new SemContextFlipCoverAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlipCoverAction[] newArray(int i) {
            return new SemContextFlipCoverAction[i];
        }
    };
    public static final int OPEN = 0;
    public static final int UNKNOWN = -1;
    private Bundle mContext;

    SemContextFlipCoverAction() {
        this.mContext = new Bundle();
    }

    SemContextFlipCoverAction(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
    }
}
