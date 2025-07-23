package com.samsung.android.media;

/* loaded from: classes6.dex */
public enum SemQuramDngDateTimeFormat {
    QuramDngDateTimeFormatUnknown(0),
    QuramDngDateTimeFormatExif(1),
    QuramDngDateTimeFormatUnixLittleEndian(2),
    QuramDngDateTimeFormatUnixBigEndian(3);

    public int date;

    SemQuramDngDateTimeFormat(int i) {
        this.date = i;
    }

    public int getData() {
        return this.date;
    }
}
