package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.Date;

/* loaded from: classes5.dex */
class ItemCreator {
  ItemCreator() {}

  enum ConstructorType {
    MEDIA_SERVER("MEDIA_SERVER"),
    WEB_CONTENT("WEB_CONTENT"),
    LOCAL_CONTENT("LOCAL_CONTENT"),
    UNKNOWN("UNKNOWN");

    private final String enumString;

    ConstructorType(String enumStr) {
      this.enumString = enumStr;
    }

    public String enumToString() {
      return this.enumString;
    }

    public static ConstructorType stringToEnum(String enumStr) {
      if (enumStr == null) {
        return UNKNOWN;
      }
      if (enumStr.equals("LOCAL_CONTENT")) {
        return LOCAL_CONTENT;
      }
      if (enumStr.equals("MEDIA_SERVER")) {
        return MEDIA_SERVER;
      }
      if (enumStr.equals("UNKNOWN")) {
        return UNKNOWN;
      }
      if (enumStr.equals("WEB_CONTENT")) {
        return WEB_CONTENT;
      }
      return UNKNOWN;
    }
  }

  static Item fromBundle(Bundle itemBundle) {
    String itemConstructor;
    Item.WebContentBuilder.DeliveryMode deliverymode;
    if (itemBundle == null
        || (itemConstructor = itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY))
            == null
        || itemConstructor.isEmpty()) {
      return null;
    }
    ConstructorType conType = ConstructorType.stringToEnum(itemConstructor);
    switch (C48651.f2951xbd24f7e5[conType.ordinal()]) {
      case 1:
        Item result = createMediaServerItem(itemBundle);
        return result;
      case 2:
        Uri uri = (Uri) itemBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
        String mimeType = itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        Item.WebContentBuilder builder =
            new Item.WebContentBuilder(uri, mimeType)
                .setTitle(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE))
                .setSubtitle(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH))
                .setAlbumTitle(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_ALBUM_TITLE))
                .setArtist(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_ARTIST))
                .setGenre(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_GENRE))
                .setDuration(itemBundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_DURATION));
        long dateTime = itemBundle.getLong(AllShareKey.BUNDLE_DATE_ITEM_DATE);
        if (dateTime > 0) {
          builder = builder.setDate(new Date(dateTime));
        }
        String playModeStr = itemBundle.getString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE);
        Item.WebContentBuilder.DeliveryMode deliveryMode =
            Item.WebContentBuilder.DeliveryMode.UNKNOWN;
        if (playModeStr == null || playModeStr.isEmpty()) {
          deliverymode = Item.WebContentBuilder.DeliveryMode.UNKNOWN;
        } else {
          deliverymode = Item.WebContentBuilder.DeliveryMode.stringToEnum(playModeStr);
        }
        Item result2 = builder.setDeliveryMode(deliverymode).build();
        return result2;
      case 3:
        String path = itemBundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
        String mimeType2 = itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        Item result3 =
            new Item.LocalContentBuilder(path, mimeType2)
                .setTitle(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE))
                .setSubtitle(itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH))
                .build();
        return result3;
      default:
        return null;
    }
  }

  private static Item createMediaServerItem(Bundle itemBundle) {
    String itemType = itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE);
    Item.MediaType mediaType = Item.MediaType.stringToEnum(itemType);
    switch (C48651.$SwitchMap$com$samsung$android$allshare$Item$MediaType[mediaType.ordinal()]) {
      case 1:
        Item result = new AudioItemImpl(itemBundle);
        return result;
      case 2:
        Item result2 = new ImageItemImpl(itemBundle);
        return result2;
      case 3:
        Item result3 = new VideoItemImpl(itemBundle);
        return result3;
      case 4:
        Item result4 = new FolderItemImpl(itemBundle);
        return result4;
      case 5:
        return null;
      default:
        return null;
    }
  }

  /* renamed from: com.samsung.android.allshare.ItemCreator$1 */
  static /* synthetic */ class C48651 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

    /* renamed from: $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType */
    static final /* synthetic */ int[] f2951xbd24f7e5;

    static {
      int[] iArr = new int[Item.MediaType.values().length];
      $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr;
      try {
        iArr[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_IMAGE.ordinal()] =
            2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_VIDEO.ordinal()] =
            3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_FOLDER.ordinal()] =
            4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_UNKNOWN.ordinal()] =
            5;
      } catch (NoSuchFieldError e5) {
      }
      int[] iArr2 = new int[ConstructorType.values().length];
      f2951xbd24f7e5 = iArr2;
      try {
        iArr2[ConstructorType.MEDIA_SERVER.ordinal()] = 1;
      } catch (NoSuchFieldError e6) {
      }
      try {
        f2951xbd24f7e5[ConstructorType.WEB_CONTENT.ordinal()] = 2;
      } catch (NoSuchFieldError e7) {
      }
      try {
        f2951xbd24f7e5[ConstructorType.LOCAL_CONTENT.ordinal()] = 3;
      } catch (NoSuchFieldError e8) {
      }
    }
  }
}
