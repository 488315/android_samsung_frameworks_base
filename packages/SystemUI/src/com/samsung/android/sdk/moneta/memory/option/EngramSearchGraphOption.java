package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramSearchGraphOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchGraphOption> CREATOR = new Creator();
    private final String destIri;
    private final String keywords;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchGraphOption(parcel.readString(), parcel.readString(), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchGraphOption[i];
        }
    }

    public final class WrapBuilder {
        public final String destIri;
        public final String keywords;

        public WrapBuilder(String str, String str2) {
            this.keywords = str;
            this.destIri = str2;
        }
    }

    public /* synthetic */ EngramSearchGraphOption(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getDestIri() {
        return this.destIri;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeString(this.destIri);
    }

    private EngramSearchGraphOption(String str, String str2) {
        this.keywords = str;
        this.destIri = str2;
    }
}
