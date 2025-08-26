package com.samsung.android.sdk.moneta.preference.entity;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class Relationship implements Parcelable {
    public static final Parcelable.Creator<Relationship> CREATOR = new Creator();
    private final RelationDetails details;
    private final RelationType type;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Relationship(RelationType.valueOf(parcel.readString()), RelationDetails.valueOf(parcel.readString()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Relationship[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Relationship() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ Relationship copy$default(Relationship relationship, RelationType relationType, RelationDetails relationDetails, int i, Object obj) {
        if ((i & 1) != 0) {
            relationType = relationship.type;
        }
        if ((i & 2) != 0) {
            relationDetails = relationship.details;
        }
        return relationship.copy(relationType, relationDetails);
    }

    public final RelationType component1() {
        return this.type;
    }

    public final RelationDetails component2() {
        return this.details;
    }

    public final Relationship copy(RelationType relationType, RelationDetails relationDetails) {
        return new Relationship(relationType, relationDetails);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Relationship)) {
            return false;
        }
        Relationship relationship = (Relationship) obj;
        return this.type == relationship.type && this.details == relationship.details;
    }

    public final RelationDetails getDetails() {
        return this.details;
    }

    public final RelationType getType() {
        return this.type;
    }

    public int hashCode() {
        return this.details.hashCode() + (this.type.hashCode() * 31);
    }

    public String toString() {
        return "Relationship(type=" + this.type + ", details=" + this.details + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.type.name());
        parcel.writeString(this.details.name());
    }

    public Relationship(RelationType relationType, RelationDetails relationDetails) {
        this.type = relationType;
        this.details = relationDetails;
    }

    public /* synthetic */ Relationship(RelationType relationType, RelationDetails relationDetails, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? RelationType.UNKNOWN : relationType, (i & 2) != 0 ? RelationDetails.UNKNOWN : relationDetails);
    }
}
