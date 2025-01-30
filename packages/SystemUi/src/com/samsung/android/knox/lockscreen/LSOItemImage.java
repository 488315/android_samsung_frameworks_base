package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import android.widget.ImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOItemImage extends LSOItemData {
    public static final int LSO_FIELD_IMAGE_PATH = 128;
    public static final int LSO_FIELD_IMAGE_URL = 256;
    public static final int LSO_FIELD_SCALE_TYPE = 512;
    public String imagePath;
    public long pollingInterval;
    public int scaleType;
    public String url;

    public LSOItemImage() {
        super((byte) 3);
        this.scaleType = -1;
    }

    public final String getImagePath() {
        return this.imagePath;
    }

    public final long getPollingInterval() {
        return this.pollingInterval;
    }

    public final ImageView.ScaleType getScaleType() {
        ImageView.ScaleType[] values = ImageView.ScaleType.values();
        int i = this.scaleType;
        return (i < 0 || i >= values.length) ? ImageView.ScaleType.CENTER : values[i];
    }

    public final int getScaleTypeAsInteger() {
        return this.scaleType;
    }

    public final String getUrl() {
        return this.url;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.imagePath = readStringFromParcel(parcel, 128);
        this.scaleType = readIntFromParcel(parcel, 512, -1);
        if (isFieldUpdated(256)) {
            this.url = parcel.readString();
            this.pollingInterval = parcel.readLong();
        }
    }

    public final void setImagePath(String str) {
        this.imagePath = str;
        updateFieldFlag(128);
    }

    public final void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType.ordinal();
        updateFieldFlag(512);
    }

    public final void setURL(String str, long j) {
        if (str == null || str.length() == 0) {
            this.url = null;
            this.pollingInterval = 0L;
        } else {
            this.url = str;
            this.pollingInterval = j;
        }
        updateFieldFlag(256);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final String toString() {
        return toString(toString("ImageView " + super.toString(), 128, "ImagePath:" + this.imagePath), 256, "ImageUrl:" + this.url + " PollingInterval:" + this.pollingInterval);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        writeToParcel(parcel, 128, this.imagePath);
        writeToParcel(parcel, 512, this.scaleType);
        if (isFieldUpdated(256)) {
            parcel.writeString(this.url);
            parcel.writeLong(this.pollingInterval);
        }
    }

    public LSOItemImage(Parcel parcel) {
        super((byte) 3, parcel);
    }

    public final void setScaleType(int i) {
        this.scaleType = i;
        updateFieldFlag(512);
    }

    public LSOItemImage(String str) {
        super((byte) 3);
        setImagePath(str);
    }
}
