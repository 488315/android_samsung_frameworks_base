package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EnterpriseContainerObject implements EnterpriseContainerConstants, Parcelable {
    public static final Parcelable.Creator<EnterpriseContainerObject> CREATOR = new Parcelable.Creator<EnterpriseContainerObject>() { // from class: com.samsung.android.knox.container.EnterpriseContainerObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnterpriseContainerObject createFromParcel(Parcel parcel) {
            return new EnterpriseContainerObject(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final EnterpriseContainerObject createFromParcel(Parcel parcel) {
            return new EnterpriseContainerObject(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnterpriseContainerObject[] newArray(int i) {
            Log.d("EnterpriseContainerObject", "EnterpriseContainerObject[] array to be created");
            return new EnterpriseContainerObject[i];
        }
    };
    public int admin;
    public int containerType;
    public String email;

    /* renamed from: id */
    public int f480id;
    public int lockType;
    public String name;

    public EnterpriseContainerObject() {
        this.f480id = -1;
        this.admin = -1;
        this.email = null;
        this.name = null;
        this.lockType = 0;
        this.containerType = 0;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getContainerAdmin() {
        return this.admin;
    }

    public final String getContainerEmail() {
        return this.email;
    }

    public final int getContainerId() {
        return this.f480id;
    }

    public final int getContainerLockType() {
        return this.lockType;
    }

    public final String getContainerName() {
        return this.name;
    }

    public final int getContainerType() {
        return this.containerType;
    }

    public final void setContainerAdmin(int i) {
        this.admin = i;
    }

    public final void setContainerEmail(String str) {
        this.email = str;
    }

    public final void setContainerId(int i) {
        this.f480id = i;
    }

    public final void setContainerLockType(int i) {
        this.lockType = i;
    }

    public final void setContainerName(String str) {
        this.name = str;
    }

    public final void setContainerType(int i) {
        this.containerType = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f480id);
        parcel.writeInt(this.admin);
        String str = this.email;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString("");
        }
        String str2 = this.name;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString("");
        }
        parcel.writeInt(this.lockType);
        parcel.writeInt(this.containerType);
    }

    public EnterpriseContainerObject(Parcel parcel) {
        this.f480id = -1;
        this.admin = -1;
        this.email = null;
        this.name = null;
        this.lockType = 0;
        this.containerType = 0;
        this.f480id = parcel.readInt();
        this.admin = parcel.readInt();
        this.email = parcel.readString();
        this.name = parcel.readString();
        this.lockType = parcel.readInt();
        this.containerType = parcel.readInt();
    }
}
