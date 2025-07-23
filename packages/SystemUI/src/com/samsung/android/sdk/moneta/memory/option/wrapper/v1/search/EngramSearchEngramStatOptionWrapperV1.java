package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchEngramStatOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchEngramStatOptionWrapperV1 implements Parcelable {
    private final String keywords;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchEngramStatOptionWrapperV1> CREATOR = new Creator();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchEngramStatOptionWrapperV1(parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchEngramStatOptionWrapperV1[i];
        }
    }

    public EngramSearchEngramStatOptionWrapperV1(String str) {
        this.keywords = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    public final EngramSearchEngramStatOption toOption() {
        return new EngramSearchEngramStatOption(new EngramSearchEngramStatOption.WrapBuilder(this.keywords).keywords, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
    }
}
