package com.samsung.android.allshare.extension.impl;

import android.location.Location;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Parcel;
import com.samsung.android.allshare.Caption;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.Subtitle;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes5.dex */
public class SimpleVideoItem extends Item implements IBundleHolder {
  private Bundle mBundle;

  public SimpleVideoItem(Bundle bundle) {
    this.mBundle = null;
    this.mBundle = bundle;
  }

  @Override // com.samsung.android.allshare.Item
  public Date getDate() {
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  public long getDuration() {
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return -1L;
    }
    return bundle.getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
  }

  @Override // com.samsung.android.allshare.Item
  public String getTitle() {
    Bundle bundle = this.mBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
  }

  @Override // com.samsung.android.allshare.Item
  public Item.MediaType getType() {
    return Item.MediaType.ITEM_VIDEO;
  }

  @Override // com.samsung.android.allshare.Item
  public Uri getURI() {
    Bundle bundle = this.mBundle;
    return (Uri)
        (bundle == null ? null : bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI));
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
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
  public Location getLocation() {
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  public String getResolution() {
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  public Uri getSubtitle() {
    Bundle bundle = this.mBundle;
    return (Uri)
        (bundle == null
            ? null
            : bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE));
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
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  public Item.WebContentBuilder.DeliveryMode getWebContentDeliveryMode() {
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  @Deprecated
  public Item.WebContentBuilder.PlayMode getWebContentPlayMode() {
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
  public ArrayList<Item.Resource> getResourceList() {
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  public String getChannelNr() {
    throw new IllegalAccessError("SimpleVideoItem doesn't support this method.");
  }

  @Override // com.samsung.android.allshare.Item
  public ArrayList<Caption> getCaptionList() {
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return new ArrayList<>();
    }
    return bundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ITEM_CAPTION_LIST);
  }
}
