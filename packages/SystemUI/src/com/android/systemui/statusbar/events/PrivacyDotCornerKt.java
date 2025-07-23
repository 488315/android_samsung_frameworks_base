package com.android.systemui.statusbar.events;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PrivacyDotCornerKt {
    public static final PrivacyDotCorner rotatedCorner(PrivacyDotCorner privacyDotCorner, int i) {
        int index = privacyDotCorner.getIndex() - i;
        if (index < 0) {
            index += 4;
        }
        return (PrivacyDotCorner) PrivacyDotCorner.$ENTRIES.get(index);
    }
}
