package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextEventContext implements Parcelable {
    public static final Parcelable.Creator<SemContextEventContext> CREATOR = new Parcelable.Creator<SemContextEventContext>() { // from class: com.samsung.android.hardware.context.SemContextEventContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEventContext createFromParcel(Parcel parcel) {
            return new SemContextEventContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEventContext[] newArray(int i) {
            return new SemContextEventContext[i];
        }
    };
    protected static final long serialVersionUID = 4514449696888150558L;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setValues(Bundle bundle) {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public SemContextEventContext() {
    }

    public SemContextEventContext(Parcel parcel) {
    }
}
