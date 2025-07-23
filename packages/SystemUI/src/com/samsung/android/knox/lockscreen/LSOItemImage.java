package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import android.widget.ImageView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LSOItemImage extends LSOItemData {
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

    public String getImagePath() {
        return this.imagePath;
    }

    public long getPollingInterval() {
        return this.pollingInterval;
    }

    public ImageView.ScaleType getScaleType() {
        ImageView.ScaleType[] values = ImageView.ScaleType.values();
        int i = this.scaleType;
        return (i < 0 || i >= values.length) ? ImageView.ScaleType.CENTER : values[i];
    }

    public int getScaleTypeAsInteger() {
        return this.scaleType;
    }

    public String getUrl() {
        return this.url;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.imagePath = readStringFromParcel(parcel, 128);
        this.scaleType = readIntFromParcel(parcel, 512, -1);
        if (isFieldUpdated(256)) {
            this.url = parcel.readString();
            this.pollingInterval = parcel.readLong();
        }
    }

    public void setImagePath(String str) {
        this.imagePath = str;
        updateFieldFlag(128);
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType.ordinal();
        updateFieldFlag(512);
    }

    public void setURL(String str, long j) {
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
    public String toString() {
        return toString(toString("ImageView " + super.toString(), 128, "ImagePath:" + this.imagePath), 256, "ImageUrl:" + this.url + " PollingInterval:" + this.pollingInterval);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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

    public void setScaleType(int i) {
        this.scaleType = i;
        updateFieldFlag(512);
    }

    public LSOItemImage(String str) {
        super((byte) 3);
        setImagePath(str);
    }
}
