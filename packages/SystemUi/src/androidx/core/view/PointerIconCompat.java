package androidx.core.view;

import android.content.Context;
import android.view.PointerIcon;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PointerIconCompat {
    public final PointerIcon mPointerIcon;

    private PointerIconCompat(PointerIcon pointerIcon) {
        this.mPointerIcon = pointerIcon;
    }

    public static PointerIconCompat getSystemIcon(Context context) {
        return new PointerIconCompat(PointerIcon.getSystemIcon(context, 1002));
    }
}
