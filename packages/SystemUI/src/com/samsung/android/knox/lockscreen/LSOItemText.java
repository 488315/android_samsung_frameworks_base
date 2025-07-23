package com.samsung.android.knox.lockscreen;

import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LSOItemText extends LSOItemData {
    public static final float DEFAULT_TEXT_SIZE = LSOTextSize.NORMAL.nativeVal;
    public static final int LSO_FIELD_TEXT = 128;
    public static final int LSO_FIELD_TEXT_COLOR = 256;
    public static final int LSO_FIELD_TEXT_SIZE = 512;
    public static final int LSO_FIELD_TEXT_STYLE = 1024;
    public String text;
    public int text_color;
    public float text_size;
    public int text_style;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum LSOTextSize {
        TINY(0.85f),
        SMALL(0.93f),
        NORMAL(1.0f),
        LARGE(1.3f),
        HUGE(1.8f);

        public final float nativeVal;

        LSOTextSize(float f) {
            this.nativeVal = f;
        }
    }

    public LSOItemText() {
        super((byte) 2);
        this.text_color = -1;
        this.text_style = -1;
        this.text_size = DEFAULT_TEXT_SIZE;
    }

    public String getText() {
        return this.text;
    }

    public int getTextColor() {
        return this.text_color;
    }

    public LSOTextSize getTextSize() {
        return getTextSize(this.text_size);
    }

    public float getTextSizeAsFloat() {
        return this.text_size;
    }

    public int getTextStyle() {
        return this.text_style;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.text = readStringFromParcel(parcel, 128);
        this.text_color = readIntFromParcel(parcel, 256, -1);
        this.text_style = readIntFromParcel(parcel, 1024, -1);
        this.text_size = readFloatFromParcel(parcel, 512, DEFAULT_TEXT_SIZE);
    }

    public void setText(String str) {
        this.text = str;
        updateFieldFlag(128);
    }

    public void setTextColor(int i) {
        this.text_color = i;
        updateFieldFlag(256);
    }

    public void setTextSize(float f) {
        setTextSize(getTextSize(f));
    }

    public void setTextStyle(int i) {
        this.text_style = i;
        updateFieldFlag(1024);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public String toString() {
        return toString(toString(toString(toString("TextView " + super.toString(), 128, "Text:" + this.text), 256, "Text_Color:" + this.text_color), 1024, "Text_Style:" + this.text_style), 512, "Text_Size:" + this.text_size);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        writeToParcel(parcel, 128, this.text);
        writeToParcel(parcel, 256, this.text_color);
        writeToParcel(parcel, 1024, this.text_style);
        writeToParcel(parcel, 512, this.text_size);
    }

    public final LSOTextSize getTextSize(float f) {
        LSOTextSize lSOTextSize = LSOTextSize.HUGE;
        if (f >= lSOTextSize.nativeVal) {
            return lSOTextSize;
        }
        LSOTextSize lSOTextSize2 = LSOTextSize.LARGE;
        if (f >= lSOTextSize2.nativeVal) {
            return lSOTextSize2;
        }
        LSOTextSize lSOTextSize3 = LSOTextSize.NORMAL;
        if (f >= lSOTextSize3.nativeVal) {
            return lSOTextSize3;
        }
        LSOTextSize lSOTextSize4 = LSOTextSize.SMALL;
        return f >= lSOTextSize4.nativeVal ? lSOTextSize4 : LSOTextSize.TINY;
    }

    public void setTextSize(LSOTextSize lSOTextSize) {
        float f = lSOTextSize.nativeVal;
        if (f == DEFAULT_TEXT_SIZE) {
            removeFieldFlag(512);
        } else {
            this.text_size = f;
            updateFieldFlag(512);
        }
    }

    public LSOItemText(Parcel parcel) {
        super((byte) 2, parcel);
    }

    public LSOItemText(String str) {
        super((byte) 2);
        setText(str);
    }
}
