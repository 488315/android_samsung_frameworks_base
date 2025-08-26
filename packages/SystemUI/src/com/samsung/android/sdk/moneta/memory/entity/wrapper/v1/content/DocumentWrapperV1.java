package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.Document;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class DocumentWrapperV1 extends ContentWrapper {
    private final String contentUri;
    private final String id;
    private final long rawFileId;
    private final String title;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<DocumentWrapperV1> CREATOR = new Creator();

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
            return new DocumentWrapperV1(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readLong());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DocumentWrapperV1[i];
        }
    }

    public DocumentWrapperV1(String str, String str2, String str3, long j) {
        this.id = str;
        this.contentUri = str2;
        this.title = str3;
        this.rawFileId = j;
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

    public final long getRawFileId() {
        return this.rawFileId;
    }

    public final String getTitle() {
        return this.title;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.contentUri);
        parcel.writeString(this.title);
        parcel.writeLong(this.rawFileId);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public Document toContent() {
        return new Document(this.id, this.contentUri, this.title, this.rawFileId);
    }
}
