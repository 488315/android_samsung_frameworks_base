package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextAnyMotionDetector extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextAnyMotionDetector> CREATOR = new Parcelable.Creator<SemContextAnyMotionDetector>() { // from class: com.samsung.android.hardware.context.SemContextAnyMotionDetector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAnyMotionDetector createFromParcel(Parcel parcel) {
            return new SemContextAnyMotionDetector(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAnyMotionDetector[] newArray(int i) {
            return new SemContextAnyMotionDetector[i];
        }
    };
    public static final int STATUS_ACTION = 1;
    public static final int STATUS_NONE = 0;
    private Bundle mContext;

    SemContextAnyMotionDetector() {
        this.mContext = new Bundle();
    }

    SemContextAnyMotionDetector(Parcel parcel) {
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
