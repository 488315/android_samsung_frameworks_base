package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.security.keystore.KeyProperties;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.widget.MessagingMessage;
import com.google.android.mms.ContentType;
import com.samsung.android.allshare.ItemCreator;
import com.samsung.android.wallpaperbackup.BnRConstants;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/* loaded from: classes6.dex */
public abstract class Item implements Parcelable {
    public abstract String getAlbumTitle();

    public abstract String getArtist();

    public abstract int getBitrate();

    public abstract ArrayList<Caption> getCaptionList();

    public abstract String getChannelNr();

    public abstract ContentBuildType getContentBuildType();

    public abstract long getDuration();

    public abstract String getExtension();

    public abstract long getFileSize();

    public abstract String getGenre();

    public abstract String getMimetype();

    public abstract SeekMode getSeekMode();

    public abstract Uri getSubtitle();

    public abstract ArrayList<Subtitle> getSubtitleList();

    public abstract Uri getThumbnail();

    public abstract String getTitle();

    public abstract Uri getURI();

    public abstract boolean isRootFolder();

    protected Item() {
    }

    public enum MediaType {
        ITEM_FOLDER("ITEM_FOLDER"),
        ITEM_AUDIO("ITEM_AUDIO"),
        ITEM_IMAGE("ITEM_IMAGE"),
        ITEM_VIDEO("ITEM_VIDEO"),
        ITEM_UNKNOWN("ITEM_UNKNOWN");

        private final String enumString;

        MediaType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static MediaType stringToEnum(String str) {
            if (str == null) {
                return ITEM_UNKNOWN;
            }
            if (str.equals("ITEM_AUDIO")) {
                return ITEM_AUDIO;
            }
            if (str.equals("ITEM_FOLDER")) {
                return ITEM_FOLDER;
            }
            if (str.equals("ITEM_IMAGE")) {
                return ITEM_IMAGE;
            }
            if (str.equals("ITEM_UNKNOWN")) {
                return ITEM_UNKNOWN;
            }
            if (str.equals("ITEM_VIDEO")) {
                return ITEM_VIDEO;
            }
            return ITEM_UNKNOWN;
        }
    }

    public enum ContentAttributeType {
        CONTENT_360_VIEW("360View"),
        CONTENT_UNKNOWN("UNKNOWN");

        private final String enumString;

        ContentAttributeType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static ContentAttributeType stringToEnum(String str) {
            if (str == null) {
                return CONTENT_UNKNOWN;
            }
            if (str.equals("360View")) {
                return CONTENT_360_VIEW;
            }
            if (str.equals("UNKNOWN")) {
                return CONTENT_UNKNOWN;
            }
            return CONTENT_UNKNOWN;
        }
    }

    public enum ContentBuildType {
        LOCAL(CalendarContract.ACCOUNT_TYPE_LOCAL),
        PROVIDER("PROVIDER"),
        WEB("WEB"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        ContentBuildType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }
    }

    public enum SeekMode {
        BYTE("BYTE"),
        TIME(SystemNotificationChannels.TIME),
        ANY("ANY"),
        NONE(KeyProperties.DIGEST_NONE),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        SeekMode(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static SeekMode stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("ANY")) {
                return ANY;
            }
            if (str.equals("BYTE")) {
                return BYTE;
            }
            if (str.equals(KeyProperties.DIGEST_NONE)) {
                return NONE;
            }
            if (str.equals(SystemNotificationChannels.TIME)) {
                return TIME;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public static class LocalContentBuilder {
        private String mFilepath;
        private String mMimetype;
        private String mTitle = null;
        private String mSubtitlePath = null;
        private ArrayList<Caption> mCaptionList = new ArrayList<>();
        private ContentAttributeType mContentAttribute = ContentAttributeType.CONTENT_UNKNOWN;

        public LocalContentBuilder(String str, String str2) {
            this.mFilepath = null;
            this.mMimetype = null;
            this.mFilepath = str;
            this.mMimetype = str2;
        }

        public LocalContentBuilder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public LocalContentBuilder setSubtitle(String str) {
            this.mSubtitlePath = str;
            return this;
        }

        public LocalContentBuilder setCaptionList(ArrayList<Caption> arrayList) {
            this.mCaptionList = arrayList;
            return this;
        }

        public LocalContentBuilder setContentAttribute(ContentAttributeType contentAttributeType) {
            this.mContentAttribute = contentAttributeType;
            return this;
        }

        public Item build() {
            if (!checkFilePathValid(this.mSubtitlePath)) {
                this.mSubtitlePath = null;
            }
            if (this.mFilepath.startsWith("content:")) {
                return new BuilderGeneratedItem(ItemCreator.ConstructorType.LOCAL_CONTENT, this.mFilepath, this.mTitle, this.mSubtitlePath, this.mCaptionList, this.mContentAttribute, this.mMimetype);
            }
            int ordinal = Item.convertItemTypeFromMimeType(this.mMimetype).ordinal();
            if (ordinal == 1 || ordinal == 2 || ordinal == 3) {
                return new BuilderGeneratedItem(ItemCreator.ConstructorType.LOCAL_CONTENT, this.mFilepath, this.mTitle, this.mSubtitlePath, this.mCaptionList, this.mContentAttribute, this.mMimetype);
            }
            DLog.e_api("Item", "build error!");
            return null;
        }

        protected static boolean checkFilePathValid(String str) {
            if (str == null || str.length() == 0) {
                DLog.e_api("Item", "[checkFilePathValid] filePath is null or length is 0");
                return false;
            }
            if (str.startsWith("file:")) {
                str = str.substring(7);
            }
            return !str.startsWith("/data/data") && new File(str).exists();
        }
    }

    static class BuilderGeneratedItem extends Item implements IBundleHolder {
        public static final Parcelable.Creator<BuilderGeneratedItem> CREATOR = new Parcelable.Creator<BuilderGeneratedItem>() { // from class: com.samsung.android.allshare.Item.BuilderGeneratedItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BuilderGeneratedItem createFromParcel(Parcel parcel) {
                return new BuilderGeneratedItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BuilderGeneratedItem[] newArray(int i) {
                return new BuilderGeneratedItem[i];
            }
        };
        private String mAlbumTitle;
        private String mArtist;
        private ArrayList<Caption> mCaptionList;
        private ItemCreator.ConstructorType mConType;
        private ContentAttributeType mContentAttribute;
        private Date mDate;
        private WebContentBuilder.DeliveryMode mDeliveryMode;
        private long mDuration;
        private String mGenre;
        private String mItemFilepath;
        private String mItemMimetype;
        private String mItemTitle;
        private String mSubtitlePath;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.samsung.android.allshare.Item
        public int getBitrate() {
            return -1;
        }

        @Override // com.samsung.android.allshare.Item
        public long getFileSize() {
            return -1L;
        }

        @Override // com.samsung.android.allshare.Item
        public Uri getThumbnail() {
            return null;
        }

        @Override // com.samsung.android.allshare.Item
        public boolean isRootFolder() {
            return false;
        }

        private BuilderGeneratedItem(ItemCreator.ConstructorType constructorType, String str, String str2, String str3, ArrayList<Caption> arrayList, ContentAttributeType contentAttributeType, String str4) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = str;
            this.mItemMimetype = str4;
            this.mItemTitle = str2;
            this.mConType = constructorType;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mSubtitlePath = str3;
            this.mCaptionList = arrayList;
            this.mContentAttribute = contentAttributeType;
        }

        private BuilderGeneratedItem(ItemCreator.ConstructorType constructorType, Uri uri, String str, String str2, String str3, ArrayList<Caption> arrayList, ContentAttributeType contentAttributeType, WebContentBuilder.DeliveryMode deliveryMode) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = uri.toString();
            this.mItemMimetype = str2;
            this.mItemTitle = str;
            this.mConType = constructorType;
            this.mDeliveryMode = deliveryMode;
            this.mSubtitlePath = str3;
            this.mCaptionList = arrayList;
            this.mContentAttribute = contentAttributeType;
        }

        private BuilderGeneratedItem(ItemCreator.ConstructorType constructorType, Uri uri, String str, String str2, String str3, ArrayList<Caption> arrayList, ContentAttributeType contentAttributeType, WebContentBuilder.DeliveryMode deliveryMode, String str4, String str5, String str6, Date date, long j) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = uri.toString();
            this.mItemMimetype = str2;
            this.mItemTitle = str;
            this.mConType = constructorType;
            this.mDeliveryMode = deliveryMode;
            this.mSubtitlePath = str3;
            this.mCaptionList = arrayList;
            this.mContentAttribute = contentAttributeType;
            this.mArtist = str4;
            this.mAlbumTitle = str5;
            this.mGenre = str6;
            this.mDate = date;
            this.mDuration = j;
        }

        @Override // com.samsung.android.allshare.Item
        public String getTitle() {
            return this.mItemTitle;
        }

        @Override // com.samsung.android.allshare.Item
        public Uri getURI() {
            String str = this.mItemFilepath;
            if (str == null) {
                return null;
            }
            try {
                Uri parse = Uri.parse(str);
                String scheme = parse.getScheme();
                if (scheme != null && !scheme.isEmpty()) {
                    return parse;
                }
                return Uri.fromFile(new File(this.mItemFilepath));
            } catch (Exception unused) {
                return null;
            }
        }

        @Override // com.sec.android.allshare.iface.IBundleHolder
        public Bundle getBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TITLE, this.mItemTitle);
            bundle.putString(AllShareKey.BUNDLE_STRING_FILEPATH, this.mItemFilepath);
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, getURI());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, this.mItemMimetype);
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY, this.mConType.enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE, this.mDeliveryMode.enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH, this.mSubtitlePath);
            bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ITEM_CAPTION_LIST, this.mCaptionList);
            bundle.putString(AllShareKey.BUNDLE_STRING_CONTENT_ATTRIBUTE, this.mContentAttribute.enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_ARTIST, this.mArtist);
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_ALBUM_TITLE, this.mAlbumTitle);
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_GENRE, this.mGenre);
            Date date = this.mDate;
            bundle.putLong(AllShareKey.BUNDLE_DATE_ITEM_DATE, date != null ? date.getTime() : 0L);
            bundle.putLong(AllShareKey.BUNDLE_LONG_ITEM_DURATION, this.mDuration);
            return bundle;
        }

        @Override // com.samsung.android.allshare.Item
        public String getAlbumTitle() {
            String str = this.mAlbumTitle;
            return str == null ? "" : str;
        }

        @Override // com.samsung.android.allshare.Item
        public String getArtist() {
            String str = this.mArtist;
            return str == null ? "" : str;
        }

        @Override // com.samsung.android.allshare.Item
        public String getGenre() {
            String str = this.mGenre;
            return str == null ? "" : str;
        }

        @Override // com.samsung.android.allshare.Item
        public long getDuration() {
            return this.mDuration;
        }

        @Override // com.samsung.android.allshare.Item
        public Uri getSubtitle() {
            String str = this.mSubtitlePath;
            if (str == null) {
                return null;
            }
            return Uri.parse(str);
        }

        @Override // com.samsung.android.allshare.Item
        public ArrayList<Caption> getCaptionList() {
            ArrayList<Caption> arrayList = this.mCaptionList;
            return arrayList == null ? new ArrayList<>() : arrayList;
        }

        @Override // com.samsung.android.allshare.Item
        public String getMimetype() {
            return this.mItemMimetype;
        }

        @Override // com.samsung.android.allshare.Item
        public String getExtension() {
            return "";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mItemFilepath);
            parcel.writeString(this.mItemMimetype);
            parcel.writeString(this.mItemTitle);
            parcel.writeString(this.mConType.enumToString());
            parcel.writeString(this.mDeliveryMode.enumToString());
            parcel.writeString(this.mSubtitlePath);
        }

        private void readFromParcel(Parcel parcel) {
            this.mItemFilepath = parcel.readString();
            this.mItemMimetype = parcel.readString();
            this.mItemTitle = parcel.readString();
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            this.mSubtitlePath = parcel.readString();
            this.mConType = ItemCreator.ConstructorType.stringToEnum(readString);
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.stringToEnum(readString2);
        }

        private BuilderGeneratedItem(Parcel parcel) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            readFromParcel(parcel);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof BuilderGeneratedItem)) {
                return false;
            }
            BuilderGeneratedItem builderGeneratedItem = (BuilderGeneratedItem) obj;
            if (getURI() == null) {
                return builderGeneratedItem.getURI() == null;
            }
            return getURI().equals(builderGeneratedItem.getURI());
        }

        public int hashCode() {
            if (getURI() != null) {
                return getURI().hashCode();
            }
            return 0;
        }

        @Override // com.samsung.android.allshare.Item
        public ContentBuildType getContentBuildType() {
            int i = AnonymousClass1.$SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[this.mConType.ordinal()];
            if (i == 1) {
                return ContentBuildType.LOCAL;
            }
            if (i == 2) {
                return ContentBuildType.PROVIDER;
            }
            if (i == 3) {
                return ContentBuildType.WEB;
            }
            if (i == 4) {
                return ContentBuildType.UNKNOWN;
            }
            return ContentBuildType.UNKNOWN;
        }

        @Override // com.samsung.android.allshare.Item
        public ArrayList<Subtitle> getSubtitleList() {
            return new ArrayList<>();
        }

        @Override // com.samsung.android.allshare.Item
        public SeekMode getSeekMode() {
            return SeekMode.BYTE;
        }

        @Override // com.samsung.android.allshare.Item
        public String getChannelNr() {
            return "";
        }
    }

    /* renamed from: com.samsung.android.allshare.Item$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType;

        static {
            int[] iArr = new int[ItemCreator.ConstructorType.values().length];
            $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType = iArr;
            try {
                iArr[ItemCreator.ConstructorType.LOCAL_CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.MEDIA_SERVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.WEB_CONTENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class WebContentBuilder {
        private String mMimetype;
        private Uri mUri;
        private String mTitle = null;
        private DeliveryMode mDeliveryMode = null;
        private String mSubtitlePath = null;
        private ArrayList<Caption> mCaptionList = new ArrayList<>();
        private ContentAttributeType mContentAttribute = ContentAttributeType.CONTENT_UNKNOWN;
        private String mArtist = null;
        private String mAlbumTitle = null;
        private String mGenre = null;
        private Date mDate = null;
        private long mDuration = -1;

        public enum DeliveryMode {
            RELAY("RELAY"),
            REDIRECT("REDIRECT"),
            UNKNOWN("UNKNOWN");

            private final String enumString;

            DeliveryMode(String str) {
                this.enumString = str;
            }

            public String enumToString() {
                return this.enumString;
            }

            public static DeliveryMode stringToEnum(String str) {
                if (str == null) {
                    return UNKNOWN;
                }
                if (str.equals("REDIRECT")) {
                    return REDIRECT;
                }
                if (str.equals("RELAY")) {
                    return RELAY;
                }
                if (str.equals("UNKNOWN")) {
                    return UNKNOWN;
                }
                return UNKNOWN;
            }
        }

        public WebContentBuilder(Uri uri, String str) {
            this.mUri = null;
            this.mMimetype = null;
            this.mUri = uri;
            this.mMimetype = str;
        }

        public WebContentBuilder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public WebContentBuilder setSubtitle(String str) {
            this.mSubtitlePath = str;
            return this;
        }

        public WebContentBuilder setCaptionList(ArrayList<Caption> arrayList) {
            this.mCaptionList = arrayList;
            return this;
        }

        public WebContentBuilder setContentAttribute(ContentAttributeType contentAttributeType) {
            this.mContentAttribute = contentAttributeType;
            return this;
        }

        public WebContentBuilder setDeliveryMode(DeliveryMode deliveryMode) {
            this.mDeliveryMode = deliveryMode;
            return this;
        }

        public Item build() {
            if (this.mUri == null || this.mMimetype == null) {
                DLog.e_api("Item", "build error! mUri == null || mMimetype == null");
                return null;
            }
            if (!checkSubtitlePathValid(this.mSubtitlePath)) {
                this.mSubtitlePath = null;
            }
            if (this.mMimetype.equals(ContentType.VIDEO_UNSPECIFIED)) {
                this.mMimetype = "video/mp4";
            }
            DLog.i_api("Item", "item build mime : " + this.mMimetype + " item build uri: " + this.mUri);
            if (this.mDeliveryMode == null) {
                this.mDeliveryMode = DeliveryMode.UNKNOWN;
            }
            String scheme = this.mUri.getScheme();
            if (scheme == null || scheme.contains("content") || scheme.contains("file")) {
                DLog.e_api("Item", "build error! scheme == null || scheme.contains(content) || scheme.contains(file)");
                return null;
            }
            int ordinal = Item.convertItemTypeFromMimeType(this.mMimetype).ordinal();
            if (ordinal == 1 || ordinal == 2 || ordinal == 3) {
                return new BuilderGeneratedItem(ItemCreator.ConstructorType.WEB_CONTENT, this.mUri, this.mTitle, this.mMimetype, this.mSubtitlePath, this.mCaptionList, this.mContentAttribute, this.mDeliveryMode, this.mArtist, this.mAlbumTitle, this.mGenre, this.mDate, this.mDuration);
            }
            return null;
        }

        public WebContentBuilder setArtist(String str) {
            this.mArtist = str;
            return this;
        }

        public WebContentBuilder setAlbumTitle(String str) {
            this.mAlbumTitle = str;
            return this;
        }

        public WebContentBuilder setGenre(String str) {
            this.mGenre = str;
            return this;
        }

        public WebContentBuilder setDate(Date date) {
            this.mDate = date;
            return this;
        }

        public WebContentBuilder setDuration(long j) {
            this.mDuration = j;
            return this;
        }

        private boolean checkSubtitlePathValid(String str) {
            if (str == null || str.length() == 0) {
                return false;
            }
            if (str.startsWith("file:")) {
                str = str.substring(7);
            }
            return !str.startsWith("/data/data") && new File(str).exists();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaType convertItemTypeFromMimeType(String str) {
        if (str == null) {
            return MediaType.ITEM_UNKNOWN;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        if (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals("video")) {
                return MediaType.ITEM_VIDEO;
            }
            if (nextToken.equals("audio")) {
                return MediaType.ITEM_AUDIO;
            }
            if (nextToken.equals("image")) {
                return MediaType.ITEM_IMAGE;
            }
            if (str.startsWith("application/x-dtcp1")) {
                if (str.contains(BnRConstants.VIDEO_DIR_PATH)) {
                    return MediaType.ITEM_VIDEO;
                }
                if (str.contains("audio/")) {
                    return MediaType.ITEM_AUDIO;
                }
                if (str.contains(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                    return MediaType.ITEM_IMAGE;
                }
            }
            return MediaType.ITEM_UNKNOWN;
        }
        return MediaType.ITEM_UNKNOWN;
    }

    public String toString() {
        return "Title[" + getTitle() + "] Uri[" + getURI() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
