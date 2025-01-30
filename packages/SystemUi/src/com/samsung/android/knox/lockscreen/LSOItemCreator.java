package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
        NestedScrollView$$ExternalSyntheticOutline0.m34m("Unknown ItemType: ", b, TAG);
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
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Unknown ItemType: ", b, TAG);
            return null;
        }
        return new LSOItemWidget(parcel);
    }
}
