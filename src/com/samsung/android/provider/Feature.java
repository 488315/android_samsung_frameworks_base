package com.samsung.android.provider;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class Feature implements Parcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new Parcelable.Creator<Feature>() { // from class: com.samsung.android.provider.Feature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Feature createFromParcel(Parcel parcel) {
            return new Feature(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Feature[] newArray(int i) {
            return new Feature[i];
        }
    };
    private boolean abTest;
    private String name;
    private String value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Feature(String str, String str2, boolean z) {
        this.name = str;
        this.value = str2;
        this.abTest = z;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAbTest() {
        return this.abTest;
    }

    public String getString() {
        return this.value;
    }

    public boolean getBoolean() {
        return Boolean.parseBoolean(this.value);
    }

    public int getInt() {
        return Integer.parseInt(this.value);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.value);
        parcel.writeBoolean(this.abTest);
    }

    protected Feature(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.name = parcel.readString();
        this.value = parcel.readString();
        this.abTest = parcel.readBoolean();
    }
}
