package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;

import java.util.ArrayList;

/* loaded from: classes3.dex */
final class AudioItemImpl extends Item implements IBundleHolder, Parcelable {
    public static final Parcelable.Creator<AudioItemImpl> CREATOR =
            new Parcelable.Creator<
                    AudioItemImpl>() { // from class: com.samsung.android.allshare.AudioItemImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioItemImpl createFromParcel(Parcel src) {
                    return new AudioItemImpl(src);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioItemImpl[] newArray(int size) {
                    return new AudioItemImpl[size];
                }
            };
    private final ItemImpl mItemImpl;

    AudioItemImpl(Bundle bundle) {
        this.mItemImpl = new ItemImpl(bundle);
    }

    public Uri getAlbumArt() {
        return (Uri)
                (this.mItemImpl.getBundle() == null
                        ? null
                        : this.mItemImpl
                                .getBundle()
                                .getParcelable(AllShareKey.BUNDLE_PARCELABLE_AUDIO_ITEM_ALBUMART));
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        return this.mItemImpl.getBundle() == null
                ? ""
                : this.mItemImpl
                        .getBundle()
                        .getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_ALBUM_TITLE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        return this.mItemImpl.getBundle() == null
                ? ""
                : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_ARTIST);
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        if (this.mItemImpl.getBundle() == null) {
            return -1L;
        }
        return this.mItemImpl.getBundle().getLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION);
    }

    @Override // com.samsung.android.allshare.Item
    public String getMimetype() {
        return this.mItemImpl.getBundle() == null
                ? ""
                : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        return this.mItemImpl.getBundle() == null
                ? ""
                : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_EXTENSION);
    }

    @Override // com.samsung.android.allshare.Item
    public long getFileSize() {
        return this.mItemImpl.getFileSize();
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        return this.mItemImpl.getTitle();
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        return this.mItemImpl.getURI();
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        return this.mItemImpl.getBundle() == null
                ? ""
                : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_GENRE);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && (o instanceof AudioItemImpl) && hashCode() == o.hashCode()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String objID;
        if (this.mItemImpl == null || (objID = this.mItemImpl.getObjectID()) == null) {
            return -1;
        }
        return objID.hashCode();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mItemImpl.getBundle();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(getBundle());
    }

    private AudioItemImpl(Parcel src) {
        Bundle bundle = src.readBundle(Bundle.class.getClassLoader());
        this.mItemImpl = new ItemImpl(bundle);
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        return (Uri)
                (this.mItemImpl.getBundle() == null
                        ? null
                        : this.mItemImpl
                                .getBundle()
                                .getParcelable(AllShareKey.BUNDLE_PARCELABLE_AUDIO_ITEM_ALBUMART));
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        return false;
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        if (this.mItemImpl == null) {
            return Item.ContentBuildType.UNKNOWN;
        }
        return this.mItemImpl.getContentBuildType();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        if (this.mItemImpl == null) {
            return new ArrayList<>();
        }
        return this.mItemImpl.getSubtitleList();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        if (this.mItemImpl == null) {
            return Item.SeekMode.UNKNOWN;
        }
        Bundle bundle = this.mItemImpl.getBundle();
        if (bundle == null) {
            return Item.SeekMode.UNKNOWN;
        }
        String seekModeStr = bundle.getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_SEEKMODE);
        if (seekModeStr == null) {
            return Item.SeekMode.UNKNOWN;
        }
        Item.SeekMode seekMode = Item.SeekMode.stringToEnum(seekModeStr);
        return seekMode;
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        Bundle bundle;
        if (this.mItemImpl == null || (bundle = this.mItemImpl.getBundle()) == null) {
            return -1;
        }
        return bundle.getInt(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_BITRATE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        if (this.mItemImpl == null) {
            return "";
        }
        return this.mItemImpl.getChannelNr();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        return null;
    }
}
