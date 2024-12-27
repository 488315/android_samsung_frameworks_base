package com.android.internal.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;

public final class InputConnectionCommandHeader implements Parcelable {
    public static final Parcelable.Creator<InputConnectionCommandHeader> CREATOR =
            new Parcelable.Creator<
                    InputConnectionCommandHeader>() { // from class:
                                                      // com.android.internal.inputmethod.InputConnectionCommandHeader.1
                @Override // android.os.Parcelable.Creator
                public InputConnectionCommandHeader createFromParcel(Parcel in) {
                    int sessionId = in.readInt();
                    return new InputConnectionCommandHeader(sessionId);
                }

                @Override // android.os.Parcelable.Creator
                public InputConnectionCommandHeader[] newArray(int size) {
                    return new InputConnectionCommandHeader[size];
                }
            };
    public final int mSessionId;

    public InputConnectionCommandHeader(int sessionId) {
        this.mSessionId = sessionId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSessionId);
    }
}
