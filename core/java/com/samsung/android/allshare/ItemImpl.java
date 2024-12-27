package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.sec.android.allshare.iface.message.AllShareKey;

import java.util.ArrayList;

/* loaded from: classes3.dex */
final class ItemImpl extends Item {
    public static final Parcelable.Creator<ItemImpl> CREATOR =
            new Parcelable.Creator<
                    ItemImpl>() { // from class: com.samsung.android.allshare.ItemImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ItemImpl createFromParcel(Parcel src) {
                    return new ItemImpl(src);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ItemImpl[] newArray(int size) {
                    return new ItemImpl[size];
                }
            };
    private static final String DATETIME_FORMAT = "CCYY-MM-DDThh:mm:ss";
    private static final String DATETIME_FORMAT_WITH_MS = "CCYY-MM-DDThh:mm:ss.sss";
    private static final String DATETIME_FORMAT_WITH_MS_OFFSET = "CCYY-MM-DDThh:mm:ss.sss+hh:mm";
    private static final String DATETIME_FORMAT_WITH_MS_OFFSET_Z = "CCYY-MM-DDThh:mm:ss.sssZ";
    private static final String DATETIME_FORMAT_WITH_OFFSET = "CCYY-MM-DDThh:mm:ss+hh:mm";
    private static final String DATETIME_FORMAT_WITH_OFFSET_Z = "CCYY-MM-DDThh:mm:ssZ";
    private static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATETIME_PATTERN_WITH_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String DATETIME_PATTERN_WITH_MS_WITH_OFFSET =
            "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
    private static final String DATETIME_PATTERN_WITH_MS_WITH_OFFSET_Z =
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DATETIME_PATTERN_WITH_OFFSET = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
    private static final String DATETIME_PATTERN_WITH_OFFSET_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATE_FORMAT = "CCYY-MM-DD";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TAG = "ItemImpl";
    private Bundle mBundle;

    protected ItemImpl(Bundle bundle) {
        this.mBundle = null;
        this.mBundle = bundle;
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

    private static String getFormatter(String dateStr) {
        int dateStrLen = dateStr.length();
        if (dateStrLen == DATE_FORMAT.length()) {
            return DATE_PATTERN;
        }
        if (dateStrLen == DATETIME_FORMAT.length()) {
            return DATETIME_PATTERN;
        }
        if (dateStrLen == DATETIME_FORMAT_WITH_OFFSET.length()) {
            return DATETIME_PATTERN_WITH_OFFSET;
        }
        if (dateStrLen == DATETIME_FORMAT_WITH_OFFSET_Z.length()) {
            return DATETIME_PATTERN_WITH_OFFSET_Z;
        }
        if (dateStrLen == DATETIME_FORMAT_WITH_MS.length()) {
            return DATETIME_PATTERN_WITH_MS;
        }
        if (dateStrLen == DATETIME_FORMAT_WITH_MS_OFFSET.length()) {
            return DATETIME_PATTERN_WITH_MS_WITH_OFFSET;
        }
        if (dateStrLen == DATETIME_FORMAT_WITH_MS_OFFSET_Z.length()) {
            return DATETIME_PATTERN_WITH_MS_WITH_OFFSET_Z;
        }
        return null;
    }

    Bundle getBundle() {
        return this.mBundle;
    }

    protected final String getObjectID() {
        String objID;
        Bundle bundle = getBundle();
        if (bundle == null
                || (objID = bundle.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID)) == null) {
            return "";
        }
        return objID;
    }

    static Item getItem(Bundle b) {
        String typeStr;
        if (b == null || (typeStr = b.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE)) == null) {
            return null;
        }
        Item.MediaType type = Item.MediaType.stringToEnum(typeStr);
        switch (type) {
        }
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getBundle(), 0);
    }

    private void readFromParcel(Parcel src) {
        Bundle bundle = (Bundle) src.readParcelable(Bundle.class.getClassLoader());
        this.mBundle = bundle;
    }

    private ItemImpl(Parcel src) {
        this.mBundle = null;
        readFromParcel(src);
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
        return this.mBundle == null
                ? ""
                : this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        return this.mBundle == null
                ? ""
                : this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_EXTENSION);
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        return -1L;
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        return null;
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
    public boolean isRootFolder() {
        return false;
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        if (this.mBundle == null) {
            return Item.ContentBuildType.UNKNOWN;
        }
        String itemConstructor =
                this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
        if (itemConstructor == null || itemConstructor.isEmpty()) {
            return Item.ContentBuildType.UNKNOWN;
        }
        ItemCreator.ConstructorType conType =
                ItemCreator.ConstructorType.stringToEnum(itemConstructor);
        switch (conType) {
        }
        return Item.ContentBuildType.UNKNOWN;
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        return new ArrayList<>();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        return Item.SeekMode.NONE;
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        return -1;
    }

    final class ResourceImpl extends Item.Resource {
        public final Parcelable.Creator<ResourceImpl> CREATOR;
        private Bundle mBundle;

        ResourceImpl(Bundle bundle) {
            super();
            this.mBundle = null;
            this.CREATOR =
                    new Parcelable.Creator<
                            ResourceImpl>() { // from class:
                                              // com.samsung.android.allshare.ItemImpl.ResourceImpl.1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
                        public ResourceImpl createFromParcel(Parcel source) {
                            return new ResourceImpl(source);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
                        public ResourceImpl[] newArray(int size) {
                            return new ResourceImpl[size];
                        }
                    };
            this.mBundle = bundle;
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public Item.MediaType getType() {
            if (this.mBundle == null) {
                return Item.MediaType.ITEM_UNKNOWN;
            }
            String typeStr = this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE);
            if (typeStr == null) {
                return Item.MediaType.ITEM_UNKNOWN;
            }
            return Item.MediaType.stringToEnum(typeStr);
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public Item.SeekMode getSeekMode() {
            if (this.mBundle == null) {
                return Item.SeekMode.UNKNOWN;
            }
            String seekModeStr =
                    this.mBundle.getString(AllShareKey.BUNDLE_STRING_RESOURCE_ITEM_SEEKMODE);
            if (seekModeStr == null) {
                return Item.SeekMode.UNKNOWN;
            }
            return Item.SeekMode.stringToEnum(seekModeStr);
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public Uri getURI() {
            if (this.mBundle == null) {
                return null;
            }
            return (Uri) this.mBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public long getDuration() {
            if (this.mBundle == null) {
                return -1L;
            }
            return this.mBundle.getLong(AllShareKey.BUNDLE_LONG_RESOURCE_ITEM_DURATION);
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public long getFileSize() {
            if (this.mBundle == null) {
                return -1L;
            }
            long size = this.mBundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE);
            if (size <= 0) {
                return -1L;
            }
            return size;
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public String getMimetype() {
            if (this.mBundle == null) {
                return "";
            }
            return this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        }

        @Override // com.samsung.android.allshare.Item.Resource
        public int getBitrate() {
            if (this.mBundle == null) {
                return -1;
            }
            return this.mBundle.getInt(AllShareKey.BUNDLE_STRING_RESOURCE_ITEM_BITRATE);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mBundle, 0);
        }

        private void readFromParcel(Parcel src) {
            Bundle bundle = (Bundle) src.readParcelable(Bundle.class.getClassLoader());
            this.mBundle = bundle;
        }

        private ResourceImpl(Parcel src) {
            super();
            this.mBundle = null;
            this.CREATOR =
                    new Parcelable.Creator<
                            ResourceImpl>() { // from class:
                                              // com.samsung.android.allshare.ItemImpl.ResourceImpl.1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
                        public ResourceImpl createFromParcel(Parcel source) {
                            return new ResourceImpl(source);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
                        public ResourceImpl[] newArray(int size) {
                            return new ResourceImpl[size];
                        }
                    };
            readFromParcel(src);
        }
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        if (this.mBundle == null) {
            return "";
        }
        String channelNr = this.mBundle.getString(AllShareKey.BUNDLE_INT_ITEM_CHANNELNR);
        return channelNr;
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        if (this.mBundle == null) {
            return new ArrayList<>();
        }
        return this.mBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ITEM_CAPTION_LIST);
    }
}
