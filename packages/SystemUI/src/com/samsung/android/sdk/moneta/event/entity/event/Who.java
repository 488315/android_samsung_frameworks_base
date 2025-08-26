package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Who implements Parcelable {
    public static final Parcelable.Creator<Who> CREATOR = new Creator();
    private final String category;
    private final String contactId;
    private final String email;
    private final String groupName;
    private final boolean isContributor;
    private final String name;
    private final String nickName;
    private final String phoneNumber;
    private final String relation;
    private final String snsName;
    private final String sourcePackage;
    private final String sourceUri;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Who(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Who[i];
        }
    }

    public Who(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, String str10, String str11) {
        this.name = str;
        this.phoneNumber = str2;
        this.contactId = str3;
        this.email = str4;
        this.groupName = str5;
        this.nickName = str6;
        this.snsName = str7;
        this.relation = str8;
        this.category = str9;
        this.isContributor = z;
        this.sourcePackage = str10;
        this.sourceUri = str11;
    }

    public static /* synthetic */ Who copy$default(Who who, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, String str10, String str11, int i, Object obj) {
        if ((i & 1) != 0) {
            str = who.name;
        }
        if ((i & 2) != 0) {
            str2 = who.phoneNumber;
        }
        if ((i & 4) != 0) {
            str3 = who.contactId;
        }
        if ((i & 8) != 0) {
            str4 = who.email;
        }
        if ((i & 16) != 0) {
            str5 = who.groupName;
        }
        if ((i & 32) != 0) {
            str6 = who.nickName;
        }
        if ((i & 64) != 0) {
            str7 = who.snsName;
        }
        if ((i & 128) != 0) {
            str8 = who.relation;
        }
        if ((i & 256) != 0) {
            str9 = who.category;
        }
        if ((i & 512) != 0) {
            z = who.isContributor;
        }
        if ((i & 1024) != 0) {
            str10 = who.sourcePackage;
        }
        if ((i & 2048) != 0) {
            str11 = who.sourceUri;
        }
        String str12 = str10;
        String str13 = str11;
        String str14 = str9;
        boolean z2 = z;
        String str15 = str7;
        String str16 = str8;
        String str17 = str5;
        String str18 = str6;
        return who.copy(str, str2, str3, str4, str17, str18, str15, str16, str14, z2, str12, str13);
    }

    public final String component1() {
        return this.name;
    }

    public final boolean component10() {
        return this.isContributor;
    }

    public final String component11() {
        return this.sourcePackage;
    }

    public final String component12() {
        return this.sourceUri;
    }

    public final String component2() {
        return this.phoneNumber;
    }

    public final String component3() {
        return this.contactId;
    }

    public final String component4() {
        return this.email;
    }

    public final String component5() {
        return this.groupName;
    }

    public final String component6() {
        return this.nickName;
    }

    public final String component7() {
        return this.snsName;
    }

    public final String component8() {
        return this.relation;
    }

    public final String component9() {
        return this.category;
    }

    public final Who copy(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, String str10, String str11) {
        return new Who(str, str2, str3, str4, str5, str6, str7, str8, str9, z, str10, str11);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Who)) {
            return false;
        }
        Who who = (Who) obj;
        return Intrinsics.areEqual(this.name, who.name) && Intrinsics.areEqual(this.phoneNumber, who.phoneNumber) && Intrinsics.areEqual(this.contactId, who.contactId) && Intrinsics.areEqual(this.email, who.email) && Intrinsics.areEqual(this.groupName, who.groupName) && Intrinsics.areEqual(this.nickName, who.nickName) && Intrinsics.areEqual(this.snsName, who.snsName) && Intrinsics.areEqual(this.relation, who.relation) && Intrinsics.areEqual(this.category, who.category) && this.isContributor == who.isContributor && Intrinsics.areEqual(this.sourcePackage, who.sourcePackage) && Intrinsics.areEqual(this.sourceUri, who.sourceUri);
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getContactId() {
        return this.contactId;
    }

    public final String getEmail() {
        return this.email;
    }

    public final String getGroupName() {
        return this.groupName;
    }

    public final String getName() {
        return this.name;
    }

    public final String getNickName() {
        return this.nickName;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getRelation() {
        return this.relation;
    }

    public final String getSnsName() {
        return this.snsName;
    }

    public final String getSourcePackage() {
        return this.sourcePackage;
    }

    public final String getSourceUri() {
        return this.sourceUri;
    }

    public int hashCode() {
        return this.sourceUri.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.phoneNumber), 31, this.contactId), 31, this.email), 31, this.groupName), 31, this.nickName), 31, this.snsName), 31, this.relation), 31, this.category), 31, this.isContributor), 31, this.sourcePackage);
    }

    public final boolean isContributor() {
        return this.isContributor;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Who(name=");
        sb.append(this.name);
        sb.append(", phoneNumber=");
        sb.append(this.phoneNumber);
        sb.append(", contactId=");
        sb.append(this.contactId);
        sb.append(", email=");
        sb.append(this.email);
        sb.append(", groupName=");
        sb.append(this.groupName);
        sb.append(", nickName=");
        sb.append(this.nickName);
        sb.append(", snsName=");
        sb.append(this.snsName);
        sb.append(", relation=");
        sb.append(this.relation);
        sb.append(", category=");
        sb.append(this.category);
        sb.append(", isContributor=");
        sb.append(this.isContributor);
        sb.append(", sourcePackage=");
        sb.append(this.sourcePackage);
        sb.append(", sourceUri=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.sourceUri, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.phoneNumber);
        parcel.writeString(this.contactId);
        parcel.writeString(this.email);
        parcel.writeString(this.groupName);
        parcel.writeString(this.nickName);
        parcel.writeString(this.snsName);
        parcel.writeString(this.relation);
        parcel.writeString(this.category);
        parcel.writeInt(this.isContributor ? 1 : 0);
        parcel.writeString(this.sourcePackage);
        parcel.writeString(this.sourceUri);
    }
}
