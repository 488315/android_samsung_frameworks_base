package com.airbnb.lottie.model;

/* loaded from: classes.dex */
public final class Marker {
    public final float durationFrames;
    private final String name;
    public final float startFrame;

    public Marker(String str, float f, float f2) {
        this.name = str;
        this.durationFrames = f2;
        this.startFrame = f;
    }

    public final boolean matchesName(String str) {
        String str2 = this.name;
        if (str2.equalsIgnoreCase(str)) {
            return true;
        }
        return str2.endsWith("\r") && str2.substring(0, str2.length() - 1).equalsIgnoreCase(str);
    }
}
