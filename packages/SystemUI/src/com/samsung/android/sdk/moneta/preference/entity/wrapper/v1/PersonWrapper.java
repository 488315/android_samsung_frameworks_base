package com.samsung.android.sdk.moneta.preference.entity.wrapper.v1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class PersonWrapper implements Parcelable {
    public static final Parcelable.Creator<PersonWrapper> CREATOR = new Creator();
    private final String id;
    private final Bundle preferences;
    private final Bundle properties;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new PersonWrapper(parcel.readString(), parcel.readBundle(PersonWrapper.class.getClassLoader()), parcel.readBundle(PersonWrapper.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PersonWrapper[i];
        }
    }

    public PersonWrapper(String str, Bundle bundle, Bundle bundle2) {
        this.id = str;
        this.preferences = bundle;
        this.properties = bundle2;
    }

    public static /* synthetic */ PersonWrapper copy$default(PersonWrapper personWrapper, String str, Bundle bundle, Bundle bundle2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = personWrapper.id;
        }
        if ((i & 2) != 0) {
            bundle = personWrapper.preferences;
        }
        if ((i & 4) != 0) {
            bundle2 = personWrapper.properties;
        }
        return personWrapper.copy(str, bundle, bundle2);
    }

    public final String component1() {
        return this.id;
    }

    public final Bundle component2() {
        return this.preferences;
    }

    public final Bundle component3() {
        return this.properties;
    }

    public final PersonWrapper copy(String str, Bundle bundle, Bundle bundle2) {
        return new PersonWrapper(str, bundle, bundle2);
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
        return Intrinsics.areEqual(this.id, personWrapper.id) && Intrinsics.areEqual(this.preferences, personWrapper.preferences) && Intrinsics.areEqual(this.properties, personWrapper.properties);
    }

    public final String getId() {
        return this.id;
    }

    public final Bundle getPreferences() {
        return this.preferences;
    }

    public final Bundle getProperties() {
        return this.properties;
    }

    public int hashCode() {
        return this.properties.hashCode() + ((this.preferences.hashCode() + (this.id.hashCode() * 31)) * 31);
    }

    public String toString() {
        return "PersonWrapper(id=" + this.id + ", preferences=" + this.preferences + ", properties=" + this.properties + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeBundle(this.preferences);
        parcel.writeBundle(this.properties);
    }
}
