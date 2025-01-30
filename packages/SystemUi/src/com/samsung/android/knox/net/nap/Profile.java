package com.samsung.android.knox.net.nap;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Profile implements Parcelable {
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() { // from class: com.samsung.android.knox.net.nap.Profile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final Profile[] newArray(int i) {
            return new Profile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Profile[] newArray(int i) {
            return new Profile[i];
        }
    };
    public int activationState;
    public String jsonProfile;
    public int userId;

    public /* synthetic */ Profile(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getActivationState() {
        return this.activationState;
    }

    public final String getJsonProfile() {
        return this.jsonProfile;
    }

    public final int getUserId() {
        return this.userId;
    }

    public final void readFromParcel(Parcel parcel) {
        this.jsonProfile = parcel.readString();
        this.userId = parcel.readInt();
        this.activationState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.jsonProfile);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.activationState);
    }

    public Profile(int i, String str, int i2) {
        this.jsonProfile = str;
        this.userId = i2;
        this.activationState = i;
    }

    private Profile(Parcel parcel) {
        readFromParcel(parcel);
    }
}
