package com.samsung.android.allshare;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;

/* loaded from: classes3.dex */
final class ImageItemImpl extends Item implements IBundleHolder {
    public static final Parcelable.Creator<ImageItemImpl> CREATOR = new Parcelable.Creator<ImageItemImpl>() { // from class: com.samsung.android.allshare.ImageItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageItemImpl createFromParcel(Parcel src) {
            return new ImageItemImpl(src);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageItemImpl[] newArray(int size) {
            return new ImageItemImpl[size];
        }
    };
    private final ItemImpl mItemImpl;

    ImageItemImpl(Bundle bundle) {
        this.mItemImpl = new ItemImpl(bundle);
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_IMAGE_ITEM_THUMBNAIL));
    }

    public String getResolution() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_IMAGE_ITEM_RESOLUTION);
    }

    public Location getLocation() {
        return (Location) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_IMAGE_ITEM_LOCATION));
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
        if (this.mItemImpl == null) {
            return -1L;
        }
        return this.mItemImpl.getFileSize();
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        if (this.mItemImpl == null) {
            return "";
        }
        return this.mItemImpl.getTitle();
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        if (this.mItemImpl == null) {
            return null;
        }
        return this.mItemImpl.getURI();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && (o instanceof ImageItemImpl) && hashCode() == o.hashCode()) {
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
        if (this.mItemImpl == null) {
            return null;
        }
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

    private ImageItemImpl(Parcel src) {
        Bundle bundle = src.readBundle(Bundle.class.getClassLoader());
        this.mItemImpl = new ItemImpl(bundle);
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        return -1L;
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
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
        return this.mItemImpl.getSeekMode();
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        if (this.mItemImpl == null) {
            return -1;
        }
        return this.mItemImpl.getBitrate();
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
