package com.samsung.android.knox.custom;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class StatusbarIconItem implements Parcelable {
    public static final Parcelable.Creator<StatusbarIconItem> CREATOR = new Parcelable.Creator<StatusbarIconItem>() { // from class: com.samsung.android.knox.custom.StatusbarIconItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusbarIconItem createFromParcel(Parcel parcel) {
            return new StatusbarIconItem(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusbarIconItem[] newArray(int i) {
            return new StatusbarIconItem[i];
        }
    };
    public static final int STATUSBAR_ICON_BATTERY_BARS = 2;
    public static final int STATUSBAR_ICON_BATTERY_TEXT = 3;
    public static final int STATUSBAR_ICON_CLOCK_TEXT = 1;
    public static final int STATUSBAR_ICON_MOBILE_BARS = 4;
    public static final int STATUSBAR_ICON_SMART_STAY = 6;
    public static final int STATUSBAR_ICON_WIFI_BARS = 5;
    public final String TAG;
    public AttributeColour[] mAttributeColour;
    public final String mAttributeColour_KEY;
    public int mIcon;
    public final String mIcon_KEY;

    public /* synthetic */ StatusbarIconItem(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AttributeColour getAttributeColour(int i) {
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        if (attributeColourArr == null || attributeColourArr.length <= i) {
            return null;
        }
        return attributeColourArr[i];
    }

    public AttributeColour[] getAttributeColourArray() {
        return this.mAttributeColour;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public void setAttributeColour(int i, int i2, int i3) {
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        if (attributeColourArr == null || attributeColourArr.length <= i) {
            return;
        }
        attributeColourArr[i] = new AttributeColour(this, i2, i3);
    }

    public String toString() {
        return "descr:" + describeContents() + " icon:" + this.mIcon + " attributeColour:" + Arrays.toString(this.mAttributeColour);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIcon);
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        int length = attributeColourArr != null ? attributeColourArr.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            for (AttributeColour attributeColour : this.mAttributeColour) {
                parcel.writeInt(attributeColour.getAttribute());
                parcel.writeInt(attributeColour.getColour());
            }
        }
    }

    public StatusbarIconItem(int i, AttributeColour[] attributeColourArr) {
        this.TAG = "StatusbarIconItem";
        this.mIcon_KEY = "ICON";
        this.mAttributeColour_KEY = "ATTRIBUTE_COLOUR";
        this.mIcon = i;
        this.mAttributeColour = attributeColourArr;
    }

    public class AttributeColour {
        public int mAttribute;
        public int mColour;

        public AttributeColour(StatusbarIconItem statusbarIconItem) {
            this.mAttribute = 0;
            this.mColour = 0;
        }

        public int getAttribute() {
            return this.mAttribute;
        }

        public int getColour() {
            return this.mColour;
        }

        public void setAttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }

        public AttributeColour(StatusbarIconItem statusbarIconItem, int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }
    }

    private StatusbarIconItem(Parcel parcel) {
        this.TAG = "StatusbarIconItem";
        this.mIcon_KEY = "ICON";
        this.mAttributeColour_KEY = "ATTRIBUTE_COLOUR";
        this.mIcon = parcel.readInt();
        int i = parcel.readInt();
        this.mAttributeColour = null;
        if (i > 0) {
            this.mAttributeColour = new AttributeColour[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.mAttributeColour[i2] = new AttributeColour(this, parcel.readInt(), parcel.readInt());
            }
        }
    }
}
