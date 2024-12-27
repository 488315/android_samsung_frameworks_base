package com.samsung.android.allshare.extension.impl;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

import com.samsung.android.allshare.Caption;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.Subtitle;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;

import java.util.ArrayList;

public class SimpleFolderItem extends Item implements IBundleHolder {
    private Bundle mBundle;

    public SimpleFolderItem(Bundle bundle) {
        this.mBundle = null;
        this.mBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        return -1L;
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        return this.mBundle == null
                ? ""
                : this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        return (Uri)
                (this.mBundle == null
                        ? null
                        : this.mBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mBundle);
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mBundle;
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public long getFileSize() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getMimetype() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        throw new IllegalAccessError("SimpleFolderItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        return null;
    }
}
