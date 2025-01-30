package com.samsung.android.knox.custom;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StatusbarIconItem implements Parcelable {
    public static final Parcelable.Creator<StatusbarIconItem> CREATOR = new Parcelable.Creator<StatusbarIconItem>() { // from class: com.samsung.android.knox.custom.StatusbarIconItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final StatusbarIconItem[] newArray(int i) {
            return new StatusbarIconItem[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final StatusbarIconItem createFromParcel(Parcel parcel) {
            return new StatusbarIconItem(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final StatusbarIconItem[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final AttributeColour getAttributeColour(int i) {
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        if (attributeColourArr == null || attributeColourArr.length <= i) {
            return null;
        }
        return attributeColourArr[i];
    }

    public final AttributeColour[] getAttributeColourArray() {
        return this.mAttributeColour;
    }

    public final int getIcon() {
        return this.mIcon;
    }

    public final void setAttributeColour(int i, int i2, int i3) {
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        if (attributeColourArr == null || attributeColourArr.length <= i) {
            return;
        }
        attributeColourArr[i] = new AttributeColour(i2, i3);
    }

    public final String toString() {
        return "descr:0 icon:" + this.mIcon + " attributeColour:" + Arrays.toString(this.mAttributeColour);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIcon);
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        int length = attributeColourArr != null ? attributeColourArr.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            for (AttributeColour attributeColour : this.mAttributeColour) {
                parcel.writeInt(attributeColour.mAttribute);
                parcel.writeInt(attributeColour.mColour);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class AttributeColour {
        public int mAttribute;
        public int mColour;

        public AttributeColour() {
            this.mAttribute = 0;
            this.mColour = 0;
        }

        public final int getAttribute() {
            return this.mAttribute;
        }

        public final int getColour() {
            return this.mColour;
        }

        public final void setAttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }

        public AttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }
    }

    private StatusbarIconItem(Parcel parcel) {
        this.TAG = "StatusbarIconItem";
        this.mIcon_KEY = "ICON";
        this.mAttributeColour_KEY = "ATTRIBUTE_COLOUR";
        this.mIcon = parcel.readInt();
        int readInt = parcel.readInt();
        this.mAttributeColour = null;
        if (readInt > 0) {
            this.mAttributeColour = new AttributeColour[readInt];
            for (int i = 0; i < readInt; i++) {
                this.mAttributeColour[i] = new AttributeColour(parcel.readInt(), parcel.readInt());
            }
        }
    }
}
