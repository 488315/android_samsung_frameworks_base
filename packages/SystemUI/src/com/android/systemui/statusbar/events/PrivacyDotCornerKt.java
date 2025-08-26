package com.android.systemui.statusbar.events;

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
