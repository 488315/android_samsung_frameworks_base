package com.samsung.android.allshare;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class VideoItemImpl extends Item implements IBundleHolder {
  public static final Parcelable.Creator<VideoItemImpl> CREATOR =
      new Parcelable.Creator<
          VideoItemImpl>() { // from class: com.samsung.android.allshare.VideoItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoItemImpl createFromParcel(Parcel src) {
          return new VideoItemImpl(src);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoItemImpl[] newArray(int size) {
          return new VideoItemImpl[size];
        }
      };
  private final ItemImpl mItemImpl;

  VideoItemImpl(Bundle bundle) {
    this.mItemImpl = new ItemImpl(bundle);
  }

  @Override // com.samsung.android.allshare.Item
  public Uri getThumbnail() {
    return (Uri)
        (this.mItemImpl.getBundle() == null
            ? null
            : this.mItemImpl
                .getBundle()
                .getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_THUMBNAIL));
  }

  @Override // com.samsung.android.allshare.Item
  public Uri getSubtitle() {
    return (Uri)
        (this.mItemImpl.getBundle() == null
            ? null
            : this.mItemImpl
                .getBundle()
                .getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE));
  }

  @Override // com.samsung.android.allshare.Item
  public long getDuration() {
    if (this.mItemImpl.getBundle() == null) {
      return -1L;
    }
    return this.mItemImpl.getBundle().getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
  }

  @Override // com.samsung.android.allshare.Item
  public String getResolution() {
    return this.mItemImpl.getBundle() == null
        ? ""
        : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_VIDEO_ITEM_RESOLUTION);
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
  public Item.MediaType getType() {
    return Item.MediaType.ITEM_VIDEO;
  }

  @Override // com.samsung.android.allshare.Item
  public Date getDate() {
    return this.mItemImpl.getDate();
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

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return (o instanceof VideoItemImpl) && hashCode() == o.hashCode();
  }

  public int hashCode() {
    String objID = this.mItemImpl.getObjectID();
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

  private VideoItemImpl(Parcel src) {
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
  public String getGenre() {
    return "";
  }

  @Override // com.samsung.android.allshare.Item
  public Location getLocation() {
    return null;
  }

  @Override // com.samsung.android.allshare.Item
  public boolean isRootFolder() {
    return false;
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
    Bundle bundle = this.mItemImpl.getBundle();
    if (bundle == null) {
      return new ArrayList<>();
    }
    ArrayList<Parcelable> subtitleList =
        bundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE_LIST);
    ArrayList<Subtitle> result = new ArrayList<>();
    if (subtitleList == null) {
      return result;
    }
    Iterator<Parcelable> it = subtitleList.iterator();
    while (it.hasNext()) {
      Parcelable parcel = it.next();
      result.add(new SubtitleImpl((Bundle) parcel));
    }
    return result;
  }

  @Override // com.samsung.android.allshare.Item
  public Item.SeekMode getSeekMode() {
    Bundle bundle = this.mItemImpl.getBundle();
    if (bundle == null) {
      return Item.SeekMode.UNKNOWN;
    }
    String seekModeStr = bundle.getString(AllShareKey.BUNDLE_STRING_VIDEO_ITEM_SEEKMODE);
    if (seekModeStr == null) {
      return Item.SeekMode.UNKNOWN;
    }
    Item.SeekMode seekMode = Item.SeekMode.stringToEnum(seekModeStr);
    return seekMode;
  }

  @Override // com.samsung.android.allshare.Item
  public int getBitrate() {
    Bundle bundle = this.mItemImpl.getBundle();
    if (bundle == null) {
      return -1;
    }
    return bundle.getInt(AllShareKey.BUNDLE_STRING_VIDEO_ITEM_BITRATE);
  }

  @Override // com.samsung.android.allshare.Item
  public ArrayList<Item.Resource> getResourceList() {
    return this.mItemImpl.getResourceList();
  }

  @Override // com.samsung.android.allshare.Item
  public String getChannelNr() {
    return this.mItemImpl.getChannelNr();
  }

  @Override // com.samsung.android.allshare.Item
  public ArrayList<Caption> getCaptionList() {
    return this.mItemImpl.getCaptionList();
  }
}
