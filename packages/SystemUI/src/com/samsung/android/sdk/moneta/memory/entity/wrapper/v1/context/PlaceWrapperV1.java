package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.context;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.context.Address;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import com.samsung.android.sdk.moneta.memory.entity.context.PlaceType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class PlaceWrapperV1 implements Parcelable {
    private final Address address;
    private final String id;
    private final Double latitude;
    private final Double longitude;
    private final String name;
    private final Double radius;
    private final PlaceType type;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<PlaceWrapperV1> CREATOR = new Creator();

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
            return new PlaceWrapperV1(parcel.readString(), parcel.readString(), PlaceType.valueOf(parcel.readString()), parcel.readInt() == 0 ? null : Address.CREATOR.createFromParcel(parcel), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() != 0 ? Double.valueOf(parcel.readDouble()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PlaceWrapperV1[i];
        }
    }

    public PlaceWrapperV1(String str, String str2, PlaceType placeType, Address address, Double d, Double d2, Double d3) {
        this.id = str;
        this.name = str2;
        this.type = placeType;
        this.address = address;
        this.latitude = d;
        this.longitude = d2;
        this.radius = d3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public final Place toContext() {
        return new Place(this.id, this.name, this.type, this.address, this.latitude, this.longitude, this.radius);
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
