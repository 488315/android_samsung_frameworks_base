package com.samsung.android.sdk.moneta.memory.entity.context;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Person implements Parcelable {
    public static final Parcelable.Creator<Person> CREATOR = new Creator();
    private final Long contactId;
    private final String id;
    private final String name;
    private final Long personId;
    private final List<String> relationships;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Person(parcel.readString(), parcel.readString(), parcel.createStringArrayList(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Person[i];
        }
    }

    public Person(String str, String str2, List<String> list, Long l, Long l2) {
        this.id = str;
        this.name = str2;
        this.relationships = list;
        this.contactId = l;
        this.personId = l2;
    }

    public static /* synthetic */ Person copy$default(Person person, String str, String str2, List list, Long l, Long l2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = person.id;
        }
        if ((i & 2) != 0) {
            str2 = person.name;
        }
        if ((i & 4) != 0) {
            list = person.relationships;
        }
        if ((i & 8) != 0) {
            l = person.contactId;
        }
        if ((i & 16) != 0) {
            l2 = person.personId;
        }
        Long l3 = l2;
        List list2 = list;
        return person.copy(str, str2, list2, l, l3);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.name;
    }

    public final List<String> component3() {
        return this.relationships;
    }

    public final Long component4() {
        return this.contactId;
    }

    public final Long component5() {
        return this.personId;
    }

    public final Person copy(String str, String str2, List<String> list, Long l, Long l2) {
        return new Person(str, str2, list, l, l2);
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
        return Intrinsics.areEqual(this.id, person.id) && Intrinsics.areEqual(this.name, person.name) && Intrinsics.areEqual(this.relationships, person.relationships) && Intrinsics.areEqual(this.contactId, person.contactId) && Intrinsics.areEqual(this.personId, person.personId);
    }

    public final Long getContactId() {
        return this.contactId;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final Long getPersonId() {
        return this.personId;
    }

    public final List<String> getRelationships() {
        return this.relationships;
    }

    public int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.relationships, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31);
        Long l = this.contactId;
        int iHashCode = (iM + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.personId;
        return iHashCode + (l2 != null ? l2.hashCode() : 0);
    }

    public String toString() {
        return "Person(id=" + this.id + ", name=" + this.name + ", relationships=" + this.relationships + ", contactId=" + this.contactId + ", personId=" + this.personId + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeStringList(this.relationships);
        Long l = this.contactId;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        Long l2 = this.personId;
        if (l2 == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l2);
        }
    }
}
