package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.ContentType;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchContentStatOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchContentStatOptionWrapperV1 implements Parcelable {
    private final int contentType;
    private final String keywords;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchContentStatOptionWrapperV1> CREATOR = new Creator();

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
            return new EngramSearchContentStatOptionWrapperV1(parcel.readString(), parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchContentStatOptionWrapperV1[i];
        }
    }

    public EngramSearchContentStatOptionWrapperV1(String str, int i) {
        this.keywords = str;
        this.contentType = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getContentType() {
        return this.contentType;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    public final EngramSearchContentStatOption toOption() {
        String str = this.keywords;
        ContentType.Companion companion = ContentType.Companion;
        Integer valueOf = Integer.valueOf(this.contentType);
        companion.getClass();
        ContentType fromInt = ContentType.Companion.fromInt(valueOf);
        fromInt.getClass();
        EngramSearchContentStatOption.WrapBuilder wrapBuilder = new EngramSearchContentStatOption.WrapBuilder(str, fromInt);
        return new EngramSearchContentStatOption(wrapBuilder.keywords, wrapBuilder.contentType, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeInt(this.contentType);
    }
}
