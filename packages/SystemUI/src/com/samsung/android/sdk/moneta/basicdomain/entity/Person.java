package com.samsung.android.sdk.moneta.basicdomain.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Person implements Parcelable {
    public static final Parcelable.Creator<Person> CREATOR = new Creator();
    private final Long contactId;
    private final Long faceGroupID;
    private final String id;
    private final String name;
    private final Bundle properties;
    private final RelationShip relationship;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Person(parcel.readString(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null, parcel.readString(), RelationShip.CREATOR.createFromParcel(parcel), parcel.readBundle(Person.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Person[i];
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class PropertiesKey {
        public static final /* synthetic */ PropertiesKey[] $VALUES;
        public static final PropertiesKey LIVING_TOGETHER;
        public static final PropertiesKey NICKNAME;
        public static final PropertiesKey USER_NICKNAME;
        private final String key;

        static {
            PropertiesKey propertiesKey = new PropertiesKey("NICKNAME", 0, "nickname");
            NICKNAME = propertiesKey;
            PropertiesKey propertiesKey2 = new PropertiesKey("USER_NICKNAME", 1, "user_nickname");
            USER_NICKNAME = propertiesKey2;
            PropertiesKey propertiesKey3 = new PropertiesKey("LIVING_TOGETHER", 2, "living_together");
            LIVING_TOGETHER = propertiesKey3;
            PropertiesKey[] propertiesKeyArr = {propertiesKey, propertiesKey2, propertiesKey3};
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

    public Person(String str, Long l, Long l2, String str2, RelationShip relationShip, Bundle bundle) {
        this.id = str;
        this.contactId = l;
        this.faceGroupID = l2;
        this.name = str2;
        this.relationship = relationShip;
        this.properties = bundle;
    }

    public static /* synthetic */ Person copy$default(Person person, String str, Long l, Long l2, String str2, RelationShip relationShip, Bundle bundle, int i, Object obj) {
        if ((i & 1) != 0) {
            str = person.id;
        }
        if ((i & 2) != 0) {
            l = person.contactId;
        }
        if ((i & 4) != 0) {
            l2 = person.faceGroupID;
        }
        if ((i & 8) != 0) {
            str2 = person.name;
        }
        if ((i & 16) != 0) {
            relationShip = person.relationship;
        }
        if ((i & 32) != 0) {
            bundle = person.properties;
        }
        RelationShip relationShip2 = relationShip;
        Bundle bundle2 = bundle;
        return person.copy(str, l, l2, str2, relationShip2, bundle2);
    }

    private final StringBuilder getPropertiesString() {
        StringBuilder sb = new StringBuilder("properties: [");
        sb.append('\n');
        for (String str : this.properties.keySet()) {
            if (Intrinsics.areEqual(str, PropertiesKey.NICKNAME.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb2 = new StringBuilder("value: ");
                sb2.append(this.properties.getString(str));
                sb.append(sb2.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PropertiesKey.USER_NICKNAME.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb3 = new StringBuilder("value: ");
                sb3.append(this.properties.getString(str));
                sb.append(sb3.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PropertiesKey.LIVING_TOGETHER.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb4 = new StringBuilder("value: ");
                sb4.append(this.properties.getString(str));
                sb.append(sb4.toString());
                sb.append('\n');
            }
        }
        sb.append("]\n");
        return sb;
    }

    public final String component1() {
        return this.id;
    }

    public final Long component2() {
        return this.contactId;
    }

    public final Long component3() {
        return this.faceGroupID;
    }

    public final String component4() {
        return this.name;
    }

    public final RelationShip component5() {
        return this.relationship;
    }

    public final Bundle component6() {
        return this.properties;
    }

    public final Person copy(String str, Long l, Long l2, String str2, RelationShip relationShip, Bundle bundle) {
        return new Person(str, l, l2, str2, relationShip, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        Person person = (Person) obj;
        return Intrinsics.areEqual(this.id, person.id) && Intrinsics.areEqual(this.contactId, person.contactId) && Intrinsics.areEqual(this.faceGroupID, person.faceGroupID) && Intrinsics.areEqual(this.name, person.name) && this.relationship == person.relationship && Intrinsics.areEqual(this.properties, person.properties);
    }

    public final Long getContactId() {
        return this.contactId;
    }

    public final Long getFaceGroupID() {
        return this.faceGroupID;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final Bundle getProperties() {
        return this.properties;
    }

    public final RelationShip getRelationship() {
        return this.relationship;
    }

    public int hashCode() {
        int iHashCode = this.id.hashCode() * 31;
        Long l = this.contactId;
        int iHashCode2 = (iHashCode + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.faceGroupID;
        return this.properties.hashCode() + ((this.relationship.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode2 + (l2 != null ? l2.hashCode() : 0)) * 31, 31, this.name)) * 31);
    }

    public String toString() {
        return "name : " + this.name + "\ncontactId : " + this.contactId + "\nfaceGroupID : " + this.faceGroupID + "\nrelationship : " + this.relationship + '\n' + ((Object) getPropertiesString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Long l = this.contactId;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        Long l2 = this.faceGroupID;
        if (l2 == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l2);
        }
        parcel.writeString(this.name);
        this.relationship.writeToParcel(parcel, i);
        parcel.writeBundle(this.properties);
    }

    public /* synthetic */ Person(String str, Long l, Long l2, String str2, RelationShip relationShip, Bundle bundle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, l, l2, (i & 8) != 0 ? "" : str2, relationShip, bundle);
    }
}
