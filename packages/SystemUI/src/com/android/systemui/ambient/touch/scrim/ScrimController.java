package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;

/* loaded from: classes.dex */
public interface ScrimController {
    void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent);

    default void reset$1() {
    }

    default void show$2() {
    }
}
