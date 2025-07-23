package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.Media;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MediaWrapperV1 extends ContentWrapper {
    private final String contentUri;
    private final String id;
    private final long mediaId;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<MediaWrapperV1> CREATOR = new Creator();

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
            return new MediaWrapperV1(parcel.readString(), parcel.readString(), parcel.readLong());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaWrapperV1[i];
        }
    }

    public MediaWrapperV1(String str, String str2, long j) {
        this.id = str;
        this.contentUri = str2;
        this.mediaId = j;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getContentUri() {
        return this.contentUri;
    }

    public final String getId() {
        return this.id;
    }

    public final long getMediaId() {
        return this.mediaId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.contentUri);
        parcel.writeLong(this.mediaId);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public Media toContent() {
        return new Media(this.id, this.contentUri, this.mediaId);
    }
}
