package com.android.internal.inputmethod;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes4.dex */
public final class InputConnectionCommandHeader implements Parcelable {
    public static final Parcelable.Creator<InputConnectionCommandHeader> CREATOR = new Parcelable.Creator<InputConnectionCommandHeader>() { // from class: com.android.internal.inputmethod.InputConnectionCommandHeader.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputConnectionCommandHeader createFromParcel(Parcel in) {
            int sessionId = in.readInt();
            return new InputConnectionCommandHeader(sessionId);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputConnectionCommandHeader[] newArray(int size) {
            return new InputConnectionCommandHeader[size];
        }
    };
    public final int mSessionId;

    public InputConnectionCommandHeader(int sessionId) {
        this.mSessionId = sessionId;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSessionId);
    }
}
