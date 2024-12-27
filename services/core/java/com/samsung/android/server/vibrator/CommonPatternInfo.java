package com.samsung.android.server.vibrator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CommonPatternInfo {
    public final int duration;
    public final int frequency;
    public final int index;
    public final int scale;
    public final int type;

    public CommonPatternInfo(int i, int i2, int i3, int i4, int i5) {
        this.type = i;
        this.index = i2;
        this.scale = i3;
        this.duration = i4;
        this.frequency = i5;
    }

    public final String toString() {
        return "CommonPatternInfo: type: " + this.type + " index: " + this.index + " scale: " + this.scale + " duration: " + this.duration + " frequency: " + this.frequency;
    }
}
