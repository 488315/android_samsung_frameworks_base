package com.samsung.android.sdk.moneta.memory.entity.context;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Place implements Parcelable {
    public static final Parcelable.Creator<Place> CREATOR = new Creator();
    private final Address address;
    private final String id;
    private final Double latitude;
    private final Double longitude;
    private final String name;
    private final Double radius;
    private final PlaceType type;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Place(parcel.readString(), parcel.readString(), PlaceType.valueOf(parcel.readString()), parcel.readInt() == 0 ? null : Address.CREATOR.createFromParcel(parcel), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() != 0 ? Double.valueOf(parcel.readDouble()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Place[i];
        }
    }

    public Place(String str, String str2, PlaceType placeType, Address address, Double d, Double d2, Double d3) {
        this.id = str;
        this.name = str2;
        this.type = placeType;
        this.address = address;
        this.latitude = d;
        this.longitude = d2;
        this.radius = d3;
    }

    public static /* synthetic */ Place copy$default(Place place, String str, String str2, PlaceType placeType, Address address, Double d, Double d2, Double d3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = place.id;
        }
        if ((i & 2) != 0) {
            str2 = place.name;
        }
        if ((i & 4) != 0) {
            placeType = place.type;
        }
        if ((i & 8) != 0) {
            address = place.address;
        }
        if ((i & 16) != 0) {
            d = place.latitude;
        }
        if ((i & 32) != 0) {
            d2 = place.longitude;
        }
        if ((i & 64) != 0) {
            d3 = place.radius;
        }
        Double d4 = d2;
        Double d5 = d3;
        Double d6 = d;
        PlaceType placeType2 = placeType;
        return place.copy(str, str2, placeType2, address, d6, d4, d5);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.name;
    }

    public final PlaceType component3() {
        return this.type;
    }

    public final Address component4() {
        return this.address;
    }

    public final Double component5() {
        return this.latitude;
    }

    public final Double component6() {
        return this.longitude;
    }

    public final Double component7() {
        return this.radius;
    }

    public final Place copy(String str, String str2, PlaceType placeType, Address address, Double d, Double d2, Double d3) {
        return new Place(str, str2, placeType, address, d, d2, d3);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Place)) {
            return false;
        }
        Place place = (Place) obj;
        return Intrinsics.areEqual(this.id, place.id) && Intrinsics.areEqual(this.name, place.name) && this.type == place.type && Intrinsics.areEqual(this.address, place.address) && Intrinsics.areEqual(this.latitude, place.latitude) && Intrinsics.areEqual(this.longitude, place.longitude) && Intrinsics.areEqual(this.radius, place.radius);
    }

    public final Address getAddress() {
        return this.address;
    }

    public final String getId() {
        return this.id;
    }

    public final Double getLatitude() {
        return this.latitude;
    }

    public final Double getLongitude() {
        return this.longitude;
    }

    public final String getName() {
        return this.name;
    }

    public final Double getRadius() {
        return this.radius;
    }

    public final PlaceType getType() {
        return this.type;
    }

    public int hashCode() {
        int iHashCode = this.id.hashCode() * 31;
        String str = this.name;
        int iHashCode2 = (this.type.hashCode() + ((iHashCode + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        Address address = this.address;
        int iHashCode3 = (iHashCode2 + (address == null ? 0 : address.hashCode())) * 31;
        Double d = this.latitude;
        int iHashCode4 = (iHashCode3 + (d == null ? 0 : d.hashCode())) * 31;
        Double d2 = this.longitude;
        int iHashCode5 = (iHashCode4 + (d2 == null ? 0 : d2.hashCode())) * 31;
        Double d3 = this.radius;
        return iHashCode5 + (d3 != null ? d3.hashCode() : 0);
    }

    public String toString() {
        return "Place(id=" + this.id + ", name=" + this.name + ", type=" + this.type + ", address=" + this.address + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", radius=" + this.radius + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.type.name());
        Address address = this.address;
        if (address == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            address.writeToParcel(parcel, i);
        }
        Double d = this.latitude;
        if (d == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d.doubleValue());
        }
        Double d2 = this.longitude;
        if (d2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d2.doubleValue());
        }
        Double d3 = this.radius;
        if (d3 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d3.doubleValue());
        }
    }
}
