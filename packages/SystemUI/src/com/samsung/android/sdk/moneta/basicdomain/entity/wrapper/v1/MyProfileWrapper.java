package com.samsung.android.sdk.moneta.basicdomain.entity.wrapper.v1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MyProfileWrapper implements Parcelable {
    public static final Parcelable.Creator<MyProfileWrapper> CREATOR = new Creator();
    private final String name;
    private final List<String> phoneNumbers;
    private final Bundle properties;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MyProfileWrapper(parcel.readString(), parcel.createStringArrayList(), parcel.readBundle(MyProfileWrapper.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MyProfileWrapper[i];
        }
    }

    public MyProfileWrapper(String str, List<String> list, Bundle bundle) {
        this.name = str;
        this.phoneNumbers = list;
        this.properties = bundle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MyProfileWrapper copy$default(MyProfileWrapper myProfileWrapper, String str, List list, Bundle bundle, int i, Object obj) {
        if ((i & 1) != 0) {
            str = myProfileWrapper.name;
        }
        if ((i & 2) != 0) {
            list = myProfileWrapper.phoneNumbers;
        }
        if ((i & 4) != 0) {
            bundle = myProfileWrapper.properties;
        }
        return myProfileWrapper.copy(str, list, bundle);
    }

    public final String component1() {
        return this.name;
    }

    public final List<String> component2() {
        return this.phoneNumbers;
    }

    public final Bundle component3() {
        return this.properties;
    }

    public final MyProfileWrapper copy(String str, List<String> list, Bundle bundle) {
        return new MyProfileWrapper(str, list, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MyProfileWrapper)) {
            return false;
        }
        MyProfileWrapper myProfileWrapper = (MyProfileWrapper) obj;
        return Intrinsics.areEqual(this.name, myProfileWrapper.name) && Intrinsics.areEqual(this.phoneNumbers, myProfileWrapper.phoneNumbers) && Intrinsics.areEqual(this.properties, myProfileWrapper.properties);
    }

    public final String getName() {
        return this.name;
    }

    public final List<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public final Bundle getProperties() {
        return this.properties;
    }

    public int hashCode() {
        return this.properties.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.phoneNumbers, this.name.hashCode() * 31, 31);
    }

    public String toString() {
        return "MyProfileWrapper(name=" + this.name + ", phoneNumbers=" + this.phoneNumbers + ", properties=" + this.properties + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeStringList(this.phoneNumbers);
        parcel.writeBundle(this.properties);
    }
}
