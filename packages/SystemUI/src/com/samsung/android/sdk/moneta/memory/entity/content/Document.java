package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Document extends Content {
    public static final Parcelable.Creator<Document> CREATOR = new Creator();
    private final String contentUri;
    private final String id;
    private final long rawFileId;
    private final String title;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Document(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readLong());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Document[i];
        }
    }

    public Document(String str, String str2, String str3, long j) {
        this.id = str;
        this.contentUri = str2;
        this.title = str3;
        this.rawFileId = j;
    }

    public static /* synthetic */ Document copy$default(Document document, String str, String str2, String str3, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = document.id;
        }
        if ((i & 2) != 0) {
            str2 = document.contentUri;
        }
        if ((i & 4) != 0) {
            str3 = document.title;
        }
        if ((i & 8) != 0) {
            j = document.rawFileId;
        }
        String str4 = str3;
        return document.copy(str, str2, str4, j);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.contentUri;
    }

    public final String component3() {
        return this.title;
    }

    public final long component4() {
        return this.rawFileId;
    }

    public final Document copy(String str, String str2, String str3, long j) {
        return new Document(str, str2, str3, j);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Document)) {
            return false;
        }
        Document document = (Document) obj;
        return Intrinsics.areEqual(this.id, document.id) && Intrinsics.areEqual(this.contentUri, document.contentUri) && Intrinsics.areEqual(this.title, document.title) && this.rawFileId == document.rawFileId;
    }

    public final String getContentUri() {
        return this.contentUri;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.content.Content
    public String getId() {
        return this.id;
    }

    public final long getRawFileId() {
        return this.rawFileId;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Long.hashCode(this.rawFileId) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.contentUri), 31, this.title);
    }

    public String toString() {
        return "Document(id=" + this.id + ", contentUri=" + this.contentUri + ", title=" + this.title + ", rawFileId=" + this.rawFileId + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.contentUri);
        parcel.writeString(this.title);
        parcel.writeLong(this.rawFileId);
    }
}
