package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StringFlag implements Flag, Parcelable {
    public static final Parcelable.Creator<StringFlag> CREATOR;

    /* renamed from: default, reason: not valid java name */
    public final String f829default;

    /* renamed from: id */
    public final int f292id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.flags.StringFlag$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new StringFlag(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new StringFlag[i];
            }
        };
    }

    public /* synthetic */ StringFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringFlag)) {
            return false;
        }
        StringFlag stringFlag = (StringFlag) obj;
        return this.f292id == stringFlag.f292id && Intrinsics.areEqual(this.name, stringFlag.name) && Intrinsics.areEqual(this.namespace, stringFlag.namespace) && Intrinsics.areEqual(this.f829default, stringFlag.f829default) && this.teamfood == stringFlag.teamfood && this.overridden == stringFlag.overridden;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.f829default, AppInfo$$ExternalSyntheticOutline0.m41m(this.namespace, AppInfo$$ExternalSyntheticOutline0.m41m(this.name, Integer.hashCode(this.f292id) * 31, 31), 31), 31);
        boolean z = this.teamfood;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m41m + i) * 31;
        boolean z2 = this.overridden;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        int i = this.f292id;
        String str = this.name;
        String str2 = this.namespace;
        String str3 = this.f829default;
        boolean z = this.teamfood;
        boolean z2 = this.overridden;
        StringBuilder m61m = AbstractC0662xaf167275.m61m("StringFlag(id=", i, ", name=", str, ", namespace=");
        AppOpItem$$ExternalSyntheticOutline0.m97m(m61m, str2, ", default=", str3, ", teamfood=");
        m61m.append(z);
        m61m.append(", overridden=");
        m61m.append(z2);
        m61m.append(")");
        return m61m.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f292id);
        parcel.writeString(this.name);
        parcel.writeString(this.namespace);
        parcel.writeString(this.f829default);
    }

    public StringFlag(int i, String str, String str2, String str3, boolean z, boolean z2) {
        this.f292id = i;
        this.name = str;
        this.namespace = str2;
        this.f829default = str3;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ StringFlag(int i, String str, String str2, String str3, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? false : z2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private StringFlag(Parcel parcel) {
        this(r1, r3, r4, r10 == null ? "" : r10, false, false, 48, null);
        int readInt = parcel.readInt();
        String readString = parcel.readString();
        String str = readString == null ? "" : readString;
        String readString2 = parcel.readString();
        String str2 = readString2 == null ? "" : readString2;
        String readString3 = parcel.readString();
    }
}
