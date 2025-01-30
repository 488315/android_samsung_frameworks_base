package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextPutDownMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextPutDownMotion> CREATOR = new Parcelable.Creator<SemContextPutDownMotion>() { // from class: com.samsung.android.hardware.context.SemContextPutDownMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPutDownMotion createFromParcel(Parcel in) {
            return new SemContextPutDownMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPutDownMotion[] newArray(int size) {
            return new SemContextPutDownMotion[size];
        }
    };
    public static final int FALSE = 2;
    public static final int NONE = 0;
    public static final int TRUE = 1;
    private Bundle mContext;

    SemContextPutDownMotion() {
        this.mContext = new Bundle();
    }

    SemContextPutDownMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
