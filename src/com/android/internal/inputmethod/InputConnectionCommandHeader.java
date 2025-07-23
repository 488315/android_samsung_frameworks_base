package com.android.internal.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class InputConnectionCommandHeader implements Parcelable {
    public static final Parcelable.Creator<InputConnectionCommandHeader> CREATOR = new Parcelable.Creator<InputConnectionCommandHeader>() { // from class: com.android.internal.inputmethod.InputConnectionCommandHeader.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputConnectionCommandHeader createFromParcel(Parcel parcel) {
            return new InputConnectionCommandHeader(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputConnectionCommandHeader[] newArray(int i) {
            return new InputConnectionCommandHeader[i];
        }
    };
    public final int mSessionId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InputConnectionCommandHeader(int i) {
        this.mSessionId = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSessionId);
    }
}
