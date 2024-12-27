package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;

public interface ScrimController {
    void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent);

    default void reset$1() {
    }

    default void show() {
    }
}
