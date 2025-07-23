package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Where implements Parcelable {
    public static final Parcelable.Creator<Where> CREATOR = new Creator();
    private final String address;
    private final String city;
    private final String country;
    private final String placeName;
    private final String poi;
    private final String postalCode;
    private final String sourcePackage;
    private final String sourceUri;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Where(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Where[i];
        }
    }

    public Where(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.placeName = str;
        this.address = str2;
        this.poi = str3;
        this.country = str4;
        this.city = str5;
        this.postalCode = str6;
        this.sourcePackage = str7;
        this.sourceUri = str8;
    }

    public static /* synthetic */ Where copy$default(Where where, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, Object obj) {
        if ((i & 1) != 0) {
            str = where.placeName;
        }
        if ((i & 2) != 0) {
            str2 = where.address;
        }
        if ((i & 4) != 0) {
            str3 = where.poi;
        }
        if ((i & 8) != 0) {
            str4 = where.country;
        }
        if ((i & 16) != 0) {
            str5 = where.city;
        }
        if ((i & 32) != 0) {
            str6 = where.postalCode;
        }
        if ((i & 64) != 0) {
            str7 = where.sourcePackage;
        }
        if ((i & 128) != 0) {
            str8 = where.sourceUri;
        }
        String str9 = str7;
        String str10 = str8;
        String str11 = str5;
        String str12 = str6;
        return where.copy(str, str2, str3, str4, str11, str12, str9, str10);
    }

    public final String component1() {
        return this.placeName;
    }

    public final String component2() {
        return this.address;
    }

    public final String component3() {
        return this.poi;
    }

    public final String component4() {
        return this.country;
    }

    public final String component5() {
        return this.city;
    }

    public final String component6() {
        return this.postalCode;
    }

    public final String component7() {
        return this.sourcePackage;
    }

    public final String component8() {
        return this.sourceUri;
    }

    public final Where copy(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        return new Where(str, str2, str3, str4, str5, str6, str7, str8);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Where)) {
            return false;
        }
        Where where = (Where) obj;
        return Intrinsics.areEqual(this.placeName, where.placeName) && Intrinsics.areEqual(this.address, where.address) && Intrinsics.areEqual(this.poi, where.poi) && Intrinsics.areEqual(this.country, where.country) && Intrinsics.areEqual(this.city, where.city) && Intrinsics.areEqual(this.postalCode, where.postalCode) && Intrinsics.areEqual(this.sourcePackage, where.sourcePackage) && Intrinsics.areEqual(this.sourceUri, where.sourceUri);
    }

    public final String getAddress() {
        return this.address;
    }

    public final String getCity() {
        return this.city;
    }

    public final String getCountry() {
        return this.country;
    }

    public final String getPlaceName() {
        return this.placeName;
    }

    public final String getPoi() {
        return this.poi;
    }

    public final String getPostalCode() {
        return this.postalCode;
    }

    public final String getSourcePackage() {
        return this.sourcePackage;
    }

    public final String getSourceUri() {
        return this.sourceUri;
    }

    public int hashCode() {
        return this.sourceUri.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.placeName.hashCode() * 31, 31, this.address), 31, this.poi), 31, this.country), 31, this.city), 31, this.postalCode), 31, this.sourcePackage);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Where(placeName=");
        sb.append(this.placeName);
        sb.append(", address=");
        sb.append(this.address);
        sb.append(", poi=");
        sb.append(this.poi);
        sb.append(", country=");
        sb.append(this.country);
        sb.append(", city=");
        sb.append(this.city);
        sb.append(", postalCode=");
        sb.append(this.postalCode);
        sb.append(", sourcePackage=");
        sb.append(this.sourcePackage);
        sb.append(", sourceUri=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.sourceUri, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.placeName);
        parcel.writeString(this.address);
        parcel.writeString(this.poi);
        parcel.writeString(this.country);
        parcel.writeString(this.city);
        parcel.writeString(this.postalCode);
        parcel.writeString(this.sourcePackage);
        parcel.writeString(this.sourceUri);
    }
}
