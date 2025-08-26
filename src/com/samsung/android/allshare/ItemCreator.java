package com.samsung.android.allshare;

import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.Date;

/* loaded from: classes6.dex */
class ItemCreator {
    ItemCreator() {
    }

    enum ConstructorType {
        MEDIA_SERVER("MEDIA_SERVER"),
        WEB_CONTENT("WEB_CONTENT"),
        LOCAL_CONTENT("LOCAL_CONTENT"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        ConstructorType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static ConstructorType stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("LOCAL_CONTENT")) {
                return LOCAL_CONTENT;
            }
            if (str.equals("MEDIA_SERVER")) {
                return MEDIA_SERVER;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            if (str.equals("WEB_CONTENT")) {
                return WEB_CONTENT;
            }
            return UNKNOWN;
        }
    }

    static Item fromBundle(Bundle bundle) {
        String string;
        Item.WebContentBuilder.DeliveryMode deliveryModeStringToEnum;
        if (bundle == null || (string = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY)) == null || string.isEmpty()) {
            return null;
        }
        int iOrdinal = ConstructorType.stringToEnum(string).ordinal();
        if (iOrdinal != 1) {
            if (iOrdinal != 2) {
                return null;
            }
            return new Item.LocalContentBuilder(bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH), bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE)).setTitle(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE)).setSubtitle(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH)).build();
        }
        Item.WebContentBuilder duration = new Item.WebContentBuilder((Uri) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI), bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE)).setTitle(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE)).setSubtitle(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH)).setAlbumTitle(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_ALBUM_TITLE)).setArtist(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_ARTIST)).setGenre(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_GENRE)).setDuration(bundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_DURATION));
        long j = bundle.getLong(AllShareKey.BUNDLE_DATE_ITEM_DATE);
        if (j > 0) {
            duration = duration.setDate(new Date(j));
        }
        String string2 = bundle.getString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE);
        if (string2 == null || string2.isEmpty()) {
            deliveryModeStringToEnum = Item.WebContentBuilder.DeliveryMode.UNKNOWN;
        } else {
            deliveryModeStringToEnum = Item.WebContentBuilder.DeliveryMode.stringToEnum(string2);
        }
        return duration.setDeliveryMode(deliveryModeStringToEnum).build();
    }
}
