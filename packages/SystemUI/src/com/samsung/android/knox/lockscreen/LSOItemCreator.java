package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LSOItemCreator {
    public static final byte LSO_ITEM_TYPE_CONTAINER = 4;
    public static final byte LSO_ITEM_TYPE_IMAGE = 3;
    public static final byte LSO_ITEM_TYPE_NONE = 0;
    public static final byte LSO_ITEM_TYPE_SPACE = 1;
    public static final byte LSO_ITEM_TYPE_TEXT = 2;
    public static final byte LSO_ITEM_TYPE_WIDGET = 5;
    public static final String TAG = "LSO_LSOItemCreator";

    public static LSOItemData createItem(byte b) {
        if (b == 1) {
            return new LSOItemSpace();
        }
        if (b == 2) {
            return new LSOItemText();
        }
        if (b == 3) {
            return new LSOItemImage();
        }
        if (b == 4) {
            return new LSOItemContainer();
        }
        if (b == 5) {
            return new LSOItemWidget();
        }
        ClockEventController$$ExternalSyntheticOutline0.m(b, "Unknown ItemType: ", TAG);
        return null;
    }

    public static LSOItemData createItem(byte b, Parcel parcel) {
        if (b == 1) {
            return new LSOItemSpace(parcel);
        }
        if (b == 2) {
            return new LSOItemText(parcel);
        }
        if (b == 3) {
            return new LSOItemImage(parcel);
        }
        if (b == 4) {
            return new LSOItemContainer(parcel);
        }
        if (b != 5) {
            ClockEventController$$ExternalSyntheticOutline0.m(b, "Unknown ItemType: ", TAG);
            return null;
        }
        return new LSOItemWidget(parcel);
    }
}
