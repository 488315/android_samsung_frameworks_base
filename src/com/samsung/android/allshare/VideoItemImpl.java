package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
final class VideoItemImpl extends Item implements IBundleHolder {
    public static final Parcelable.Creator<VideoItemImpl> CREATOR = new Parcelable.Creator<VideoItemImpl>() { // from class: com.samsung.android.allshare.VideoItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoItemImpl createFromParcel(Parcel parcel) {
            return new VideoItemImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoItemImpl[] newArray(int i) {
            return new VideoItemImpl[i];
        }
    };
    private final ItemImpl mItemImpl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        return false;
    }

    VideoItemImpl(Bundle bundle) {
        this.mItemImpl = new ItemImpl(bundle);
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_THUMBNAIL));
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE));
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        if (this.mItemImpl.getBundle() == null) {
            return -1L;
        }
        return this.mItemImpl.getBundle().getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
    }

    public String getResolution() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_VIDEO_ITEM_RESOLUTION);
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof VideoItemImpl) && hashCode() == obj.hashCode();
    }

    public int hashCode() {
        return this.mItemImpl.getObjectID().hashCode();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mItemImpl.getBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(getBundle());
    }

    private VideoItemImpl(Parcel parcel) {
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
        return this.mItemImpl.getContentBuildType();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        Bundle bundle = this.mItemImpl.getBundle();
        if (bundle == null) {
            return new ArrayList<>();
        }
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE_LIST);
        ArrayList<Subtitle> arrayList = new ArrayList<>();
        if (parcelableArrayList != null) {
            Iterator it = parcelableArrayList.iterator();
            while (it.hasNext()) {
                arrayList.add(new SubtitleImpl((Bundle) ((Parcelable) it.next())));
            }
        }
        return arrayList;
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        Bundle bundle = this.mItemImpl.getBundle();
        if (bundle == null) {
            return Item.SeekMode.UNKNOWN;
        }
        String string = bundle.getString(AllShareKey.BUNDLE_STRING_VIDEO_ITEM_SEEKMODE);
        if (string == null) {
            return Item.SeekMode.UNKNOWN;
        }
        return Item.SeekMode.stringToEnum(string);
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
    public String getChannelNr() {
        return this.mItemImpl.getChannelNr();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        return this.mItemImpl.getCaptionList();
    }
}
