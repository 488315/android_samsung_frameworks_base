package com.samsung.android.sdk.moneta.preference.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Person implements Parcelable {
    public static final Parcelable.Creator<Person> CREATOR = new Creator();
    private final String id;
    private final String name;
    private final String phoneNumber;
    private final Bundle preferences;
    private final Bundle properties;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Person(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readBundle(Person.class.getClassLoader()), parcel.readBundle(Person.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Person[i];
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PreferencesKey {
        public static final /* synthetic */ PreferencesKey[] $VALUES;
        public static final PreferencesKey DAYS_OF_CONTACT;
        public static final PreferencesKey END_TIMESTAMP;
        public static final PreferencesKey LATEST_TIMESTAMP;
        public static final PreferencesKey NUM_OF_CONTACT;
        public static final PreferencesKey NUM_OF_INCOMING;
        public static final PreferencesKey NUM_OF_OUTGOING;
        public static final PreferencesKey OUTGOING_RATE;
        public static final PreferencesKey PERIOD_OF_CONTACT;
        public static final PreferencesKey PREFERENCE_LEVEL;
        public static final PreferencesKey REQUESTED_DAYS;
        public static final PreferencesKey START_TIMESTAMP;
        private final String key;

        static {
            PreferencesKey preferencesKey = new PreferencesKey("START_TIMESTAMP", 0, "start_timestamp");
            START_TIMESTAMP = preferencesKey;
            PreferencesKey preferencesKey2 = new PreferencesKey("END_TIMESTAMP", 1, "end_timestamp");
            END_TIMESTAMP = preferencesKey2;
            PreferencesKey preferencesKey3 = new PreferencesKey("NUM_OF_CONTACT", 2, "num_of_contact");
            NUM_OF_CONTACT = preferencesKey3;
            PreferencesKey preferencesKey4 = new PreferencesKey("LATEST_TIMESTAMP", 3, "latest_timestamp");
            LATEST_TIMESTAMP = preferencesKey4;
            PreferencesKey preferencesKey5 = new PreferencesKey("PERIOD_OF_CONTACT", 4, "period_of_contact");
            PERIOD_OF_CONTACT = preferencesKey5;
            PreferencesKey preferencesKey6 = new PreferencesKey("NUM_OF_INCOMING", 5, "num_of_incoming");
            NUM_OF_INCOMING = preferencesKey6;
            PreferencesKey preferencesKey7 = new PreferencesKey("NUM_OF_OUTGOING", 6, "num_of_outgoing");
            NUM_OF_OUTGOING = preferencesKey7;
            PreferencesKey preferencesKey8 = new PreferencesKey("OUTGOING_RATE", 7, "outgoing_rate");
            OUTGOING_RATE = preferencesKey8;
            PreferencesKey preferencesKey9 = new PreferencesKey("REQUESTED_DAYS", 8, "requested_days");
            REQUESTED_DAYS = preferencesKey9;
            PreferencesKey preferencesKey10 = new PreferencesKey("DAYS_OF_CONTACT", 9, "days_of_contact");
            DAYS_OF_CONTACT = preferencesKey10;
            PreferencesKey preferencesKey11 = new PreferencesKey("PREFERENCE_LEVEL", 10, "preference_level");
            PREFERENCE_LEVEL = preferencesKey11;
            PreferencesKey[] preferencesKeyArr = {preferencesKey, preferencesKey2, preferencesKey3, preferencesKey4, preferencesKey5, preferencesKey6, preferencesKey7, preferencesKey8, preferencesKey9, preferencesKey10, preferencesKey11};
            $VALUES = preferencesKeyArr;
            EnumEntriesKt.enumEntries(preferencesKeyArr);
        }

        private PreferencesKey(String str, int i, String str2) {
            this.key = str2;
        }

        public static PreferencesKey valueOf(String str) {
            return (PreferencesKey) Enum.valueOf(PreferencesKey.class, str);
        }

        public static PreferencesKey[] values() {
            return (PreferencesKey[]) $VALUES.clone();
        }

        public final String getKey() {
            return this.key;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PropertiesKey {
        public static final /* synthetic */ PropertiesKey[] $VALUES;
        public static final PropertiesKey CONTACT_ID;
        public static final PropertiesKey FACE_ID_LIST;
        private final String key;

        static {
            PropertiesKey propertiesKey = new PropertiesKey("CONTACT_ID", 0, "contact_id");
            CONTACT_ID = propertiesKey;
            PropertiesKey propertiesKey2 = new PropertiesKey("FACE_ID_LIST", 1, "face_id_list");
            FACE_ID_LIST = propertiesKey2;
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

    public Person(String str, String str2, String str3, Bundle bundle, Bundle bundle2) {
        this.id = str;
        this.name = str2;
        this.phoneNumber = str3;
        this.preferences = bundle;
        this.properties = bundle2;
    }

    public static /* synthetic */ Person copy$default(Person person, String str, String str2, String str3, Bundle bundle, Bundle bundle2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = person.id;
        }
        if ((i & 2) != 0) {
            str2 = person.name;
        }
        if ((i & 4) != 0) {
            str3 = person.phoneNumber;
        }
        if ((i & 8) != 0) {
            bundle = person.preferences;
        }
        if ((i & 16) != 0) {
            bundle2 = person.properties;
        }
        Bundle bundle3 = bundle2;
        String str4 = str3;
        return person.copy(str, str2, str4, bundle, bundle3);
    }

    private final StringBuilder getPreferencesString() {
        StringBuilder sb = new StringBuilder("properties: [");
        sb.append('\n');
        for (String str : this.preferences.keySet()) {
            if (Intrinsics.areEqual(str, PreferencesKey.START_TIMESTAMP.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb2 = new StringBuilder("value: ");
                sb2.append(this.preferences.getLong(str));
                sb.append(sb2.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.END_TIMESTAMP.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb3 = new StringBuilder("value: ");
                sb3.append(this.preferences.getLong(str));
                sb.append(sb3.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.NUM_OF_CONTACT.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb4 = new StringBuilder("value: ");
                sb4.append(this.preferences.getInt(str));
                sb.append(sb4.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.LATEST_TIMESTAMP.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb5 = new StringBuilder("value: ");
                sb5.append(this.preferences.getLong(str));
                sb.append(sb5.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.PERIOD_OF_CONTACT.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb6 = new StringBuilder("value: ");
                sb6.append(this.preferences.getDouble(str));
                sb.append(sb6.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.NUM_OF_INCOMING.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb7 = new StringBuilder("value: ");
                sb7.append(this.preferences.getInt(str));
                sb.append(sb7.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.NUM_OF_OUTGOING.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb8 = new StringBuilder("value: ");
                sb8.append(this.preferences.getInt(str));
                sb.append(sb8.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.OUTGOING_RATE.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb9 = new StringBuilder("value: ");
                sb9.append(this.preferences.getDouble(str));
                sb.append(sb9.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.REQUESTED_DAYS.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb10 = new StringBuilder("value: ");
                sb10.append(this.preferences.getInt(str));
                sb.append(sb10.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.DAYS_OF_CONTACT.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb11 = new StringBuilder("value: ");
                sb11.append(this.preferences.getInt(str));
                sb.append(sb11.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PreferencesKey.PREFERENCE_LEVEL.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb12 = new StringBuilder("value: ");
                sb12.append(this.preferences.getSerializable(str, PreferenceLevel.class));
                sb.append(sb12.toString());
                sb.append('\n');
            }
        }
        sb.append("]\n");
        return sb;
    }

    private final StringBuilder getPropertiesString() {
        StringBuilder sb = new StringBuilder("properties: [");
        sb.append('\n');
        for (String str : this.properties.keySet()) {
            if (Intrinsics.areEqual(str, PropertiesKey.CONTACT_ID.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb2 = new StringBuilder("value: ");
                sb2.append(this.properties.getString(str));
                sb.append(sb2.toString());
                sb.append('\n');
            } else if (Intrinsics.areEqual(str, PropertiesKey.FACE_ID_LIST.getKey())) {
                sb.append("key : " + str + ' ');
                StringBuilder sb3 = new StringBuilder("value: ");
                sb3.append(this.properties.getIntegerArrayList(str));
                sb.append(sb3.toString());
                sb.append('\n');
            }
        }
        sb.append("]\n");
        return sb;
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.name;
    }

    public final String component3() {
        return this.phoneNumber;
    }

    public final Bundle component4() {
        return this.preferences;
    }

    public final Bundle component5() {
        return this.properties;
    }

    public final Person copy(String str, String str2, String str3, Bundle bundle, Bundle bundle2) {
        return new Person(str, str2, str3, bundle, bundle2);
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
        return Intrinsics.areEqual(this.id, person.id) && Intrinsics.areEqual(this.name, person.name) && Intrinsics.areEqual(this.phoneNumber, person.phoneNumber) && Intrinsics.areEqual(this.preferences, person.preferences) && Intrinsics.areEqual(this.properties, person.properties);
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final Bundle getPreferences() {
        return this.preferences;
    }

    public final Bundle getProperties() {
        return this.properties;
    }

    public int hashCode() {
        return this.properties.hashCode() + ((this.preferences.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31, this.phoneNumber)) * 31);
    }

    public String toString() {
        return "id : " + this.id + '\n' + ((Object) getPreferencesString()) + ((Object) getPropertiesString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.phoneNumber);
        parcel.writeBundle(this.preferences);
        parcel.writeBundle(this.properties);
    }

    public /* synthetic */ Person(String str, String str2, String str3, Bundle bundle, Bundle bundle2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, bundle, bundle2);
    }
}
