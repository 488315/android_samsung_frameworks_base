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

/* loaded from: classes6.dex */
public class SimpleImageItem extends Item implements IBundleHolder {
    private Bundle mBundle;

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

    public SimpleImageItem(Bundle bundle) {
        this.mBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        Bundle bundle = this.mBundle;
        return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        Bundle bundle = this.mBundle;
        return (Uri) (bundle == null ? null : bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mBundle;
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public long getFileSize() {
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return -1L;
        }
        return bundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getMimetype() {
        Bundle bundle = this.mBundle;
        return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        throw new IllegalAccessError("SimpleImageItem doesn't support this method.");
    }
}
