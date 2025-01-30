package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BooleanFlag implements Flag, Parcelable {
    public static final Parcelable.Creator<BooleanFlag> CREATOR;

    /* renamed from: default, reason: not valid java name */
    public final boolean f828default;

    /* renamed from: id */
    public final int f288id;
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

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public int getId() {
        return this.f288id;
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
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeBoolean(Boolean.valueOf(this.f828default).booleanValue());
        parcel.writeBoolean(getTeamfood());
        parcel.writeBoolean(getOverridden());
    }

    public BooleanFlag(int i, String str, String str2, boolean z, boolean z2, boolean z3) {
        this.f288id = i;
        this.name = str;
        this.namespace = str2;
        this.f828default = z;
        this.teamfood = z2;
        this.overridden = z3;
    }

    public /* synthetic */ BooleanFlag(int i, String str, String str2, boolean z, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? false : z2, (i2 & 32) != 0 ? false : z3);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private BooleanFlag(Parcel parcel) {
        this(r1, r3, r0 == null ? "" : r0, parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
        int readInt = parcel.readInt();
        String readString = parcel.readString();
        String str = readString == null ? "" : readString;
        String readString2 = parcel.readString();
    }
}
