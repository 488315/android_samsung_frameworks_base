package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.context;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.context.Address;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class AddressWrapperV1 implements Parcelable {
    private final String adminArea;
    private final String countryName;
    private final String fullAddress;
    private final String locality;
    private final String streetName;
    private final String subAdminArea;
    private final String subLocality;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<AddressWrapperV1> CREATOR = new Creator();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new AddressWrapperV1(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new AddressWrapperV1[i];
        }
    }

    public AddressWrapperV1(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.fullAddress = str;
        this.countryName = str2;
        this.adminArea = str3;
        this.subAdminArea = str4;
        this.locality = str5;
        this.subLocality = str6;
        this.streetName = str7;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getAdminArea() {
        return this.adminArea;
    }

    public final String getCountryName() {
        return this.countryName;
    }

    public final String getFullAddress() {
        return this.fullAddress;
    }

    public final String getLocality() {
        return this.locality;
    }

    public final String getStreetName() {
        return this.streetName;
    }

    public final String getSubAdminArea() {
        return this.subAdminArea;
    }

    public final String getSubLocality() {
        return this.subLocality;
    }

    public final Address toContext() {
        return new Address(this.fullAddress, this.countryName, this.adminArea, this.subAdminArea, this.locality, this.subLocality, this.streetName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fullAddress);
        parcel.writeString(this.countryName);
        parcel.writeString(this.adminArea);
        parcel.writeString(this.subAdminArea);
        parcel.writeString(this.locality);
        parcel.writeString(this.subLocality);
        parcel.writeString(this.streetName);
    }

    public /* synthetic */ AddressWrapperV1(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7);
    }
}
