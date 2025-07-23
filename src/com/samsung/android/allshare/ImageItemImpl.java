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

/* loaded from: classes6.dex */
final class ImageItemImpl extends Item implements IBundleHolder {
    public static final Parcelable.Creator<ImageItemImpl> CREATOR = new Parcelable.Creator<ImageItemImpl>() { // from class: com.samsung.android.allshare.ImageItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageItemImpl createFromParcel(Parcel parcel) {
            return new ImageItemImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageItemImpl[] newArray(int i) {
            return new ImageItemImpl[i];
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
    public long getDuration() {
        return -1L;
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        return false;
    }

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
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return -1L;
        }
        return itemImpl.getFileSize();
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return "";
        }
        return itemImpl.getTitle();
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return null;
        }
        return itemImpl.getURI();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && (obj instanceof ImageItemImpl) && hashCode() == obj.hashCode();
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
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return null;
        }
        return itemImpl.getBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(getBundle());
    }

    private ImageItemImpl(Parcel parcel) {
        this.mItemImpl = new ItemImpl(parcel.readBundle(Bundle.class.getClassLoader()));
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
    public String getGenre() {
        return "";
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
        return itemImpl.getSeekMode();
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return -1;
        }
        return itemImpl.getBitrate();
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
