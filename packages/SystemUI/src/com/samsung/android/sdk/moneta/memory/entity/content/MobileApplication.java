package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class MobileApplication extends Content {
    public static final Parcelable.Creator<MobileApplication> CREATOR = new Creator();
    private final List<String> altNames;
    private final String id;
    private final String name;
    private final String packageId;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MobileApplication(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createStringArrayList());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MobileApplication[i];
        }
    }

    public MobileApplication(String str, String str2, String str3, List<String> list) {
        this.id = str;
        this.packageId = str2;
        this.name = str3;
        this.altNames = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MobileApplication copy$default(MobileApplication mobileApplication, String str, String str2, String str3, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = mobileApplication.id;
        }
        if ((i & 2) != 0) {
            str2 = mobileApplication.packageId;
        }
        if ((i & 4) != 0) {
            str3 = mobileApplication.name;
        }
        if ((i & 8) != 0) {
            list = mobileApplication.altNames;
        }
        return mobileApplication.copy(str, str2, str3, list);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.packageId;
    }

    public final String component3() {
        return this.name;
    }

    public final List<String> component4() {
        return this.altNames;
    }

    public final MobileApplication copy(String str, String str2, String str3, List<String> list) {
        return new MobileApplication(str, str2, str3, list);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileApplication)) {
            return false;
        }
        MobileApplication mobileApplication = (MobileApplication) obj;
        return Intrinsics.areEqual(this.id, mobileApplication.id) && Intrinsics.areEqual(this.packageId, mobileApplication.packageId) && Intrinsics.areEqual(this.name, mobileApplication.name) && Intrinsics.areEqual(this.altNames, mobileApplication.altNames);
    }

    public final List<String> getAltNames() {
        return this.altNames;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.content.Content
    public String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPackageId() {
        return this.packageId;
    }

    public int hashCode() {
        return this.altNames.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.packageId), 31, this.name);
    }

    public String toString() {
        return "MobileApplication(id=" + this.id + ", packageId=" + this.packageId + ", name=" + this.name + ", altNames=" + this.altNames + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.packageId);
        parcel.writeString(this.name);
        parcel.writeStringList(this.altNames);
    }
}
