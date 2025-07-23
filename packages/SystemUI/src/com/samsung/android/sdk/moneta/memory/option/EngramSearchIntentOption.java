package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchIntentOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchIntentOption> CREATOR = new Creator();
    private final String keywords;
    private final Integer limit;
    private final Integer offset;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchIntentOption(parcel.readString(), parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchIntentOption[i];
        }
    }

    public /* synthetic */ EngramSearchIntentOption(String str, Integer num, Integer num2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, num, num2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    public final Integer getLimit() {
        return this.limit;
    }

    public final Integer getOffset() {
        return this.offset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        Integer num = this.limit;
        if (num == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(num.intValue());
        }
        Integer num2 = this.offset;
        if (num2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(num2.intValue());
        }
    }

    private EngramSearchIntentOption(String str, Integer num, Integer num2) {
        this.keywords = str;
        this.limit = num;
        this.offset = num2;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrapBuilder {
        public final String keywords;
        public final Integer limit;
        public final Integer offset;

        public WrapBuilder(String str, Integer num, Integer num2) {
            this.keywords = str;
            this.limit = num;
            this.offset = num2;
        }

        public /* synthetic */ WrapBuilder(String str, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? 20 : num, (i & 4) != 0 ? 0 : num2);
        }
    }

    public /* synthetic */ EngramSearchIntentOption(String str, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? 20 : num, (i & 4) != 0 ? 0 : num2);
    }
}
