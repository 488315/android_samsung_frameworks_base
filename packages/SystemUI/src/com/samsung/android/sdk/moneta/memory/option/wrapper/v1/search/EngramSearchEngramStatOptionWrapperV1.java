package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchEngramStatOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramSearchEngramStatOptionWrapperV1 implements Parcelable {
    private final String keywords;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchEngramStatOptionWrapperV1> CREATOR = new Creator();

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
