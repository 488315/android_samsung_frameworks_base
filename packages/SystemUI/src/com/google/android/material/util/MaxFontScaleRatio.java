package com.google.android.material.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum MaxFontScaleRatio {
    SMALL(1.0f),
    MEDIUM(1.15f),
    LARGE(1.3f),
    /* JADX INFO: Fake field, exist only in values array */
    EXTRA_LARGE(1.5f),
    /* JADX INFO: Fake field, exist only in values array */
    HUGE(1.7f),
    /* JADX INFO: Fake field, exist only in values array */
    EXTRA_HUGE(2.0f);

    private final float ratio;

    MaxFontScaleRatio(float f) {
        this.ratio = f;
    }

    public final float getRatio() {
        return this.ratio;
    }
}
