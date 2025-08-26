package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchIntentOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramSearchIntentOptionWrapperV1 implements Parcelable {
    private final String keywords;
    private final Integer limit;
    private final Integer offset;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchIntentOptionWrapperV1> CREATOR = new Creator();

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
            return new EngramSearchIntentOptionWrapperV1(parcel.readString(), parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), parcel.readInt() != 0 ? Integer.valueOf(parcel.readInt()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchIntentOptionWrapperV1[i];
        }
    }

    public EngramSearchIntentOptionWrapperV1(String str, Integer num, Integer num2) {
        this.keywords = str;
        this.limit = num;
        this.offset = num2;
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

    public final EngramSearchIntentOption toOption() {
        EngramSearchIntentOption.WrapBuilder wrapBuilder = new EngramSearchIntentOption.WrapBuilder(this.keywords, this.limit, this.offset);
        return new EngramSearchIntentOption(wrapBuilder.keywords, wrapBuilder.limit, wrapBuilder.offset, null);
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

    public /* synthetic */ EngramSearchIntentOptionWrapperV1(String str, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? 20 : num, (i & 4) != 0 ? 0 : num2);
    }
}
