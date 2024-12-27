package com.samsung.android.provider;

import android.os.Parcel;
import android.os.Parcelable;

public class Feature implements Parcelable {
    public static final Parcelable.Creator<Feature> CREATOR =
            new Parcelable.Creator<
                    Feature>() { // from class: com.samsung.android.provider.Feature.1
                @Override // android.os.Parcelable.Creator
                public Feature createFromParcel(Parcel source) {
                    return new Feature(source);
                }

                @Override // android.os.Parcelable.Creator
                public Feature[] newArray(int size) {
                    return new Feature[size];
                }
            };
    private boolean abTest;
    private String name;
    private String value;

    public Feature(String featureName, String featureValue, boolean isAb) {
        this.name = featureName;
        this.value = featureValue;
        this.abTest = isAb;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
        dest.writeBoolean(this.abTest);
    }

    protected Feature(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.value = source.readString();
        this.abTest = source.readBoolean();
    }
}
