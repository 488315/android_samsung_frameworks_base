package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchGraphOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramSearchGraphOptionWrapperV1 implements Parcelable {
    private final String destIri;
    private final String keywords;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchGraphOptionWrapperV1> CREATOR = new Creator();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchGraphOptionWrapperV1(parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchGraphOptionWrapperV1[i];
        }
    }

    public EngramSearchGraphOptionWrapperV1(String str, String str2) {
        this.keywords = str;
        this.destIri = str2;
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

    public final EngramSearchGraphOption toOption() {
        EngramSearchGraphOption.WrapBuilder wrapBuilder = new EngramSearchGraphOption.WrapBuilder(this.keywords, this.destIri);
        return new EngramSearchGraphOption(wrapBuilder.keywords, wrapBuilder.destIri, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeString(this.destIri);
    }
}
