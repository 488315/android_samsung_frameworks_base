package com.samsung.android.server.vibrator;

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
        return "CommonPatternInfo: type: "
                + this.type
                + " index: "
                + this.index
                + " scale: "
                + this.scale
                + " duration: "
                + this.duration
                + " frequency: "
                + this.frequency;
    }
}
