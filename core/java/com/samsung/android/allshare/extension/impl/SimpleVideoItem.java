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

/* loaded from: classes3.dex */
public class SimpleVideoItem extends Item implements IBundleHolder {
    private Bundle mBundle;

    public SimpleVideoItem(Bundle bundle) {
        this.mBundle = null;
        this.mBundle = bundle;
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        if (this.mBundle == null) {
            return -1L;
        }
        return this.mBundle.getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        return this.mBundle == null ? "" : this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        return (Uri) (this.mBundle == null ? null : this.mBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI));
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
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return (Uri) (this.mBundle == null ? null : this.mBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE));
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public long getFileSize() {
        if (this.mBundle == null) {
            return -1L;
        }
        return this.mBundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getMimetype() {
        return this.mBundle == null ? "" : this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        if (this.mBundle == null) {
            return new ArrayList<>();
        }
        return this.mBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ITEM_CAPTION_LIST);
    }
}
