package com.samsung.android.allshare;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

/* loaded from: classes5.dex */
final class ItemImpl extends Item {
  public static final Parcelable.Creator<ItemImpl> CREATOR =
      new Parcelable.Creator<ItemImpl>() { // from class: com.samsung.android.allshare.ItemImpl.1
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
    Bundle bundle = this.mBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
  }

  @Override // com.samsung.android.allshare.Item
  public Uri getURI() {
    Bundle bundle = this.mBundle;
    return (Uri)
        (bundle == null ? null : bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI));
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

  @Override // com.samsung.android.allshare.Item
  public Date getDate() {
    String dateStr;
    Bundle bundle = this.mBundle;
    if (bundle == null
        || (dateStr = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_DATE)) == null
        || dateStr.isEmpty()) {
      return null;
    }
    try {
      String format = getFormatter(dateStr);
      if (format == null) {
        return null;
      }
      SimpleDateFormat dateTime_format = new SimpleDateFormat(format);
      dateTime_format.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
      Date date = dateTime_format.parse(dateStr);
      return date;
    } catch (ParseException p1) {
      DLog.w_api(
          TAG,
          "getDate  ParseException: "
              + this.mBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE),
          p1);
      return null;
    }
  }

  Bundle getBundle() {
    return this.mBundle;
  }

  protected final String getObjectID() {
    String objID;
    Bundle bundle = getBundle();
    if (bundle == null || (objID = bundle.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID)) == null) {
      return "";
    }
    return objID;
  }

  @Override // com.samsung.android.allshare.Item
  public Item.MediaType getType() {
    return Item.MediaType.ITEM_UNKNOWN;
  }

  static Item getItem(Bundle b) {
    String typeStr;
    if (b == null || (typeStr = b.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE)) == null) {
      return null;
    }
    Item.MediaType type = Item.MediaType.stringToEnum(typeStr);
    switch (C48672.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
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
  public Location getLocation() {
    return null;
  }

  @Override // com.samsung.android.allshare.Item
  public String getResolution() {
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
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return Item.ContentBuildType.UNKNOWN;
    }
    String itemConstructor = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
    if (itemConstructor == null || itemConstructor.isEmpty()) {
      return Item.ContentBuildType.UNKNOWN;
    }
    ItemCreator.ConstructorType conType = ItemCreator.ConstructorType.stringToEnum(itemConstructor);
    switch (C48672.f2952xbd24f7e5[conType.ordinal()]) {
    }
    return Item.ContentBuildType.UNKNOWN;
  }

  /* renamed from: com.samsung.android.allshare.ItemImpl$2 */
  static /* synthetic */ class C48672 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

    /* renamed from: $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType */
    static final /* synthetic */ int[] f2952xbd24f7e5;

    static {
      int[] iArr = new int[ItemCreator.ConstructorType.values().length];
      f2952xbd24f7e5 = iArr;
      try {
        iArr[ItemCreator.ConstructorType.LOCAL_CONTENT.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2952xbd24f7e5[ItemCreator.ConstructorType.MEDIA_SERVER.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f2952xbd24f7e5[ItemCreator.ConstructorType.WEB_CONTENT.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2952xbd24f7e5[ItemCreator.ConstructorType.UNKNOWN.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
      int[] iArr2 = new int[Item.MediaType.values().length];
      $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr2;
      try {
        iArr2[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
      } catch (NoSuchFieldError e5) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_IMAGE.ordinal()] =
            2;
      } catch (NoSuchFieldError e6) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_VIDEO.ordinal()] =
            3;
      } catch (NoSuchFieldError e7) {
      }
    }
  }

  @Override // com.samsung.android.allshare.Item
  public Item.WebContentBuilder.DeliveryMode getWebContentDeliveryMode() {
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return Item.WebContentBuilder.DeliveryMode.UNKNOWN;
    }
    String deliveryModeStr = bundle.getString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE);
    if (deliveryModeStr == null || deliveryModeStr.isEmpty()) {
      DLog.w_api(TAG, " getWebContentDeliveryMode() : deliveryModeStr is null or empty! ");
      return Item.WebContentBuilder.DeliveryMode.UNKNOWN;
    }
    Item.WebContentBuilder.DeliveryMode deliveryMode =
        Item.WebContentBuilder.DeliveryMode.stringToEnum(deliveryModeStr);
    return deliveryMode;
  }

  @Override // com.samsung.android.allshare.Item
  @Deprecated
  public Item.WebContentBuilder.PlayMode getWebContentPlayMode() {
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return Item.WebContentBuilder.PlayMode.UNKNOWN;
    }
    String playModeStr = bundle.getString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE);
    if (playModeStr == null || playModeStr.isEmpty()) {
      Item.WebContentBuilder.PlayMode playMode = Item.WebContentBuilder.PlayMode.UNKNOWN;
      return playMode;
    }
    Item.WebContentBuilder.PlayMode playMode2 =
        Item.WebContentBuilder.PlayMode.stringToEnum(playModeStr);
    return playMode2;
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

  @Override // com.samsung.android.allshare.Item
  public ArrayList<Item.Resource> getResourceList() {
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return new ArrayList<>();
    }
    ArrayList<Parcelable> resourceList =
        bundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ITEM_RESOURCE_LIST);
    ArrayList<Item.Resource> result = new ArrayList<>();
    if (resourceList == null) {
      return result;
    }
    Iterator<Parcelable> it = resourceList.iterator();
    while (it.hasNext()) {
      Parcelable parcel = it.next();
      result.add(new ResourceImpl((Bundle) parcel));
    }
    return result;
  }

  final class ResourceImpl extends Item.Resource {
    public final Parcelable.Creator<ResourceImpl> CREATOR;
    private Bundle mBundle;

    ResourceImpl(Bundle bundle) {
      super();
      this.mBundle = null;
      this.CREATOR =
          new Parcelable.Creator<
              ResourceImpl>() { // from class: com.samsung.android.allshare.ItemImpl.ResourceImpl.1
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
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return Item.MediaType.ITEM_UNKNOWN;
      }
      String typeStr = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE);
      if (typeStr == null) {
        return Item.MediaType.ITEM_UNKNOWN;
      }
      return Item.MediaType.stringToEnum(typeStr);
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public Item.SeekMode getSeekMode() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return Item.SeekMode.UNKNOWN;
      }
      String seekModeStr = bundle.getString(AllShareKey.BUNDLE_STRING_RESOURCE_ITEM_SEEKMODE);
      if (seekModeStr == null) {
        return Item.SeekMode.UNKNOWN;
      }
      return Item.SeekMode.stringToEnum(seekModeStr);
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public Uri getURI() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return null;
      }
      return (Uri) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public long getDuration() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return -1L;
      }
      return bundle.getLong(AllShareKey.BUNDLE_LONG_RESOURCE_ITEM_DURATION);
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public String getResolution() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return "";
      }
      return bundle.getString(AllShareKey.BUNDLE_STRING_RESOURCE_ITEM_RESOLUTION);
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public long getFileSize() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return -1L;
      }
      long size = bundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE);
      if (size <= 0) {
        return -1L;
      }
      return size;
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public String getMimetype() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return "";
      }
      return bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item.Resource
    public int getBitrate() {
      Bundle bundle = this.mBundle;
      if (bundle == null) {
        return -1;
      }
      return bundle.getInt(AllShareKey.BUNDLE_STRING_RESOURCE_ITEM_BITRATE);
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
              ResourceImpl>() { // from class: com.samsung.android.allshare.ItemImpl.ResourceImpl.1
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
    Bundle bundle = this.mBundle;
    if (bundle == null) {
      return "";
    }
    String channelNr = bundle.getString(AllShareKey.BUNDLE_INT_ITEM_CHANNELNR);
    return channelNr;
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
