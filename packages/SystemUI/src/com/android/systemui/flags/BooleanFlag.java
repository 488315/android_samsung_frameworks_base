package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class BooleanFlag implements ParcelableFlag {
    public static final Parcelable.Creator<BooleanFlag> CREATOR;

    /* renamed from: default, reason: not valid java name */
    public final boolean f36default;
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
        CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(final Parcel parcel) {
                return new BooleanFlag(parcel) { // from class: com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1$createFromParcel$1
                    {
                        DefaultConstructorMarker defaultConstructorMarker = null;
                    }
                };
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new BooleanFlag[i];
            }
        };
    }

    public /* synthetic */ BooleanFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // com.android.systemui.flags.Flag
    public String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.Flag
    public String getNamespace() {
        return this.namespace;
    }

    public boolean getOverridden() {
        return this.overridden;
    }

    public boolean getTeamfood() {
        return this.teamfood;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(0);
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeBoolean(this.f36default);
        parcel.writeBoolean(getTeamfood());
        parcel.writeBoolean(getOverridden());
    }

    public BooleanFlag(String str, String str2, boolean z, boolean z2, boolean z3) {
        this.name = str;
        this.namespace = str2;
        this.f36default = z;
        this.teamfood = z2;
        this.overridden = z3;
    }

    public /* synthetic */ BooleanFlag(String str, String str2, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3);
    }

    private BooleanFlag(int i, String str, String str2, boolean z, boolean z2, boolean z3) {
        this(str, str2, z, z2, z3);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private BooleanFlag(android.os.Parcel r8) {
        /*
            r7 = this;
            int r1 = r8.readInt()
            java.lang.String r0 = r8.readString()
            java.lang.String r2 = ""
            if (r0 != 0) goto Le
            r3 = r2
            goto Lf
        Le:
            r3 = r0
        Lf:
            java.lang.String r0 = r8.readString()
            if (r0 != 0) goto L17
            r4 = r2
            goto L18
        L17:
            r4 = r0
        L18:
            boolean r5 = r8.readBoolean()
            boolean r6 = r8.readBoolean()
            boolean r8 = r8.readBoolean()
            r0 = r7
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.flags.BooleanFlag.<init>(android.os.Parcel):void");
    }
}
