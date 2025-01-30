package com.samsung.android.allshare;

import android.location.Location;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.IBundleHolder;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes5.dex */
class FolderItemImpl extends Item implements IBundleHolder {
    public static final Parcelable.Creator<FolderItemImpl> CREATOR = new Parcelable.Creator<FolderItemImpl>() { // from class: com.samsung.android.allshare.FolderItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FolderItemImpl createFromParcel(Parcel src) {
            return new FolderItemImpl(src);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FolderItemImpl[] newArray(int size) {
            return new FolderItemImpl[size];
        }
    };
    private final ItemImpl mItemImpl;

    FolderItemImpl(Bundle bundle) {
        this.mItemImpl = new ItemImpl(bundle);
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        return false;
    }

    @Override // com.samsung.android.allshare.Item
    public Item.MediaType getType() {
        return Item.MediaType.ITEM_FOLDER;
    }

    @Override // com.samsung.android.allshare.Item
    public Date getDate() {
        return this.mItemImpl.getDate();
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        return this.mItemImpl.getTitle();
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        return this.mItemImpl.getURI();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && (o instanceof FolderItemImpl) && hashCode() == o.hashCode()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String objID = this.mItemImpl.getObjectID();
        return objID.hashCode();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mItemImpl.getBundle();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(getBundle());
    }

    private FolderItemImpl(Parcel src) {
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
    public String getExtension() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public long getFileSize() {
        return -1L;
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public Location getLocation() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public String getMimetype() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public String getResolution() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        return this.mItemImpl.getContentBuildType();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.WebContentBuilder.DeliveryMode getWebContentDeliveryMode() {
        return this.mItemImpl.getWebContentDeliveryMode();
    }

    @Override // com.samsung.android.allshare.Item
    @Deprecated
    public Item.WebContentBuilder.PlayMode getWebContentPlayMode() {
        return this.mItemImpl.getWebContentPlayMode();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        return this.mItemImpl.getSubtitleList();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        return this.mItemImpl.getSeekMode();
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        return this.mItemImpl.getBitrate();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Item.Resource> getResourceList() {
        return this.mItemImpl.getResourceList();
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        return null;
    }
}
