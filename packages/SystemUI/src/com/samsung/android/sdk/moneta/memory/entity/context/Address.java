package com.samsung.android.sdk.moneta.memory.entity.context;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Address implements Parcelable {
    public static final Parcelable.Creator<Address> CREATOR = new Creator();
    private final String adminArea;
    private final String countryName;
    private final String fullAddress;
    private final String locality;
    private final String streetName;
    private final String subAdminArea;
    private final String subLocality;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Address(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Address[i];
        }
    }

    public Address(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.fullAddress = str;
        this.countryName = str2;
        this.adminArea = str3;
        this.subAdminArea = str4;
        this.locality = str5;
        this.subLocality = str6;
        this.streetName = str7;
    }

    public static /* synthetic */ Address copy$default(Address address, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, Object obj) {
        if ((i & 1) != 0) {
            str = address.fullAddress;
        }
        if ((i & 2) != 0) {
            str2 = address.countryName;
        }
        if ((i & 4) != 0) {
            str3 = address.adminArea;
        }
        if ((i & 8) != 0) {
            str4 = address.subAdminArea;
        }
        if ((i & 16) != 0) {
            str5 = address.locality;
        }
        if ((i & 32) != 0) {
            str6 = address.subLocality;
        }
        if ((i & 64) != 0) {
            str7 = address.streetName;
        }
        String str8 = str6;
        String str9 = str7;
        String str10 = str5;
        String str11 = str3;
        return address.copy(str, str2, str11, str4, str10, str8, str9);
    }

    public final String component1() {
        return this.fullAddress;
    }

    public final String component2() {
        return this.countryName;
    }

    public final String component3() {
        return this.adminArea;
    }

    public final String component4() {
        return this.subAdminArea;
    }

    public final String component5() {
        return this.locality;
    }

    public final String component6() {
        return this.subLocality;
    }

    public final String component7() {
        return this.streetName;
    }

    public final Address copy(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        return new Address(str, str2, str3, str4, str5, str6, str7);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        return Intrinsics.areEqual(this.fullAddress, address.fullAddress) && Intrinsics.areEqual(this.countryName, address.countryName) && Intrinsics.areEqual(this.adminArea, address.adminArea) && Intrinsics.areEqual(this.subAdminArea, address.subAdminArea) && Intrinsics.areEqual(this.locality, address.locality) && Intrinsics.areEqual(this.subLocality, address.subLocality) && Intrinsics.areEqual(this.streetName, address.streetName);
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

    public int hashCode() {
        int hashCode = this.fullAddress.hashCode() * 31;
        String str = this.countryName;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.adminArea;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.subAdminArea;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.locality;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.subLocality;
        int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.streetName;
        return hashCode6 + (str6 != null ? str6.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Address(fullAddress=");
        sb.append(this.fullAddress);
        sb.append(", countryName=");
        sb.append(this.countryName);
        sb.append(", adminArea=");
        sb.append(this.adminArea);
        sb.append(", subAdminArea=");
        sb.append(this.subAdminArea);
        sb.append(", locality=");
        sb.append(this.locality);
        sb.append(", subLocality=");
        sb.append(this.subLocality);
        sb.append(", streetName=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.streetName, ')');
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

    public /* synthetic */ Address(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7);
    }
}
