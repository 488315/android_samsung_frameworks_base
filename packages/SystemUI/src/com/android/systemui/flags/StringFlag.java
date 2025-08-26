package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class StringFlag implements ParcelableFlag {
    public static final Parcelable.Creator<StringFlag> CREATOR;

    /* renamed from: default, reason: not valid java name */
    public final String f41default;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringFlag)) {
            return false;
        }
        StringFlag stringFlag = (StringFlag) obj;
        return Intrinsics.areEqual(this.name, stringFlag.name) && Intrinsics.areEqual(this.namespace, stringFlag.namespace) && Intrinsics.areEqual(this.f41default, stringFlag.f41default) && this.teamfood == stringFlag.teamfood && this.overridden == stringFlag.overridden;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getNamespace() {
        return this.namespace;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.overridden) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace), 31, this.f41default), 31, this.teamfood);
    }

    public final String toString() {
        String str = this.name;
        String str2 = this.namespace;
        String str3 = this.f41default;
        boolean z = this.teamfood;
        boolean z2 = this.overridden;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("StringFlag(name=", str, ", namespace=", str2, ", default=");
        sbM.append(str3);
        sbM.append(", teamfood=");
        sbM.append(z);
        sbM.append(", overridden=");
        return MoveResult$$ExternalSyntheticOutline0.m(sbM, z2, ")");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeString(this.namespace);
        parcel.writeString(this.f41default);
    }

    public StringFlag(String str, String str2, String str3, boolean z, boolean z2) {
        this.name = str;
        this.namespace = str2;
        this.f41default = str3;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ StringFlag(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
    }

    private StringFlag(int i, String str, String str2, String str3) {
        this(str, str2, str3, false, false, 24, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private StringFlag(Parcel parcel) {
        int i = parcel.readInt();
        String string = parcel.readString();
        string = string == null ? "" : string;
        String string2 = parcel.readString();
        string2 = string2 == null ? "" : string2;
        String string3 = parcel.readString();
        this(i, string, string2, string3 != null ? string3 : "");
    }
}
