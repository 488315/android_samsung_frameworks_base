package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;

/* loaded from: classes6.dex */
final class AudioItemImpl extends Item implements IBundleHolder, Parcelable {
    public static final Parcelable.Creator<AudioItemImpl> CREATOR = new Parcelable.Creator<AudioItemImpl>() { // from class: com.samsung.android.allshare.AudioItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioItemImpl createFromParcel(Parcel parcel) {
            return new AudioItemImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioItemImpl[] newArray(int i) {
            return new AudioItemImpl[i];
        }
    };
    private final ItemImpl mItemImpl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        return false;
    }

    AudioItemImpl(Bundle bundle) {
        this.mItemImpl = new ItemImpl(bundle);
    }

    public Uri getAlbumArt() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_AUDIO_ITEM_ALBUMART));
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_ALBUM_TITLE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_ARTIST);
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
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_EXTENSION);
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
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_GENRE);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && (obj instanceof AudioItemImpl) && hashCode() == obj.hashCode();
    }

    public int hashCode() {
        String objectID;
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null || (objectID = itemImpl.getObjectID()) == null) {
            return -1;
        }
        return objectID.hashCode();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mItemImpl.getBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(getBundle());
    }

    private AudioItemImpl(Parcel parcel) {
        this.mItemImpl = new ItemImpl(parcel.readBundle(Bundle.class.getClassLoader()));
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_AUDIO_ITEM_ALBUMART));
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return Item.ContentBuildType.UNKNOWN;
        }
        return itemImpl.getContentBuildType();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return new ArrayList<>();
        }
        return itemImpl.getSubtitleList();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return Item.SeekMode.UNKNOWN;
        }
        Bundle bundle = itemImpl.getBundle();
        if (bundle == null) {
            return Item.SeekMode.UNKNOWN;
        }
        String string = bundle.getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_SEEKMODE);
        if (string == null) {
            return Item.SeekMode.UNKNOWN;
        }
        return Item.SeekMode.stringToEnum(string);
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        Bundle bundle;
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null || (bundle = itemImpl.getBundle()) == null) {
            return -1;
        }
        return bundle.getInt(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_BITRATE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return "";
        }
        return itemImpl.getChannelNr();
    }
}
