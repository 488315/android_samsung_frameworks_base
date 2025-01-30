package com.samsung.android.knox.lockscreen;

import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOItemText extends LSOItemData {
    public static final float DEFAULT_TEXT_SIZE = LSOTextSize.NORMAL.nativeVal;
    public static final int LSO_FIELD_TEXT = 128;
    public static final int LSO_FIELD_TEXT_COLOR = 256;
    public static final int LSO_FIELD_TEXT_SIZE = 512;
    public static final int LSO_FIELD_TEXT_STYLE = 1024;
    public String text;
    public int text_color;
    public float text_size;
    public int text_style;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final String getText() {
        return this.text;
    }

    public final int getTextColor() {
        return this.text_color;
    }

    public final LSOTextSize getTextSize() {
        return getTextSize(this.text_size);
    }

    public final float getTextSizeAsFloat() {
        return this.text_size;
    }

    public final int getTextStyle() {
        return this.text_style;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.text = readStringFromParcel(parcel, 128);
        this.text_color = readIntFromParcel(parcel, 256, -1);
        this.text_style = readIntFromParcel(parcel, 1024, -1);
        this.text_size = readFloatFromParcel(parcel, 512, DEFAULT_TEXT_SIZE);
    }

    public final void setText(String str) {
        this.text = str;
        updateFieldFlag(128);
    }

    public final void setTextColor(int i) {
        this.text_color = i;
        updateFieldFlag(256);
    }

    public final void setTextSize(float f) {
        setTextSize(getTextSize(f));
    }

    public final void setTextStyle(int i) {
        this.text_style = i;
        updateFieldFlag(1024);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final String toString() {
        return toString(toString(toString(toString("TextView " + super.toString(), 128, "Text:" + this.text), 256, "Text_Color:" + this.text_color), 1024, "Text_Style:" + this.text_style), 512, "Text_Size:" + this.text_size);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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

    public final void setTextSize(LSOTextSize lSOTextSize) {
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
