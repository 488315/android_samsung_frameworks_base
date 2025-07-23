package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class What implements Parcelable {
    public static final Parcelable.Creator<What> CREATOR = new Creator();
    private final String category;
    private final boolean isMain;
    private final String sourcePackage;
    private final String sourceUri;
    private final String title;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new What(parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new What[i];
        }
    }

    public What(String str, String str2, boolean z, String str3, String str4) {
        this.title = str;
        this.category = str2;
        this.isMain = z;
        this.sourcePackage = str3;
        this.sourceUri = str4;
    }

    public static /* synthetic */ What copy$default(What what, String str, String str2, boolean z, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = what.title;
        }
        if ((i & 2) != 0) {
            str2 = what.category;
        }
        if ((i & 4) != 0) {
            z = what.isMain;
        }
        if ((i & 8) != 0) {
            str3 = what.sourcePackage;
        }
        if ((i & 16) != 0) {
            str4 = what.sourceUri;
        }
        String str5 = str4;
        boolean z2 = z;
        return what.copy(str, str2, z2, str3, str5);
    }

    public final String component1() {
        return this.title;
    }

    public final String component2() {
        return this.category;
    }

    public final boolean component3() {
        return this.isMain;
    }

    public final String component4() {
        return this.sourcePackage;
    }

    public final String component5() {
        return this.sourceUri;
    }

    public final What copy(String str, String str2, boolean z, String str3, String str4) {
        return new What(str, str2, z, str3, str4);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof What)) {
            return false;
        }
        What what = (What) obj;
        return Intrinsics.areEqual(this.title, what.title) && Intrinsics.areEqual(this.category, what.category) && this.isMain == what.isMain && Intrinsics.areEqual(this.sourcePackage, what.sourcePackage) && Intrinsics.areEqual(this.sourceUri, what.sourceUri);
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getSourcePackage() {
        return this.sourcePackage;
    }

    public final String getSourceUri() {
        return this.sourceUri;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return this.sourceUri.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.title.hashCode() * 31, 31, this.category), 31, this.isMain), 31, this.sourcePackage);
    }

    public final boolean isMain() {
        return this.isMain;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("What(title=");
        sb.append(this.title);
        sb.append(", category=");
        sb.append(this.category);
        sb.append(", isMain=");
        sb.append(this.isMain);
        sb.append(", sourcePackage=");
        sb.append(this.sourcePackage);
        sb.append(", sourceUri=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.sourceUri, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.category);
        parcel.writeInt(this.isMain ? 1 : 0);
        parcel.writeString(this.sourcePackage);
        parcel.writeString(this.sourceUri);
    }
}
