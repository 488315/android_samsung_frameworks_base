package com.android.systemui.dreams.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ScrimController {
    void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent);

    default void reset() {
    }

    default void show() {
    }
}
