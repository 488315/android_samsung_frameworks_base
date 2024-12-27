package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;

import com.sec.android.allshare.iface.message.AllShareKey;

import java.util.Date;

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
                || (itemConstructor =
                                itemBundle.getString(
                                        AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY))
                        == null
                || itemConstructor.isEmpty()) {
            return null;
        }
        ConstructorType conType = ConstructorType.stringToEnum(itemConstructor);
        switch (conType.ordinal()) {
            case 1:
                Uri uri = (Uri) itemBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
                String mimeType = itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
                Item.WebContentBuilder builder =
                        new Item.WebContentBuilder(uri, mimeType)
                                .setTitle(
                                        itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE))
                                .setSubtitle(
                                        itemBundle.getString(
                                                AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH))
                                .setAlbumTitle(
                                        itemBundle.getString(
                                                AllShareKey.BUNDLE_STRING_ITEM_ALBUM_TITLE))
                                .setArtist(
                                        itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_ARTIST))
                                .setGenre(
                                        itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_GENRE))
                                .setDuration(
                                        itemBundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_DURATION));
                long dateTime = itemBundle.getLong(AllShareKey.BUNDLE_DATE_ITEM_DATE);
                if (dateTime > 0) {
                    builder = builder.setDate(new Date(dateTime));
                }
                String playModeStr = itemBundle.getString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE);
                if (playModeStr == null || playModeStr.isEmpty()) {
                    deliverymode = Item.WebContentBuilder.DeliveryMode.UNKNOWN;
                } else {
                    deliverymode = Item.WebContentBuilder.DeliveryMode.stringToEnum(playModeStr);
                }
                Item result = builder.setDeliveryMode(deliverymode).build();
                return result;
            case 2:
                String path = itemBundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                String mimeType2 = itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
                Item result2 =
                        new Item.LocalContentBuilder(path, mimeType2)
                                .setTitle(
                                        itemBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE))
                                .setSubtitle(
                                        itemBundle.getString(
                                                AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH))
                                .build();
                return result2;
            default:
                return null;
        }
    }
}
