package com.samsung.android.sdk.moneta.basicdomain.entity.wrapper.v1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PersonWrapper implements Parcelable {
    public static final Parcelable.Creator<PersonWrapper> CREATOR = new Creator();
    private final String id;
    private final Bundle properties;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new PersonWrapper(parcel.readString(), parcel.readBundle(PersonWrapper.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PersonWrapper[i];
        }
    }

    public PersonWrapper(String str, Bundle bundle) {
        this.id = str;
        this.properties = bundle;
    }

    public static /* synthetic */ PersonWrapper copy$default(PersonWrapper personWrapper, String str, Bundle bundle, int i, Object obj) {
        if ((i & 1) != 0) {
            str = personWrapper.id;
        }
        if ((i & 2) != 0) {
            bundle = personWrapper.properties;
        }
        return personWrapper.copy(str, bundle);
    }

    public final String component1() {
        return this.id;
    }

    public final Bundle component2() {
        return this.properties;
    }

    public final PersonWrapper copy(String str, Bundle bundle) {
        return new PersonWrapper(str, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersonWrapper)) {
            return false;
        }
        PersonWrapper personWrapper = (PersonWrapper) obj;
        return Intrinsics.areEqual(this.id, personWrapper.id) && Intrinsics.areEqual(this.properties, personWrapper.properties);
    }

    public final String getId() {
        return this.id;
    }

    public final Bundle getProperties() {
        return this.properties;
    }

    public int hashCode() {
        return this.properties.hashCode() + (this.id.hashCode() * 31);
    }

    public String toString() {
        return "PersonWrapper(id=" + this.id + ", properties=" + this.properties + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeBundle(this.properties);
    }
}
