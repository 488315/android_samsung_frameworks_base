package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.MediaSession;
import com.samsung.android.sdk.moneta.memory.entity.content.MediaType;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class MediaSessionWrapperV1 extends ContentWrapper {
    private final String albumArtBitmap;
    private final Uri albumArtUri;
    private final String albumTitle;
    private final String artist;
    private final Long duration;
    private final String id;
    private final String mediaId;
    private final MediaType mediaType;
    private final String title;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<MediaSessionWrapperV1> CREATOR = new Creator();

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
            return new MediaSessionWrapperV1(parcel.readString(), parcel.readString(), MediaType.valueOf(parcel.readString()), parcel.readString(), parcel.readString(), (Uri) parcel.readParcelable(MediaSessionWrapperV1.class.getClassLoader()), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaSessionWrapperV1[i];
        }
    }

    public MediaSessionWrapperV1(String str, String str2, MediaType mediaType, String str3, String str4, Uri uri, Long l, String str5, String str6) {
        this.id = str;
        this.mediaId = str2;
        this.mediaType = mediaType;
        this.title = str3;
        this.albumArtBitmap = str4;
        this.albumArtUri = uri;
        this.duration = l;
        this.artist = str5;
        this.albumTitle = str6;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getAlbumArtBitmap() {
        return this.albumArtBitmap;
    }

    public final Uri getAlbumArtUri() {
        return this.albumArtUri;
    }

    public final String getAlbumTitle() {
        return this.albumTitle;
    }

    public final String getArtist() {
        return this.artist;
    }

    public final Long getDuration() {
        return this.duration;
    }

    public final String getId() {
        return this.id;
    }

    public final String getMediaId() {
        return this.mediaId;
    }

    public final MediaType getMediaType() {
        return this.mediaType;
    }

    public final String getTitle() {
        return this.title;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.mediaId);
        parcel.writeString(this.mediaType.name());
        parcel.writeString(this.title);
        parcel.writeString(this.albumArtBitmap);
        parcel.writeParcelable(this.albumArtUri, i);
        Long l = this.duration;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        parcel.writeString(this.artist);
        parcel.writeString(this.albumTitle);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public MediaSession toContent() {
        return new MediaSession(this.id, this.mediaId, this.mediaType, this.title, this.albumArtBitmap, this.albumArtUri, this.duration, this.artist, this.albumTitle);
    }
}
