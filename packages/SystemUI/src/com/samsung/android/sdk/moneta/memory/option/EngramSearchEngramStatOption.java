package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchEngramStatOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchEngramStatOption> CREATOR = new Creator();
    private final String keywords;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchEngramStatOption(parcel.readString(), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchEngramStatOption[i];
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrapBuilder {
        public final String keywords;

        public WrapBuilder(String str) {
            this.keywords = str;
        }
    }

    public /* synthetic */ EngramSearchEngramStatOption(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
    }

    private EngramSearchEngramStatOption(String str) {
        this.keywords = str;
    }
}
