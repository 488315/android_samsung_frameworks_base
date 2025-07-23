package com.samsung.android.knox.net.nap;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Profile implements Parcelable {
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() { // from class: com.samsung.android.knox.net.nap.Profile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Profile[] newArray(int i) {
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
    public int describeContents() {
        return 0;
    }

    public int getActivationState() {
        return this.activationState;
    }

    public String getJsonProfile() {
        return this.jsonProfile;
    }

    public int getUserId() {
        return this.userId;
    }

    public final void readFromParcel(Parcel parcel) {
        this.jsonProfile = parcel.readString();
        this.userId = parcel.readInt();
        this.activationState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
