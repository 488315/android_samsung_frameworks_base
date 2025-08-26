package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class BooleanFlag implements ParcelableFlag {
    public static final Parcelable.Creator<BooleanFlag> CREATOR;

    /* renamed from: default, reason: not valid java name */
    public final boolean f40default;
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
        parcel.writeBoolean(this.f40default);
        parcel.writeBoolean(getTeamfood());
        parcel.writeBoolean(getOverridden());
    }

    public BooleanFlag(String str, String str2, boolean z, boolean z2, boolean z3) {
        this.name = str;
        this.namespace = str2;
        this.f40default = z;
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
    private BooleanFlag(Parcel parcel) {
        int i = parcel.readInt();
        String string = parcel.readString();
        string = string == null ? "" : string;
        String string2 = parcel.readString();
        this(i, string, string2 == null ? "" : string2, parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
    }
}
