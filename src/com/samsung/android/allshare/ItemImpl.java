package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.ItemCreator;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;

/* loaded from: classes6.dex */
final class ItemImpl extends Item {
    public static final Parcelable.Creator<ItemImpl> CREATOR = new Parcelable.Creator<ItemImpl>() { // from class: com.samsung.android.allshare.ItemImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ItemImpl createFromParcel(Parcel parcel) {
            return new ItemImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ItemImpl[] newArray(int i) {
            return new ItemImpl[i];
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
    private static final String DATETIME_PATTERN_WITH_MS_WITH_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
    private static final String DATETIME_PATTERN_WITH_MS_WITH_OFFSET_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DATETIME_PATTERN_WITH_OFFSET = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
    private static final String DATETIME_PATTERN_WITH_OFFSET_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATE_FORMAT = "CCYY-MM-DD";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TAG = "ItemImpl";
    private Bundle mBundle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
    public int getBitrate() {
        return -1;
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

    protected ItemImpl(Bundle bundle) {
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

    private static String getFormatter(String str) {
        int length = str.length();
        if (length == 10) {
            return DATE_PATTERN;
        }
        if (length == 19) {
            return DATETIME_PATTERN;
        }
        if (length == 25) {
            return DATETIME_PATTERN_WITH_OFFSET;
        }
        if (length == 20) {
            return DATETIME_PATTERN_WITH_OFFSET_Z;
        }
        if (length == 23) {
            return DATETIME_PATTERN_WITH_MS;
        }
        if (length == 29) {
            return DATETIME_PATTERN_WITH_MS_WITH_OFFSET;
        }
        if (length == 24) {
            return DATETIME_PATTERN_WITH_MS_WITH_OFFSET_Z;
        }
        return null;
    }

    Bundle getBundle() {
        return this.mBundle;
    }

    protected final String getObjectID() {
        String string;
        Bundle bundle = getBundle();
        return (bundle == null || (string = bundle.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID)) == null) ? "" : string;
    }

    static Item getItem(Bundle bundle) {
        String string;
        if (bundle == null || (string = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE)) == null) {
            return null;
        }
        int i = AnonymousClass2.$SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.stringToEnum(string).ordinal()];
        if (i == 1) {
            return new AudioItemImpl(bundle);
        }
        if (i == 2) {
            return new ImageItemImpl(bundle);
        }
        if (i != 3) {
            return null;
        }
        return new VideoItemImpl(bundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(getBundle(), 0);
    }

    private void readFromParcel(Parcel parcel) {
        this.mBundle = (Bundle) parcel.readParcelable(Bundle.class.getClassLoader());
    }

    private ItemImpl(Parcel parcel) {
        this.mBundle = null;
        readFromParcel(parcel);
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
    public String getExtension() {
        Bundle bundle = this.mBundle;
        return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_EXTENSION);
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return Item.ContentBuildType.UNKNOWN;
        }
        String string = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
        if (string == null || string.isEmpty()) {
            return Item.ContentBuildType.UNKNOWN;
        }
        int i = AnonymousClass2.$SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.stringToEnum(string).ordinal()];
        if (i == 1) {
            return Item.ContentBuildType.LOCAL;
        }
        if (i == 2) {
            return Item.ContentBuildType.PROVIDER;
        }
        if (i == 3) {
            return Item.ContentBuildType.WEB;
        }
        if (i == 4) {
            return Item.ContentBuildType.UNKNOWN;
        }
        return Item.ContentBuildType.UNKNOWN;
    }

    /* renamed from: com.samsung.android.allshare.ItemImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;
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
            int[] iArr2 = new int[Item.MediaType.values().length];
            $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr2;
            try {
                iArr2[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
        }
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
    public String getChannelNr() {
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return "";
        }
        return bundle.getString(AllShareKey.BUNDLE_INT_ITEM_CHANNELNR);
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
