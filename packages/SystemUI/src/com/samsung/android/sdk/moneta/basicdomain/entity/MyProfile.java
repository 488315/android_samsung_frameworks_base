package com.samsung.android.sdk.moneta.basicdomain.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class MyProfile implements Parcelable {
    public static final Parcelable.Creator<MyProfile> CREATOR = new Creator();
    private final String name;
    private final List<String> phoneNumbers;
    private final Bundle properties;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MyProfile(parcel.readString(), parcel.createStringArrayList(), parcel.readBundle(MyProfile.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MyProfile[i];
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class PropertiesKey {
        public static final /* synthetic */ PropertiesKey[] $VALUES;
        public static final PropertiesKey AGE_GROUP;
        public static final PropertiesKey PHOTO_URL;
        private final String key;

        static {
            PropertiesKey propertiesKey = new PropertiesKey("AGE_GROUP", 0, "age_group");
            AGE_GROUP = propertiesKey;
            PropertiesKey propertiesKey2 = new PropertiesKey("PHOTO_URL", 1, "photo_url");
            PHOTO_URL = propertiesKey2;
            PropertiesKey[] propertiesKeyArr = {propertiesKey, propertiesKey2};
            $VALUES = propertiesKeyArr;
            EnumEntriesKt.enumEntries(propertiesKeyArr);
        }

        private PropertiesKey(String str, int i, String str2) {
            this.key = str2;
        }

        public static PropertiesKey valueOf(String str) {
            return (PropertiesKey) Enum.valueOf(PropertiesKey.class, str);
        }

        public static PropertiesKey[] values() {
            return (PropertiesKey[]) $VALUES.clone();
        }

        public final String getKey() {
            return this.key;
        }
    }

    public MyProfile(String str, List<String> list, Bundle bundle) {
        this.name = str;
        this.phoneNumbers = list;
        this.properties = bundle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MyProfile copy$default(MyProfile myProfile, String str, List list, Bundle bundle, int i, Object obj) {
        if ((i & 1) != 0) {
            str = myProfile.name;
        }
        if ((i & 2) != 0) {
            list = myProfile.phoneNumbers;
        }
        if ((i & 4) != 0) {
            bundle = myProfile.properties;
        }
        return myProfile.copy(str, list, bundle);
    }

    private final StringBuilder getPropertiesString() {
        StringBuilder sb = new StringBuilder("properties: [");
        sb.append('\n');
        for (String str : this.properties.keySet()) {
            if (Intrinsics.areEqual(str, PropertiesKey.AGE_GROUP.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb2 = new StringBuilder("value: ");
                sb2.append(this.properties.getParcelable(str, AgeGroup.class));
                sb.append(sb2.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PropertiesKey.PHOTO_URL.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb3 = new StringBuilder("value: ");
                sb3.append(this.properties.getString(str));
                sb.append(sb3.toString());
                sb.append('\n');
            }
        }
        return sb;
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

    public final MyProfile copy(String str, List<String> list, Bundle bundle) {
        return new MyProfile(str, list, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MyProfile)) {
            return false;
        }
        MyProfile myProfile = (MyProfile) obj;
        return Intrinsics.areEqual(this.name, myProfile.name) && Intrinsics.areEqual(this.phoneNumbers, myProfile.phoneNumbers) && Intrinsics.areEqual(this.properties, myProfile.properties);
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
        return getPropertiesString().toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeStringList(this.phoneNumbers);
        parcel.writeBundle(this.properties);
    }
}
