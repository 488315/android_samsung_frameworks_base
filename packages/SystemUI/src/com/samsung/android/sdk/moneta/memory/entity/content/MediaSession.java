package com.samsung.android.sdk.moneta.memory.entity.content;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class MediaSession extends Content {
    public static final Parcelable.Creator<MediaSession> CREATOR = new Creator();
    private final String albumArtBitmap;
    private final Uri albumArtUri;
    private final String albumTitle;
    private final String artist;
    private final Long duration;
    private final String id;
    private final String mediaId;
    private final MediaType mediaType;
    private final String title;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MediaSession(parcel.readString(), parcel.readString(), MediaType.valueOf(parcel.readString()), parcel.readString(), parcel.readString(), (Uri) parcel.readParcelable(MediaSession.class.getClassLoader()), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaSession[i];
        }
    }

    public MediaSession(String str, String str2, MediaType mediaType, String str3, String str4, Uri uri, Long l, String str5, String str6) {
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

    public static /* synthetic */ MediaSession copy$default(MediaSession mediaSession, String str, String str2, MediaType mediaType, String str3, String str4, Uri uri, Long l, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = mediaSession.id;
        }
        if ((i & 2) != 0) {
            str2 = mediaSession.mediaId;
        }
        if ((i & 4) != 0) {
            mediaType = mediaSession.mediaType;
        }
        if ((i & 8) != 0) {
            str3 = mediaSession.title;
        }
        if ((i & 16) != 0) {
            str4 = mediaSession.albumArtBitmap;
        }
        if ((i & 32) != 0) {
            uri = mediaSession.albumArtUri;
        }
        if ((i & 64) != 0) {
            l = mediaSession.duration;
        }
        if ((i & 128) != 0) {
            str5 = mediaSession.artist;
        }
        if ((i & 256) != 0) {
            str6 = mediaSession.albumTitle;
        }
        String str7 = str5;
        String str8 = str6;
        Uri uri2 = uri;
        Long l2 = l;
        String str9 = str4;
        MediaType mediaType2 = mediaType;
        return mediaSession.copy(str, str2, mediaType2, str3, str9, uri2, l2, str7, str8);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.mediaId;
    }

    public final MediaType component3() {
        return this.mediaType;
    }

    public final String component4() {
        return this.title;
    }

    public final String component5() {
        return this.albumArtBitmap;
    }

    public final Uri component6() {
        return this.albumArtUri;
    }

    public final Long component7() {
        return this.duration;
    }

    public final String component8() {
        return this.artist;
    }

    public final String component9() {
        return this.albumTitle;
    }

    public final MediaSession copy(String str, String str2, MediaType mediaType, String str3, String str4, Uri uri, Long l, String str5, String str6) {
        return new MediaSession(str, str2, mediaType, str3, str4, uri, l, str5, str6);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSession)) {
            return false;
        }
        MediaSession mediaSession = (MediaSession) obj;
        return Intrinsics.areEqual(this.id, mediaSession.id) && Intrinsics.areEqual(this.mediaId, mediaSession.mediaId) && this.mediaType == mediaSession.mediaType && Intrinsics.areEqual(this.title, mediaSession.title) && Intrinsics.areEqual(this.albumArtBitmap, mediaSession.albumArtBitmap) && Intrinsics.areEqual(this.albumArtUri, mediaSession.albumArtUri) && Intrinsics.areEqual(this.duration, mediaSession.duration) && Intrinsics.areEqual(this.artist, mediaSession.artist) && Intrinsics.areEqual(this.albumTitle, mediaSession.albumTitle);
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

    @Override // com.samsung.android.sdk.moneta.memory.entity.content.Content
    public String getId() {
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

    public int hashCode() {
        int iHashCode = (this.mediaType.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.mediaId)) * 31;
        String str = this.title;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.albumArtBitmap;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Uri uri = this.albumArtUri;
        int iHashCode4 = (iHashCode3 + (uri == null ? 0 : uri.hashCode())) * 31;
        Long l = this.duration;
        int iHashCode5 = (iHashCode4 + (l == null ? 0 : l.hashCode())) * 31;
        String str3 = this.artist;
        int iHashCode6 = (iHashCode5 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.albumTitle;
        return iHashCode6 + (str4 != null ? str4.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MediaSession(id=");
        sb.append(this.id);
        sb.append(", mediaId=");
        sb.append(this.mediaId);
        sb.append(", mediaType=");
        sb.append(this.mediaType);
        sb.append(", title=");
        sb.append(this.title);
        sb.append(", albumArtBitmap=");
        sb.append(this.albumArtBitmap);
        sb.append(", albumArtUri=");
        sb.append(this.albumArtUri);
        sb.append(", duration=");
        sb.append(this.duration);
        sb.append(", artist=");
        sb.append(this.artist);
        sb.append(", albumTitle=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.albumTitle, ')');
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

    public static /* synthetic */ void getAlbumArtBitmap$annotations() {
    }

    public static /* synthetic */ void getMediaId$annotations() {
    }
}
