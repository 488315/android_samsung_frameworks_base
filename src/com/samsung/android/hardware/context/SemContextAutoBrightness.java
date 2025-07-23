package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextAutoBrightness extends SemContextEventContext {
    public static final int CONFIG_DATA_DOWNLOADED = 1000;
    public static final Parcelable.Creator<SemContextAutoBrightness> CREATOR = new Parcelable.Creator<SemContextAutoBrightness>() { // from class: com.samsung.android.hardware.context.SemContextAutoBrightness.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightness createFromParcel(Parcel parcel) {
            return new SemContextAutoBrightness(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoBrightness[] newArray(int i) {
            return new SemContextAutoBrightness[i];
        }
    };
    public static final int EBOOK_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int UPDATE_MODE = 2;
    private Bundle mContext;

    SemContextAutoBrightness() {
        this.mContext = new Bundle();
    }

    SemContextAutoBrightness(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAmbientLux() {
        return this.mContext.getInt("AmbientLux");
    }

    public int getCandela() {
        return this.mContext.getInt("Candela");
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
