package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class StringFlag implements ParcelableFlag {
    public static final Parcelable.Creator<StringFlag> CREATOR;

    /* renamed from: default, reason: not valid java name */
    public final String f37default;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;

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

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringFlag)) {
            return false;
        }
        StringFlag stringFlag = (StringFlag) obj;
        return Intrinsics.areEqual(this.name, stringFlag.name) && Intrinsics.areEqual(this.namespace, stringFlag.namespace) && Intrinsics.areEqual(this.f37default, stringFlag.f37default) && this.teamfood == stringFlag.teamfood && this.overridden == stringFlag.overridden;
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
        return Boolean.hashCode(this.overridden) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace), 31, this.f37default), 31, this.teamfood);
    }

    public final String toString() {
        String str = this.name;
        String str2 = this.namespace;
        String str3 = this.f37default;
        boolean z = this.teamfood;
        boolean z2 = this.overridden;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("StringFlag(name=", str, ", namespace=", str2, ", default=");
        m.append(str3);
        m.append(", teamfood=");
        m.append(z);
        m.append(", overridden=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(m, z2, ")");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeString(this.namespace);
        parcel.writeString(this.f37default);
    }

    public StringFlag(String str, String str2, String str3, boolean z, boolean z2) {
        this.name = str;
        this.namespace = str2;
        this.f37default = str3;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ StringFlag(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
    }

    private StringFlag(int i, String str, String str2, String str3) {
        this(str, str2, str3, false, false, 24, null);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private StringFlag(android.os.Parcel r5) {
        /*
            r4 = this;
            int r0 = r5.readInt()
            java.lang.String r1 = r5.readString()
            java.lang.String r2 = ""
            if (r1 != 0) goto Ld
            r1 = r2
        Ld:
            java.lang.String r3 = r5.readString()
            if (r3 != 0) goto L14
            r3 = r2
        L14:
            java.lang.String r5 = r5.readString()
            if (r5 != 0) goto L1b
            goto L1c
        L1b:
            r2 = r5
        L1c:
            r4.<init>(r0, r1, r3, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.flags.StringFlag.<init>(android.os.Parcel):void");
    }
}
