package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Media extends Content {
    public static final Parcelable.Creator<Media> CREATOR = new Creator();
    private final String contentUri;
    private final String id;
    private final long mediaId;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Media(parcel.readString(), parcel.readString(), parcel.readLong());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Media[i];
        }
    }

    public Media(String str, String str2, long j) {
        this.id = str;
        this.contentUri = str2;
        this.mediaId = j;
    }

    public static /* synthetic */ Media copy$default(Media media, String str, String str2, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = media.id;
        }
        if ((i & 2) != 0) {
            str2 = media.contentUri;
        }
        if ((i & 4) != 0) {
            j = media.mediaId;
        }
        return media.copy(str, str2, j);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.contentUri;
    }

    public final long component3() {
        return this.mediaId;
    }

    public final Media copy(String str, String str2, long j) {
        return new Media(str, str2, j);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Media)) {
            return false;
        }
        Media media = (Media) obj;
        return Intrinsics.areEqual(this.id, media.id) && Intrinsics.areEqual(this.contentUri, media.contentUri) && this.mediaId == media.mediaId;
    }

    public final String getContentUri() {
        return this.contentUri;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.content.Content
    public String getId() {
        return this.id;
    }

    public final long getMediaId() {
        return this.mediaId;
    }

    public int hashCode() {
        return Long.hashCode(this.mediaId) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.contentUri);
    }

    public String toString() {
        return "Media(id=" + this.id + ", contentUri=" + this.contentUri + ", mediaId=" + this.mediaId + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.contentUri);
        parcel.writeLong(this.mediaId);
    }
}
